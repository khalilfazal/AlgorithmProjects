import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.JFrame;

public class Main {

    static final int MAX_POINTS = 1000000;

    /**
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Random randgen = new Random();
        ArrayList<Point2D> sortedPoints = new ArrayList<Point2D>();
        ArrayList<Point2D> origPoints = new ArrayList<Point2D>();
        for (int i = 0; i < MAX_POINTS; i++) {
            sortedPoints.add(new Point2D.Double(Math.round(10 + 900 * randgen.nextFloat()), Math.round(10 + 800 * randgen.nextFloat())));

            //System.out.println(sortedPoints.get(i).getX() + "," + sortedPoints.get(i).getY());
        }
        origPoints = (ArrayList<Point2D>) sortedPoints.clone();

        for (int i = 0; i == sortedPoints.size() - 1; i++) {
            if (sortedPoints.get(i).getX() == (sortedPoints.get(i + 1).getX()) && sortedPoints.get(i).getY() == sortedPoints.get(i + 1).getY()) {
                sortedPoints.remove(i + 1);
            }
        }
        sortedPoints.trimToSize();
        Collections.sort(sortedPoints, new PointComparator());

        sortedPoints = MonotoneChain.monotoneChain(sortedPoints);

        JFrame frame = new JFrame("Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020, 820);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(new DrawPoints(sortedPoints, origPoints));
    }
}
