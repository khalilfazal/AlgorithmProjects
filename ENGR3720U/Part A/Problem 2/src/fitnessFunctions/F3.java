package fitnessFunctions;

public class F3 implements FitnessFunction {
    @Override
    public Double apply(final Double[] parameters) {
        Double sum = 0.0;

        for (int i = 1; i <= parameters.length; i++) {
            Double innerSum = 0.0;

            for (int j = 1; j <= i; j++) {
                innerSum += parameters[j - 1];
            }

            sum += Math.pow(innerSum, 2);
        }

        return sum;
    }
}