package algorithms.project.benchmark;

public class Griewank implements Benchmark {

    @Override
    public float benchmark(Float... params) {
        float lhs = 0;
        float rhs = 1;
        for (int i = 0; i < params.length; i++) {
            lhs += params[i] * params[i];
        }
        for (int i = 0; i < params.length; i++) {
            rhs *= Math.cos(params[i] / Math.sqrt(i + 1));
        }
        return 1 + (lhs / 4000.0f - rhs);
    }
}
