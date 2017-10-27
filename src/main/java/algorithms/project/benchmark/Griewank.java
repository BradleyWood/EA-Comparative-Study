package algorithms.project.benchmark;

public class Griewank implements Benchmark {

    @Override
    public Double benchmark(Double... params) {
        double lhs = 0;
        double rhs = 1;
        for (int i = 0; i < params.length; i++) {
            lhs += params[i] * params[i];
        }
        for (int i = 0; i < params.length; i++) {
            rhs *= Math.cos(params[i] / Math.sqrt(i + 1));
        }
        return 1 + (lhs / 4000.0f - rhs);
    }
}
