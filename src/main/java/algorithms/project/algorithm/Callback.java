package algorithms.project.algorithm;

import java.util.List;
import java.util.Vector;

public interface Callback {

    /**
     * @return Callback interval in number of generations
     */
    int interval();

    void callback(List<Vector<Double>> population);
}
