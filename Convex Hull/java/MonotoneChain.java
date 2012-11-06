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
        Point2D p;

        while (i.hasNext()) {
            p = i.next();

            while (lower.size() > 1 && !ccw(lower.get(lower.size() - 2), lower.get(lower.size() - 1), p)) {
                lower.remove(lower.size() - 1);
            }

            lower.add(p);
        }

        while (i.hasPrevious()) {
            p = i.previous();

            while (upper.size() > 1 && !ccw(upper.get(upper.size() - 2), upper.get(upper.size() - 1), p)) {
                upper.remove(upper.size() - 1);
            }

            upper.add(p);
        }

        lower.remove(lower.size() - 1);
        upper.remove(upper.size() - 1);

        lower.addAll(upper);
        lower.add(lower.get(0));

        return lower;
    }
}
