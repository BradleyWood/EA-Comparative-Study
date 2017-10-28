package algorithms.project.benchmark;

public class Ackley implements Benchmark {

    @Override
    public Double benchmark(Double... params) {
        double sumOfSquares = 0;
        float s2 = 0;

        for (int i = 0; i < params.length; i++) {
            sumOfSquares += params[i] * params[i];
            s2 += Math.cos(2 * Math.PI * params[i]);
        }
        return -20f * Math.exp(-0.2f * Math.sqrt(sumOfSquares / params.length)) - Math.exp(s2 / params.length) + 20f + Math.E;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
