package algorithms.project;

import algorithms.project.algorithm.Callback;
import algorithms.project.algorithm.EvolutionaryAlgorithm;
import algorithms.project.benchmark.FitnessFunction;
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

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Display implements Callback {

    private final LinkedList<Point> points = new LinkedList<>();

    private final EvolutionaryAlgorithm algorithm;
    private final FitnessFunction fitnessFunction;
    private final boolean save;
    private double maxZ;
    private int frameCount = 0;

    private final Mapper mapper;

    private Chart chart;

    private Display(final EvolutionaryAlgorithm algorithm, final FitnessFunction fitnessFunction, final boolean save) {
        this.algorithm = algorithm;
        this.fitnessFunction = fitnessFunction;
        this.save = save;
        mapper = new Mapper() {
            public double f(double x, double y) {
                Vector<Double> v = new Vector<>();
                v.add(x);
                v.add(y);
                double bm = fitnessFunction.fitness(v);
                if (bm > maxZ)
                    maxZ = bm;
                return bm;
            }
        };
    }

    @Override
    public int interval() {
        return 200;
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
            Point dr = new Point(new Coord3d(v.get(0), v.get(1), fitnessFunction.fitness(v)), Color.GREEN, 4);
            points.add(dr);
            chart.addDrawable(dr, false);
        }
        chart.resumeAnimator();

        if (save) {
            try {
                File file = new File(new File("."), "/results/" + algorithm.getClass().getSimpleName() + "/" + fitnessFunction.getClass().getSimpleName());
                file.getAbsoluteFile().mkdirs();
                File f = new File(file, "/" + frameCount + ".png");
                f.createNewFile();
                chart.screenshot(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        frameCount++;
    }

    private void show() {
        Range range = new Range(-100, 100);
        int steps = 300;
        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapHotCold(), 0, maxZ));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.BLACK);
        chart = new AWTChart(Quality.Advanced);
        chart.addController(new AWTCameraMouseController());
        chart.add(surface);

        algorithm.addCallback(this);
        chart.open(fitnessFunction.getClass().getSimpleName(), 600, 600);
        Vector<Double> v = algorithm.run(fitnessFunction);
        LinkedList<Vector<Double>> lst = new LinkedList<>();
        lst.add(v);
        callback(lst);
    }

    public static void display(EvolutionaryAlgorithm algorithm, FitnessFunction fitnessFunction) {
        display(algorithm, fitnessFunction, false);
    }

    public static void display(EvolutionaryAlgorithm algorithm, FitnessFunction fitnessFunction, boolean save) {
        new Display(algorithm, fitnessFunction, save).show();
    }
}
