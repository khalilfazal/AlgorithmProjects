import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Extension of JPanel, represents a canvas to render things onto.
 * 
 * @authors Khalil Fazal
 * @studentNumber 100 425 046
 * @authors Daniel Smullen
 * @studentNumber 100 440 203
 * @author Rayhaan Shakeel
 * @studentNumber 100 425 726
 */

public class DrawPoints extends JPanel {
    private static final long serialVersionUID = -1283970060614246126L;

    // Store an internal ArrayList which we will use to render the points from.
    ArrayList<Point2D> lpoints;
    ArrayList<Point2D> upoints;
    ArrayList<Point2D> orpoints;

    /**
     * The constructor for the DrawPoints canvas. Takes external ArrayLists of
     * points and saves them internally inside the class.
     * 
     * @param lpoints
     *            The points to be used for the lower hull rendering.
     * @param upoints
     *            The points to be used for the upper hull rendering.
     * @param opoints
     *            The points to be used for rendering the original points.
     */
    public DrawPoints(final ArrayList<Point2D> lpoints, final ArrayList<Point2D> upoints,
            final ArrayList<Point2D> opoints) {
        this.lpoints = lpoints;
        this.upoints = upoints;
        this.orpoints = opoints;
    }

    /**
     * Draws the actual points on the screen.
     * 
     * @param lpoints
     *            Takes the points to use to draw lines between.
     * @param g2d
     *            The Graphics2D object to use to complete the rendering.
     * @param line
     *            Flags whether or not to draw lines between the points or not.
     * @param text
     *            Flags whether to put text illustrating the coordinates of each
     *            point being rendered.
     */
    private void drawPoints(final ArrayList<Point2D> lpoints, final Graphics2D g2d,
            final boolean line, final boolean text) {
        /*
         * Iterate through the list of points and draw them. If a line
         * was requested, draw lines between them. If text
         * was requested, output the coordinates of the points, too.
         * Otherwise, just draw the points.
         */
        for (int i = 0; i < lpoints.size(); i++) {
            g2d.drawLine((int) Math.round(lpoints.get(i).getX()),
                    (int) Math.round(lpoints.get(i).getY()),
                    (int) Math.round(lpoints.get(i).getX()),
                    (int) Math.round(lpoints.get(i).getY()));
            if (text) {
                g2d.drawString(
                        "(" + i + ")" + (int) Math.round(lpoints.get(i).getX())
                                + "," + (int) Math.round(lpoints.get(i).getY()),
                        (int) Math.round(lpoints.get(i).getX()),
                        (int) Math.round(lpoints.get(i).getY() - 10));
            }
        }
        if (line) {
            for (int i = 0; i < lpoints.size() - 1; i++) {
                g2d.drawLine((int) lpoints.get(i).getX(), (int) lpoints.get(i)
                        .getY(), (int) lpoints.get(i + 1).getX(), (int) lpoints
                        .get(i + 1).getY());
            }
        }
    }

    /* (non-Javadoc)
     * Overloads the paintComponent method of the Graphics object.
     * This is done so that we can choose what to render.
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        /*
         * Render the original points in black, 
         * without text or lines between them.
         */
        g2d.setColor(Color.black);
        this.drawPoints(this.orpoints, g2d, false, false);

        /*
         * Render the lower hull points in blue, without text,
         * but with lines between them.
         */
        g2d.setColor(Color.blue);
        this.drawPoints(this.lpoints, g2d, true, false);

        /*
         * Render the upper hull points in green, without text,
         * but with lines between them.
         */
        g2d.setColor(Color.green);
        this.drawPoints(this.upoints, g2d, true, false);
    }
}