package tree;

public class Main {
	
	public static void main(String[] args){
		TagLoader loader = new TagLoader();
		
		Tag[] tags = loader.load(1000);
		TreeAlgorithm tree = new QT();
//		TreeAlgorithm tree = new QwT();
		
		tree.run(tags);
		System.out.println("Total de bits transmitidos: "+tree.getTotalBits());
		System.out.println("Total de iterações: "+tree.getTotalIterations());
		
	}
	
}
