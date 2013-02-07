package main;

import java.security.SecureRandom;
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
    private static final Random generator = new SecureRandom();

    /**
     * Casts an object to a double Needed if the object is an instance of an
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
    private final FitnessFunction fitness;

    // Population of possible solutions
    private final List<Double[]> population;

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

        this.fitness = (FitnessFunction) params.get("fitness");

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

        this.population = new ArrayList<Double[]>();

        this.createInitial();

        this.fitnesses = new ArrayList<Double>();

        this.calculateFitnesses();
    }

    /**
     * Generates the initial random population which is uniformly distributed.
     */
    private void createInitial() {
        for (int i = 0; i < this.size; i++) {
            final Double[] individual = new Double[this.dimensions];

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

        for (final Double[] solution : this.population) {
            this.fitnesses.add(this.fitness.apply(solution));
        }
    }

    /**
     * Generates the next population.
     */
    public void repopulate() {
        final List<Double[]> nextGen = new ArrayList<Double[]>();

        for (int i = 0; i < this.size; i++) {
            final Double[] original = this.population.get(i);
            final Double originalFitness = this.fitnesses.get(i);

            final Double[][] randomParents = this.distinct(i);
            final Double[] mutation = this.mutate(randomParents);
            final Double[] crossOver = this.crossOver(original, mutation);

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
    public double bestFitnessValue() {
        double best;

        if (this.max) {
            best = Double.NEGATIVE_INFINITY;
        } else {
            best = Double.POSITIVE_INFINITY;
        }

        for (final double current : this.fitnesses) {
            if (this.max == current > best) {
                best = current;
            }
        }

        return best;
    }

    /**
     * Choose 3 distinct parents from the current population ignoring an index.
     *
     * @param ignore
     *            the index from population to ignore
     * @return 3 distinct parents
     */
    private Double[][] distinct(final int ignore) {
        final Double[][] output = new Double[3][this.dimensions];
        final List<Double[]> copy = new ArrayList<Double[]>(this.population);

        for (int i = 0; i < output.length; i++) {
            final int index = generator.nextInt(copy.size());
            output[i] = copy.remove(index).clone();
        }

        return output;
    }

    /**
     * Creates a mutated vector.
     *
     * @param vectors
     *            From which a mutated vector is created
     * @return a mutated vector
     */
    private Double[] mutate(final Double[][] vectors) {
        final Double[] mutation = this.add(this.weigh(this.difference(vectors[0], vectors[1])), vectors[2]);
        return this.enforceBounds(mutation);
    }

    /**
     * Enforces the lower and upper bounds of a solution's parameter
     *
     * @param vector
     *            the vector whose bounds need to be checked
     * @return a vector whose bounds are checked
     */
    private Double[] enforceBounds(final Double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] > this.upperBound) {
                vector[i] = this.upperBound;
            } else if (vector[i] < this.lowerBound) {
                vector[i] = this.lowerBound;
            }
        }

        return vector;
    }

    /**
     * Finds the difference between two vectors.
     *
     * @param vector1
     *            The first vector
     * @param vector2
     *            The second vector
     * @return The difference
     */
    private Double[] difference(final Double[] vector1, final Double[] vector2) {
        for (int i = 0; i < this.dimensions; i++) {
            vector1[i] -= vector2[i];
        }

        return vector1;
    }

    /**
     * Amplifies the vector by the mutation factor.
     *
     * @param vector
     *            The vector which will be amplified
     * @return the amplified vector
     */
    private Double[] weigh(final Double[] vector) {
        for (int i = 0; i < this.dimensions; i++) {
            vector[i] *= this.mutation;
        }

        return vector;
    }

    /**
     * Finds the sum between two vectors.
     *
     * @param vector1
     *            The first vector
     * @param vector2
     *            The second vector
     * @return The sum
     */
    private Double[] add(final Double[] vector1, final Double[] vector2) {
        for (int i = 0; i < this.dimensions; i++) {
            vector1[i] += vector2[i];
        }

        return vector1;
    }

    /**
     * Shuffles two competing vectors to generate new solutions and the increase
     * the diversity of the population. It is assured that the shuffled vector
     * contains at least one parameter from the mutated vector.
     *
     * @param original
     *            The original vector
     * @param mutated
     *            The mutated vector
     * @return A new shuffled vector
     */
    private Double[] crossOver(final Double[] original, final Double[] mutated) {
        final int randomIndex = generator.nextInt(this.dimensions);
        final Double[] shuffled = new Double[original.length];

        for (int i = 0; i < this.dimensions; i++) {
            if (nextDoubleRange(0, 1) <= this.crossover || i == randomIndex) {
                shuffled[i] = mutated[i];
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
    private boolean selectShuffled(final Double originalFitness, final Double[] shuffled) {
        final boolean condition = originalFitness > this.fitness.apply(shuffled);

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
}
