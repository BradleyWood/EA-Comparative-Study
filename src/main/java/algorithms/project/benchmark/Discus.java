package algorithms.project.benchmark;

import algorithms.project.algorithm.FitnessFunction;

import java.util.Vector;

public class Discus implements FitnessFunction {

    @Override
    public Double fitness(Vector<Double> params) {
        double fitness = 1000000 * params.get(0) * params.get(0);
        int n = params.size();
        for (int i = 1; i < n; i++) {
            fitness += params.get(i) * params.get(i);
        }
        return fitness;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
