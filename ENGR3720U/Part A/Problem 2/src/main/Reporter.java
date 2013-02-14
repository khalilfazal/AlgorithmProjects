package main;

import java.util.Arrays;
import java.util.Scanner;

import jstats.Mean;
import jstats.Variance;
import fitnessFunctions.F1;
import fitnessFunctions.F2;
import fitnessFunctions.F3;
import fitnessFunctions.F4;
import fitnessFunctions.F5;

public class Reporter {
    private static final int n = 5;
    private static final Benchmark[] benchmarks;

    static {
        benchmarks = new Benchmark[n];

        benchmarks[0] = new Benchmark(new F1(), -5.12, 5.12);
        benchmarks[1] = new Benchmark(new F2(), -5.12, 5.12);
        benchmarks[2] = new Benchmark(new F3(), -65, 65);
        benchmarks[3] = new Benchmark(new F4(), -2, 2);
        benchmarks[4] = new Benchmark(new F5(), -5.12, 5.12);
    }

    public static String createLine(final char separator, final int length) {
        final char[] line = new char[length];
        Arrays.fill(line, separator);
        return new String(line);
    }

    private static int getSelection(final Scanner in, final int choices) {
        Integer selection = null;

        while (selection == null) {
            System.out.print("Choose an option: ");

            if (in.hasNextInt()) {
                selection = in.nextInt();

                if (selection < 1 || selection > choices) {
                    System.err.println("Invalid selection.");
                    selection = null;
                }
            } else {
                in.close();
                System.out.println();
                System.exit(0);
            }
        }

        return selection;
    }

    public static void main(final String[] args) {
        System.out.println("1. Show the table displaying the results of running all benchmarks.");

        // System.out.println("\tMay take between 5 to 6.25 hours to complete.");
        System.out.println("\tMay take between 22 to 34 minutes to complete.");

        System.out.println("2. Show a performance graph of a particular benchmark function.");

        // System.out.println("\tTakes around 1.5 minutes to complete.");
        System.out.println("\tTakes around 5 to 8 seconds to complete.");

        final Scanner in = new Scanner(System.in);

        switch (getSelection(in, 2)) {
            case 1:
                showResults();
                break;
            case 2:
                System.out.println();

                for (int i = 0; i < benchmarks.length; i++) {
                    System.out.println(String.format("%d. %s", i + 1, benchmarks[i].getTitle()));
                }

                viewPerformance(getSelection(in, n) - 1);
                break;
        }

        in.close();
    }

    /**
     * May take between 5.5 to 6.25 hours to complete with SecureRandom, 22 to
     * 34 minutes with Random.
     */
    public static void showResults() {
        final StringBuilder output = new StringBuilder();
        final int lineLength = 78;

        output.append("Benchmark Function | Mean Fitness Value | Standard Deviation of Fitness Values\n");
        output.append(createLine('=', lineLength));
        output.append("\n");

        // final long startTime = System.nanoTime();

        for (int i = 1; i <= benchmarks.length; i++) {
            final double[] sample = benchmarks[i].getSample();
            final double mean = Mean.arithmeticMean(sample);
            final double stddev = Variance.sampleStandardDeviation(sample);

            output.append(String.format("%17s%1d | %18.2f | %36.2f\n", "f", i, mean, stddev));
            output.append(createLine('-', lineLength));
            output.append("\n");
        }

        // System.out.println(System.nanoTime() - startTime);

        System.out.println(output.toString());
    }

    /**
     * Takes around 1.5 minutes to complete with SecureRandom, 5 to 8 seconds
     * with Random.
     * 
     * @param function
     */
    public static void viewPerformance(final int function) {
        benchmarks[function].viewPerformance();
    }
}