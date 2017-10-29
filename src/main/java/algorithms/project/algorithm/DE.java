package algorithms.project.algorithm;

import algorithms.project.benchmark.Benchmark;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import static algorithms.project.util.Utility.*;

public class DE extends GeneticAlgorithm {

    private final Random random = new Random();

    private int dim;
    private int populationSize;
    private double crossoverRate;
    private double mutationFactor;
    private double varMin;
    private double varMax;

    protected DE() {
    }

    public Vector<Double> run(Benchmark benchmark) {
        ArrayList<Vector<Double>> population = createPopulation(populationSize, dim, varMin, varMax);

        double minFitness = Double.POSITIVE_INFINITY;
        for (int g = 0; g < 5000 * dim && minFitness > benchmark.optimum() + 1e-10; g++) {
            for (int i = 0; i < populationSize; i++) {
                int Xa = selectParent(i);
                int Xb = selectParent(i, Xa);
                int Xc = selectParent(i, Xa, Xb);

                Vector<Double> Xi = population.get(i);
                Vector<Double> Vi = add(population.get(Xa), scalarMult(sub(population.get(Xc), population.get(Xb)), mutationFactor));

                Vector<Double> Ui = new Vector<>();
                for (int j = 0; j < dim; j++) {
                    if (random.nextFloat() < crossoverRate) {
                        Ui.add(Vi.get(j));
                    } else {
                        Ui.add(population.get(i).get(j));
                    }
                }
                double bUi = benchmark.benchmark(Ui);
                double bXi = benchmark.benchmark(Xi);
                if (bUi < bXi) {
                    population.set(i, Ui);
                }
                if (bUi < minFitness) {
                    minFitness = bUi;
                }
                if (callback != null && i % callback.interval() == 0) {
                    callback.callback(population);
                }
            }
        }
        Double bestFitness = Double.POSITIVE_INFINITY;
        Vector<Double> bestVector = null;

        for (int i = 0; i < populationSize; i++) {
            Vector<Double> Pi = population.get(i);
            Double c = benchmark.benchmark(Pi);
            if (c < bestFitness) {
                bestFitness = c;
                bestVector = Pi;
            }
        }
        return bestVector;
    }

    private int selectParent(int... indices) {
        int r;

        do {
            r = random.nextInt(populationSize);
        } while (contains(r, indices));

        return r;
    }

    private boolean contains(int target, int... values) {
        for (int value : values) {
            if (value == target)
                return true;
        }
        return false;
    }

    public void setVarRange(double varMin, double varMax) {
        this.varMin = varMin;
        this.varMax = varMax;
    }

    public int getDimentions() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public double getMutationFactor() {
        return mutationFactor;
    }

    public void setMutationFactor(double mutationFactor) {
        this.mutationFactor = mutationFactor;
    }
}
