package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {
	
	private static final int QTD_BITS = 96;

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
	        	System.out.println("Erro ao abrir navegador!");
	        	System.out.println("Äs imagens foram geradas na pasta ~/index/dfsa ou ~/index/tree, "
	        			+ "para protocolos DFSA e baseados em Árvore, respectivamente.");
	        	return;
	        }
		}catch (Exception e){
			System.out.println("Erro ao abrir navegador!");
        	System.out.println("Äs imagens foram geradas na pasta ~/index/dfsa ou ~/index/tree, "
        			+ "para protocolos DFSA e baseados em Árvore, respectivamente.");
			return;
		}
	}
	
	/**
	 * Cria um numero X de tags (96 bits) aleatorias, sem repeticao, 
	 * de acordo com o valor passado como parametro.
	 * 
	 * @param qtdTags
	 * 			Quantidadade de tags que devem ser geradas.
	 * @return Buffer com todas as tags, uma por linha.
	 */
	private static StringBuilder criarTags(int qtdTags) {
		StringBuilder builder = null;
		List<String> l = null;
		BigInteger a = null;
		String tag = null;
		
		builder = new StringBuilder();
		l = new ArrayList<String>();
		
		for(int j = 0; j < qtdTags; j++) {
			a = new BigInteger(140, new Random());
			
			if(!l.contains(a)) {
				tag = a.toString(2).substring(0, QTD_BITS);
				l.add(tag);
				builder.append(tag.concat("\r\n"));
			}
		}
		
		return builder;
	}
	
	/**
	 * Gera 1000 arquivos, numerados de 1 a 1000, 
	 * com o numero de tags passado como parametro, 
	 * salvando-os em seus respectivos diretorios.
	 * 
	 * @param qtdTags
	 * 			Numero de tags em cada arquivo criado.
	 */
	private static void salvarArquivos(int qtdTags) {
		byte[] buffer = null;
		RandomAccessFile raf = null;
		FileChannel channel = null;
		ByteBuffer byteBuffer = null;
		
		try {
			for(int i = 1; i <= 500; i++) {
				buffer = criarTags(qtdTags).toString().getBytes();
				
				raf = new RandomAccessFile("tags/"+qtdTags+"/"+i+".txt", "rw");
				channel = raf.getChannel();
				byteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, buffer.length);
				
				byteBuffer.put(buffer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(channel != null) {
					channel.close();
				}				
				if (raf != null) {
					raf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	/**
	 * Gera todos os arquivos para os testes.
	 */
	public static void gerarArquivosTags() {
		for(int qtdTags = 100; qtdTags <= 1000; qtdTags+=100) {
			System.out.printf("Criando arquivos com %d tags... ", qtdTags);
			salvarArquivos(qtdTags);
			System.out.println("OK!");
		}
		
		System.out.println("Ärquivos criados!");
	}
	
	public static void main(String[] args) {
		gerarArquivosTags();
	}
}
