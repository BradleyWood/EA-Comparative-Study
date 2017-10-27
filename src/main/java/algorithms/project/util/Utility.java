package algorithms.project.util;

import java.util.Vector;

public class Utility {

    public static Vector<Double> scalarMult(Vector<Double> v, double factor) {
        Vector<Double> ret = new Vector<>(v.size());
        for (int i = 0; i < v.size(); i++) {
            ret.add(v.get(i) * factor);
        }
        return ret;
    }

    public static Vector<Double> add(Vector<Double> v1, Vector<Double> v2) {
        if (v1.size() != v2.size())
            throw new IllegalArgumentException("Vector sizes are not equal");
        Vector<Double> ret = new Vector<>(v1.size());
        for (int i = 0; i < v1.size(); i++) {
            ret.add(v1.get(i) + v2.get(i));
        }
        return ret;
    }

    public static Vector<Double> sub(Vector<Double> v1, Vector<Double> v2) {
        if (v1.size() != v2.size())
            throw new IllegalArgumentException("Vector sizes are not equal");
        Vector<Double> ret = new Vector<>(v1.size());
        for (int i = 0; i < v1.size(); i++) {
            ret.add(v1.get(i) - v2.get(i));
        }
        return ret;
    }

}
