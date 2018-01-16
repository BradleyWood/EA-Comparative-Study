package algorithms.project.benchmark;

import java.util.Vector;

public class Weierstrass implements FitnessFunction {


    private final double a;
    private final double b;
    private double rhs;
    private final int kMax;

    public Weierstrass(double a, double b, int kMax) {
        this.a = a;
        this.b = b;
        this.kMax = kMax;
        double summation = 0;
        for(int i = 0; i < kMax + 1; i++) {
            summation += Math.pow(a, i) * Math.cos(2 * Math.PI * Math.pow(b, i) * 0.5);
        }
        rhs = summation;
    }

    public Weierstrass() {
        this(0.5, 3, 20);
    }

    @Override
    public Double fitness(Vector<Double> vector) {
        double summation = 0;
        for (int i = 0; i < vector.size(); i++) {
            for (int j = 0; j < kMax + 1; j++) {
                summation += Math.pow(a, j) * Math.cos(2 * Math.PI * Math.pow(b, j) * (vector.get(i) + 0.5));
            }
        }
        return summation - vector.size() * rhs;
    }

    @Override
    public Double optimum() {
        return Double.NEGATIVE_INFINITY;
    }
}
