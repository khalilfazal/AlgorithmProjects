import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Main {

    static final int MAX_POINTS = 100;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Random randgen = new Random();
        ArrayList<Point2D> myPoints = new ArrayList<Point2D>();

        for (int i = 0; i < MAX_POINTS; i++) {
            myPoints.add(new Point2D.Double(randgen.nextInt(100) * randgen.nextFloat(), randgen.nextInt(100) * randgen.nextFloat()));
        }

        // sort the points

        myPoints = MonotoneChain.monotoneChain(myPoints);

        JFrame frame = new JFrame("Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DrawPoints(myPoints));
        frame.setSize(250, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
