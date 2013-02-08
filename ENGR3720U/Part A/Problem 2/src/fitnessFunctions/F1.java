package fitnessFunctions;

public class F1 implements FitnessFunction {
    @Override
    public Double apply(final double[] parameters) {
        double sum = 0.0;

        for (final double parameter : parameters) {
            sum += Math.pow(parameter, 2);
        }

        return sum;
    }
}