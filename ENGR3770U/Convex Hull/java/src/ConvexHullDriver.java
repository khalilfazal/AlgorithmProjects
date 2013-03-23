import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * This class operates as a driver for the test suite, allowing users to
 * interact with the two methods for finding a convex hull.
 * 
 * @authors Khalil Fazal
 * @studentNumber 100 425 046
 * @authors Daniel Smullen
 * @studentNumber 100 440 203
 * @author Rayhaan Shakeel
 * @studentNumber 100 425 726
 */
public class ConvexHullDriver {

    public static void main(final String[] args) {
        // Use a variable to store the maximum number of points that we need.
        int MAX_POINTS = 0;

        // Create a Scanner object so we can read from the keyboard.
        Scanner input = new Scanner(System.in);

        /*
         * Prompt the user to select whether they want random points, or to
         * define their own.
         */
        char selection = ' ';
        System.out.println("Random points (r) " + "or define your own points (d)?");
        do {
            final String s1 = input.nextLine();
            final char c1 = s1.charAt(0);
            if (c1 == 'r' || c1 == 'd') {
                selection = c1;
            }
        } while (selection == ' ');

        final ArrayList<Point2D> myPoints = new ArrayList<Point2D>();

        // If the user wants random points, get how many.
        if (selection == 'r') {
            System.out.println("How many points?");
            input.reset();
            do {
                MAX_POINTS = input.nextInt();
            } while (MAX_POINTS == 0);

            final Random randgen = new Random();
            for (int i = 0; i < MAX_POINTS; i++) {
                myPoints.add(new Point2D.Double(Math.round(10 + 900 * randgen.nextFloat()), Math.round(10 + 700 * randgen.nextFloat())));
            }
        } else {
            /*
             * If the user wants to define their points, get them and add them
             * to the ArrayList which we'll use to store the points. Stop
             * collecting points when the user enters -1.
             */
            System.out.println("Enter your points.");
            System.out.println("The x coordinates may range within this interval: [10, 910].");
            System.out.println("The y coordinates may range within this interval: [10, 710].");
            System.out.println("Coordinates are space seperated. Points are line seperated. Terminate input with '-1'.");
            while (!input.hasNext("-1")) {
                myPoints.add(new Point2D.Double(input.nextDouble(), input.nextDouble()));
            }
        }

        // Prompt the user to choose one of the two algorithms.
        System.out.println("Which algorithm? Smullen's Algorithm (s) " + "or Monotone Chain (m)?");
        selection = ' ';

        input.close();
        input = new Scanner(System.in);

        do {
            final String s1 = input.nextLine();
            final char c1 = s1.charAt(0);
            if (c1 == 's' || c1 == 'm') {
                selection = c1;
            }
        } while (selection == ' ');

        input.close();

        // Create a canvas to render the points and the convex hull on.
        final JFrame frame = new JFrame("Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020, 820);
        frame.setLocationRelativeTo(null);

        /*
         * Depending on which algorithm the user chose, compute and render the
         * convex hull.
         */
        SmullenAlgorithm myConvexHull;
        MonotoneChain myMonotoneHull;
        if (selection == 's') {
            myConvexHull = new SmullenAlgorithm(myPoints);
            frame.add(new DrawPoints(myConvexHull.getLowerHull(), myConvexHull.getUpperHull(), myConvexHull.getAllPoints()));
        } else {
            myMonotoneHull = new MonotoneChain(myPoints);
            frame.add(new DrawMonotonePoints(myMonotoneHull.getHull(), myMonotoneHull.getAllPoints()));
        }

        // Once the points have been rendered, make them visible on the screen.
        frame.setVisible(true);
    }
}
