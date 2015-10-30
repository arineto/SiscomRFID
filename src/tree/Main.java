package tree;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import dfsa.DFSA;
import dfsa.EomLee;
import dfsa.Estimator;

public class Main {
	
//	public static void main(String[] args){
//		TagLoader loader = new TagLoader();
//		
//		Tag[] tags = loader.load(1000);
//		TreeAlgorithm tree = new QT();
////		TreeAlgorithm tree = new QwT();
//		
//		tree.run(tags);
//		System.out.println("Total de bits transmitidos: "+tree.getTotalBits());
//		System.out.println("Total de iterações: "+tree.getTotalIterations());
//	}
	
	public static void main(String[] args){
		
		TagLoader loader = new TagLoader();
		TreeAlgorithm tree;
		
		int[] tagsM = {1,2,3,4,5,6,7,8,9,10};
		int tagsNum;

		int bits;
		int iterations;
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("QwT.csv", "UTF-8");
			writer.println("Tags,Iteracoes,Bits");
		
			for(int i=0; i<tagsM.length; i++){
				iterations = 0;
				bits = 0;
				tagsNum = 100*tagsM[i];
				Tag[] tags = loader.load(tagsNum);
				
				for(int j=1; j<501; j++){
//					tree = new QT();
					tree = new QwT();
					
					tree.run(tags);
					iterations += tree.getTotalIterations();
					bits += tree.getTotalBits();
				}
				
				writer.println(tagsNum+","+iterations/500+","+bits/500);
			}
			
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
