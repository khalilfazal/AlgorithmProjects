package testers;

import java.util.Map;

import main.DifferentialEvolution;

import com.google.common.base.Function;
import com.google.gson.internal.StringMap;

public class DifferentialEvolutionTester {
    public static void main(final String[] args) {
        final Map<String, Object> params = new StringMap<Object>();
        params.put("max", false);
        params.put("dimensions", 5);
        params.put("size", 6);
        params.put("mutation", 0.80);
        params.put("crossover", 0.50);
        params.put("lower", -5);
        params.put("upper", 5);
        params.put("fitness", new Function<Double[], Double>() {
            @Override
            public Double apply(final Double[] parameters) {
                double sum = 0;

                for (final double param : parameters) {
                    sum += param;
                }

                return sum;
            }
        });

        final DifferentialEvolution population = new DifferentialEvolution(params);

        System.out.println(population);
        population.repopulate();
        System.out.println(population);
        System.out.println(population.bestFitnessValue());
    }
}