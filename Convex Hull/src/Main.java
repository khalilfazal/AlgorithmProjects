import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;

public class Main {

    static final int MAX_POINTS = 10;

    /**
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Random randgen = new Random();
        ArrayList<Point2D> myPoints = new ArrayList<Point2D>();
        ArrayList<Point2D> origPoints = new ArrayList<Point2D>();
        ArrayList<Point2D> newPoints = new ArrayList<Point2D>();

        for (int i = 0; i < MAX_POINTS; i++) {
            myPoints.add(new Point2D.Double(10 + randgen.nextInt(1000) * randgen.nextFloat(), 10 + randgen.nextInt(600) * randgen.nextFloat()));
            System.out.println(myPoints.get(i).getX() + "," + myPoints.get(i).getY());
        }

        Collections.sort(myPoints, new PointComparator());
        origPoints = (ArrayList<Point2D>) myPoints.clone();
        newPoints = (ArrayList<Point2D>) MonotoneChain.monotoneChain(myPoints).clone();

        JFrame frame = new JFrame("Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1010, 610);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(new DrawPoints(newPoints, origPoints));
    }
}
