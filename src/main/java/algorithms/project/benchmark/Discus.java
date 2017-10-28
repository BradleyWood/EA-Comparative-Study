package algorithms.project.benchmark;

public class Discus implements Benchmark {

    @Override
    public Double benchmark(Double... params) {
        double fitness = 1000000 * params[0] * params[0];

        for (int i = 1; i < params.length; i++) {
            fitness += params[i] * params[i];
        }
        return fitness;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
