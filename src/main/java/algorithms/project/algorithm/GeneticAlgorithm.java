package algorithms.project.algorithm;

import algorithms.project.benchmark.Benchmark;

import java.util.Vector;

public abstract class GeneticAlgorithm {

    protected Callback callback = null;

    public abstract Vector<Double> run(Benchmark benchmark);

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
