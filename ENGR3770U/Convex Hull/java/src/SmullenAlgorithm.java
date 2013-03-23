import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Smullen's algorithm - uses second derivatives to calculate the concavity of a
 * series of points. Iterating through these points and removing points which do
 * not conform to the convex hull shape results in a lower and upper convex
 * hull.
 * 
 * @version 1.5
 * @authors Khalil Fazal
 * @studentNumber 100 425 046
 * @authors Daniel Smullen
 * @studentNumber 100 440 203
 * @author Rayhaan Shakeel
 * @studentNumber 100 425 726
 */
public class SmullenAlgorithm {
    /*
     * Store 3 ArrayLists which are used to store the original points, the
     * points on the upper hull, and the points on the lower hull.
     */
    private ArrayList<Point2D> orig;
    private ArrayList<Point2D> upper;
    private ArrayList<Point2D> lower;

    /**
     * This function removes the duplicate points within the input data set. Any
     * points which lie on the same x and y coordinate are merged into one
     * single point.
     * 
     * @param myPoints
     *            The ArrayList<Point2D> of points to remove duplicates from.
     * @return Returns an ArrayList<Point2D> which does not contain any
     *         duplicate points.
     */
    private ArrayList<Point2D> removeDuplicates(final ArrayList<Point2D> myPoints) {
        /*
         * Iterate through all the points, and if any points lie on the same X
         * coordinate and Y coordinate, they are duplicates.
         */
        for (int i = 0; i == myPoints.size() - 1; i++) {
            if (myPoints.get(i).getX() == myPoints.get(i + 1).getX()
                    && myPoints.get(i).getY() == myPoints.get(i + 1).getY()) {
                myPoints.remove(i + 1);
            }
        }
        /*
         * Remove any extra empty nodes in the ArrayList. This is a safety
         * measure to ensure no NULL values exist as a result of removed points.
         * Used to ensure compatibility with different implementations of the
         * ArrayList data structure.
         */
        myPoints.trimToSize();
        return myPoints;
    }

    /**
     * Prepares the input data set for processing by the Smullen Algorithm by
     * sorting the points based on their X values (and then their Y values if
     * the X coordinates are the same). Then, computes the upper and lower
     * convex hull and stores it inside the 3 private ArrayLists inside the
     * class..
     * 
     * @param myPoints
     *            Takes an input data set of points for processing.
     */
    @SuppressWarnings("unchecked")
    private void init(ArrayList<Point2D> myPoints) {

        // Store the original, unmodified points.
        this.orig = new ArrayList<Point2D>();
        this.orig = myPoints;

        // Go through the points and remove all duplicates.
        myPoints = this.removeDuplicates(myPoints);

        // Sort all the points using a PointComparator object.
        Collections.sort(myPoints, new PointComparator());

        /*
         * Instantiate the upper and lower hull ArrayLists with a new ArrayList
         * instance.
         */
        this.upper = new ArrayList<Point2D>();
        this.lower = new ArrayList<Point2D>();

        /*
         * Compute the upper and lower hulls. Use a copy of the points we have
         * in case we need to do some sort of processing with the points
         * separate from computing the convex hull.
         */
        this.upper = this.UpperHull((ArrayList<Point2D>) myPoints.clone());
        this.lower = this.LowerHull((ArrayList<Point2D>) myPoints.clone());
    }

    /**
     * The constructor for the SmullenAlgorithm object. On construction, run the
     * init() function.
     * 
     * @param myPoints
     *            Takes an input data set of points for processing.
     */
    public SmullenAlgorithm(final ArrayList<Point2D> myPoints) {
        this.init(myPoints);
    }

    /**
     * Accessor method for the lower hull points.
     * 
     * @return Returns the points on the lower convex hull.
     */
    public ArrayList<Point2D> getLowerHull() {
        return this.lower;
    }

    /**
     * Accessor method for the upper hull points.
     * 
     * @return Returns the points on the upper convex hull.
     */
    public ArrayList<Point2D> getUpperHull() {
        return this.upper;
    }

    /**
     * Accessor method for both the upper and lower hull points. This function
     * concatenates the two hulls into one giant ArrayList object. First, the
     * points for the lower hull are stored in the ArrayList, then the upper
     * hull points are added to the end.
     * 
     * @return Returns an ArrayList of all the points on the convex hull - both
     *         the lower and upper hull.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Point2D> getConvexHull() {
        ArrayList<Point2D> convexhull = new ArrayList<Point2D>();
        convexhull = (ArrayList<Point2D>) this.lower.clone();
        convexhull.addAll(this.upper);
        return convexhull;
    }

    /**
     * Internal calculus function used to determine the concavity of three
     * points. Uses the second derivative to determine concavity.
     * 
     * @pre Must be given three points sorted by X coordinate, or unpredictable
     *      results may occur. The assumption is that we are testing the
     *      concavity based on a parabola that is based on p2 as the vertex.
     * @param p1
     *            The first point to test the concavity of.
     * @param p2
     *            The second point to test the concavity of. This point is
     *            assumed to be the vertex of the parabola.
     * @param p3
     *            The third point to test the concavity of.
     * @return Returns a Double which is a measure of the actual concavity.
     *         Positive values indicate concave up, negative values indicate
     *         concave down (or convex, depending on your perspective).
     */
    private Double Concavity(final Point2D p1, final Point2D p2, final Point2D p3) {
        // Find the delta Y and delta X of the first two points.
        final double dy1 = p2.getY() - p1.getY();
        final double dx1 = p2.getX() - p1.getX();

        // Find the delta Y and delta X of the second two points.
        final double dy2 = p3.getY() - p2.getY();
        final double dx2 = p3.getX() - p2.getX();

        /*
         * Find the first derivative of the first and second points.
         */
        final double slope1 = dy1 / dx1;

        /*
         * Now find the first derivative of the second and third points.
         */
        final double slope2 = dy2 / dx2;

        // Now find the second derivative of the points.
        final Double concavity = slope2 - slope1;

        return concavity;
    }

    /**
     * Function used to compute the upper convex hull. Uses concavity to
     * determine whether a point is on the hull or not. Since this is the upper
     * hull, the concavity should be <= 0.
     * 
     * @pre The ArrayList<Point2D> given to this function must be sorted by X
     *      value.
     * @param points
     *            The sorted points we want to find the upper hull of.
     * @return Returns an ArrayList<Point2D> which consists of the points on the
     *         upper hull.
     */
    private ArrayList<Point2D> UpperHull(final ArrayList<Point2D> points) {
        // We need to track whether we removed a point in each iteration.
        boolean pointRemoved = false;

        // Keep looping until we don't need to remove any more points.
        do {
            pointRemoved = false;

            // Traverse the points left to right.
            for (int i = 0; i <= points.size(); i++) {

                /*
                 * If there aren't enough points to test the concavity of
                 * anymore, we're at the end of the shape and we can assume that
                 * the shape is convex at this point.
                 */
                if (points.size() < 3 || i + 2 > points.size() - 1) {
                    break;
                } else {
                    // Test the concavity of the next three points.
                    final Double conc = this.Concavity(points.get(i), points.get(i + 1),
                            points.get(i + 2));

                    if (conc <= 0) {
                        /*
                         * If the concavity is in the wrong direction, remove
                         * the point in the middle and step backwards. Zero (a
                         * horizontal line) means that it's still safe to remove
                         * the middle point.
                         */
                        points.remove(i + 1);
                        pointRemoved = true;
                        i = i - 1;
                        /*
                         * If the concavity is undefined, it means we're dealing
                         * with a vertical line, so it's still safe to remove
                         * the middle point. Do so, and step backwards.
                         */
                    } else if (conc.isNaN()) {
                        points.remove(i + 1);
                        pointRemoved = true;
                        i = i - 1;
                    }
                }
            }
            /*
             * Remove any extra empty nodes in the ArrayList. This is a safety
             * measure to ensure no NULL values exist as a result of removed
             * points. Used to ensure compatibility with different
             * implementations of the ArrayList data structure.
             */
            points.trimToSize();
        } while (pointRemoved);

        return points;
    }

    private ArrayList<Point2D> LowerHull(final ArrayList<Point2D> points) {
        // We need to track whether we removed a point in each iteration.
        boolean pointRemoved = false;

        // Keep looping until we don't need to remove any more points.
        do {
            pointRemoved = false;

            // Traverse the points left to right.
            for (int i = 0; i <= points.size(); i++) {

                /*
                 * If there aren't enough points to test the concavity of
                 * anymore, we're at the end of the shape and we can assume that
                 * the shape is convex at this point.
                 */
                if (points.size() < 3 || i + 2 > points.size() - 1) {
                    break;
                } else {
                    // Test the concavity of the next three points.
                    final Double conc = this.Concavity(points.get(i), points.get(i + 1),
                            points.get(i + 2));

                    if (conc >= 0) {
                        /*
                         * If the concavity is in the wrong direction, remove
                         * the point in the middle and step backwards. Zero (a
                         * horizontal line) means that it's still safe to remove
                         * the middle point.
                         */
                        points.remove(i + 1);
                        pointRemoved = true;
                        i = i - 1;
                    } else if (conc.isNaN()) {
                        /*
                         * If the concavity is undefined, it means we're dealing
                         * with a vertical line, so it's still safe to remove
                         * the middle point. Do so, and step backwards.
                         */
                        points.remove(i + 1);
                        pointRemoved = true;
                        i = i - 1;
                    }
                }
            }
            /*
             * Remove any extra empty nodes in the ArrayList. This is a safety
             * measure to ensure no NULL values exist as a result of removed
             * points. Used to ensure compatibility with different
             * implementations of the ArrayList data structure.
             */
            points.trimToSize();
        } while (pointRemoved);

        return points;
    }

    /**
     * Accessor method to return all the original points that were input.
     * 
     * @return Returns an ArrayList<Point2D> of all the original unmodified
     *         points.
     */
    public ArrayList<Point2D> getAllPoints() {
        return this.orig;
    }

    /**
     * Modifies the original points and recomputes the hull.
     * 
     * @param orig
     *            Takes an ArrayList<Point2D> of the points to replace the
     *            original instantiation's points with.
     */
    public void setPoints(final ArrayList<Point2D> orig) {
        this.init(orig);
    }
}
