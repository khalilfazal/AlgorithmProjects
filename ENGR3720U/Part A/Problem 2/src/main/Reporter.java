package main;

import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import ui.EvolutionaryGraph;
import ui.StatisticsTable;
import fitnessFunctions.F1;
import fitnessFunctions.F2;
import fitnessFunctions.F3;
import fitnessFunctions.F4;
import fitnessFunctions.F5;
import fitnessFunctions.FitnessFunction;

/**
 * Contains the {@link Reporter#main(String[])} function. Provides an
 * interactive interface through the console to:
 * <ul>
 * <li>run a {@link Benchmark},</li>
 * <li>view a {@link EvolutionaryGraph},</li>
 * <li>and view {@link SummaryStatistics}</li>
 * </ul>
 * on the application of the {@link DifferentialEvolution} Algorithm for various
 * {@link FitnessFunction}s.
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class Reporter {

    /**
     * Some whitespace used to enhance the readability of the console's standard
     * {@link OutputStream}
     */
    private static final String TAB = Reporter.createLine(' ', 3);

    /**
     * A collection of {@link Benchmark}s at the user's disposal
     */
    private static final Benchmark[] benchmarks = new Benchmark[] {
            new Benchmark(new F1(), -5.12, 5.12),
            new Benchmark(new F2(), -5.12, 5.12),
            new Benchmark(new F3(), -65, 65),
            new Benchmark(new F4(), -2, 2),
            new Benchmark(new F5(), -5.12, 5.12)
    };

    /**
     * A static method for creating a series of characters of arbitrary length
     * 
     * @param unit
     *            A character which the line will be made up of
     * @param length
     *            the length of the series
     * @return A series of characters
     */
    public static String createLine(final char unit, final int length) {
        final char[] line = new char[length];
        Arrays.fill(line, unit);
        return new String(line);
    }

    /**
     * Prompts for a response from the user.
     * 
     * @param in
     *            The input {@link Scanner} used to receive responses
     * @param prompts
     *            A prompt including a list of choices
     * @param choices
     *            The number of choices
     * @return A selected choice
     */
    private static int getSelection(final Scanner in, final Object[][] prompts, final int choices) {
        Integer selection = null;

        for (final Object[] prompt : prompts) {
            if (prompt.length == 1) {
                System.out.println(prompt[0]);
            } else {
                System.out.printf((String) prompt[0], Arrays.copyOfRange(prompt, 1, prompt.length));
            }
        }

        do {
            System.out.print("Choose an option: ");

            if (in.hasNextInt()) {
                selection = in.nextInt();

                if (selection == null || selection < 1 || selection > choices) {
                    System.err.println("Invalid selection.");
                    selection = null;
                } else {
                    return selection;
                }
            } else {
                if (in.hasNextLine()) {
                    System.err.println("Invalid selection.");
                    in.nextLine();
                } else {
                    in.close();
                    System.exit(0);
                }
            }
        } while (selection == null);

        // Unreachable ?
        in.close();
        System.exit(0);

        return 0;
    }

    /**
     * The main entry point.
     * 
     * @param args
     *            Not used
     */
    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);

        final Object[][] initialPrompt = new Object[][] {
                { "1. Show the table displaying the results of running all benchmarks." },
                { "%sMay take between 22 to 34 minutes to complete.\n", TAB },
                { "2. Show a performance graph of a particular benchmark function." },
                { "%sTakes around 5 to 8 seconds to complete.\n", TAB }
        };

        switch (getSelection(in, initialPrompt, 2)) {
            case 1:
                showResults();
                break;
            case 2:
                final Object[][] functionPrompt = new Object[1 + benchmarks.length][];

                functionPrompt[0] = new Object[] {
                        "\nAvailable Functions:"
                };

                for (int i = 0; i < benchmarks.length; i++) {
                    functionPrompt[i + 1] = new Object[] {
                            "%s%d. %s\n", TAB, i + 1, benchmarks[i].getTitle()
                    };
                }

                final int function = getSelection(in, functionPrompt, benchmarks.length) - 1;

                final Object[][] axisPrompt = new Object[][] {
                        { "\nAvailable Scales for the Y-Axis:" },
                        { "%s1. Linear\n", TAB },
                        { "%s2. Logarithmic\n", TAB }
                };

                switch (getSelection(in, axisPrompt, 2)) {
                    case 1:
                        viewPerformance(function, false);
                        break;
                    case 2:
                        viewPerformance(function, true);
                }

                break;
        }

        in.close();
    }

    /**
     * May take between 5.5 to 6.25 hours to complete with {@link SecureRandom},
     * 22 to 34 minutes with {@link Random}.
     */
    public static void showResults() {
        /*final StringBuilder output = new StringBuilder();
        final int lineLength = 78;

        output.append("\n");
        output.append("Benchmark Function | Mean Fitness Value | Standard Deviation of Fitness Values\n");
        output.append(createLine('=', lineLength));
        output.append("\n");

        // final long startTime = System.nanoTime(); 

        for (final Benchmark benchmark : benchmarks) {
            final SummaryStatistics sample = benchmark.getSample();

            final double mean = sample.getMean();
            final double stddev = sample.getStandardDeviation();

            output.append(String.format("%18s | %18.2f | %36.2f\n", benchmark.getShortTitle(), mean, stddev));
            output.append(createLine('-', lineLength));
            output.append("\n");
        }

        // System.out.println(System.nanoTime() - startTime);

        System.out.println(output.toString());*/

        final BlockingDeque<Vector<Object>> rows = new LinkedBlockingDeque<Vector<Object>>(benchmarks.length);

        new Thread(new StatisticsTable(rows)).start();

        for (final Benchmark benchmark : benchmarks) {
            final SummaryStatistics sample = benchmark.getSample();

            try {
                rows.put(new Vector<Object>() {
                    private static final long serialVersionUID = 429635938388808072L;

                    {
                        this.add(benchmark.getShortTitle());
                        this.add(sample.getMean());
                        this.add(sample.getStandardDeviation());
                    }
                });
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Takes around 1.5 minutes to complete with {@link SecureRandom}cureRandom, 5 to 8 seconds
     * with Random.
     * 
     * @param fitnessFuntion
     *            The {@link FitnessFunction} to be studied
     * @param logScale
     *            Whether to scale the y-axis logarithmically or not
     */
    public static void viewPerformance(final int fitnessFuntion, final boolean logScale) {
        benchmarks[fitnessFuntion].viewPerformance(logScale);
    }
}