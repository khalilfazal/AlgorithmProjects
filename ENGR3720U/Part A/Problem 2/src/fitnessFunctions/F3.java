package fitnessFunctions;

/**
 * Schwefel's Problem 1.2 Function
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class F3 extends FitnessFunction {
    /**
     * @see fitnessFunctions.FitnessFunction#apply(double[])
     */
    @Override
    public Double apply(final double[] parameters) {
        double sum = 0.0;

        for (int i = 1; i <= parameters.length; i++) {
            double innerSum = 0.0;

            for (int j = 1; j <= i; j++) {
                innerSum += parameters[j - 1];
            }

            sum += Math.pow(innerSum, 2);
        }

        return sum;
    }

    /**
     * @see fitnessFunctions.FitnessFunction#toString()
     */
    @Override
    public String toString() {
        return "Schwefel's Problem 1.2";
    }
}