package main;

import java.util.Map;

import org.jfree.ui.RefineryUtilities;

import com.google.gson.internal.StringMap;

import fitnessFunctions.FitnessFunction;

public class Benchmark {
    private static final int runs = 50;

    private final int generations;
    private final DifferentialEvolution population;
    private final String sortTitle;

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
        this.population = new DifferentialEvolution(params);
        this.sortTitle = fitness.getClass().getSimpleName();
    }

    private double[] run() {
        final double[] bests = new double[this.generations];
        this.population.reset();

        // final long startTime = System.nanoTime();

        for (int i = 0; i < this.generations - 1; i++) {
            bests[i] = this.population.bestFitnessValue();
            this.population.repopulate();
        }

        // System.out.println(this.population);
        // System.out.println(System.nanoTime() - startTime);

        return bests;
    }

    public String getTitle() {
        return this.population.getFunctionName();
    }

    public double[] getSample() {
        final double[] sample = new double[runs];

        for (int i = 0; i < sample.length; i++) {
            this.run();
            sample[i] = this.population.bestFitnessValue();
        }

        return sample;
    }

    public void viewPerformance() {
        final Chart chart = new Chart(this.getTitle());

        chart.setVisible(true);
        chart.pack();
        chart.show(this.run());
        chart.pack();

        RefineryUtilities.centerFrameOnScreen(chart);
    }

    public String getShortTitle() {
        return this.sortTitle;
    }
}
