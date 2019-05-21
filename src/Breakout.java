
import acm.graphics.GOval;
import acm.graphics.GPolygon;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class Breakout extends GraphicsProgram {

    public void run(){




        GPolygon a = new GPolygon();

        a.addVertex(-22, 0);
        a.addVertex(0, 36);
        a.addVertex(22, 0);
        a.addVertex(0, -36);
        a.setLocation(300,400);
        add(a);

        GPolygon b = new GPolygon();
        b.addVertex(-22, 0);
        b.addEdge(22,36);
        b.addEdge(22,-36);
        b.addEdge(-22, -36);
        b.addEdge(-22, 36);
        b.setLocation(100,100);
        add(b);

    }
    /*This method will call:
     *   makePaddle()
     *   makeBricks()
     *   makeBall()          */
    private void initialize(){


    }


    private void makePaddle(){


    }


    private void makeBricks(){


    }

    private void makeBall() {

    }




}
