package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fitnessFunctions.FitnessFunction;

/**
 * Models the differential evolution algorithm
 * 
 * @author Khalil Fazal
 * @studentNumber 100425046
 */
public class DifferentialEvolution {
    // A uniformly distributed random number generator
    // private static final Random generator = new SecureRandom();

    // A faster but weaker random number generator
    private static final Random generator = new Random();

    /**
     * Casts an object to a double. Needed if the object is an instance of an
     * Integer.
     * 
     * @param object
     *            the object that needs to be cast
     * @return a casted double
     */
    private static double castToDouble(final Object object) {
        if (object instanceof Integer) {
            return ((Integer) object).doubleValue();
        } else {
            return (double) object;
        }
    }

    /**
     * Generates a random double in a range
     * 
     * @param low
     *            the range's lower bound
     * @param high
     *            the range's upper bound
     * @return a random double
     */
    private static double nextDoubleRange(final double low, final double high) {
        return low + (high - low) * generator.nextDouble();
    }

    // A setting to indicate whether to maximize or minimize the fitness
    // function
    private final boolean max;

    // The problem's dimensionality
    private final int dimensions;

    // Population size
    private final int size;

    // Mutation constant
    private final double mutation;

    // Crossover rate
    private final double crossover;

    // The lower bound of each parameter in a solution
    private final double lowerBound;

    // the upper bound of each parameter in a solution
    private final double upperBound;

    // Objective function or fitness function
    private final FitnessFunction function;

    // Population of possible solutions
    private final List<double[]> population;

    // Fitness values for each solution in the population
    private final List<Double> fitnesses;

    /**
     * Sets the settings for the algorithm and generates an initial population.
     * 
     * @param params
     *            settings for the differential evolution algorithm
     */
    public DifferentialEvolution(final Map<String, Object> params) {
        this.max = (boolean) params.get("max");
        this.dimensions = (int) params.get("dimensions");
        this.size = (int) params.get("size");
        this.mutation = castToDouble(params.get("mutation"));
        this.crossover = castToDouble(params.get("crossover"));
        this.lowerBound = castToDouble(params.get("lower"));
        this.upperBound = castToDouble(params.get("upper"));
        this.function = (FitnessFunction) params.get("fitness");

        // Can not proceed if the dimension is negative.
        if (this.dimensions < 0) {
            throw new IllegalArgumentException("Dimension must not be negative.");
        }

        // Can not proceed if the population size is not >= 4.
        if (this.size < 4) {
            throw new IllegalArgumentException("Population size must be >= 4.");
        }

        // Can not proceed if the mutation constant is not in the range [0, 2].
        if (this.mutation < 0 || this.mutation > 2) {
            throw new IllegalArgumentException("The mutation constant must be in the range [0, 2].");
        }

        // Can not proceed if the crossover rate is not in the range (0, 1).
        if (this.crossover <= 0 || this.crossover >= 1) {
            throw new IllegalArgumentException("The crossover rate must be in the range (0, 1).");
        }

        // Can not proceed if lowerBound is not < upperBound.
        if (this.lowerBound >= this.upperBound) {
            throw new IllegalArgumentException("The lower bound must be less than the upper bound.");
        }

        this.population = new ArrayList<double[]>();
        this.createInitial();

        this.fitnesses = new ArrayList<Double>();
        this.calculateFitnesses();
    }

    /**
     * Generates the initial random population which is uniformly distributed.
     */
    private void createInitial() {
        for (int i = 0; i < this.size; i++) {
            final double[] individual = new double[this.dimensions];

            for (int j = 0; j < this.dimensions; j++) {
                individual[j] = nextDoubleRange(this.lowerBound, this.upperBound);
            }

            this.population.add(individual);
        }
    }

    /**
     * Calculates the fitness values for each solution in the population
     */
    private void calculateFitnesses() {
        this.fitnesses.clear();

        for (final double[] solution : this.population) {
            this.fitnesses.add(this.function.apply(solution));
        }
    }

    /**
     * Generates the next population.
     */
    public void repopulate() {
        final List<double[]> nextGen = new ArrayList<double[]>();

        for (int i = 0; i < this.size; i++) {
            final double[] original = this.population.get(i);
            final double originalFitness = this.fitnesses.get(i);

            final double[][] randomParents = this.distinct(i);
            final double[] mutation = this.mutate(randomParents);
            final double[] crossOver = this.crossOver(original, mutation);

            if (this.selectShuffled(originalFitness, crossOver)) {
                nextGen.add(crossOver);
            } else {
                nextGen.add(original);
            }
        }

        this.population.clear();
        this.population.addAll(nextGen);
        this.calculateFitnesses();
    }

    /**
     * Calculates the best fitness value in the current generation.
     * 
     * @return the best fitness value in the current generation
     */
    public Double bestFitnessValue() {
        final List<Double> copy = new ArrayList<Double>(this.fitnesses);

        if (copy.isEmpty()) {
            return null;
        } else {
            Double best = copy.remove(0);

            while (!copy.isEmpty()) {
                final double current = copy.remove(0);

                if (this.max == current > best) {
                    best = current;
                }
            }

            return best;
        }
    }

    /**
     * Choose 3 distinct parents from the current population ignoring an index.
     * 
     * @param ignore
     *            the index from population to ignore
     * @return 3 distinct parents
     */
    private double[][] distinct(final int ignore) {
        final double[][] output = new double[3][this.dimensions];
        final List<double[]> copy = new ArrayList<double[]>(this.population);

        for (int i = 0; i < output.length; i++) {
            final int index = generator.nextInt(copy.size());
            output[i] = copy.remove(index).clone();
        }

        return output;
    }

    /**
     * Creates a mutated vector.
     * 
     * @param randomParents
     *            From which a mutated vector is created
     * @return a mutated vector
     */
    private double[] mutate(final double[][] randomParents) {
        final double[] mutation = this.add(this.weigh(this.difference(randomParents[0], randomParents[1])), randomParents[2]);
        return this.enforceBounds(mutation);
    }

    /**
     * Enforces the lower and upper bounds of a solution's parameter
     * 
     * @param mutation2
     *            the vector whose bounds need to be checked
     * @return a vector whose bounds are checked
     */
    private double[] enforceBounds(final double[] mutation2) {
        for (int i = 0; i < mutation2.length; i++) {
            if (mutation2[i] > this.upperBound) {
                mutation2[i] = this.upperBound;
            } else if (mutation2[i] < this.lowerBound) {
                mutation2[i] = this.lowerBound;
            }
        }

        return mutation2;
    }

    /**
     * Finds the difference between two vectors.
     * 
     * @param randomParents
     *            The first vector
     * @param randomParents2
     *            The second vector
     * @return The difference
     */
    private double[] difference(final double[] randomParents, final double[] randomParents2) {
        for (int i = 0; i < this.dimensions; i++) {
            randomParents[i] -= randomParents2[i];
        }

        return randomParents;
    }

    /**
     * Amplifies the vector by the mutation factor.
     * 
     * @param ds
     *            The vector which will be amplified
     * @return the amplified vector
     */
    private double[] weigh(final double[] ds) {
        for (int i = 0; i < this.dimensions; i++) {
            ds[i] *= this.mutation;
        }

        return ds;
    }

    /**
     * Finds the sum between two vectors.
     * 
     * @param ds
     *            The first vector
     * @param randomParents
     *            The second vector
     * @return The sum
     */
    private double[] add(final double[] ds, final double[] randomParents) {
        for (int i = 0; i < this.dimensions; i++) {
            ds[i] += randomParents[i];
        }

        return ds;
    }

    /**
     * Shuffles two competing vectors to generate new solutions and the increase
     * the diversity of the population. It is assured that the shuffled vector
     * contains at least one parameter from the mutated vector.
     * 
     * @param original
     *            The original vector
     * @param mutation2
     *            The mutated vector
     * @return A new shuffled vector
     */
    private double[] crossOver(final double[] original, final double[] mutation2) {
        final double[] shuffled = new double[original.length];
        final int randomIndex;

        if (this.dimensions == 0) {
            randomIndex = 0;
        } else {
            randomIndex = generator.nextInt(this.dimensions);
        }

        for (int i = 0; i < this.dimensions; i++) {
            if (nextDoubleRange(0, 1) <= this.crossover || i == randomIndex) {
                shuffled[i] = mutation2[i];
            } else {
                shuffled[i] = original[i];
            }
        }

        return shuffled;
    }

    /**
     * Decides whether to select the shuffled vector for the next generation by
     * the fitness function.
     * 
     * @param originalFitness
     *            The original vector's fitness
     * @param shuffled
     *            The shuffled vector
     * @return The chosen vector
     */
    private boolean selectShuffled(final double originalFitness, final double[] shuffled) {
        final boolean condition = originalFitness > this.function.apply(shuffled);

        /*  condition | this.max | return
         * ===========+==========+========
         *    false   |   false  | false
         * -----------+----------+--------
         *    false   |   true   | true
         * -----------+----------+--------
         *    true    |   false  | true
         * -----------+----------+--------
         *    true    |   true   | false
         * -----------+----------+--------
         */

        return condition != this.max;
    }

    /**
     * Shows a string representation of the current generation
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder output = new StringBuilder();

        for (int i = 0; i < this.dimensions + 2; i++) {
            for (int j = 0; j < this.size; j++) {
                switch (i) {
                    case 0:
                        output.append(String.format("%11s %2d |", "individual", j));
                        break;
                    case 1:
                        output.append(String.format("%14.2f |", this.fitnesses.get(j)));
                        break;
                    default:
                        output.append(String.format("%14.2f |", this.population.get(j)[i - 2]));
                }
            }

            output.append("\n");

            char separator;

            if (i == 0) {
                separator = '%';
            } else if (i == 1) {
                separator = '=';
            } else {
                separator = '-';
            }

            output.append(Reporter.createLine(separator, 16 * this.size));

            output.append("\n");
        }

        return output.toString();
    }

    public String getFunctionName() {
        return this.function.toString();
    }
}
