package main;

import java.util.Arrays;

import jstats.Mean;
import jstats.Variance;
import fitnessFunctions.F1;
import fitnessFunctions.F2;
import fitnessFunctions.F3;
import fitnessFunctions.F4;
import fitnessFunctions.F5;

public class Reporter {
    private static final int n = 5;

    private Reporter() {
    };

    private static Benchmark getBenchmark(final int function) {
        switch (function) {
            case 1:
                return new Benchmark("1st De Jong", -5.12, 5.12, new F1());
            case 2:
                return new Benchmark("Axis Parallel Hyper-Ellipsoid", -5.12, 5.12, new F2());
            case 3:
                return new Benchmark("Schwefel's Problem 1.2", -65, 65, new F3());
            case 4:
                return new Benchmark("Rosenbrock's Valley", -2, 2, new F4());
            case 5:
                return new Benchmark("Rastrigin's Function", -5.12, 5.12, new F5());
            default:
                throw new IllegalArgumentException(String.format("There are only %d benchmark functions.", n));
        }
    }

    public static String createLine(final char separator, final int length) {
        final char[] line = new char[length];
        Arrays.fill(line, separator);
        return new String(line);
    }

    public static String showResults() {
        final StringBuilder output = new StringBuilder();
        final int lineLength = 78;

        output.append("Benchmark Function | Mean Fitness Value | Standard Deviation of Fitness Values\n");
        output.append(createLine('=', lineLength));
        output.append("\n");

        // final long startTime = System.nanoTime();

        for (int i = 1; i <= n; i++) {
            final double[] sample = getBenchmark(i).getSample();
            final double mean = Mean.arithmeticMean(sample);
            final double stddev = Variance.sampleStandardDeviation(sample);

            output.append(String.format("%17s%1d | %18.2f | %36.2f\n", "f", i, mean, stddev));
            output.append(createLine('-', lineLength));
            output.append("\n");
        }

        // System.out.println(System.nanoTime() - startTime);

        return output.toString();
    }

    public static void viewPerformance(final int function) {
        getBenchmark(function).viewPerformance();
    }
}