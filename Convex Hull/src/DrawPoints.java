import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawPoints extends JPanel {
    private static final long serialVersionUID = 2148625023722117829L;
    private final ArrayList<Point2D> points;

    public DrawPoints(ArrayList<Point2D> points) {
        this.points = points;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);

        for (int i = 0; i < points.size(); i++) {
            g2d.drawLine((int) Math.round(points.get(i).getX()), (int) Math.round(points.get(i).getY()), (int) Math.round(points.get(i).getX()), (int) Math.round(points.get(i).getY()));
        }
    }
}