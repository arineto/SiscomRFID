package charts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
	
	public void create () throws IOException{
	
		JFreeChart jChart = ChartFactory.createXYLineChart("", chart.getAxisY(), chart.getAxisX(), createDataset(), PlotOrientation.HORIZONTAL, true, false, false);
		
		XYPlot xyPlot = (XYPlot) jChart.getPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
		
		if(chart.getTitle() == "Tempo para identfica��o"){
			
			//Calculando o intervalo entre os valores do eixo Y
	        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
	        domain.setTickUnit(new NumberTickUnit((float)chart.getTimeData()[9][1]/10));
		} else {
			
			//Calculando o intervalo entre os valores do eixo Y
	        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
	        domain.setTickUnit(new NumberTickUnit((float)chart.getData()[9][1]/10));
		}

        
        NumberAxis yAxis = (NumberAxis) xyPlot.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false);
		
		OutputStream image = new FileOutputStream(chart.getTitle() + ".png");
		ChartUtilities.writeChartAsPNG(image, jChart, 500, 500);
		image.close();
	}
	
	public XYDataset createDataset(){
		
		XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries serie1 = new XYSeries("Eom Lee");
		XYSeries serie2 = new XYSeries("Lower Bound");
		
		int n = 100;
		
		if(chart.getTitle() == "Tempo para identfica��o"){
			
			for(int i = 0; i<chart.getTimeData().length; i++){
				serie1.add(chart.getTimeData()[i][0], n);
				serie2.add(chart.getTimeData()[i][1], n);
				n += 100;
			}
			
			collection.addSeries(serie1);
			collection.addSeries(serie2);
		} else {
			
			for(int i = 0; i<chart.getData().length; i++){
				serie1.add(chart.getData()[i][0], n);
				serie2.add(chart.getData()[i][1], n);
				n += 100;
			}
			
			collection.addSeries(serie1);
			collection.addSeries(serie2);	
		}


		
		return collection;
	}
	
}
