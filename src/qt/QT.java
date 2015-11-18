package qt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import charts.Chart;
import charts.LineChartTree;

public class QT {
	/**
	 * Quantidade de iteracoes em uma consulta.
	 */
	private int iteracoes;
	/**
	 * Quantidade de bits trocados em uma consulta
	 * na direcao leitor/tag.
	 */
	private int bitsLeitorTag;
	/**
	 * Quantidade de bits trocados em uma consulta
	 * na direcao tag/leitor.
	 */
	private int bitsTagLeitor;
	/**
	 * Lista de tags utilizadas em uma consulta.
	 */
	private final List<String> tags;
	
	private final int qtdTags;
	
	/**
	 * Construtor.
	 * @param tags
	 * 			Lista de tags.
	 */
	public QT(final List<String> tags) {
		this.tags = tags;
		this.qtdTags = tags.size();
		this.iteracoes = 0;
		this.bitsLeitorTag = 0;
		this.bitsTagLeitor = 0;
	}
	
	/**
	 * Metodo responsavel por verificar conflitos.
	 * 
	 * @param prefixo
	 * 			Prefixo sobre o qual sera feita a consulta
	 * @return Lista de tags podendo ter apenas um elemento,
	 * 		   em caso de sucesso, ou mais, em caso de conflito.
	 */
	private List<String> compara(final String prefixo) {
		List<String> matches = new ArrayList<String>();
		
		for(String tag : this.tags) {
			if(tag.startsWith(prefixo)) {
				matches.add(tag);
			}
		}
		
		return matches;
	}
	
	/**
	 * Conta o numero de iteracoes e calcula a quantidade
	 * de bits trocados.
	 * 
	 * @param matches
	 * 			Lista com tags que casaram com o prefixo.
	 * @param prefixo
	 * 			Prefixo sobre o qual foi feita a consulta.
	 */
	private void contar(List<String> matches, String prefixo) {
		this.iteracoes++;
		this.bitsLeitorTag += prefixo.length();
		this.bitsTagLeitor += (matches.size()*128);
	}
	
	/**
	 * Execucao do protocolo QT.
	 * 
	 * @param prefixo
	 * 			Prefixo sobre o qual sera feita a consulta.
	 * @param bitExtra
	 * 			Bit adicionado ao prefixo em caso de conflito.
	 */
	private void consulta(final String prefixo, final String bitExtra) {
		String novoPrefixo = prefixo.concat(bitExtra);
		
		List<String> matches = compara(novoPrefixo);
		
		contar(matches, novoPrefixo);
		
		if(matches.size() == 1) {
			// Sucesso
			getTags().removeAll(matches);
		} else if(matches.size() > 1){
			// Conflito
			consulta(novoPrefixo, "0");
			consulta(novoPrefixo, "1");
		} else {
			// Idle
		}
	}
	
	/**
	 * Metodo responsavel por imprimir o resultado em
	 * um arquivo .csv.
	 * 
	 * @param resultados
	 * 			Resultados obtidos apos uma consulta.
	 */
	private void escrever(List<String> resultados) {
	    File file = new File("QT.csv");

        FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.append("Tags,Iteracoes,Bits\n");
			for(String resultado : resultados) {
            	writer.append(resultado);
            }
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null) {
					writer.close();
	        	}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	/**
	 * Retorna o numero de iteracoes de consulta.
	 * 
	 * @return Numero de iteracoes de uma consulta.
	 */
	public int getIteracoes() {
		return this.iteracoes;
	}

	/**
	 * Quantidade de bits trocados em uma consulta
	 * na direcao leitor/tag.
	 * 
	 * @return Quantidade de bits trocados em uma consulta
	 * 		   na direcao leitor/tag.
	 */
	public int getBitsLeitorTag() {
		return this.bitsLeitorTag;
	}
	
	/**
	 * Retorna a quantidade de bits trocados em uma consulta.
	 * 
	 * @return Quantidade de bits trocados em uma consulta.
	 */
	public int getBitsTagLeitor() {
		return this.bitsTagLeitor/qtdTags;
	}

	/**
	 * Retorna a lista de tags usadas em uma consulta.
	 * 
	 * @return Lista de tags usadas em uma consulta.
	 */
	private List<String> getTags() {
		return this.tags;
	}
	
	/**
	 * Inicia o protocolo QT.
	 */
	public void run() {
		this.consulta("", "0");
		this.consulta("", "1");
	}

	public static void main(String[] args) {
		QT qt = null;
		List<String> tags = null;
		List<String> resultados = new ArrayList<String>();
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		int totalPassos, bitsLeitorTag, bitsTagLeitor;
		
		int[][] passosDados = new int[10][2];
		int[][] bitsLeitorTagDados = new int[10][2];
		int[][] bitsTagLeitorDados = new int[10][2];
		
		// Para cada caso de teste, faca:
		for(int i = 1 ; i*100 <= 1000; i++) {
			totalPassos = 0;
			bitsLeitorTag = 0;
			bitsTagLeitor = 0;
			
			for(int j = 1; j <= 500; j++) {
				// Carregar as tags na memoria:
				tags = (new TagLoader()).load(i*100, j);
				// Criar novo objeto:
				qt = new QT(tags);
				// Executar protocolo:
				qt.run();
//				// Adicionar resultado da consulta a uma lista:
//				resultados.add(i + "," + qt.getIteracoes() + "," + qt.getBitsTrocados() + "\n");
				totalPassos += qt.getIteracoes();
				bitsLeitorTag += qt.getBitsLeitorTag();
				bitsTagLeitor += qt.getBitsTagLeitor();
			}
			
			passosDados[i-1][0] = totalPassos/500;
			bitsLeitorTagDados[i-1][0] = bitsLeitorTag/500;
			bitsTagLeitorDados[i-1][0] = bitsTagLeitor/500;
			
			System.out.println(i);
		}
		
		System.out.println((Calendar.getInstance().getTimeInMillis() - startTime)/1000+"s");
		
		LineChartTree passosGrafico = new LineChartTree(new Chart("01-passos-qt", "Numero de passos", "Numero de etiquetas", passosDados));
		LineChartTree bitsLeitorTagGrafico = new LineChartTree(new Chart("02-leitor-tag-qt", "Numero de bits trocados Leitor->Tag", "Numero de etiquetas", bitsLeitorTagDados));
		LineChartTree bitsTagLeitorGrafico = new LineChartTree(new Chart("03-tag-leitor-qt", "Numero de bits trocados Tag->Leitor", "Numero de etiquetas", bitsTagLeitorDados));
		
		try {
			passosGrafico.create();
			bitsLeitorTagGrafico.create();
			bitsTagLeitorGrafico.create();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Escrever em arquivo:
		qt.escrever(resultados);
	}
}
