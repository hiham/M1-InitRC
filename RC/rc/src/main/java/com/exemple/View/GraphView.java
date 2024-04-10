package main.java.com.exemple.View;

import main.java.com.exemple.Tools.Cross;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphView extends JFrame {
        private List<Integer> values = new ArrayList<> ();
        private ArrayList<XYSeries> series = new ArrayList<> ();

        public GraphView(ArrayList<CutView> points, String applicationTitle , String chartTitle ) {
            super(applicationTitle);
            XYDataset dataset = createDataset (points);
            JFreeChart chart = ChartFactory.createXYLineChart (
                    chartTitle,
                    "Annee","Acroissement",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,true,false);
            XYPlot plot = chart.getXYPlot ();
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            plot.setRenderer(renderer);
            plot.setDomainGridlinesVisible(true);
            plot.setRangeGridlinesVisible(false);

            ChartPanel chartPanel = new ChartPanel( chart ){
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(800, 600);
                }
            };
            setContentPane( chartPanel );
        }

        private XYDataset createDataset(ArrayList<CutView> points ) {
            int index = 0;
            XYSeriesCollection dataset = new XYSeriesCollection ();
            for (int i = 0; i < points.size (); i++) {
                    series.add (new XYSeries ("Tab "+i));
            }
            for (XYSeries s: series) {
                fillSerie (s,index,points);
                dataset.addSeries (s);
                index++;
            }

            series.add (new XYSeries ("Moyenne"));
            fillAverage(series.get (series.size ()-1),points);
            dataset.addSeries (series.get (series.size ()-1));
            return dataset;
        }

    private double calculateDistance(Point p1, Point p2) {
        int deltaX = p2.x - p1.x;
        int deltaY = p2.y - p1.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private void fillSerie(XYSeries serie,int i,ArrayList<CutView> points)
    {
        for (int j = 0; j < points.get (i).getCrossArrayList ().size () - 1; j++) {
            double distance = calculateDistance(points.get (i).getCrossArrayList ().get (j).getCenter (),points.get (i).getCrossArrayList ().get (j+1).getCenter ());
            serie.add (j,distance);
        }

    }


    public void fillAverage(XYSeries series,ArrayList<CutView> points) {
        // Find the maximum length among all lists
        int maxLength = 0;
        int index = 0;
        for (CutView list : points) {
            maxLength = Math.max(maxLength, list.getCrossArrayList ().size ());
            index++;
        }

        // Initialize a list to store the sum of values at each index
        ArrayList<Double> sumList = new ArrayList<>(maxLength);
        ArrayList<Integer> indexes = new ArrayList<>(maxLength);

        // Initialize sumList with zeros
        for (int i = 0; i < maxLength; i++) {
            sumList.add(0.0);
            indexes.add (0);
        }

        for (CutView obj : points) {
            for (int i = 0; i < obj.getCrossArrayList ().size () -1; i++) {
                sumList.set(i, sumList.get(i) + (int) calculateDistance(obj.getCrossArrayList ().get (i).getCenter (),obj.getCrossArrayList ().get (i+1).getCenter ()));
                indexes.set(i,indexes.get (i) + +1);
            }
        }

        // Calculate the average of values at each index
        int ix = 0;
        int year = 0;
        for (double sum : sumList) {
            double average = sum / indexes.get (ix);
            series.add (ix,average);
            ix++;
        }


    }

}


