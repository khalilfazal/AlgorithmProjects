package fitnessFunctions;

import com.google.common.base.Function;

/**
 * A function which is mapped from <code>double[] -> double</code>
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 * @author Rayhaan Shakeel
 * @studentNumber 100425726
 * @author Baldip Bhogal
 * @studentNumber 100252234
 */
public abstract class FitnessFunction implements Function<double[], Double> {

    /**
     * Evaluates the {@link FitnessFunction} given parameters
     * 
     * @param parameters
     *            The function's parameters
     * @return The function's result
     * @see com.google.common.base.Function#apply(java.lang.Object)
     */
    @Override
    public abstract Double apply(double[] parameters);

    /**
     * Shows the {@link FitnessFunction}'s Name
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public abstract String toString();
}
