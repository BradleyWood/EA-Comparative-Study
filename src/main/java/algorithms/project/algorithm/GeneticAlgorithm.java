package algorithms.project.algorithm;

import algorithms.project.benchmark.Benchmark;

import java.util.Vector;

public abstract class GeneticAlgorithm {

    protected Callback callback = null;
    protected int populationSize;
    protected int dim;
    protected double varMin;
    protected double varMax;

    public abstract Vector<Double> run(Benchmark benchmark);

    public void setCallback(Callback callback) {
        this.callback = callback;
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

    public void setVarRange(double varMin, double varMax) {
        this.varMin = varMin;
        this.varMax = varMax;
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
}
