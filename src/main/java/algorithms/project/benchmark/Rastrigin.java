package algorithms.project.benchmark;

import java.util.Vector;

public class Rastrigin implements FitnessFunction {

    @Override
    public Double fitness(Vector<Double> params) {
        int n = params.size();
        double fitness = 10 * n;
        for (int i = 0; i < n; i++) {
            fitness += params.get(i) * params.get(i) - 10 * Math.cos(2 * Math.PI * params.get(i));
        }
        return fitness;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
