package algorithms.project.benchmark;

public class Discus implements Benchmark {

    @Override
    public float benchmark(Float... params) {
        float fitness = 1000000 * params[0] * params[0];

        for (int i = 1; i < params.length; i++) {
            fitness += params[i] * params[i];
        }
        return fitness;
    }
}
