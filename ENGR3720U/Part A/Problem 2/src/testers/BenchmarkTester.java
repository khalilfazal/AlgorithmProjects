package testers;

import main.Benchmark;
import fitnessFunctions.FitnessFunction;

public class BenchmarkTester {
    public static void main(final String[] args) {
        final Benchmark f1 = new Benchmark(new FitnessFunction() {
            @Override
            public Double apply(final double[] parameters) {
                double output = 0.0;

                for (final Double parameter : parameters) {
                    output += Math.pow(parameter, 2);
                }

                return output;
            }

            @Override
            public String toString() {
                return "Sum of Series";
            }
        }, -5.12, 5.12);

        f1.viewPerformance();
    }
}
