package algorithms.project.benchmark;

/**
 * A test benchmark
 * The most fit solution contains positive sorted numbers less than 1000
 */
public class Sorted implements Benchmark {

    @Override
    public Double benchmark(Double... params) {
        double invCount = getInvCount(params);
        for (int i = 0; i < params.length; i++) {
            if (params[i] > 1000 || params[i] < 0) {
                return Double.POSITIVE_INFINITY;
            }
        }
        return invCount;
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
