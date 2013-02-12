package fitnessFunctions;

public class F1 extends FitnessFunction {
    @Override
    public Double apply(final double[] population) {
        double sum = 0.0;

        for (final double element : population) {
            sum += Math.pow(element, 2);
        }

        return sum;
    }

    @Override
    public String toString() {
        return "1st De Jong";
    }
}