package algorithms.project.benchmark;

import java.util.Vector;

public class Katsuura implements Benchmark {

    @Override
    public Double benchmark(Vector<Double> params) {
        double product = 1;
        int n = params.size();
        for (int i = 0; i < n; i++) {
            double summation = 0;
            for (int j = 1; j <= 32; j++) {
                double term = Math.pow(2, j) * params.get(i);
                summation += Math.abs(term - Math.round(term)) / Math.pow(2, j);
            }
            product *= Math.pow(1 + ((i + 1) * summation), 10.0 / Math.pow(n, 1.2));
        }
        return (10.0 / n * n) * product - (10.0 / n * n);
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
