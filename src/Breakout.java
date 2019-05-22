
import acm.graphics.*;
import acm.program.GraphicsProgram;


public class Breakout extends GraphicsProgram {

    // Screen
    private final int SCREEN_WIDTH          = 416;
    private final int SCREEN_HEIGHT         = 716;

    //Border
    private final int BORDER_WIDTH = 400;
    private final int BORDER_HEIGHT = 600;
    private final int BORDER_NORTH_OFFSET = 86;
    private final int BORDER_OFFSET = 8;

    private final int PADDLE_WIDTH          = 60;
    private final int PADDLE_HEIGHT         = 10;
    private final double INITIAL_PADDLE_X = (SCREEN_WIDTH - PADDLE_WIDTH) / 2;
    private final double INITIAL_PADDLE_Y = (SCREEN_HEIGHT - PADDLE_WIDTH)* 0.9;
    private final GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);


    private final int BALL_RADIUS           = 10;

    private final int BRICK_WIDTH           = 36;
    private final int BRICK_HEIGHT          = 8;

    private final int BALL_SPEED            = 10;
    private final GPoint canvasCenter = new GPoint(
            (SCREEN_WIDTH - BALL_RADIUS)/2,
            (SCREEN_HEIGHT - BALL_RADIUS)/2 + BORDER_NORTH_OFFSET);

    //Not yet the proper initial paddle location.
    private final GOval ball = new GOval(BALL_RADIUS, BALL_RADIUS);
    private final GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
    private final GRect innerBorder = new GRect(BORDER_WIDTH,
            BORDER_HEIGHT);

    private void initializeScreen(){
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
    }

    private void initializeInnerBorder(){
        innerBorder.setLocation(BORDER_OFFSET,
                BORDER_NORTH_OFFSET);
        add(innerBorder);
    }

    private void initializePaddle(){
        paddle.setLocation(INITIAL_PADDLE_X, INITIAL_PADDLE_Y);
        add(paddle);
    }

    private void initializeBall(){
        ball.setLocation(canvasCenter);
        add(ball);
    }

    private void intitializeBricks(){

    }

    private void initializeHeader(){

    }



    private void initialize(){
        initializeScreen();
        initializeInnerBorder();
        initializePaddle();
        initializeBall();
    }

    public void run(){
        initialize();
    }


}
