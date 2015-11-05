package dfsa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import charts.Chart;
import charts.LineChart;

public class Main {
	
	public static void main(String[] args) throws IOException{
		
		Estimator estimator;
		DFSA algorithm;
		
		int[] tagsM = {1,2,3,4,5,6,7,8,9,10};
		int collision;
		int empty;
		long time;
		int frames;
		int slots;
		int tags;
		
		int [][] collisionChartData = new int [10][2];
		int [][] emptyChartData = new int [10][2];
		int [][] slotsChartData = new int [10][2];
		float [][] timeChartData = new float [10][2];
		int [][] estimationErrorTagsChartData = new int [10][2];
		
		int tagsNum;
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("EomLee.csv", "UTF-8");
			writer.println("Tags,Frames,Colisões,Vazios,Tempo,Slots");
		
			for(int i=0; i<tagsM.length; i++){
				
				collision = 0;
				empty = 0;
				time = 0;
				frames = 0;
				slots = 0;
				tags = 0;
				
				tagsNum = 100*tagsM[i];
				
				for(int j=1; j<1001; j++){
					//estimator = new LowerBound();
					estimator = new EomLee();
					
					algorithm = new DFSA(estimator, tagsNum);
					algorithm.run();
					
					frames += algorithm.getTotalFrames();
					collision += algorithm.getTotalCollisionSlots();
					empty += algorithm.getTotalEmptySlots();
					time += algorithm.getTotalTime();
					slots += algorithm.getTotalSlots();
					tags += algorithm.getEstimationError();
				}
				
				collisionChartData[i][0] = collision/1000;
				emptyChartData[i][0] = empty/1000;
				slotsChartData[i][0] = slots/1000;
				timeChartData[i][0] = (float)time/1000;
				estimationErrorTagsChartData[i][0] = tags/1000;
				
				writer.println(tagsNum+","+frames/1000+","+collision/1000+","+empty/1000+","+(float) time/1000+","+slots/1000);
	//			System.out.println("Resultados para "+tagsNum+" tags:");
	//			System.out.println("Total de frames usados: "+ frames/1000);
	//			System.out.println("Total de slots com colisÃ£o: " + collision/1000);
	//			System.out.println("Total de slots vazios: " + empty/1000);
	//			System.out.println("Tempo total gasto para a execuÃ§Ã£o: " + (float) time/1000 + " milisegundos");
	//			System.out.println("============================================================================");
				
			}
			
			writer.close();
			
			/////////////////////////////////////////////////////////////////////////////////////////////
			
			writer = new PrintWriter("LowerBound.csv", "UTF-8");
			writer.println("Tags,Frames,Colisões,Vazios,Tempo");
		
			for(int i=0; i<tagsM.length; i++){
				
				collision = 0;
				empty = 0;
				time = 0;
				frames = 0;
				slots = 0;
				tags = 0;
				
				tagsNum = 100*tagsM[i];
				
				for(int j=1; j<1001; j++){
					estimator = new LowerBound();
					//estimator = new EomLee();
					
					algorithm = new DFSA(estimator, tagsNum);
					algorithm.run();
					
					frames += algorithm.getTotalFrames();
					collision += algorithm.getTotalCollisionSlots();
					empty += algorithm.getTotalEmptySlots();
					time += algorithm.getTotalTime();
					slots += algorithm.getTotalSlots();
					tags += algorithm.getEstimationError();
				}
				
				collisionChartData[i][1] = collision/1000;
				emptyChartData[i][1] = empty/1000;
				slotsChartData[i][1] = slots/1000;
				timeChartData[i][1] = (float)time/1000;
				estimationErrorTagsChartData[i][1] = tags/1000;
				
				writer.println(tagsNum+","+frames/1000+","+collision/1000+","+empty/1000+","+(float) time/1000);
	//			System.out.println("Resultados para "+tagsNum+" tags:");
	//			System.out.println("Total de frames usados: "+ frames/1000);
	//			System.out.println("Total de slots com colisÃ£o: " + collision/1000);
	//			System.out.println("Total de slots vazios: " + empty/1000);
	//			System.out.println("Tempo total gasto para a execuÃ§Ã£o: " + (float) time/1000 + " milisegundos");
	//			System.out.println("============================================================================");
				
			}
			
			writer.close();
			
			LineChart chartCollision = new LineChart(new Chart("Slots com colisão", "Número de etiquetas", "Número de colisões", collisionChartData));
			chartCollision.create();
			LineChart chartEmpty = new LineChart(new Chart("Slots vazios", "Número de etiquetas", "Número de slots vazios", emptyChartData));
			chartEmpty.create();
			LineChart chartTotalSlots = new LineChart(new Chart("Total de slots", "Número de etiquetas", "Número de slots", slotsChartData));
			chartTotalSlots.create();
			LineChart chartTime = new LineChart(new Chart("Tempo para identficação", "Número de etiquetas", "Tempo para identificação (ms)", timeChartData));
			chartTime.create();
			LineChart chartError = new LineChart(new Chart("Erro abs. médio de estimação", "Número de etiquetas", "Erro abs. médio de estimação", estimationErrorTagsChartData));
			chartError.create();
		
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}