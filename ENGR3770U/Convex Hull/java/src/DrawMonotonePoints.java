import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;

public class DrawMonotonePoints extends JPanel {
    private static final long serialVersionUID = 2962861086948391473L;

    List<Point2D> ipoints;
    List<Point2D> orpoints;

    public DrawMonotonePoints(final List<Point2D> list, final List<Point2D> list2) {
        this.ipoints = list;
        this.orpoints = list2;
    }

    private void drawPoints(final List<Point2D> ipoints2, final Graphics2D g2d, final boolean line, final boolean text) {
        for (int i = 0; i < ipoints2.size(); i++) {
            g2d.drawLine((int) Math.round(ipoints2.get(i).getX()), (int) Math.round(ipoints2.get(i).getY()), (int) Math.round(ipoints2.get(i).getX()), (int) Math.round(ipoints2.get(i).getY()));
            if (text) {
                g2d.drawString("(" + i + ")" + (int) Math.round(ipoints2.get(i).getX()) + "," + (int) Math.round(ipoints2.get(i).getY()), (int) Math.round(ipoints2.get(i).getX()), (int) Math.round(ipoints2.get(i).getY() - 10));
            }
        }
        if (line) {
            for (int i = 0; i < ipoints2.size() - 1; i++) {
                g2d.drawLine((int) ipoints2.get(i).getX(), (int) ipoints2.get(i).getY(), (int) ipoints2.get(i + 1).getX(), (int) ipoints2.get(i + 1).getY());
            }
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        this.drawPoints(this.orpoints, g2d, false, false);
        g2d.setColor(Color.blue);
        this.drawPoints(this.ipoints, g2d, true, false);
    }
}