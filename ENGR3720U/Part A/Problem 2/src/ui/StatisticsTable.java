package ui;

import java.awt.Dimension;
import java.util.Collections;
import java.util.concurrent.BlockingDeque;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

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
    private final BlockingDeque<Object[]> dataQueue;

    /**
     * The table can be modified through this object
     */
    private final DefaultTableModel model;

    /**
     * Builds the table.
     * 
     * @param functionLabels
     *      An array of labels for the functions
     * @param dataQueue
     *      A reference from where data will be listened from
     */
    public StatisticsTable(final Object[] functionLabels, final BlockingDeque<Object[]> dataQueue) {
        this.dataQueue = dataQueue;
        this.capacity = this.dataQueue.remainingCapacity();

        this.model = new DefaultTableModel();
        this.model.addColumn("Benchmark Function", functionLabels);
        this.model.addColumn("Mean Fitness Value");
        this.model.addColumn("Standard Deviation of Fitness Values");

        final JTable table = new JTable(this.model);

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create scroll frame
        final JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        frame.add(scrollPane);

        // Experimentally determined values
        frame.setMinimumSize(new Dimension(551, 125));

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
        try {
            for (int i = 0; i < this.capacity; i++) {
                final Object[] values = this.dataQueue.take();

                this.model.setValueAt(values[0], i, 1);
                this.model.setValueAt(values[1], i, 2);
            }
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
