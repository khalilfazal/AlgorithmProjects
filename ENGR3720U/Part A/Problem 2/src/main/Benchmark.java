package main;

import java.util.Map;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.jfree.ui.RefineryUtilities;

import com.google.gson.internal.StringMap;

import fitnessFunctions.FitnessFunction;

public class Benchmark {
    private static final int runs = 50;

    private final int generations;
    private final DifferentialEvolution evolutionaryMechanism;
    private final String shortTitle;

    public Benchmark(final FitnessFunction fitness, final double lowerBound, final double upperBound) {
        final int n = 30;

        final Map<String, Object> params = new StringMap<Object>();
        params.put("max", false);
        params.put("dimensions", n);
        params.put("size", n * 2);
        params.put("mutation", 0.5);
        params.put("crossover", 0.9);
        params.put("fitness", fitness);
        params.put("lower", lowerBound);
        params.put("upper", upperBound);

        this.generations = n * 1000;
        this.evolutionaryMechanism = new DifferentialEvolution(params);
        this.shortTitle = fitness.getClass().getSimpleName();
    }

    private double[] run() {
        final double[] bests = new double[this.generations];
        this.evolutionaryMechanism.reset();

        // final long startTime = System.nanoTime();

        for (int i = 0; i < this.generations; i++) {
            this.evolutionaryMechanism.repopulate();
            bests[i] = this.evolutionaryMechanism.bestFitnessValue();
        }

        // System.out.println(this.population);
        // System.out.println(System.nanoTime() - startTime);

        return bests;
    }

    public String getTitle() {
        return this.evolutionaryMechanism.getFunctionName();
    }

    public SummaryStatistics getSample() {
        final SummaryStatistics sample = new SummaryStatistics();

        for (int i = 0; i < runs; i++) {
            this.run();
            sample.addValue(this.evolutionaryMechanism.bestFitnessValue());
        }

        return sample;
    }

    public void viewPerformance(final boolean logScale) {
        final Chart chart = new Chart(this.getTitle(), logScale);

        chart.setVisible(true);
        chart.pack();
        chart.show(this.run());
        chart.pack();

        RefineryUtilities.centerFrameOnScreen(chart);
    }

    public String getShortTitle() {
        return this.shortTitle;
    }
}
