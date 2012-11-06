import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawPoints extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -8372619086334060399L;
    ArrayList<Point2D> points;
    ArrayList<Point2D> orpoints;

    public DrawPoints(ArrayList<Point2D> ipoints, ArrayList<Point2D> opoints) {
        this.points = ipoints;
        this.orpoints = opoints;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setBackground(Color.white);
        g2d.setColor(Color.red);
        for (int i = 0; i < orpoints.size(); i++) {
            g2d.fillOval((int) Math.round(orpoints.get(i).getX() - 4), (int) Math.round(orpoints.get(i).getY() - 4), 8, 8);
            g2d.drawString("(" + i + ")" + (int) Math.round(orpoints.get(i).getX()) + "," + (int) Math.round(orpoints.get(i).getY()), (int) Math.round(orpoints.get(i).getX()), (int) Math.round(orpoints.get(i).getY()));
        }
        g2d.setColor(Color.black);
        for (int i = 0; i < points.size(); i++) {
            g2d.fillOval((int) Math.round(points.get(i).getX() - 2.5), (int) Math.round(points.get(i).getY() - 2.5), 5, 5);
            g2d.drawString("(" + i + ")" + (int) Math.round(points.get(i).getX()) + "," + (int) Math.round(points.get(i).getY()), (int) Math.round(points.get(i).getX()), (int) Math.round(points.get(i).getY()));
        }
        for (int i = 0; i < points.size() - 1; i++) {
            g2d.drawLine((int) points.get(i).getX(), (int) points.get(i).getY(), (int) points.get(i + 1).getX(), (int) points.get(i + 1).getY());
        }
    }
}