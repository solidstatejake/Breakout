
import acm.graphics.*;
import acm.program.GraphicsProgram;


public class Breakout extends GraphicsProgram {

    // Screen
    private final int SCREEN_WIDTH          = 416;
    private final int SCREEN_HEIGHT         = 716;

    //Border
    private final int BORDER_WIDTH = 400;
    private final int BORDER_HEIGHT = 600;
    private final int BORDER_OFFSET_NORTH = 86; //From screen top to border top.
    private final int BORDER_OFFSET = 8;

    private final int PADDLE_WIDTH          = 60;
    private final int PADDLE_HEIGHT         = 10;
    private final double INITIAL_PADDLE_X = (SCREEN_WIDTH - PADDLE_WIDTH) / 2;
    private final double INITIAL_PADDLE_Y = (SCREEN_HEIGHT - PADDLE_WIDTH)* 0.9;
    private final GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);


    private final int BALL_RADIUS           = 10;
    private final int BALL_SPEED            = 10;

    private final int BRICK_WIDTH           = 36;
    private final int BRICK_HEIGHT          = 15;
    private final int BRICK_SPACING         = 4;
    private final int BRICKS_IN_ROW         = 10;
    private final int BRICKS_IN_COL         = 10;



    private final GPoint canvasCenter = new GPoint(
            (SCREEN_WIDTH - BALL_RADIUS)/2,
            (SCREEN_HEIGHT - BALL_RADIUS)/2 + BORDER_OFFSET_NORTH);

    //Not yet the proper initial paddle location.
    private final GOval ball = new GOval(BALL_RADIUS, BALL_RADIUS);
    private final GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
    private final GRect border = new GRect(BORDER_WIDTH,
            BORDER_HEIGHT);

    private void initializeScreen(){
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
    }

    private void initializeBorder(){
        border.setLocation(BORDER_OFFSET, BORDER_OFFSET_NORTH);
        add(border);
    }

    private void initializePaddle(){
        paddle.setLocation(INITIAL_PADDLE_X, INITIAL_PADDLE_Y);
        add(paddle);
    }

    private void initializeBall(){
        ball.setLocation(canvasCenter);
        add(ball);
    }

    private void initializeBricks(){
        int xCoord = 0, yCoord = 0;

        for (int row = 0; row < BRICKS_IN_ROW; row++) {
            if (row == 0) {
                yCoord = BORDER_OFFSET_NORTH + 60;
            } else{
                yCoord = 60 + BORDER_OFFSET_NORTH + (row * ((BRICK_SPACING / 2) + BRICK_HEIGHT));
            }

//            yCoord = 20 + BORDER_OFFSET_NORTH + BRICK_SPACING + (row * BRICK_HEIGHT);

            for (int col = 0; col < BRICKS_IN_COL; col++) {
                if(col == 0){
                    xCoord =2 +  BORDER_OFFSET;
                }else {
                    xCoord = 2 + BORDER_OFFSET + (col * (BRICK_SPACING + BRICK_WIDTH));
                }
//                xCoord = BORDER_OFFSET + 20 + (col * BRICK_WIDTH);

                add(new GRect(BRICK_WIDTH, BRICK_HEIGHT), xCoord, yCoord);
            }
        }

    }

    private void initializeHeader(){

    }



    private void initialize(){
        initializeScreen();
        initializeBorder();
        initializePaddle();
        initializeBall();
        initializeBricks();
    }

    public void run(){
        initialize();
    }


}
