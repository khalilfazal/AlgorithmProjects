package ui;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

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
public class Progress implements Runnable {

    /**
     * The progress bar on the table
     */
    private final JProgressBar bar;

    /**
     * How complete the table is
     */
    private final AtomicInteger progress;

    /**
     * The number of increments
     */
    private final int limit;

    /**
     * A countdown latch
     */
    private final BlockingQueue<Boolean> latch;

    /**
     * @param bar
     *      The progress bar on the table
     * @param progress
     *      How complete the table is
     * @param latch
     *      A countdown latch
     */
    public Progress(final JProgressBar bar, final AtomicInteger progress, final BlockingQueue<Boolean> latch) {
        this.bar = bar;
        this.progress = progress;
        this.limit = bar.getMaximum();
        this.latch = latch;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        Thread.currentThread().setName(this.getClass().getSimpleName());

        try {
            for (int i = 0; i < this.limit; i++) {
                this.latch.take();
                this.bar.setValue(this.progress.get());
            }
        } catch (final InterruptedException e) {

        }
    }
}
