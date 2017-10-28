package algorithms.project.benchmark;

public class Katsuura implements Benchmark {

    @Override
    public Double benchmark(Double... params) {
        double product = 1;
        for (int i = 0; i < params.length; i++) {
            double summation = 0;
            for (int j = 1; j <= 32; j++) {
                double term = Math.pow(2, j) * params[i];
                summation += Math.abs(term - Math.round(term)) / Math.pow(2, j);
            }
            product *= Math.pow(1 + ((i + 1) * summation), 10.0 / Math.pow(params.length, 1.2));
        }
        return (10.0 / params.length * params.length) * product - (10.0 / params.length * params.length);
    }

    @Override
    public Double optimum() {
        return Double.NEGATIVE_INFINITY;
    }
}
