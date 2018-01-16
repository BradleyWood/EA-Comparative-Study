package algorithms.project.algorithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import static algorithms.project.util.Utility.*;

public class DE extends EvolutionaryAlgorithm {

    private final Random random = new Random();

    private double crossoverRate;
    private double mutationFactor;

    protected DE() {
    }

    public Vector<Double> run(FitnessFunction fitnessFunction) {
        ArrayList<Vector<Double>> population = createPopulation(populationSize, dim, varMin, varMax);

        double minFitness = Double.POSITIVE_INFINITY;
        int nfc = 3000 * dim;
        for (int g = 0; g < nfc && minFitness > fitnessFunction.optimum() + 1e-10; g++) {
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
                double bUi = fitnessFunction.fitness(Ui);
                double bXi = fitnessFunction.fitness(Xi);
                if (bUi < bXi) {
                    population.set(i, Ui);
                }
                if (bUi < minFitness) {
                    minFitness = bUi;
                }
            }
            for (Callback callback : callbacks) {
                if (callback != null && g % callback.interval() == 0) {
                    callback.callback(population);
                }
            }
        }
        Double bestFitness = Double.POSITIVE_INFINITY;
        Vector<Double> bestVector = null;

        for (int i = 0; i < populationSize; i++) {
            Vector<Double> Pi = population.get(i);
            Double c = fitnessFunction.fitness(Pi);
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
