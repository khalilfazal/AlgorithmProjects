package fitnessFunctions;

/**
 * Rastrigin's Function
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public class F5 extends FitnessFunction {

    /**
     * <a href="http://latex.codecogs.com/svg.latex?f_5\left(X\right)=10n+\sum_{i=1}^n\left(x_i^2-10\cos\left(2\pi x_i\right)\right)">Formula in LaTeX</a>
     * 
     * Worst-case time complexity: O(parameters)
     * 
     * @see fitnessFunctions.FitnessFunction#apply(double[])
     */
    @Override
    public Double apply(final double[] parameters) {
        double sum = 10 * parameters.length;

        for (int i = 1; i <= parameters.length - 1; i++) {
            final double param = parameters[i - 1];

            sum += Math.pow(param, 2) - 10 * Math.cos(2 * Math.PI * param);
        }

        return sum;
    }

    /**
     * @see fitnessFunctions.FitnessFunction#toString()
     */
    @Override
    public String toString() {
        return "Rastrigin's Function";
    }
}