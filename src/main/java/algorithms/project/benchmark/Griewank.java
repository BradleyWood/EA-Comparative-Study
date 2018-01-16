package algorithms.project.benchmark;

import java.util.Vector;

public class Griewank implements FitnessFunction {

    @Override
    public Double fitness(Vector<Double> params) {
        double lhs = 0;
        double rhs = 1;
        int n = params.size();
        for (int i = 0; i < n; i++) {
            lhs += params.get(i) * params.get(i);
        }
        for (int i = 0; i < n; i++) {
            rhs *= Math.cos(params.get(i) / Math.sqrt(i + 1));
        }
        return lhs / 4000.0f - rhs + 1;
    }

    @Override
    public Double optimum() {
        return 0d;
    }
}
