package dfsa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import charts.Chart;
import charts.LineChart;
import util.Util;

public class Main {	
	public static Estimator estimator;
	public static DFSA algorithm;
	
	public static int[] tagsM = {1,2,3,4,5,6,7,8,9,10};
	public static int collision;
	public static int empty;
	public static int slots;
	
	public static int [][] collisionChartData = new int [10][3];
	public static int [][] emptyChartData = new int [10][3];
	public static int [][] slotsChartData = new int [10][3];
	
	public static int tagsNum;
	
	public static PrintWriter writer;
	
	public static void gerarHTML(String fileExtension, 
			String slotsColisao, String slotsVazios, String totalSlots) {
		String html = "<html>\n"
				+ "<head>\n"
				+ "<title>DFSA</title>\n"
				+ "<charset META=\"utf-8\" />\n"
				+ "</head>\n"
				+ "<body bgcolor=\"#EEEEEE\">\n"
				+ "<h1>DFSA</h1>\n"
				+ "<h2>Slots com colisao</h2>\n"
				+ "<img src=\"dfsa/"+slotsColisao.concat(fileExtension)+"\" height=\"400\" />\n"
				+ "<img src=\"dfsa/artigo/"+slotsColisao.concat(fileExtension)+"\" height=\"400\" />\n"
				+ "<img src=\"dfsa/artigo/colisoes-chen.jpg\" height=\"400\" />\n"
				+ "<h2>Slots vazios</h2>"
				+ "<img src=\"dfsa/"+slotsVazios.concat(fileExtension)+"\" height=\"400\" />\n"
				+ "<img src=\"dfsa/artigo/"+slotsVazios.concat(fileExtension)+"\" height=\"400\" />\n"
				+ "<img src=\"dfsa/artigo/vazios-chen.jpg\" height=\"400\" />\n"
				+ "<h2>Total de slots</h2>\n"
				+ "<img src=\"dfsa/"+totalSlots.concat(fileExtension)+"\" height=\"400\" />\n"
				+ "<img src=\"dfsa/artigo/"+totalSlots.concat(fileExtension)+"\" height=\"400\" />\n"
				+ "<img src=\"dfsa/artigo/totalslots-chen.jpg\" height=\"400\" />\n"
				+ "</body>\n"
				+ "</html>\n";
		
		try {
			writer = new PrintWriter("index/dfsa.html", "UTF-8");
			writer.println(html);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	public static void printCSV(String csvFileName, int chartType) {
		try {
			writer = new PrintWriter(csvFileName, "UTF-8");
			writer.println("Tags,Frames,Colisões,Vazios,Tempo,Slots");
		
			for(int i=0; i<tagsM.length; i++) {
				if(chartType == 0) {
					estimator = new EomLee();
				} else if(chartType == 1) {
					estimator = new LowerBound();
				} else if(chartType == 2) {
					estimator = new Chen();
				}
				
				collision = 0;
				empty = 0;
				slots = 0;
				
				tagsNum = 100*tagsM[i];

				System.out.printf("Simulando %d tags... ", tagsNum);
				
				for(int j=1; j<1001; j++){
					algorithm = new DFSA(estimator, tagsNum);
					algorithm.run();
					
					collision += algorithm.getTotalCollisionSlots();
					empty += algorithm.getTotalEmptySlots();
					slots += algorithm.getTotalSlots();
				}
				
				collisionChartData[i][chartType] = collision/1000;
				emptyChartData[i][chartType] = empty/1000;
				slotsChartData[i][chartType] = slots/1000;
				
				writer.println(tagsNum+","+collision/1000+","+empty/1000+","+","+slots/1000);
				
				System.out.println("OK!");
			}
			
			System.out.println("Fim das simulações!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	public static void main(String[] args) throws IOException{
		// Criar .CSVs:
		System.out.println("[EomLee]");
		printCSV("EomLee.csv", 0);
		System.out.println("[LowerBound]");
		printCSV("LowerBound.csv", 1);
		System.out.println("[Chen]");
		printCSV("Chen.csv", 2);
		
		// Gerar gráficos:
		String slotsColisaoFile = "slots-colisao";
		String slotsVaziosFile = "slots-vazios";
		String totalSlotsFile = "total-slots";
		
		String filesDir = "index/dfsa/";
		String fileExtension = ".png";
		
		LineChart chartCollision = new LineChart(new Chart(slotsColisaoFile, "Número de etiquetas", "Número de colisões", collisionChartData));
		chartCollision.createChartCollision(filesDir.concat(slotsColisaoFile.concat(fileExtension)));
		
		LineChart chartEmpty = new LineChart(new Chart(slotsVaziosFile, "Número de etiquetas", "Número de slots vazios", emptyChartData));
		chartEmpty.createChartEmpty(filesDir.concat(slotsVaziosFile.concat(fileExtension)));
		
		LineChart chartTotalSlots = new LineChart(new Chart(totalSlotsFile, "Número de etiquetas", "Número de slots", slotsChartData));
		chartTotalSlots.createChartTotal(filesDir.concat(totalSlotsFile.concat(fileExtension)));

		// Gerar HTML:
		gerarHTML(fileExtension, slotsColisaoFile, slotsVaziosFile, totalSlotsFile);
		// Abrir navegador:
		Util.startBrowser(System.getProperty("user.dir").concat("/index/dfsa.html"));
	}
}