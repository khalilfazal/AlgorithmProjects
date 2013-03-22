package fitnessFunctions;

/**
 * 1st De Jong Function
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class F1 extends FitnessFunction {

    /**
     * <a href="http://latex.codecogs.com/svg.latex?f_1\left(X\right)=\sum_{i=1}^nx_i^2">Formula in LaTeX</a>
     * 
     * Worst-case time complexity: O(parameters)
     * 
     * @see fitnessFunctions.FitnessFunction#apply(double[])
     */
    @Override
    public Double apply(final double[] parameters) {
        double sum = 0.0;

        for (final double element : parameters) {
            sum += Math.pow(element, 2);
        }

        return sum;
    }

    /**
     * @see fitnessFunctions.FitnessFunction#toString()
     */
    @Override
    public String toString() {
        return "1st De Jong";
    }
}