package algorithms.project.benchmark;

import java.util.Vector;

/**
 * High conditioned elliptic function
 */
public class HCE implements Benchmark {

    @Override
    public Double benchmark(Vector<Double> params) {
        double fitness = 0;
        int n = params.size();

        for (int i = 0; i < n; i++) {
            fitness += Math.pow(1000000, (i - 1) / (n - 1)) * params.get(i) * params.get(i);
        }
        return fitness;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
