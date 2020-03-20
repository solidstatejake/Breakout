import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {

    // Screen data
    private final int SCREEN_WIDTH = 416;
    private final int SCREEN_HEIGHT = 716;

    // Border data
    private final int BORDER_WIDTH = 400;
    private final int BORDER_HEIGHT = 600;
    private final int BORDER_OFFSET_NORTH = 86; //From screen top to border top.
    private final int BORDER_OFFSET = 8;
    private final GRect border = new GRect(BORDER_WIDTH, BORDER_HEIGHT);

    // Paddle data
    private final int PADDLE_WIDTH = 60;
    private final int PADDLE_HEIGHT = 10;
    private final double INITIAL_PADDLE_X = (SCREEN_WIDTH - PADDLE_WIDTH) / 2;
    private final double INITIAL_PADDLE_Y = (SCREEN_HEIGHT - PADDLE_WIDTH) * 0.9;
    private final GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);

    // Ball data
    private final int BALL_DIAMETER = 10;
    private final GOval ball = new GOval(BALL_DIAMETER, BALL_DIAMETER);
    private double velocityX, velocityY;

    // Brick data
    private final int BRICK_WIDTH = 36;
    private final int BRICK_HEIGHT = 15;
    private final int BRICK_SPACING = 4;
    private final int BRICKS_IN_ROW = 10;
    private final int BRICKS_IN_COL = 10;
    private final int BRICK_OFFSET_X = 2;
    private final int BRICK_OFFSET_Y = 60;

    // Lives data
    private int lives = 3;
    private final GOval life1 = new GOval(BALL_DIAMETER, BALL_DIAMETER);
    private final GOval life2 = new GOval(BALL_DIAMETER, BALL_DIAMETER);
    private final GOval life3 = new GOval(BALL_DIAMETER, BALL_DIAMETER);

    // Lives container data
    private final int LIVES_CONTAINER_WIDTH = 2 * ((3 * BALL_DIAMETER)); //80
    private final int LIVES_CONTAINER_HEIGHT = 2 * (BALL_DIAMETER + 5);        //30
    private final GRect livesContainer = new GRect(LIVES_CONTAINER_WIDTH,
            LIVES_CONTAINER_HEIGHT);

    // Score data
    private int score = 0;
    private final int MAX_SCORE = 10000; // (100 per brick)*(100 bricks)
    private final GLabel scoreboard = new GLabel("" + score);
//    private final GRect scoreboardContainer = new Grect()

    // Gameover / YouWon Banner data
    private final GLabel gameoverBanner = new GLabel("GAMEOVER");
    private final GLabel youWonBanner = new GLabel("YOU WON!");

    // Game logistics data
    private boolean gameover = false;


//    private AudioClip bounceSound = MediaTools.loadAudioClip("bounce.au");

    /**
     * Set the size of the screen and set the background color as GRAY.
     */
    private void initializeScreen() {
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setBackground(Color.GRAY);
    }

    /**
     * Initialize the "BREAKOUT" title at the top of the screen.
     */
    private void initializeHeader() {
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
    private void initializeBorder() {
        border.setLocation(BORDER_OFFSET, BORDER_OFFSET_NORTH);
        border.setFillColor(Color.LIGHT_GRAY);
        border.setFilled(true);
        add(border);
    }

    /**
     * Create and display the livesContainer and three lives on the game screen.
     */
    private void initializeLives() {
        double lifeContainerX = SCREEN_WIDTH - BORDER_OFFSET - LIVES_CONTAINER_WIDTH; //328
        double lifeContainerY = BORDER_OFFSET_NORTH - LIVES_CONTAINER_HEIGHT;         //56
        double life1_x = lifeContainerX + BALL_DIAMETER;
        double life2_x = life1_x + BALL_DIAMETER + 5;
        double life3_x = life2_x + BALL_DIAMETER + 5;
        double lives_y = lifeContainerY + BALL_DIAMETER;
        livesContainer.setLocation(lifeContainerX, lifeContainerY);
        life1.setLocation(life1_x, lives_y);
        life2.setLocation(life2_x, lives_y);
        life3.setLocation(life3_x, lives_y);
        livesContainer.setFillColor(Color.BLACK);
        livesContainer.setFilled(true);
        life1.setFillColor(Color.WHITE);
        life2.setFillColor(Color.WHITE);
        life3.setFillColor(Color.WHITE);
        life1.setFilled(true);
        life2.setFilled(true);
        life3.setFilled(true);
        add(livesContainer);
        add(life1);
        add(life2);
        add(life3);
    }

    /**
     * Create the paddle and color is BLACK.
     */
    private void initializePaddle() {
        paddle.setLocation(INITIAL_PADDLE_X, INITIAL_PADDLE_Y);
        paddle.setFillColor(Color.BLACK);
        paddle.setFilled(true);
        add(paddle);
    }

    /**
     * Create the ball and color is WHITE.
     */
    private void initializeBall() {
        int x = (SCREEN_WIDTH - BALL_DIAMETER) / 2,
                y = (SCREEN_HEIGHT - BALL_DIAMETER) / 2 + BORDER_OFFSET_NORTH;
        ball.setLocation(x, y);
        ball.setFillColor(Color.WHITE);
        ball.setFilled(true);
        assignInitialVelocities();
        add(ball);
    }

    /**
     * Helper method used in @@{initializeBrick()} to determine the color of
     * the brick based on which row it is in.
     *
     * @param currentBrick is the current brick being created in the for-loop
     *                     in initializeBrick().
     * @param row          is the current row that the for loop is on. There are 10 rows
     *                     and 10 columns of bricks.
     */
    private void determineBrickColor(GRect currentBrick, int row) {
        switch (row) {
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
    private void initializeBricks() {
        int xCoord, yCoord;

        for (int row = 0; row < BRICKS_IN_ROW; row++) {
            if (row == 0) {
                yCoord = BORDER_OFFSET_NORTH + BRICK_OFFSET_Y;
            } else {
                yCoord = BRICK_OFFSET_Y + BORDER_OFFSET_NORTH +
                        (row * ((BRICK_SPACING / 2) + BRICK_HEIGHT));
            }
            for (int col = 0; col < BRICKS_IN_COL; col++) {
                if (col == 0) {
                    xCoord = BORDER_OFFSET + BRICK_OFFSET_X;
                } else {
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
     * Create and add scoreboard to screen.
     */
    private void initializeScoreboard() {
        double x = SCREEN_WIDTH / 8;
        double y = (BORDER_OFFSET_NORTH + scoreboard.getAscent()) / 2;
        scoreboard.setFont("Helvetica-30");
        scoreboard.setColor(Color.WHITE);
        scoreboard.setLocation(x, y);
        add(scoreboard);
    }

    /**
     * Displays the banner taken as a parameter to the screen.
     *
     * @param banner is either gameoverBanner or youWonBanner.
     */
    private void initializeBanner(GLabel banner) {
        double x = (SCREEN_WIDTH - banner.getWidth()) / 4;
        double y = SCREEN_HEIGHT / 2;
        banner.setFont("Helvetica-40");
        banner.setLocation(x, y);
        add(banner);
    }

    /**
     * Aggregate all initialization methods into one place.
     */
    private void initialize() {
        initializeScreen();
        initializeHeader();
        initializeBorder();
        initializeScoreboard();
        initializeLives();
        initializePaddle();
        initializeBall();
        initializeBricks();
    }

    /**
     * Moves the ball according to it's velocityX and velocityY factors.
     */
    private void moveBall() {
        ball.setLocation(ball.getX() + velocityX, ball.getY() + velocityY);
    }

    /**
     * Randomly assign initial @velocityX and @velocityY factors.
     */
    private void assignInitialVelocities() {
        velocityX = 7;
        velocityY = 7;
    }

    /**
     * Determine if the ball is colliding with the paddle.
     *
     * @return returns true if the ball is colliding with the paddle.
     */
    private boolean isCollidingWithPaddle() {
        double ballX = ball.getX() + ball.getWidth();
        double ballY = ball.getY() + ball.getHeight();
        return (getElementAt(ballX, ballY) == paddle);
    }

    /**
     * Determine if the ball is colliding with the left or right wall.
     *
     * @return Will return true if the ball is colliding with the left or right
     * wall.
     */
    private boolean isCollidingWithHorizontalWall() {
        double leftSideOfBall = ball.getX();
        double rightSideOfBall = ball.getX() + ball.getWidth();
        int rightWall, leftWall;
        leftWall = BORDER_OFFSET;
        rightWall = (BORDER_OFFSET + BORDER_WIDTH);
        return (leftSideOfBall <= leftWall || rightSideOfBall >= rightWall);
    }

    /**
     * Determine if ball is colliding with the top or bottom wall.
     *
     * @return Will return true if the ball is colliding with the top or
     * bottom wall.
     */
    private boolean isCollidingWithTopWall() {
        double topOfBall = ball.getY();
        return topOfBall <= BORDER_OFFSET_NORTH;
    }

    /**
     * Determine if the ball is colliding with a brick.
     *
     * @return Will return true if the ball is not colliding with the border and
     * the ball is not colliding with the paddle. AKA the ball is
     * colliding with a brick.
     */
    private boolean isCollidingWithBrick() {
        GObject collidingObject;
        collidingObject = getElementAt(ball.getLocation());
        return ((collidingObject != border) && (collidingObject != paddle));
    }

    /**
     * Handle collisions between the ball and bricks.
     */
    private void brickCollisionHandler() {
        double ballTop = ball.getY();
        double ballBottom = (ballTop + ball.getHeight());
        double ballLeft = ball.getX();
        double ballRight = (ballLeft + ball.getWidth());
        GObject brick = getElementAt(ball.getLocation());
        if ((brick.getX() <= ballLeft) || (brick.getX() >= ballRight)) {
            velocityY = -velocityY;
        } else if ((brick.getY() <= ballTop) || (brick.getY() >= ballBottom)) {
            velocityX = -velocityX;
        }
        remove(brick);
    }

    /**
     * ballHandler() will change the velocity of the ball if it collides with
     * a surface.
     */
    private void ballHandler() {
        if (isCollidingWithHorizontalWall()) {
            velocityX = -velocityX;

        } else if (isCollidingWithTopWall()) {
            velocityY = -velocityY;

        } else if (isCollidingWithPaddle()) {
            // If ball strikes left half of paddle...
            if (ball.getX() < (paddle.getX() + (PADDLE_WIDTH / 2))) {
                // and it's traveling rightward, then reverse direction.
                if (velocityX > 0) {
                    velocityX = -velocityX;
                }
                // If ball strikes right half of paddle...
            } else if (ball.getX() >= (paddle.getX() + (PADDLE_WIDTH / 2))) {
                // and it's traveling leftward, then reverse direction.
                if (velocityX < 0) {
                    velocityX = -velocityX;
                }
            }
            velocityY = -velocityY;

        } else if (isCollidingWithBrick()) {
            brickCollisionHandler();
            updateScoreboard();
        }
        moveBall();
    }

    /**
     * Updates the score. Called in updateScoreboard().
     */
    private void updateScore() {
        score += 100;
    }

    /**
     * Updates the score and updates the scoreboard GLabel. Called in
     * ballHandler().
     */
    private void updateScoreboard() {
        updateScore();
        scoreboard.setLabel("" + score);
    }

    /**
     * Checks if the player has lost a life by checking if the ball has hit the
     * bottom of the border.
     */
    private void isLifeLost() {
        // Multiplied by 1.5 because that's what works.
        if ((ball.getY() + (1.5 * ball.getHeight())) >= (BORDER_OFFSET_NORTH + BORDER_HEIGHT)) {
            pause(1000);
            initializeBall();
            pause(1000);
            lives -= 1;
        }
        switch (lives) {
            case 2:
                remove(life3);
                break;
            case 1:
                remove(life2);
                break;
            case 0:
                remove(life1);
        }
    }

    /**
     * Checks if the game is over by checking if lives == 0.
     */
    private void isGameOver() {
        if (lives == 0 || score == MAX_SCORE) {
            gameover = true;
        }
    }

    /**
     * Handle mouse movements (which move the paddle).
     *
     * @param event is the mouse movement.
     */
    public void mouseMoved(MouseEvent event) {
        if (event.getX() > BORDER_OFFSET &&
                event.getX() < BORDER_OFFSET + BORDER_WIDTH - PADDLE_WIDTH) {
            paddle.setLocation(event.getX(), INITIAL_PADDLE_Y);
        }
    }

    /**
     * Aggregates relevant game-playing functions.
     */
    private void playGame() {
        addMouseListeners();
        ballHandler();
        isLifeLost();
        isGameOver();
        pause(20);
    }

    /**
     * Run initialize() to initialize screen-size, paddle, bricks, and ball.
     * Run game loop.
     */
    public void run() {
        initialize();
        pause(1000);
        while (!gameover) {
            playGame();
        }
        if (lives == 0) {
            initializeBanner(gameoverBanner);
        } else if (score == MAX_SCORE) {
            initializeBanner(youWonBanner);
        }
    }
}
