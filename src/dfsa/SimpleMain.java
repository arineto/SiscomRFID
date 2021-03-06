package dfsa;

public class SimpleMain {
	
	public static void main(String[] args){
		
		int tagsNum = 1000;
		Estimator estimator = new Chen();
		
		DFSA algorithm = new DFSA(estimator, tagsNum);
		algorithm.run();
		
		System.out.println("Resultados:");
		System.out.println("Total de slots: "+algorithm.getTotalSlots());
		System.out.println("Total de colisoes: "+algorithm.getTotalCollisionSlots());
		System.out.println("Total de vazios: "+algorithm.getTotalEmptySlots());
	}

}
