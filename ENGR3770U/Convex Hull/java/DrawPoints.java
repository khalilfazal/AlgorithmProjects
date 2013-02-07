import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPoints extends JPanel {
    ArrayList<Point2D> ipoints;
    ArrayList<Point2D> orpoints;

    public DrawPoints(ArrayList<Point2D> ipoints, ArrayList<Point2D> opoints) {
        this.ipoints = ipoints;
        this.orpoints = opoints;
    }

    private void drawPoints(ArrayList<Point2D> lpoints, Graphics2D g2d, boolean line, boolean text) {
        for (int i = 0; i < lpoints.size(); i++) {
            g2d.drawLine((int) Math.round(lpoints.get(i).getX()), (int) Math.round(lpoints.get(i).getY()), (int) Math.round(lpoints.get(i).getX()), (int) Math.round(lpoints.get(i).getY()));
            if (text) {
                g2d.drawString("(" + i + ")" + (int) Math.round(lpoints.get(i).getX()) + "," + (int) Math.round(lpoints.get(i).getY()), (int) Math.round(lpoints.get(i).getX()), (int) Math.round(lpoints.get(i).getY() - 10));
            }
        }
        if (line) {
            for (int i = 0; i < lpoints.size() - 1; i++) {
                g2d.drawLine((int) lpoints.get(i).getX(), (int) lpoints.get(i).getY(), (int) lpoints.get(i + 1).getX(), (int) lpoints.get(i + 1).getY());
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        drawPoints(orpoints, g2d, false, false);
        g2d.setColor(Color.blue);
        drawPoints(ipoints, g2d, true, false);
    }
}