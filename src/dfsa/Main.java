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
		
		int [][] collisionChartData = new int [10][10];
		
		int tagsNum;
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("EomLee.csv", "UTF-8");
			writer.println("Tags,Frames,Colisões,Vazios,Tempo");
		
			for(int i=0; i<tagsM.length; i++){
				
				collision = 0;
				empty = 0;
				time = 0;
				frames = 0;
				
				tagsNum = 100*tagsM[i];
				
				for(int j=1; j<501; j++){
					//estimator = new LowerBound();
					estimator = new EomLee();
					
					algorithm = new DFSA(estimator, tagsNum);
					algorithm.run();
					
					frames += algorithm.getTotalFrames();
					collision += algorithm.getTotalCollisionSlots();
					empty += algorithm.getTotalEmptySlots();
					time += algorithm.getTotalTime();
				}
				
				collisionChartData[i][0] = collision/500;
				
				writer.println(tagsNum+","+frames/500+","+collision/500+","+empty/500+","+(float) time/500);
	//			System.out.println("Resultados para "+tagsNum+" tags:");
	//			System.out.println("Total de frames usados: "+ frames/500);
	//			System.out.println("Total de slots com colisão: " + collision/500);
	//			System.out.println("Total de slots vazios: " + empty/500);
	//			System.out.println("Tempo total gasto para a execução: " + (float) time/500 + " milisegundos");
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
				
				tagsNum = 100*tagsM[i];
				
				for(int j=1; j<501; j++){
					estimator = new LowerBound();
					//estimator = new EomLee();
					
					algorithm = new DFSA(estimator, tagsNum);
					algorithm.run();
					
					frames += algorithm.getTotalFrames();
					collision += algorithm.getTotalCollisionSlots();
					empty += algorithm.getTotalEmptySlots();
					time += algorithm.getTotalTime();
				}
				
				collisionChartData[i][1] = collision/500;
				
				writer.println(tagsNum+","+frames/500+","+collision/500+","+empty/500+","+(float) time/500);
	//			System.out.println("Resultados para "+tagsNum+" tags:");
	//			System.out.println("Total de frames usados: "+ frames/500);
	//			System.out.println("Total de slots com colisão: " + collision/500);
	//			System.out.println("Total de slots vazios: " + empty/500);
	//			System.out.println("Tempo total gasto para a execução: " + (float) time/500 + " milisegundos");
	//			System.out.println("============================================================================");
				
			}
			
			writer.close();
			
			LineChart chartLower = new LineChart(new Chart("Slots Vazios", "N�mero de etiquetas", "N�mero de colis�es", collisionChartData));
			chartLower.create();
		
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}