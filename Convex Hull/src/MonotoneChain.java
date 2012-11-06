import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class MonotoneChain {
    public static Boolean ccw(Point2D o, Point2D a, Point2D b) {
        return (a.getX() - o.getX()) * (b.getY() - o.getY()) - (a.getY() - o.getY()) * (b.getX() - o.getX()) > 0;
    }

    public static ArrayList<Point2D> monotoneChain(ArrayList<Point2D> points) {
        Collections.sort(points, new PointComparator());
        ArrayList<Point2D> lower = new ArrayList<Point2D>();
        ArrayList<Point2D> upper = new ArrayList<Point2D>();
        ListIterator<Point2D> i = points.listIterator();

        while (i.hasNext()) {

        }
    }
}
