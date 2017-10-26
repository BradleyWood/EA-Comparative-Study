package algorithms.project.benchmark;

public class Rastringin implements Benchmark {

    @Override
    public float benchmark(Float... params) {
        float fitness = 10 * params.length;
        for (int i = 0; i < params.length; i++) {
            fitness += params[i] * params[i] - 10 * Math.cos(2 * Math.PI * params[i]);
        }
        return fitness;
    }
}
