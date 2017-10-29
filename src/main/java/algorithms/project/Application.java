package algorithms.project;

import algorithms.project.algorithm.DE;
import algorithms.project.algorithm.DEBuilder;
import algorithms.project.algorithm.GeneticAlgorithm;
import algorithms.project.benchmark.*;

import java.util.*;

public class Application {

    private static Class[] benchmarks = {Ackley.class, BentCigar.class, Discus.class,
            HCE.class, Rastrigin.class, Rosenbrock.class, Griewank.class, Katsuura.class
    };

    private static final int[] TEST_DIMENSIONS = {2, 5, 10};

    private static final int NUM_RUNS = 10;

    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_FACTOR = 0.8;
    private static final int POPULATION_SIZE = 100;

    private static final double VAR_MIN = -10;
    private static final double VAR_MAX = 10;

    private static final DE de = new DEBuilder().setCrossoverRate(CROSSOVER_RATE).setMutationFactor(MUTATION_FACTOR)
            .setPopulationSize(POPULATION_SIZE).setVariableRange(VAR_MIN, VAR_MAX).build();

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (args.length == 1 && args[0].equals("-test")) {
            testDE();
        } else if (args.length == 2 && args[0].equals("-test")) {
            String name = "algorithms.project.benchmark." + args[1];
            Class<?> clazz = Class.forName(name);
            benchmarks = new Class[]{clazz};
            testDE();
        } else if (args.length == 2 && args[0].equals("-display")) {
            String name = "algorithms.project.benchmark." + args[1];
            Class<?> clazz = Class.forName(name);
            Benchmark bm = (Benchmark) clazz.newInstance();
            de.setDim(2);
            Display.display(de, bm);
        } else {
            de.setDim(2);
            Display.display(de, new Ackley());
        }
    }

    public static void testDE() throws IllegalAccessException, InstantiationException {
        for (Class benchmark : benchmarks) {
            Benchmark bm = (Benchmark) benchmark.newInstance();
            for (int dimension : TEST_DIMENSIONS) {
                de.setDim(dimension);
                runTest(de, bm, NUM_RUNS);
            }
        }
    }

    private static Vector<Double> runTest(GeneticAlgorithm alg, Benchmark benchmark, int runs) {
        Collection<Vector<Double>> list = Collections.synchronizedCollection(new ArrayList<Vector<Double>>());
        LinkedList<Thread> threadList = new LinkedList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            Thread th = new Thread(() -> list.add(alg.run(benchmark)));
            threadList.add(th);
            th.start();
            list.add(alg.run(benchmark));
        }
        threadList.forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Vector<Double> bestVector = null;
        long finish = System.currentTimeMillis() - start;
        double worstFitness = Double.NEGATIVE_INFINITY;
        double bestFitness = Double.POSITIVE_INFINITY;
        double total = 0;
        int dim = 0;
        for (Vector<Double> v : list) {
            double fitness = benchmark.benchmark(v);
            dim = v.size();
            total += fitness;
            if (fitness > worstFitness) {
                worstFitness = fitness;
            }
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestVector = v;
            }
        }
        double mean = total / runs;
        System.out.println(benchmark.getClass().getSimpleName() + " Time= " + finish + " dim= " + dim + " Mean: " + mean + " best: " + bestFitness + " worst: " + worstFitness);
        return bestVector;
    }
}
