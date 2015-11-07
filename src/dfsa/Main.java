package dfsa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import charts.Chart;
import charts.LineChart;

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
	
	public static void gerarHTML(String fileExtension, 
			String slotsColisao, String slotsVazios, String totalSlots) {
		String html = "<html>\n"
				+ "<head>\n"
				+ "<title>DFSA</title>\n"
				+ "<charset META=\"utf-8\" />\n"
				+ "</head>\n"
				+ "<body bgcolor=\"#EEEEEE\">\n"
				+ "<h1>DFSA</h1>\n"
				+ "<h2>Slots com colisão</h2>\n"
				+ "<img src=\"dfsa/"+slotsColisao.concat(fileExtension)+"\" />\n"
				+ "<img src=\"dfsa/artigo/"+slotsColisao.concat(fileExtension)+"\" />\n"
				+ "<h2>Slots vazios</h2>"
				+ "<img src=\"dfsa/"+slotsVazios.concat(fileExtension)+"\" />\n"
				+ "<img src=\"dfsa/artigo/"+slotsVazios.concat(fileExtension)+"\" />\n"
				+ "<h2>Total de slots</h2>\n"
				+ "<img src=\"dfsa/"+totalSlots.concat(fileExtension)+"\" />\n"
				+ "<img src=\"dfsa/artigo/"+totalSlots.concat(fileExtension)+"\" />\n"
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
				collision = 0;
				empty = 0;
				slots = 0;
				
				tagsNum = 100*tagsM[i];
				
				for(int j=1; j<1001; j++){
					if(chartType == 0) {
//						estimator = new EomLee();
					} else if(chartType == 1) {
						estimator = new LowerBound();
					} else if(chartType == 2) {
						estimator = new Chen();
					}
					
					algorithm = new DFSA(estimator, tagsNum);
					algorithm.run();
					
					collision += algorithm.getTotalCollisionSlots();
					empty += algorithm.getTotalEmptySlots();
					slots += algorithm.getTotalSlots();
				}
				
				collisionChartData[i][chartType] = collision/1000;
				emptyChartData[i][chartType] = empty/1000;
				slotsChartData[i][chartType] = slots/1000;
				
//				System.out.printf("tagsNum = %d | Colisao: %d\n", tagsNum, collision/1000);
				
				writer.println(tagsNum+","+collision/1000+","+empty/1000+","+","+slots/1000);		
			}
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
//		printCSV("EomLee.csv", 0);
		printCSV("LowerBound.csv", 1);
		printCSV("Chen.csv", 2);
		
		// Gerar gráficos:
		String slotsColisaoFile = "slots-colisao";
		String slotsVaziosFile = "slots-vazios";
		String totalSlotsFile = "total-slots";
		
		String filesDir = "index/dfsa/";
		String fileExtension = ".png";
		
		LineChart chartCollision = new LineChart(new Chart(slotsColisaoFile, "Número de etiquetas", "Número de colisões", collisionChartData));
		chartCollision.create(filesDir.concat(slotsColisaoFile.concat(fileExtension)));
		
		LineChart chartEmpty = new LineChart(new Chart(slotsVaziosFile, "Número de etiquetas", "Número de slots vazios", emptyChartData));
		chartEmpty.create(filesDir.concat(slotsVaziosFile.concat(fileExtension)));
		
		LineChart chartTotalSlots = new LineChart(new Chart(totalSlotsFile, "Número de etiquetas", "Número de slots", slotsChartData));
		chartTotalSlots.create(filesDir.concat(totalSlotsFile.concat(fileExtension)));

		// Gerar HTML:
		gerarHTML(fileExtension, slotsColisaoFile, slotsVaziosFile, totalSlotsFile);
		// Abrir navegador:
		startBrowser(System.getProperty("user.dir").concat("/index/dfsa.html"));
	}
}