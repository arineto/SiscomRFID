package dfsa;

public class Main {
	
	public static void main(String[] args){
		
		Estimator estimator = new LowerBound();
		DFSA algorithm = new DFSA(estimator, 100);
		
		try {
			algorithm.run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Total de slots com colisão: " + algorithm.getTotalCollisionSlots());
		System.out.println("Total de slots vazios: " + algorithm.getTotalEmptySlots());
		System.out.println("Tempo total gasto para a execução: " + algorithm.getTotalTime() + " milisegundos");
	}

}
