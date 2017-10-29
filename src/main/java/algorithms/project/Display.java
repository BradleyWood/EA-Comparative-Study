package algorithms.project;

import algorithms.project.algorithm.Callback;
import algorithms.project.algorithm.GeneticAlgorithm;
import algorithms.project.benchmark.Benchmark;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapHotCold;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Display implements Callback {

    private final LinkedList<Point> points = new LinkedList<>();

    private final GeneticAlgorithm algorithm;
    private final Benchmark benchmark;
    private final double maxZ;

    private final Mapper mapper;

    private Chart chart;

    private Display(final GeneticAlgorithm algorithm, final Benchmark benchmark, final double maxZ) {
        this.algorithm = algorithm;
        this.benchmark = benchmark;
        this.maxZ = maxZ;
        mapper = new Mapper() {
            public double f(double x, double y) {
                Vector<Double> v = new Vector<>();
                v.add(x);
                v.add(y);
                return benchmark.benchmark(v);
            }
        };
    }

    public Display(final GeneticAlgorithm algorithm, final Benchmark benchmark) {
        this(algorithm, benchmark, 20d);
    }

    @Override
    public int interval() {
        return 400;
    }

    @Override
    public void callback(List<Vector<Double>> population) {
        chart.pauseAnimator();
        for (Point point : points) {
            chart.getScene().getGraph().remove(point, false);
        }
        points.clear();
        for (Vector<Double> v : population) {
            if (v.get(0) > 10 || v.get(0) < -10 || v.get(1) > 10 || v.get(1) < -10)
                continue;
            Point dr = new Point(new Coord3d(v.get(0), v.get(1), benchmark.benchmark(v)), Color.GREEN, 4);
            points.add(dr);
            chart.addDrawable(dr, false);
        }
        chart.resumeAnimator();
    }

    private void show() {
        Range range = new Range(-10, 10);
        int steps = 200;
        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapHotCold(), 0, maxZ));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.BLACK);
        chart = new AWTChart(Quality.Advanced);
        chart.addController(new AWTCameraMouseController());
        chart.add(surface);

        algorithm.setCallback(this);
        chart.open(benchmark.getClass().getSimpleName(), 600, 600);

        Vector<Double> v = algorithm.run(benchmark);
        Point dr = new Point(new Coord3d(v.get(0), v.get(1), 0), Color.GREEN, 4);
        chart.add(dr);
    }

    public static void display(GeneticAlgorithm algorithm, Benchmark benchmark) {
        new Display(algorithm, benchmark).show();
    }

    public static void display(GeneticAlgorithm algorithm, Benchmark benchmark, double maxZ) {
        new Display(algorithm, benchmark, maxZ).show();
    }
}
