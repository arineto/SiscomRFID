package charts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineChart {

	private Chart chart;
	
	public LineChart (Chart chart){
		
		this.chart = chart;
	}
	
	public void createChartCollision (String path) throws IOException{
	
		JFreeChart jChart = ChartFactory.createXYLineChart(
				"", chart.getAxisY(), chart.getAxisX(), 
				createDataset(), PlotOrientation.VERTICAL, true, false, false);
		
		XYPlot xyPlot = (XYPlot) jChart.getPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
        
        NumberAxis yAxis = (NumberAxis) xyPlot.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false);
        
        NumberAxis domain = (NumberAxis) xyPlot.getRangeAxis();
        domain.setTickUnit(new NumberTickUnit(200));
		
        OutputStream image = new FileOutputStream(path);
		ChartUtilities.writeChartAsPNG(image, jChart, 500, 500);
		image.close();
	}
	
	public void createChartEmpty (String path) throws IOException{
		
		JFreeChart jChart = ChartFactory.createXYLineChart(
				"", chart.getAxisY(), chart.getAxisX(), 
				createDataset(), PlotOrientation.VERTICAL, true, false, false);
		
		XYPlot xyPlot = (XYPlot) jChart.getPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
        
        NumberAxis yAxis = (NumberAxis) xyPlot.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false);
        NumberAxis xAxis = (NumberAxis) xyPlot.getDomainAxis();
        xAxis.setAutoRangeIncludesZero(false);
        
        NumberAxis domain = (NumberAxis) xyPlot.getRangeAxis();
        domain.setTickUnit(new NumberTickUnit(100));
		
        OutputStream image = new FileOutputStream(path);
		ChartUtilities.writeChartAsPNG(image, jChart, 500, 500);
		image.close();
	}
	
	
	public void createChartTotal (String path) throws IOException{
		
		JFreeChart jChart = ChartFactory.createXYLineChart(
				"", chart.getAxisY(), chart.getAxisX(), 
				createDataset(), PlotOrientation.VERTICAL, true, false, false);
		
		XYPlot xyPlot = (XYPlot) jChart.getPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
        
        NumberAxis yAxis = (NumberAxis) xyPlot.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false);
        
        NumberAxis domain = (NumberAxis) xyPlot.getRangeAxis();
        domain.setTickUnit(new NumberTickUnit(500));
		
        OutputStream image = new FileOutputStream(path);
		ChartUtilities.writeChartAsPNG(image, jChart, 500, 500);
		image.close();
	}
	
	public XYDataset createDataset(){
		
		XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries serie1 = new XYSeries("Eom Lee");
		XYSeries serie2 = new XYSeries("Lower Bound");
		XYSeries serie3 = new XYSeries("Chen");
		
		int n = 100;
		
		for(int i = 0; i<chart.getData().length; i++){
			serie1.add(n, chart.getData()[i][0]);
			serie2.add(n, chart.getData()[i][1]);
			serie3.add(n, chart.getData()[i][2]);
			n += 100;
		}
		
		collection.addSeries(serie1);
		collection.addSeries(serie2);
		collection.addSeries(serie3);
		
		return collection;
	}
	
}
