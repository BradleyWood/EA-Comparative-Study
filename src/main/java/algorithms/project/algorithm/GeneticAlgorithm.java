package algorithms.project.algorithm;

import algorithms.project.benchmark.Benchmark;

import java.util.Vector;

public interface GeneticAlgorithm {

    Vector<Double> run(Benchmark benchmark);
}
