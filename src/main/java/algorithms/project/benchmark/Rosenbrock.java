package algorithms.project.benchmark;

public class Rosenbrock implements Benchmark {

    @Override
    public Double benchmark(Double... params) {
        double fitness = 0;

        for (int i = 0; i < params.length - 1; i++) {
            fitness += 100 * (Math.pow(params[i] * params[i] - params[i + 1], 2) + Math.pow(params[i] - 1, 2));
        }
        return fitness;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
