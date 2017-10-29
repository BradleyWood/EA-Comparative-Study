package algorithms.project.benchmark;

import java.util.Vector;

public interface Benchmark {

    Double benchmark(Vector<Double> vector);
    Double optimum();
}
