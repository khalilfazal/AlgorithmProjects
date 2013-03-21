package main;

import java.awt.Window;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import ui.EvolutionaryGraph;

import com.google.gson.internal.StringMap;

import fitnessFunctions.FitnessFunction;

/**
 * Performs a {@link Benchmark} for finding the global extrema for a
 * {@link FitnessFunction} using the {@link DifferentialEvolution} algorithm. It
 * could either generate a sample over many runs or launch an
 * {@link EvolutionaryGraph} for one run.
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class Benchmark {

    /**
     * The amount of runs to perform while generating a sample
     */
    private final int runs;

    /**
     * The amount of generations per run
     */
    private final int generations;

    /**
     * A reference to the {@link DifferentialEvolution} mechanism
     */
    private final DifferentialEvolution evolutionaryMechanism;

    /**
     * A short label for the {@link FitnessFunction}
     */
    private final String shortTitle;

    /**
     * A concurrent queue of best fitness values
     */
    private final BlockingDeque<Double> bests;

    /**
     * Constructs a benchmark for a {@link FitnessFunction} using a
     * {@link DifferentialEvolution} mechanism. Some of the parameters for the
     * mechanism are constant and are set to their specifications in the
     * project's guidelines.
     * 
     * @param function
     *            The {@link FitnessFunction} for which this {@link Benchmark}
     *            is performed for
     * @param lowerBound
     *            A lower bound for the domain
     * @param upperBound
     *            An upper bound for the domain
     */
    public Benchmark(final FitnessFunction function, final double lowerBound, final double upperBound) {
        final int n = 30;

        final Map<String, Object> params = new StringMap<Object>();
        params.put("max", false);
        params.put("dimensions", n);
        params.put("size", n * 2);
        params.put("mutation", 0.5);
        params.put("crossover", 0.9);
        params.put("fitness", function);
        params.put("lower", lowerBound);
        params.put("upper", upperBound);

        this.runs = 50;
        this.generations = n * 1000;
        this.evolutionaryMechanism = new DifferentialEvolution(params);
        this.shortTitle = function.getClass().getSimpleName();
        this.bests = new LinkedBlockingDeque<Double>(this.generations);
    }

    /**
     * @return The full name of the {@link FitnessFunction}
     */
    public String getTitle() {
        return this.evolutionaryMechanism.getFunctionName();
    }

    /**
     * @return A short-hand representation of the {@link FitnessFunction}
     */
    public String getShortTitle() {
        return this.shortTitle;
    }

    /**
     * Performs all runs
     * 
     * @return A sample of the best fitness values of the last generation of
     *         each run
     */
    public SummaryStatistics getSample() {
        final SummaryStatistics sample = new SummaryStatistics();

        for (int i = 0; i < this.runs; i++) {
            this.run();
            sample.addValue(this.bests.getLast());
            this.bests.clear();
        }

        return sample;
    }

    /**
     * Graph one run of the {@link DifferentialEvolution} mechanism for the
     * {@link FitnessFunction}
     * <ul>
     * <li>x-axis: Generation</li>
     * <li>y-axis: Best Fitness Value So Far</li>
     * </ul>
     * Order of UI events:
     * <ol>
     * <li>The {@link Window} which will contain the {@link EvolutionaryGraph}
     * is made visible</li>
     * <li>Data will be added to the graph as its produced concurrently by
     * {@link Benchmark#run()}
     * <li>The {@link EvolutionaryGraph} is rescaled</li>
     * <li>The {@link EvolutionaryGraph} is re-centered with respect to its
     * {@link Window}</li>
     * </ol>
     * 
     * @param logScale
     *            Whether to scale the y-axis logarithmically or not
     */
    public void viewPerformance(final boolean logScale) {
        new Thread(new EvolutionaryGraph(this.getTitle(), this.bests, logScale)).start();

        this.run();
    }

    /**
     * Performs one (1) run of the {@link DifferentialEvolution} mechanism
     */
    private void run() {
        this.evolutionaryMechanism.reset();

        // final long startTime = System.nanoTime();

        for (int i = 0; i < this.generations; i++) {
            this.evolutionaryMechanism.repopulate();

            try {
                this.bests.put(this.evolutionaryMechanism.bestFitnessValue());
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }

        // System.out.println(this.population);
        // System.out.println(System.nanoTime() - startTime);
    }
}
