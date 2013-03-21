package ui;

import java.awt.Toolkit;
import java.util.concurrent.BlockingDeque;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A plot of the performance of the differential evolutionary mechanism while
 * attempting to discover a function's global extrema.
 * <ul>
 * <li>x-axis: Generation</li>
 * <li>y-axis: Best Fitness Value So Far, could either be represented linearly
 * or logarithmically</li>
 * </ul>
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class EvolutionaryGraph extends ApplicationFrame implements Runnable {

    /**
     * A uniquely-generated serial UID
     */
    private static final long serialVersionUID = 5020487205158586593L;

    /**
     * A collection of x,y values
     */
    private final XYSeries series;

    /**
     * A reference from where data will be listened from
     */
    private final BlockingDeque<Double> dataQueue;

    /**
     * The total amount of data that will be eventually be sent through the
     * queue
     */
    private final int capacity;

    /**
     * Set's up an empty graph.
     * 
     * @param title
     *            The graph's title, in this case it would be the function's
     *            full name
     * @param dataQueue
     *            a reference the location to listen for data
     * @param setLog
     *            Whether to scale the y-axis logarithmically or not
     */
    public EvolutionaryGraph(final String title, final BlockingDeque<Double> dataQueue, final boolean setLog) {
        super(title);

        this.series = new XYSeries("");

        final XYSeriesCollection data = new XYSeriesCollection(this.series);

        final String yLabel = setLog ? null : "Best Fitness Value So Far";

        final JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Generation",
                yLabel,
                data,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        // Set Logarithmic Y-Axis
        if (setLog) {
            final LogAxis log = new LogAxis("Best Fitness Value So Far (log10 scale)");
            log.setAutoRangeMinimumSize(Double.MIN_VALUE);
            log.setSmallestValue(Double.MIN_NORMAL);

            chart.getXYPlot().setRangeAxis(log);
        }

        final ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setContentPane(panel);

        this.dataQueue = dataQueue;
        this.capacity = dataQueue.remainingCapacity();
        this.setVisible(true);
        this.pack();
    }

    /**
     * Adds data to the graph as it's produced in the data queue.
     * <ul>
     * <li>x-axis: A data element's key, interpreted by its index</li>
     * <li>y-axis: A data element's value</li>
     * </ul>
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            for (int i = 0; i < this.capacity; i++) {
                this.series.add(i, this.dataQueue.take());
            }
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
    }
}
