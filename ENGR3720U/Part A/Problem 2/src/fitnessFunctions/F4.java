package fitnessFunctions;

public class F4 implements FitnessFunction {
    @Override
    public Double apply(final double[] parameters) {
        double sum = 0.0;

        for (int i = 1; i <= parameters.length - 1; i++) {
            final double param = parameters[i - 1];

            sum += 100 * Math.pow(parameters[i] - Math.pow(param, 2), 2) + Math.pow(1 - param, 2);
        }

        return sum;
    }
}