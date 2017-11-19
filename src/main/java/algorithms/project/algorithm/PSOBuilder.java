package algorithms.project.algorithm;

public class PSOBuilder {

    private PSO pso = new PSO();

    public PSO build() {
        return pso;
    }

    public PSOBuilder setPopulationSize(int size) {
        pso.setPopulationSize(size);
        return this;
    }

    public PSOBuilder setC1(double c1) {
        pso.setC1(c1);
        return this;
    }

    public PSOBuilder setC2(double c2) {
        pso.setC2(c2);
        return this;
    }

    public PSOBuilder setWRange(double wMax, double wMin) {
        pso.setwStart(wMax);
        pso.setwFinish(wMin);
        return this;
    }

    public PSOBuilder setVariableRange(double min, double max) {
        pso.setVarMin(min);
        pso.setVarMax(max);
        return this;
    }

    public PSOBuilder setDimensions(int dim) {
        pso.setDim(dim);
        return this;
    }

    public PSOBuilder addCallback(Callback callback) {
        pso.addCallback(callback);
        return this;
    }
}
