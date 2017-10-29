package algorithms.project.algorithm;

public class DEBuilder {

    private DE de = new DE();

    public DE build() {
        return de;
    }

    public DEBuilder setCrossoverRate(final double rate) {
        de.setCrossoverRate(rate);
        return this;
    }

    public DEBuilder setMutationFactor(final double factor) {
        de.setMutationFactor(factor);
        return this;
    }

    public DEBuilder setDimensions(int dim) {
        de.setDim(dim);
        return this;
    }

    public DEBuilder setPopulationSize(int size) {
        de.setPopulationSize(size);
        return this;
    }

    public DEBuilder setVariableRange(final double min, final double max) {
        de.setVarRange(min, max);
        return this;
    }

    public DEBuilder setCallback(final Callback callback) {
        de.setCallback(callback);
        return this;
    }
}
