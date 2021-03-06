package tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import charts.Chart;
import charts.LineChartTree;
import util.Util;

public class Main {

	public static TagLoader loader = new TagLoader();
	public static TreeAlgorithm tree;

	public static int[] tagsM = {1,2,3,4,5,6,7,8,9,10};
	public static int tagsNum;

	public static int [][] stepsChartData = new int [10][2];
	public static int [][] bitsTagChartData = new int [10][2];
	public static int [][] bitsReaderChartData = new int [10][2];

	public static int bitsTag;
	public static int bitsReader;
	public static int steps;

	public static PrintWriter writer;

	public static void gerarHTML(String fileExtension, String iterations, String bitsTag, String bitsReader) {
		String html = "<html>\n"
				+ "<head>\n"
				+ "<title>QT e QwT</title>\n"
				+ "<meta charset=\"utf-8\" />\n"
				+ "</head>\n"
				+ "<body bgcolor=\"#EEEEEE\">\n"
				+ "<h1>QT e QwT</h1>\n"
				+ "<h2>Bits transmitidos Leitor->Tag</h2>\n"
				+ "<img src=\"tree/"+bitsReader.concat(fileExtension)+"\" />\n"
				+ "<h2>Bits Transmitidos Tag->Leitor</h2>"
				+ "<img src=\"tree/"+bitsTag.concat(fileExtension)+"\" />\n"
				+ "<img src=\"tree/artigo/"+bitsTag.concat(fileExtension)+"\"  height=\"500\"/>\n"
				+ "<h2>Número de passos</h2>\n"
				+ "<img src=\"tree/"+iterations.concat(fileExtension)+"\" />\n"
				+ "</body>\n"
				+ "</html>\n";

		try {
			writer = new PrintWriter("index/tree.html", "UTF-8");
			writer.println(html);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	public static void printCSV(String csvFileName, int chartType){
		try {
			writer = new PrintWriter(csvFileName, "UTF-8");
			writer.println("Tags,Bits leitor->tag,Bits tag->leitor,iteracoes");
			
			Tag[] tags;
			int testIterations = 500;

			for(int i=0; i<tagsM.length; i++){
				steps = 0;
				bitsTag = 0;
				bitsReader = 0;
				tagsNum = 100*tagsM[i];
				
				System.out.printf("Simulando %d tags... ", tagsNum);

				for(int j=1; j<=testIterations; j++){
					tags = loader.load(tagsNum, j);
					
					if(chartType == 0) {
						tree = new QT();
					} else if(chartType == 1) {
						tree = new QwT();
					}

					tree.run(tags);
					steps += tree.getTotalIterations();
					bitsTag += tree.getTotalBitsTag();
					bitsReader += tree.getTotalBitsReader();
				}

				stepsChartData[i][chartType] = steps/testIterations;
				bitsTagChartData[i][chartType] = bitsTag/testIterations;
				bitsReaderChartData[i][chartType] = bitsReader/testIterations;
				
				writer.println(tagsNum+","+bitsReader/testIterations+","+bitsTag/testIterations+","+","+steps/testIterations);	
				
				System.out.println("OK!");
			}
			
			System.out.println("Fim das simula��es!");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	public static void main(String[] args) throws IOException{

		try{
			// Criar .CSVs:
			System.out.println("[QT]");
			printCSV("QT.csv", 0);
			System.out.println("[QwT]");
			printCSV("QwT.csv", 1);
			
			// Gerar graficos:
			String iterationsFile = "passos";
			String bitsReaderFile = "bits-leitor-tag";
			String bitsTagFile = "bits-tag-leitor";
			
			String fileExtension = ".png";
		
			LineChartTree stepsChart = new LineChartTree(new Chart(iterationsFile, "Numero de passos", "Numero de etiquetas", stepsChartData));
			stepsChart.create();
			LineChartTree bitsTagChart = new LineChartTree(new Chart(bitsTagFile, "Numero de bits trocados Tag->Leitor", "Numero de etiquetas", bitsTagChartData));
			bitsTagChart.create();
			LineChartTree bitsReaderChart = new LineChartTree(new Chart(bitsReaderFile, "Numero de bits trocados Leitor->Tag", "Numero de etiquetas", bitsReaderChartData));
			bitsReaderChart.create();
			
			// Gerar HTML:
			gerarHTML(fileExtension, iterationsFile, bitsTagFile, bitsReaderFile);
			// Abrir navegador:
			Util.startBrowser(System.getProperty("user.dir").concat("/index/tree.html"));

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
