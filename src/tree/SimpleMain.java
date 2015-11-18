package tree;

import java.util.Calendar;

public class SimpleMain {
	public static void main(String[] args){
		TagLoader loader = new TagLoader();
		TreeAlgorithm tree = null;
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		for(int i = 100; i <= 1000; i+=100) {
			for(int j = 1; j <= 500; j++) {
				Tag[] tags = loader.load(i, j);
				tree = new QT();
//				TreeAlgorithm tree = new QwT();
				tree.run(tags);
			}
			
//			System.out.println("Total de bits transmitidos Tag->Leitor: "+tree.getTotalBitsTag());
//			System.out.println("Total de bits transmitidos Leitor->Tag: "+tree.getTotalBitsReader());
//			System.out.println("Total de iterações: "+tree.getTotalIterations());
			System.out.println(i);
		}
		
		System.out.println((Calendar.getInstance().getTimeInMillis() - startTime)/1000+"s");
		
		
	}
}
