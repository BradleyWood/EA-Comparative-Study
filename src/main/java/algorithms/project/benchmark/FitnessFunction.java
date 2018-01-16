package algorithms.project.benchmark;

import java.util.Vector;

public interface FitnessFunction {

    Double fitness(Vector<Double> vector);
    Double optimum();
}
