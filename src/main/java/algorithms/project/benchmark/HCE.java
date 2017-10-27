package algorithms.project.benchmark;

/**
 * High conditioned elliptic function
 */
public class HCE implements Benchmark {

    @Override
    public Double benchmark(Double... params) {
        double fitness = 0;

        for (int i = 0; i < params.length; i++) {
            fitness += Math.pow(1000000, (i - 1) / (params.length - 1)) * params[i] * params[i];
        }
        return fitness;
    }
}
