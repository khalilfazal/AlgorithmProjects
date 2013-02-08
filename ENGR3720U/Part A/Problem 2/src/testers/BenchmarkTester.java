package testers;

import main.Benchmark;
import fitnessFunctions.FitnessFunction;

public class BenchmarkTester {
    public static void main(final String[] args) {
        final Benchmark f1 = new Benchmark("1st De Jong", -5.12, 5.12, new FitnessFunction() {
            @Override
            public Double apply(final double[] parameters) {
                double output = 0.0;

                for (final Double parameter : parameters) {
                    output += Math.pow(parameter, 2);
                }

                return output;
            }
        });

        f1.viewPerformance();
    }
}
