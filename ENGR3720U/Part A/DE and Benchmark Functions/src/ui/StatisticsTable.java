package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Collections;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import main.Benchmark;

/**
 * Shows a table of statistics gathered for each function.
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class StatisticsTable implements Runnable {

    /**
     * The number of expected rows to display
     */
    private final int capacity;

    /**
     * A reference from where data will be listened from
     */
    private final BlockingDeque<String[]> dataQueue;

    /**
     * The table can be modified through this object
     */
    private final DefaultTableModel model;

    /**
     * Used to update the progress bar in a worker thread
     */
    private final JProgressBar progressBar;

    /**
     * A value that can be updated atomically
     */
    private final AtomicInteger progress;

    /**
     * A countdown latch
     */
    private final BlockingQueue<Boolean> latch;

    /**
     * Builds the table.
     * 
     * @param functionLabels
     *      An array of labels for the functions
     * @param progress
     *      A reference to how complete the table is
     * @param latch 
     *      A countdown latch
     * @param dataQueue
     *      A reference from where data will be listened from
     */
    public StatisticsTable(final String[] functionLabels, final AtomicInteger progress, final BlockingQueue<Boolean> latch, final BlockingDeque<String[]> dataQueue) {
        this.dataQueue = dataQueue;
        this.progress = progress;
        this.latch = latch;
        this.capacity = this.dataQueue.remainingCapacity();

        this.model = new DefaultTableModel();
        this.model.addColumn("Benchmark Function", functionLabels);
        this.model.addColumn("Mean Fitness Value");
        this.model.addColumn("Standard Deviation of Fitness Values");

        final JTable table = new JTable(this.model);
        table.setEnabled(false);

        // Set the column width
        final TableCellRenderer rend = table.getTableHeader().getDefaultRenderer();

        for (final TableColumn column : Collections.list(table.getColumnModel().getColumns())) {
            column.setHeaderRenderer(rend);
            column.sizeWidthToFit();
        }

        // Center all values in the table
        ((DefaultTableCellRenderer) table.getDefaultRenderer(String.class)).setHorizontalAlignment(JLabel.CENTER);

        // Create frame
        final JFrame frame = new JFrame();
        frame.setTitle(this.getClass().getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame's layout
        final Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        // Add progress bar
        this.progressBar = new JProgressBar(0, this.capacity * Benchmark.runs);
        this.progressBar.setValue(0);
        this.progressBar.setStringPainted(true);
        frame.add(this.progressBar);

        // Add scroll frame containing the table
        final JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        frame.add(scrollPane);

        // Experimentally determined values
        frame.setSize(new Dimension(551, 145));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Adds data to the table as it's produced in the data queue.
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        Thread.currentThread().setName(this.getClass().getSimpleName());

        new Thread(new Progress(this.progressBar, this.progress, this.latch)).start();

        try {
            for (int i = 0; i < this.capacity; i++) {
                final String[] values = this.dataQueue.take();

                this.model.setValueAt(values[0], i, 1);
                this.model.setValueAt(values[1], i, 2);
            }
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
