package algorithms.project.algorithm;

import algorithms.project.benchmark.Benchmark;
import algorithms.project.util.Utility;

import java.util.List;
import java.util.Vector;

public class PSO extends GeneticAlgorithm {

    private int populationSize;
    private int dim;
    private double varMin;
    private double varMax;

    private double c1;
    private double c2;
    private double wStart;
    private double wFinish;

    @Override
    public Vector<Double> run(Benchmark benchmark) {
        List<Vector<Double>> particles = Utility.createPopulation(populationSize, dim, varMin, varMax);
        List<Vector<Double>> velocities = Utility.createPopulation(populationSize, dim, 0, 0);

        double gBestFitness = Double.POSITIVE_INFINITY;
        Vector<Double> gBest = null;

        for (int i = 0; i < 5000 * dim; i++) {
            double pBestFitness = Double.POSITIVE_INFINITY;
            Vector<Double> pBest = null;
            for (int j = 0; j < populationSize; j++) {

            }
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
}
