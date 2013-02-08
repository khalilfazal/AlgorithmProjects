package fitnessFunctions;

public class F2 implements FitnessFunction {
    @Override
    public Double apply(final double[] parameters) {
        double sum = 0.0;

        for (int i = 1; i <= parameters.length; i++) {
            sum += i * Math.pow(parameters[i - 1], 2);
        }

        return sum;
    }
}