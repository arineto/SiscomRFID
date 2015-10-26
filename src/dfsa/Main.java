package dfsa;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {
	
	public static void main(String[] args){
		
		Estimator estimator;
		DFSA algorithm;
		
		int[] tagsM = {1,2,3,4,5,6,7,8,9,10};
		int collision;
		int empty;
		long time;
		int frames;
		
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
//					estimator = new LowerBound();
					estimator = new EomLee();
					
					algorithm = new DFSA(estimator, tagsNum);
					algorithm.run();
					
					frames += algorithm.getTotalFrames();
					collision += algorithm.getTotalCollisionSlots();
					empty += algorithm.getTotalEmptySlots();
					time += algorithm.getTotalTime();
				}
				
				writer.println(tagsNum+","+frames/500+","+collision/500+","+empty/500+","+(float) time/500);
	//			System.out.println("Resultados para "+tagsNum+" tags:");
	//			System.out.println("Total de frames usados: "+ frames/500);
	//			System.out.println("Total de slots com colisão: " + collision/500);
	//			System.out.println("Total de slots vazios: " + empty/500);
	//			System.out.println("Tempo total gasto para a execução: " + (float) time/500 + " milisegundos");
	//			System.out.println("============================================================================");
				
			}
			
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}