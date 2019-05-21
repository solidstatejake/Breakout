
import acm.graphics.*;
import acm.program.GraphicsProgram;


public class Breakout extends GraphicsProgram {

    // Constants
    private final int SCREEN_WIDTH          = 416;
    private final int SCREEN_HEIGHT         = 716;

    private final int INNER_BORDER_WIDTH    = 400;
    private final int INNER_BORDER_HEIGHT   = 600;

    private final int PADDLE_WIDTH          = 60;
    private final int PADDLE_HEIGHT         = 10;
    private final double INITIAL_PADDLE_X = (SCREEN_WIDTH - PADDLE_WIDTH) / 2;
    private final double INITIAL_PADDLE_Y = (SCREEN_HEIGHT - PADDLE_WIDTH)* 0.9;
    private final GPoint initialPaddleLocation = new GPoint(INITIAL_PADDLE_X,
                                                            INITIAL_PADDLE_Y);
    private final GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);



    private final int BALL_RADIUS           = 10;

    private final int BRICK_WIDTH           = 36;
    private final int BRICK_HEIGHT          = 8;

    private final int BALL_SPEED            = 10;
    private final GPoint canvasCenter = new GPoint(getWidth()/2,
                                                  getHeight()/2);

    //Not yet the proper initial paddle location.
    private final GOval ball = new GOval(BALL_RADIUS, BALL_RADIUS);
    private final GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
    private final GRect innerBorder = new GRect(INNER_BORDER_WIDTH,
                                                INNER_BORDER_HEIGHT);

    private void initializeScreen(){
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
    }
    private void initializeInnerBorder(){
        innerBorder.setLocation(8, 86);
        add(innerBorder);
    }

    private void initializePaddle(){
        paddle.setLocation(initialPaddleLocation);
        add(paddle);
    }

    private void intitializeBricks(){

    }

    private void initializeHeader(){

    }

    private void inititalizeBall(){

    }

    private void initialize(){
        initializeScreen();
        initializeInnerBorder();
        initializePaddle();
    }

    public void run(){
        initialize();
    }


}
