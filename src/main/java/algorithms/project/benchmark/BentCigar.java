package algorithms.project.benchmark;

public class BentCigar implements Benchmark {

    @Override
    public Double benchmark(Double... params) {
        double sq0 = params[0] * params[0];

        float sumOfSquares1ToN = 0;
        for (int i = 1; i < params.length; i++) {
            sumOfSquares1ToN += params[i] * params[i];
        }
        return sq0 + 1000000 * sumOfSquares1ToN;
    }
}
