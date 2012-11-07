import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class MonotoneChain {
    private ArrayList<Point2D> points;
    private ArrayList<Point2D> hull;

    private static Boolean counterClockWise(Point2D o, Point2D a, Point2D b) {
        double ax = a.getX() - o.getX();
        double by = b.getY() - o.getY();
        double ay = a.getY() - o.getY();
        double bx = b.getX() - o.getX();
        
        return ax * by - ay * bx > 0;
    }
    
    private static Point2D last(ArrayList<Point2D> array, int i) {
        return array.get(array.size() - i);
    }
    
    private static void pop(ArrayList<Point2D> array) {
        array.remove(array.size() - 1);
    }

    public MonotoneChain(ArrayList<Point2D> points) {
        this.points = points;
        
        Collections.sort(points, new PointComparator());
        ArrayList<Point2D> lower = new ArrayList<Point2D>();
        ArrayList<Point2D> upper = new ArrayList<Point2D>();
        ListIterator<Point2D> i = points.listIterator();
        Point2D p;

        while (i.hasNext()) {
            p = i.next();

            while (lower.size() > 1 && !counterClockWise(last(lower, 2), last(lower, 1), p)) {
                lower.remove(lower.size() - 1);
            }

            lower.add(p);
        }

        while (i.hasPrevious()) {
            p = i.previous();

            while (upper.size() > 1 && !counterClockWise(last(upper, 2), last(upper, 1), p)) {
                upper.remove(upper.size() - 1);
            }

            upper.add(p);
        }

        pop(lower);
        pop(upper);

        this.hull = lower;
        this.hull.addAll(upper);
        this.hull.add(this.hull.get(0));
    }

    public ArrayList<Point2D> getHull() {
        return this.hull;
    }

    public ArrayList<Point2D> getAllPoints() {
        return this.points;
    }
}
