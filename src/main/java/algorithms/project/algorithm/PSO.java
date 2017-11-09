package algorithms.project.algorithm;

import algorithms.project.benchmark.Benchmark;

import static algorithms.project.util.Utility.*;

import java.util.Random;
import java.util.Vector;

public class PSO extends GeneticAlgorithm {

    private static final Random random = new Random();

    private int populationSize;
    private int dim;
    private double varMin;
    private double varMax;

    private double c1;
    private double c2;
    private double wStart;
    private double wFinish;

    protected PSO() {
    }

    @Override
    public Vector<Double> run(Benchmark benchmark) {
        Particle[] particles = new Particle[populationSize];
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle();
        }
        double inertiaWeight = wStart;
        double gBestFitness = Double.POSITIVE_INFINITY;
        Vector<Double> gBest = null;

        for (int i = 0; i < particles.length; i++) {
            double fitness = benchmark.benchmark(particles[i].getPosition());
            if (fitness < gBestFitness) {
                gBestFitness = fitness;
                gBest = particles[i].getPosition();
            }
        }

        for (int i = 0; i < 5000 * dim; i++) {
            for (int j = 0; j < populationSize; j++) {
                Vector<Double> xi = particles[j].getPosition();
                Vector<Double> vi = particles[j].getVelocity();

                for (int k = 0; k < dim; k++) {
                    vi.set(k, inertiaWeight * vi.get(k) + (random.nextDouble() * c1 * (particles[j].getpBest().get(k) - xi.get(k)))
                            + (random.nextDouble() * c2 * (gBest.get(k) - xi.get(k))));
                    if (vi.get(k) > 200d) {
                        vi.set(k, 200d);
                    }
                    if (vi.get(k) < -200) {
                        vi.set(k, -200d);
                    }
                    xi.set(k, xi.get(k) + vi.get(k));
                }

                double XiFitness = benchmark.benchmark(xi);
                if (XiFitness < benchmark.benchmark(particles[j].getpBest())) {
                    Vector<Double> copy = new Vector<>();
                    for (int n = 0; n < dim; n++) {
                        copy.add(xi.get(n));
                    }
                    particles[j].setpBest(copy, XiFitness);
                }
                if (XiFitness < gBestFitness) {
                    gBestFitness = XiFitness;
                    Vector<Double> copy = new Vector<>();
                    for (int n = 0; n < dim; n++) {
                        copy.add(xi.get(n));
                    }
                    gBest = copy;
                    gBestFitness = XiFitness;
                }
                if (i % callback.interval() == 0) {
                    // todo graphics callback
                }
            }
            inertiaWeight = wStart - i * (wStart - wFinish);
        }

        return gBest;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public double getVarMin() {
        return varMin;
    }

    public void setVarMin(double varMin) {
        this.varMin = varMin;
    }

    public double getVarMax() {
        return varMax;
    }

    public void setVarMax(double varMax) {
        this.varMax = varMax;
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getC2() {
        return c2;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public double getwStart() {
        return wStart;
    }

    public void setwStart(double wStart) {
        this.wStart = wStart;
    }

    public double getwFinish() {
        return wFinish;
    }

    public void setwFinish(double wFinish) {
        this.wFinish = wFinish;
    }

    private class Particle {
        private Vector<Double> position;
        private Vector<Double> velocity;
        private Vector<Double> pBest;
        private double pBestFitness;

        public Particle() {
            position = createVector(dim, varMin, varMax);
            velocity = createVector(dim, 0, 0);
            pBest = new Vector<>();
            for (int i = 0; i < dim; i++) {
                pBest.add(position.get(i));
            }
        }

        public Vector<Double> getPosition() {
            return position;
        }

        public Vector<Double> getVelocity() {
            return velocity;
        }

        public Vector<Double> getpBest() {
            return pBest;
        }

        public void setPosition(Vector<Double> position) {
            this.position = position;
        }

        public void setVelocity(Vector<Double> velocity) {
            this.velocity = velocity;
        }

        public void setpBest(Vector<Double> pBest, double pBestFitness) {
            this.pBest = pBest;
            this.pBestFitness = pBestFitness;
        }

        public double getpBestFitness() {
            return pBestFitness;
        }
    }
}
