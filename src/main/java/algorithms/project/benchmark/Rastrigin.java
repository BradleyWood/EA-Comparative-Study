package algorithms.project.benchmark;

public class Rastrigin implements Benchmark {

    @Override
    public Double benchmark(Double... params) {
        double fitness = 10 * params.length;
        for (int i = 0; i < params.length; i++) {
            fitness += params[i] * params[i] - 10 * Math.cos(2 * Math.PI * params[i]);
        }
        return fitness;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
