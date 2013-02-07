package fitnessFunctions;

public class F5 implements FitnessFunction {
    @Override
    public Double apply(final Double[] parameters) {
        Double sum = (double) (10 * parameters.length);

        for (int i = 1; i <= parameters.length - 1; i++) {
            final Double param = parameters[i - 1];

            sum += Math.pow(param, 2) - 10 * Math.cos(2 * Math.PI * param);
        }

        return sum;
    }
}