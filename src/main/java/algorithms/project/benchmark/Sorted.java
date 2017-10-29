package algorithms.project.benchmark;

import java.util.Vector;

/**
 * A test benchmark
 * The most fit solution contains positive sorted numbers less than 1000
 */
public class Sorted implements Benchmark {

    @Override
    public Double benchmark(Vector<Double> params) {
        int n = params.size();
        double invCount = getInvCount(params.toArray(new Double[params.size()]));
        for (int i = 0; i < n; i++) {
            if (params.get(i) > 1000 || params.get(i) < 0) {
                return Double.POSITIVE_INFINITY;
            }
        }
        return invCount;
    }

    @Override
    public Double optimum() {
        return 0d;
    }

    private static int getInvCount(Double[] arr) {
        int inv_count = 0;
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = i + 1; j < arr.length; j++)
                if (arr[i] > arr[j])
                    inv_count++;
        return inv_count;
    }
}
