package algorithms.project.benchmark;

import java.util.Vector;

public class Ackley implements Benchmark {

    @Override
    public Double benchmark(Vector<Double> params) {
        double sumOfSquares = 0;
        float s2 = 0;
        int n = params.size();
        for (int i = 0; i < n; i++) {
            sumOfSquares += params.get(i) * params.get(i);
            s2 += Math.cos(2 * Math.PI * params.get(i));
        }
        return -20f * Math.exp(-0.2f * Math.sqrt(sumOfSquares / n)) - Math.exp(s2 / n) + 20f + Math.E;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
