package algorithms.project;

import algorithms.project.algorithm.*;
import algorithms.project.benchmark.*;
import algorithms.project.util.Stats;
import org.apache.commons.cli.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Application {

    public static Collection<Class> BENCHMARKS = Arrays.asList(Ackley.class, BentCigar.class, Discus.class,
            HCE.class, Rastrigin.class, Rosenbrock.class, Griewank.class, Katsuura.class, Weierstrass.class);

    private static final int[] TEST_DIMENSIONS = {2, 5, 10};

    private static final int NUM_RUNS = 1;

    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_FACTOR = 0.8;
    private static final int POPULATION_SIZE = 100;

    private static final double VAR_MIN = -10;
    private static final double VAR_MAX = 10;

    private static final double C1 = 2.05;
    private static final double C2 = 2.05;
    private static final double W_START = 0.9;
    private static final double W_FINISH = 0.4;

    private static final DE de = new DEBuilder().setCrossoverRate(CROSSOVER_RATE).setMutationFactor(MUTATION_FACTOR)
            .setPopulationSize(POPULATION_SIZE).setVariableRange(VAR_MIN, VAR_MAX).build();

    private static final PSO pso = new PSOBuilder().setC1(C1).setC2(C2).setPopulationSize(POPULATION_SIZE).setVariableRange(VAR_MIN, VAR_MAX)
            .setWRange(W_START, W_FINISH).build();

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        Options options = new Options();

        options.addOption("de", false, "Run differential evolution");
        options.addOption("pso", false, "Run particle swarm optimization");

        options.addOption("save", false, "Whether to render frames to file");

        options.addOption("test", true, "Test one or more algorithms or all");
        options.addOption("display", true, "Display a specific algorithm in 3d");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            boolean test = cmd.hasOption("test");
            boolean display = cmd.hasOption("display");

            if (!test && !display) {
                System.err.println("You must specific whether to test or display");
                help(options);
            } else if (test && display) {
                System.err.println("You cannot test and display!");
                help(options);
            } else if (display) {
                String[] benchmarks = cmd.getOptionValues("display");
                if (benchmarks.length != 1) {
                    System.err.println("You must specify one and only one benchmark");
                }

                Benchmark benchmark = null;
                try {
                    benchmark = (Benchmark) Class.forName("algorithms.project.benchmark." + benchmarks[0]).newInstance();
                } catch (ClassNotFoundException e) {
                    System.err.println("Benchmark: " + benchmarks[0] + " not found.");
                }

                GeneticAlgorithm algorithm = getAlgorithm(cmd);
                if (algorithm != null) {
                    algorithm.setDim(2);
                    Display.display(algorithm, benchmark, cmd.hasOption("save"));
                } else {
                    help(options);
                }
            } else {
                String[] benchmarks = cmd.getOptionValues("test");
                System.out.println(benchmarks.length);
                GeneticAlgorithm algorithm = getAlgorithm(cmd);

                if (algorithm != null) {
                    if (benchmarks.length == 1 && benchmarks[0].toLowerCase().equals("all")) {
                        test(algorithm, BENCHMARKS);
                    } else {
                        BENCHMARKS = new LinkedList<>();
                        try {
                            for (String bm : benchmarks) {
                                BENCHMARKS.add(Class.forName("algorithms.project.benchmark." + bm));
                            }
                        } catch (ClassNotFoundException e) {
                            System.err.println("Benchmark: " + benchmarks[0] + " not found.");
                        }
                        test(algorithm, BENCHMARKS);
                    }
                } else {
                    help(options);
                }
            }

        } catch (ParseException pe) {
            System.err.println("Invalid Command line arguments");
            help(options);
        }
    }

    private static void help(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ant", options);
    }

    private static GeneticAlgorithm getAlgorithm(CommandLine cmd) {
        boolean pso_option = cmd.hasOption("pso");
        boolean de_option = cmd.hasOption("de");
        if (!pso_option && !de_option) {
            System.err.println("You must specify the algorithm!");
            return null;
        } else if (de_option && pso_option) {
            System.err.println("You Can only run one algorithm!");
            return null;
        } else if (de_option) {
            return de;
        } else {
            return pso;
        }
    }

    public static void test(GeneticAlgorithm alg, Collection<Class> benchmarks) throws IllegalAccessException, InstantiationException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet[] SHEETS = new HSSFSheet[TEST_DIMENSIONS.length];
        for (int i = 0; i < SHEETS.length; i++) {
            SHEETS[i] = wb.createSheet("Table dim=" + TEST_DIMENSIONS[i]);
        }

        Arrays.stream(SHEETS).forEach(sh -> {
            sh.createRow(0).createCell(0).setCellValue("Benchmark");
            sh.getRow(0).createCell(1).setCellValue("Mean");
            sh.getRow(0).createCell(2).setCellValue("Best");
            sh.getRow(0).createCell(3).setCellValue("Worst");
            sh.getRow(0).createCell(4).setCellValue("Standard Dev");
            int i = 1;
            for (Class benchmark : benchmarks) {
                sh.createRow(i++).createCell(0).setCellValue(benchmark.getSimpleName());
            }
        });

        int bmCountRow = 1;
        for (Class benchmark : benchmarks) {
            Benchmark bm = (Benchmark) benchmark.newInstance();
            for (int i = 0; i < TEST_DIMENSIONS.length; i++) {
                alg.setDim(TEST_DIMENSIONS[i]);
                Vector<Double> results = runTest(alg, bm, NUM_RUNS);
                for (int j = 1; j <= results.size(); j++) {
                    SHEETS[i].getRow(bmCountRow).createCell(j).setCellValue(results.get(j - 1));
                }
            }
            bmCountRow++;
        }

        try {
            FileOutputStream fos = new FileOutputStream(alg.getClass().getSimpleName() + "_results.xls");
            wb.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        }
        threadList.forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        LinkedList<Double> fitnessValues = new LinkedList<>();
        long finish = System.currentTimeMillis() - start;
        double worstFitness = Double.NEGATIVE_INFINITY;
        double bestFitness = Double.POSITIVE_INFINITY;
        double total = 0;
        int dim = 0;
        for (Vector<Double> v : list) {
            double fitness = benchmark.benchmark(v);
            fitnessValues.add(fitness);
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
        double stdev = Stats.standardDev(fitnessValues, mean);

        Vector<Double> results = new Vector<>();
        results.add(mean);
        results.add(bestFitness);
        results.add(worstFitness);
        results.add(stdev);

        System.out.println(benchmark.getClass().getSimpleName() + " Time= " + finish + " dim= " + dim + " Mean: " + mean + " best: " + bestFitness + " worst: " + worstFitness + " stdev: " + stdev);
        return results;
    }
}
