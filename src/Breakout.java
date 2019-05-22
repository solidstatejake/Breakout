
import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;


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



    private final int BALL_RADIUS           = 10;
    private final int BALL_SPEED            = 10;
    private final GOval ball = new GOval(BALL_RADIUS, BALL_RADIUS);


    private final int BRICK_WIDTH           = 36;
    private final int BRICK_HEIGHT          = 15;
    private final int BRICK_SPACING         = 4;
    private final int BRICKS_IN_ROW         = 10;
    private final int BRICKS_IN_COL         = 10;
    private final int BRICK_OFFSET_X        = 2;
    private final int BRICK_OFFSET_Y        = 60;


    private final double CANVAS_CENTER_X = SCREEN_WIDTH / 2;
    private final double CANVAS_CENTER_Y = SCREEN_HEIGHT / 2;



    private void initializeScreen(){
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        setBackground(Color.GRAY);
    }

    private void initializeBorder(){
        border.setLocation(BORDER_OFFSET, BORDER_OFFSET_NORTH);
        add(border);
    }

    private void initializePaddle(){
        paddle.setLocation(INITIAL_PADDLE_X, INITIAL_PADDLE_Y);
        paddle.setFillColor(Color.BLACK);
        paddle.setFilled(true);
        add(paddle);
    }

    private void initializeBall(){
        int     x = (SCREEN_WIDTH - BALL_RADIUS)/2,
                y = (SCREEN_HEIGHT - BALL_RADIUS)/2 + BORDER_OFFSET_NORTH;
        ball.setLocation(x, y);
        ball.setFillColor(Color.WHITE);
        ball.setFilled(true);
        add(ball);
    }

    /**
     * Helper method used in @@{initializeBrick()} to determine the color of
     * the brick based on which row it is in.
     * @param currentBrick
     * @param row
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

    private void initializeHeader(){
        double x, y;
        GLabel header = new GLabel("BREAKOUT");
        header.setFont("Helvetica-20");
        x = (SCREEN_WIDTH - header.getWidth()) / 2;
        y = header.getHeight();
        header.setLocation(x, y);
        add(header);
    }


    private void initialize(){
        initializeScreen();
        initializeBorder();
        initializePaddle();
        initializeBall();
        initializeBricks();
        initializeHeader();
    }

    public void run(){
        initialize();
    }


}
