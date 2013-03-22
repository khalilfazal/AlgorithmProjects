package fitnessFunctions;

/**
 * Axis Parallel Hyper-Ellipsoid Function
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class F2 extends FitnessFunction {

    /**
     * <a href="http://latex.codecogs.com/svg.latex?f_2\left(X\right)=\sum_{i=1}^nix_i^2">Formula in LaTeX</a>
     * 
     * Worst-case time complexity: O(parameters)
     * 
     * @see fitnessFunctions.FitnessFunction#apply(double[])
     */
    @Override
    public Double apply(final double[] parameters) {
        double sum = 0.0;

        for (int i = 1; i <= parameters.length; i++) {
            sum += i * Math.pow(parameters[i - 1], 2);
        }

        return sum;
    }

    /**
     * @see fitnessFunctions.FitnessFunction#toString()
     */
    @Override
    public String toString() {
        return "Axis Parallel Hyper-Ellipsoid";
    }
}