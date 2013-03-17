package fitnessFunctions;

/**
 * Rosenbrock's Valley Function
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class F4 extends FitnessFunction {
    /**
     * @see fitnessFunctions.FitnessFunction#apply(double[])
     */
    @Override
    public Double apply(final double[] parameters) {
        double sum = 0.0;

        for (int i = 1; i <= parameters.length - 1; i++) {
            final double param = parameters[i - 1];

            sum += 100 * Math.pow(parameters[i] - Math.pow(param, 2), 2) + Math.pow(1 - param, 2);
        }

        return sum;
    }

    /**
     * @see fitnessFunctions.FitnessFunction#toString()
     */
    @Override
    public String toString() {
        return "Rosenbrock's Valley";
    }
}