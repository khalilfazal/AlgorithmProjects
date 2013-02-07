import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

public class Main {

	static final int MAX_POINTS = 100000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random randgen = new Random();
		ArrayList<Point2D> myPoints = new ArrayList<Point2D>();
		for (int i = 0; i < MAX_POINTS; i++) {
			myPoints.add(new Point2D.Double(Math.round(10 + 900 * randgen.nextFloat()),
					Math.round(10 + (700 * randgen.nextFloat()))));
		}
		
		MonotoneChain myConvexHull = new MonotoneChain(myPoints);
		
		JFrame frame = new JFrame("Points");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1020, 820);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(new DrawPoints(myConvexHull.getHull(), myConvexHull.getAllPoints()));
	}
}
