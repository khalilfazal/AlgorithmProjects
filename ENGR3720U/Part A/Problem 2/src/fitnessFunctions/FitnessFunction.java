package fitnessFunctions;

import com.google.common.base.Function;

public abstract class FitnessFunction implements Function<double[], Double> {
    @Override
    public abstract Double apply(double[] population);

    @Override
    public abstract String toString();
}
