package algorithms.project;

import algorithms.project.algorithm.DE;
import algorithms.project.algorithm.DEBuilder;
import algorithms.project.algorithm.GeneticAlgorithm;
import algorithms.project.benchmark.*;

import java.util.*;

public class Application {

    private static final Class[] benchmarks = {Ackley.class, BentCigar.class, Discus.class,
            HCE.class, Rastrigin.class, Rosenbrock.class, Griewank.class, Katsuura.class
    };

    private static final int[] dimentions = {2, 5, 10};

    private static final int runs = 51;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        testDE();
    }

    public static void testDE() throws IllegalAccessException, InstantiationException {
        DE de = new DEBuilder().setCrossoverRate(0.9).setMutationFactor(0.8)
                .setPopulationSize(50).setVariableRange(-10, 10).build();
        for (Class benchmark : benchmarks) {
            Benchmark bm = (Benchmark) benchmark.newInstance();
            for (int dimension : dimentions) {
                de.setDim(dimension);
                runTest(de, bm, runs);
            }
        }
    }

    private static void runTest(GeneticAlgorithm alg, Benchmark benchmark, int runs) {
        Collection<Vector<Double>> list = Collections.synchronizedCollection(new ArrayList<Vector<Double>>());
        LinkedList<Thread> threadList = new LinkedList<>();
        for (int i = 0; i < runs; i++) {
            Thread th = new Thread(() -> list.add(alg.run(benchmark)));
            threadList.add(th);
            th.start();
        }
        threadList.forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        double worstFitness = Double.NEGATIVE_INFINITY;
        double bestFitness = Double.POSITIVE_INFINITY;
        double total = 0;
        int dim = 0;
        for (Vector<Double> v : list) {
            double fitness = benchmark.benchmark(v.toArray(new Double[v.size()]));
            dim = v.size();
            total += fitness;
            if (fitness > worstFitness) {
                worstFitness = fitness;
            }
            if (fitness < bestFitness) {
                bestFitness = fitness;
            }
        }
        double mean = total / runs;
        System.out.println(benchmark.getClass().getSimpleName() + ": dim=" + dim + " Mean: " + mean + " best: " + bestFitness + " worst: " + worstFitness);
    }
}
