package algorithms.project.benchmark;

import java.util.Vector;

public class Rosenbrock implements Benchmark {

    @Override
    public Double benchmark(Vector<Double> params) {
        double fitness = 0;
        int n = params.size();
        for (int i = 0; i < n - 1; i++) {
            fitness += 100 * (Math.pow(params.get(i) * params.get(i) - params.get(i + 1), 2) + Math.pow(params.get(i) - 1, 2));
        }
        return fitness;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
