package tree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import charts.Chart;
import charts.LineChart;
import charts.LineChartTree;
import dfsa.Chen;
import dfsa.DFSA;
import dfsa.EomLee;
import dfsa.Estimator;
import dfsa.LowerBound;

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
	public static int iterations;

	public static PrintWriter writer;

	public static void startBrowser(String url) {		
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();		
		try{
			if (os.indexOf( "win" ) >= 0) {
				// this doesn't support showing urls in the form of "page.html#nameLink" 
				rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
			} else if (os.indexOf( "mac" ) >= 0) {
				rt.exec( "open " + url);
			} else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {
				// Do a best guess on unix until we get a platform independent way
				// Build a list of browsers to try, in this order.
				String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
						"netscape","opera","links","lynx"};
				// Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
				StringBuffer cmd = new StringBuffer();
				for (int i=0; i<browsers.length; i++) {
					cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");	
				}		            	        	
				rt.exec(new String[] { "sh", "-c", cmd.toString() });

			} else {
				System.out.println("nope!");
				return;
			}
		}catch (Exception e){
			System.out.println("nooope!");
			return;
		}
	}

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
				+ "<img src=\"tree/artigo/"+bitsReader.concat(fileExtension)+"\" height=\"500\"/>\n"
				+ "<h2>Bits Transmitidos Tag->Leitor</h2>"
				+ "<img src=\"tree/"+bitsTag.concat(fileExtension)+"\" />\n"
				+ "<img src=\"tree/artigo/"+bitsTag.concat(fileExtension)+"\"  height=\"500\"/>\n"
				+ "<h2>Número de passos</h2>\n"
				+ "<img src=\"tree/"+iterations.concat(fileExtension)+"\" />\n"
				+ "<img src=\"tree/artigo/"+iterations.concat(fileExtension)+"\"  height=\"500\"/>\n"
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

			for(int i=0; i<tagsM.length; i++){
				iterations = 0;
				bitsTag = 0;
				bitsReader = 0;
				tagsNum = 100*tagsM[i];
				
				Tag[] tags = loader.load(tagsNum);

				for(int j=1; j<2; j++){
					if(chartType == 0) {
						tree = new QT();
					} else if(chartType == 1) {
						tree = new QwT();
					}

					tree.run(tags);
					iterations += tree.getTotalIterations();
					bitsTag += tree.getTotalBitsTag();
					bitsReader += tree.getTotalBitsReader();
				}

				stepsChartData[i][chartType] = iterations;
				bitsTagChartData[i][chartType] = bitsTag;
				bitsReaderChartData[i][chartType] = bitsReader;
				
				writer.println(tagsNum+","+bitsReader+","+bitsTag+","+","+iterations);	
			}
			writer.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException{

		try{
			// Criar .CSVs:
			printCSV("QT.csv", 0);
			printCSV("QwT.csv", 1);
			
			// Gerar gr�ficos:
			String iterationsFile = "iteracoes";
			String bitsReaderFile = "bits-leitor-tag";
			String bitsTagFile = "bits-tag-leitor";
			
			String filesDir = "index/tree/";
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
			startBrowser(System.getProperty("user.dir").concat("/index/tree.html"));

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
