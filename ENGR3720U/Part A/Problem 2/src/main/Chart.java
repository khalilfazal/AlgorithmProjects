package main;

import java.awt.Toolkit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class Chart extends ApplicationFrame {
    private static final long serialVersionUID = 5020487205158586593L;

    private final XYSeries series;

    public Chart(final String functionName) {
        super(functionName);

        this.series = new XYSeries("");

        final XYSeriesCollection data = new XYSeriesCollection(this.series);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                functionName,
                "Generation",
                null,
                data,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        // Set Logarithmic Y-Axis
        final LogAxis log = new LogAxis("Best Fitness Value So Far (log10 scale)");
        log.setAutoRangeMinimumSize(Double.MIN_VALUE);
        log.setSmallestValue(Double.MIN_NORMAL);

        chart.getXYPlot().setRangeAxis(log);

        final ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setContentPane(panel);
    }

    public void show(final double[] data) {
        for (int i = 0; i < data.length; i++) {
            this.series.add(i, data[i]);
        }
    }
}
