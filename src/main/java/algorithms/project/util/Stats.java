package algorithms.project.util;

import algorithms.project.algorithm.Callback;
import algorithms.project.benchmark.Benchmark;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Stats {

    public static class FitnessVsNFC implements Callback {

        private LinkedList<Double> fitnessValues = new LinkedList<>();
        private Benchmark benchmark;
        double best = Double.POSITIVE_INFINITY;

        public FitnessVsNFC(Benchmark benchmark) {
            this.benchmark = benchmark;
        }

        @Override
        public int interval() {
            return 10;
        }

        @Override
        public void callback(List<Vector<Double>> population) {
            for (Vector<Double> vector : population) {
                double fitness = benchmark.benchmark(vector);
                if (fitness < best) {
                    best = fitness;
                }
            }
            fitnessValues.add(best);
        }
    }

    public static void graphFitnessVsNFC(FitnessVsNFC callback, String file) throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Best Fitness Vs NFC");

        int row_count = 0;
        double nfc = 10;

        sheet.createRow(row_count).createCell(0).setCellValue("NFC");
        sheet.getRow(row_count++).createCell(1).setCellValue("Best Fitness");

        for (Double fitness : callback.fitnessValues) {
            Row row = sheet.createRow(row_count++);
            Cell nfc_cell = row.createCell(0);
            Cell bestFitness = row.createCell(1);
            nfc_cell.setCellValue(nfc);
            bestFitness.setCellValue(fitness);
            nfc += callback.interval();
        }

        FileOutputStream fos = new FileOutputStream(file);
        wb.write(fos);
        fos.close();
    }
}
