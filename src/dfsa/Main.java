package dfsa;

public class Main {
	
	public static void main(String[] args){
		
		Estimator estimator = new LowerBound();
//		Estimator estimator = new EomLee();
		
		DFSA algorithm;
		
		int[] tagsM = {1,2,3,4,5,6,7,8,9,10};
		int collision;
		int empty;
		long time;
		
		int tagsNum;
		
		for(int i=0; i<tagsM.length; i++){
			
			collision = 0;
			empty = 0;
			time = 0;
			
			tagsNum = 100*tagsM[i];
			
			for(int j=1; j<501; j++){
				algorithm = new DFSA(estimator, tagsNum);
				algorithm.run();
				
				collision += algorithm.getTotalCollisionSlots();
				empty += algorithm.getTotalEmptySlots();
				time += algorithm.getTotalTime();
			}
			
			System.out.println("Resultados para "+tagsNum+" tags:");
			System.out.println("Total de slots com colisão: " + collision/500);
			System.out.println("Total de slots vazios: " + empty/500);
			System.out.println("Tempo total gasto para a execução: " + (float) time/500 + " milisegundos");
			System.out.println("============================================================================");
			
		}
		
	}

}
