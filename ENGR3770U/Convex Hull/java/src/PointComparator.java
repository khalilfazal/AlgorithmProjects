import java.awt.geom.Point2D;
import java.util.Comparator;

/**
 * Each Algorithm that we used must given, as a precondition,
 * points as input which are sorted by x-coordinates first then by y-coordinate secondly in cases where two points have the same x-coordinate. 
 * 
 * @authors Khalil Fazal
 * @studentNumber 100 425 046
 * 
 * @authors Daniel Smullen
 * @studentNumber 100 440 203
 * 
 * @author Rayhaan Shakeel
 * @studentNumber 100 425 726
 */
public class PointComparator implements Comparator<Point2D> {
    public int compare(Point2D o1, Point2D o2) {
        
        // If the x-coordinate of the first point is less than the x-coordinate of the second point,
        // the first point must be sorted to the left of the second point.
        if (o1.getX() < o2.getX()) {
            return -1;
        }


        // If the x-coordinate of the first point is greater than the x-coordinate of the second point,
        // the first point must be sorted to the right of the second point.        
        if (o1.getX() > o2.getX()) {
            return 1;
        }
        
        // If the function has yet to return a value by now, it means that the points share the same x-coordinate.
        // For these cases, the points are sorted by their y-coordinates.
        
        // If the y-coordinate of the first point is less than the y-coordinate of the second point,
        // the first point must be sorted to the left of the second point.
        if (o1.getY() < o2.getY()) {
            return -1;
        }

        // If the y-coordinate of the first point is greater than the y-coordinate of the second point,
        // the first point must be sorted to the right of the second point.
        if (o1.getY() > o2.getY()) {
            return 1;
        }

        
        // If the function has yet to return a value by now, it can only be concluded that the points are co-incident.
        return 0;
    }
}