package algorithms.project.benchmark;

import java.util.Vector;

public class Discus implements Benchmark {

    @Override
    public Double benchmark(Vector<Double> params) {
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
