import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * This is an Implementation of the Monotone Chain Algorithm
 * https://en.wikibooks
 * .org/wiki/Algorithm_Implementation/Geometry/Convex_hull/Monotone_chain
 * 
 * @authors Khalil Fazal
 * @studentNumber 100 425 046
 * @authors Daniel Smullen
 * @studentNumber 100 440 203
 * @author Rayhaan Shakeel
 * @studentNumber 100 425 726
 */
public class MonotoneChain {

    // instance variable to store the input points
    private final List<Point2D> points;

    // instance variable to store the hull
    private final List<Point2D> hull;

    /**
     * Given three points, determines whether they form a counterclockwise
     * curve.
     * 
     * @param x
     *            The first point
     * @param y
     *            The second point
     * @param z
     *            The third point
     * @return 
     *            Whether the three points form a right-handed curve    
     */
    private static Boolean counterClockWise(final Point2D x, final Point2D y, final Point2D z) {
        final double ax = y.getX() - x.getX();
        final double by = z.getY() - x.getY();
        final double ay = y.getY() - x.getY();
        final double bx = z.getX() - x.getX();

        return ax * by - ay * bx > 0;
    }

    /**
     * Retrieves the `n`th last element in an ArrayList (indexed from 1)
     * 
     * @param upper
     *            the ArrayList
     * @param n
     *            the position of the `n`th element
     * @return
     *            the `n`th element 
     */
    private static <T> T last(final List<T> upper, final int n) {
        return upper.get(upper.size() - n);
    }

    /**
     * Removes the last element in an array list
     * 
     * @param lower
     *            the ArrayList
     */
    private static <T> void pop(final List<T> lower) {
        if (lower.size() > 0) {
            lower.remove(lower.size() - 1);
        }
    }

    /**
     * The constructor for the MonotoneChain class. This is where the hull is
     * constructed from a list of points.
     * 
     * @param myPoints
     *            and ArrayList of two-dimensional points
     */
    public MonotoneChain(final List<Point2D> myPoints) {
        this.points = myPoints;

        // Sorts the points
        Collections.sort(myPoints, new PointComparator());

        // an ArrayList of two-dimensional points which will store the lower
        // hull
        final List<Point2D> lower = new ArrayList<Point2D>();

        // an ArrayList of two-dimensional points which will store the upper
        // hull
        final List<Point2D> upper = new ArrayList<Point2D>();

        // iterate through the collection
        for (final Point2D p : myPoints) {
            // while:
            // - the lower hull contains 2 or more points
            // - and the last 2 points and the next point in the collection
            // don't form a counterclockwise curve
            while (lower.size() > 1 && !counterClockWise(last(lower, 2), last(lower, 1), p)) {

                // Remove the last point in the lower hull
                lower.remove(lower.size() - 1);
            }

            // Add the next point to the lower hull
            lower.add(p);
        }

        // iterate backwards through the collection
        for (final Point2D p : Lists.reverse(myPoints)) {
            // while:
            // - the upper hull contains 2 or more points
            // - and the last 2 points and the next point in the collection
            // don't form a counterclockwise curve
            while (upper.size() > 1 && !counterClockWise(last(upper, 2), last(upper, 1), p)) {

                // Remove the last point in the upper hull
                upper.remove(upper.size() - 1);
            }

            // Add the next point to the upper hull
            upper.add(p);
        }

        // Remove the last point added to each hull so there is no overlap
        pop(lower);
        pop(upper);

        // Merge each halves of the hull into the `this.hull` instance variable
        this.hull = lower;
        this.hull.addAll(upper);

        // Copy the first point of the hull to the end so a closed loop can be
        // drawn by
        // {@link #DrawMonotonePoints(ArrayList<Point2D> ipoints,
        // ArrayList<Point2D> opoints)}
        if (this.hull.size() > 0) {
            this.hull.add(this.hull.get(0));
        }
    }

    /**
     * @return The hull
     */
    public List<Point2D> getHull() {
        return this.hull;
    }

    /**
     * @return All the points
     */
    public List<Point2D> getAllPoints() {
        return this.points;
    }
}
