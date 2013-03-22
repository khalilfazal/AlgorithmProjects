package ui;

import java.util.concurrent.BlockingQueue;

import javax.swing.JProgressBar;

/**
 * Controls the table's progress bar
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class Progress extends Thread {

    /**
     * The progress bar on the table
     */
    private final JProgressBar bar;

    /**
     * The number of increments
     */
    private final int limit;

    /**
     * A latch
     */
    private final BlockingQueue<Boolean> latch;

    /**
     * @param bar
     *      The progress bar on the table
     * @param latch
     *      A latch
     */
    public Progress(final JProgressBar bar, final BlockingQueue<Boolean> latch) {
        this.bar = bar;
        this.limit = bar.getMaximum();
        this.latch = latch;

        this.bar.setString("00.0%");
        this.bar.setStringPainted(true);
    }

    /**
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        Thread.currentThread().setName(this.getClass().getSimpleName());

        try {
            for (int i = 1; i <= this.limit; i++) {
                this.latch.take();
                this.bar.setValue(i);
                this.bar.setString(String.format("%04.1f%%", 100.0 * i / this.limit));
            }
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
