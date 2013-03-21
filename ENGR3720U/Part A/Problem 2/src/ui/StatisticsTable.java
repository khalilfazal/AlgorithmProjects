package ui;

import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.BlockingDeque;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class StatisticsTable implements Runnable {

    private final int capacity;
    private final BlockingDeque<Vector<Object>> rowQueue;
    private final DefaultTableModel model;

    public StatisticsTable(final BlockingDeque<Vector<Object>> rowQueue) {
        final Vector<String> columns = new Vector<String>() {
            private static final long serialVersionUID = 3165682530948750950L;

            {
                this.add("Benchmark Function");
                this.add("Mean Fitness Value");
                this.add("Standard Deviation of Fitness Values");
            }
        };

        this.model = new DefaultTableModel(columns, this.capacity + 1);
        this.rowQueue = rowQueue;
        this.capacity = this.rowQueue.remainingCapacity();

        final JFrame frame = new JFrame();
        final JTable table = new JTable(this.model);
        final JScrollPane scrollPane = new JScrollPane(table);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(scrollPane);

        // Set the column width
        final TableCellRenderer rend = table.getTableHeader().getDefaultRenderer();

        for (final TableColumn column : Collections.list(table.getColumnModel().getColumns())) {
            column.setHeaderRenderer(rend);
            column.sizeWidthToFit();
        }

        frame.setSize(table.getPreferredSize());
        frame.setVisible(true);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < this.capacity; i++) {
                this.model.addRow(this.rowQueue.take());
            }
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
