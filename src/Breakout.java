/**
 *  
 */

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.KeyEvent;


public class Breakout extends GraphicsProgram {

    // Screen
    private final int SCREEN_WIDTH          = 416;
    private final int SCREEN_HEIGHT         = 716;

    //Border
    private final int BORDER_WIDTH = 400;
    private final int BORDER_HEIGHT = 600;
    private final int BORDER_OFFSET_NORTH = 86; //From screen top to border top.
    private final int BORDER_OFFSET = 8;
    private final GRect border = new GRect(BORDER_WIDTH, BORDER_HEIGHT);


    private final int PADDLE_WIDTH          = 60;
    private final int PADDLE_HEIGHT         = 10;
    private final double INITIAL_PADDLE_X = (SCREEN_WIDTH - PADDLE_WIDTH) / 2;
    private final double INITIAL_PADDLE_Y = (SCREEN_HEIGHT - PADDLE_WIDTH)* 0.9;
    private final GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
    private final double PADDLE_SPEED_FACTOR = 0.1;


    private final int BALL_RADIUS           = 10;
    private final GOval ball = new GOval(BALL_RADIUS, BALL_RADIUS);


    private final int BRICK_WIDTH           = 36;
    private final int BRICK_HEIGHT          = 15;
    private final int BRICK_SPACING         = 4;
    private final int BRICKS_IN_ROW         = 10;
    private final int BRICKS_IN_COL         = 10;
    private final int BRICK_OFFSET_X        = 2;
    private final int BRICK_OFFSET_Y        = 60;

    private boolean gameover = false;
    private double velocity_x, velocity_y;
    private int lives = 3;


    /**
     * Set the size of the screen and set the background color as GRAY.
     */
    private void initializeScreen(){
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        setBackground(Color.GRAY);
    }

    /**
     * Initialize the "BREAKOUT" title at the top of the screen.
     */
    private void initializeHeader(){
        double x, y;
        GLabel header = new GLabel("BREAKOUT");
        header.setFont("Helvetica-20");
        x = (SCREEN_WIDTH - header.getWidth()) / 2;
        y = header.getHeight();
        header.setLocation(x, y);
        add(header);
    }


    /**
     * Create the border in which the actual game is played. Color it
     * LIGHT_GRAY.
     */
    private void initializeBorder(){
        border.setLocation(BORDER_OFFSET, BORDER_OFFSET_NORTH);
        border.setFillColor(Color.LIGHT_GRAY);
        border.setFilled(true);
        add(border);
    }

    /**
     * Create the paddle and color is BLACK.
     */
    private void initializePaddle(){
        paddle.setLocation(INITIAL_PADDLE_X, INITIAL_PADDLE_Y);
        paddle.setFillColor(Color.BLACK);
        paddle.setFilled(true);
        add(paddle);
    }

    /**
     * Create the ball and color is WHITE.
     */
    private void initializeBall(){
        int     x = (SCREEN_WIDTH - BALL_RADIUS)/2,
                y = (SCREEN_HEIGHT - BALL_RADIUS)/2 + BORDER_OFFSET_NORTH;
        ball.setLocation(x, y);
        ball.setFillColor(Color.WHITE);
        ball.setFilled(true);
        assignInitialVelocities();
        add(ball);
    }

    /**
     * Helper method used in @@{initializeBrick()} to determine the color of
     * the brick based on which row it is in.
     * @param currentBrick is the current brick being created in the for-loop
     *                     in initializeBrick().
     * @param row is the current row that the for loop is on. There are 10 rows
     *            and 10 columns of bricks.
     */
    private void determineBrickColor(GRect currentBrick, int row){
        switch(row){
            case 0:
            case 1:
                currentBrick.setFillColor(Color.RED);
                break;
            case 2:
            case 3:
                currentBrick.setFillColor(Color.ORANGE);
                break;
            case 4:
            case 5:
                currentBrick.setFillColor(Color.YELLOW);
                break;
            case 6:
            case 7:
                currentBrick.setFillColor(Color.GREEN);
                break;
            case 8:
            case 9:
                currentBrick.setFillColor(Color.CYAN);
                break;
        }
        currentBrick.setFilled(true);
    }

    /**
     * Create and fill the bricks with color.
     */
    private void initializeBricks(){
        int xCoord = 0, yCoord = 0;

        for (int row = 0; row < BRICKS_IN_ROW; row++) {
            if (row == 0) {
                yCoord = BORDER_OFFSET_NORTH + BRICK_OFFSET_Y;
            } else {
                yCoord = BRICK_OFFSET_Y + BORDER_OFFSET_NORTH +
                        (row * ((BRICK_SPACING / 2) + BRICK_HEIGHT));
            }
            for (int col = 0; col < BRICKS_IN_COL; col++) {
                if(col == 0){
                    xCoord = BORDER_OFFSET + BRICK_OFFSET_X;
                }else {
                    xCoord = BORDER_OFFSET + BRICK_OFFSET_X +
                            (col * (BRICK_SPACING + BRICK_WIDTH));
                }
                GRect currentBrick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
                determineBrickColor(currentBrick, row);
                add(currentBrick, xCoord, yCoord);
            }
        }
    }


    /**
     * Aggregate all initialization methods into one place.
     */
    private void initialize(){
        initializeScreen();
        initializeBorder();
        initializePaddle();
        initializeBall();
        initializeBricks();
        initializeHeader();
    }

    /**
     * Moves the ball according to it's velocity_x and velocity_y factors.
     */
    private void moveBall(){
        ball.setLocation(ball.getX() + velocity_x,ball.getY() + velocity_y);
    }

    /**
     * Randomly assign initial @velocity_x and @velocity_y factors.
     */
    private void assignInitialVelocities(){
        RandomGenerator rgen = new RandomGenerator();
        do {
            velocity_x = rgen.nextDouble(-5, 5);
            velocity_y = rgen.nextDouble(-5, 5);
        }while((velocity_x < 3) && (velocity_y < 3));
    }

    /**
     * Determine if the ball is colliding with the paddle.
     * @return returns true if the ball is colliding with the paddle.
     */
    private boolean isCollidingWithPaddle(){
        double ballX = ball.getX() + BALL_RADIUS;
        double ballY = ball.getY() + BALL_RADIUS;
        return (getElementAt(ballX, ballY) == paddle);
    }

    /**
     * Determine if the ball is colliding with the left or right wall.
     * @return Will return true if the ball is colliding with the left or right
     *         wall.
     */
    private boolean isCollidingWithHorizontalWall(){
        double  leftSideOfBall = ball.getX(),
                rightSideOfBall = ball.getX() + (2 * BALL_RADIUS);
        int correctionFactor = 10, rightWall, leftWall;
        leftWall = BORDER_OFFSET;
        rightWall = (BORDER_OFFSET + BORDER_WIDTH + correctionFactor);
        return (leftSideOfBall <= leftWall || rightSideOfBall >= rightWall);
    }

    /**
     * Determine if ball is colliding with the top or bottom wall.
     * @return Will return true if the ball is colliding with the top or
     *         bottom wall.
     */
    private boolean isCollidingWithVerticalWall(){
        double  topOfBall = ball.getY(),
                bottomOfBall = ball.getY() + (2 * BALL_RADIUS);
        int correctionFactor = 10, topWall, bottomWall;
        topWall = BORDER_OFFSET_NORTH;
        bottomWall = BORDER_OFFSET_NORTH + BORDER_HEIGHT + correctionFactor;

        return topOfBall <= topWall || bottomOfBall >= bottomWall;
    }

    /**
     * Determine if the ball is colliding with a brick.
     * @return Will return true if the ball is not colliding with the border and
     *         the ball is not colliding with the paddle. AKA the ball is
     *         colliding with a brick.
     */
    private boolean isCollidingWithBrick(){
        GObject collidingObject;
        collidingObject = getElementAt(ball.getLocation());
        return ((collidingObject != border) && (collidingObject != paddle));
    }

    /**
     * Handle collisions between the ball and bricks.
     */
    private void brickCollisionHandler(){
        double ballTop = ball.getY();
        double ballBottom = (ballTop + (2 * BALL_RADIUS));
        double ballLeft = ball.getX();
        double ballRight = (ballLeft + (2 * BALL_RADIUS));
        GObject brick = getElementAt(ball.getLocation());
        if ((brick.getX() <= ballLeft) || (brick.getX() >= ballRight)){
            velocity_y = -velocity_y;
        } else if((brick.getY() <= ballTop) || (brick.getY() >= ballBottom)){
            velocity_x = -velocity_x;
        }
        remove(brick);
    }


    /**
     * ballHandler() will change the velocity of the ball if it collides with
     * a surface.
     */
    private void ballHandler(){
        if (isCollidingWithHorizontalWall()){
            velocity_x = -velocity_x;
        } else if (isCollidingWithVerticalWall()) {
            velocity_y = -velocity_y;
        } else if (isCollidingWithPaddle()) {
            velocity_y = -velocity_y;
        } else if (isCollidingWithBrick()){
                brickCollisionHandler();
        }
        moveBall();
    }

    /**
     * Register left-arrow-key and right-arrow-key events to move paddle.
     * @param event
     */
    public void keyPressed(KeyEvent event){
        int key = event.getKeyCode();
        switch(key){
            case KeyEvent.VK_LEFT:
                if((paddle.getX() - PADDLE_SPEED_FACTOR) > BORDER_OFFSET) {
                    paddle.setLocation(paddle.getX() - PADDLE_SPEED_FACTOR, paddle.getY());
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(paddle.getX() < SCREEN_WIDTH -
                        BORDER_OFFSET - PADDLE_WIDTH + PADDLE_SPEED_FACTOR) {
                    paddle.setLocation(paddle.getX() + PADDLE_SPEED_FACTOR, paddle.getY());
                }
        }
    }

    /**
     * Run initialize() to initialize screen-size, paddle, bricks, and ball.
     * Run game loop.
     */
    public void run(){
        initialize();
        while(!gameover){
            addKeyListeners();
            ballHandler();
            pause(15);
        }

    }


}
