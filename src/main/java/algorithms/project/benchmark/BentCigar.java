package algorithms.project.benchmark;

import algorithms.project.algorithm.FitnessFunction;

import java.util.Vector;

public class BentCigar implements FitnessFunction {

    @Override
    public Double fitness(Vector<Double> params) {
        double sq0 = params.get(0) * params.get(0);
        int n = params.size();
        float sumOfSquares1ToN = 0;
        for (int i = 1; i < n; i++) {
            sumOfSquares1ToN += params.get(i) * params.get(i);
        }
        return sq0 + 1000000 * sumOfSquares1ToN;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
