package fitnessFunctions;


public class F1 implements FitnessFunction {
    @Override
    public Double apply(final Double[] parameters) {
        Double sum = 0.0;

        for (final Double parameter : parameters) {
            sum += Math.pow(parameter, 2);
        }

        return sum;
    }
}