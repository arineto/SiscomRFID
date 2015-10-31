package qt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QT {
	/**
	 * Quantidade de iteracoes em uma consulta.
	 */
	private int iteracoes;
	/**
	 * Quantidade de bits trocados em uma consulta.
	 */
	private int bitsTrocados;
	/**
	 * Lista de tags utilizadas em uma consulta.
	 */
	private final List<String> tags;
	
	/**
	 * Construtor.
	 * @param tags
	 * 			Lista de tags.
	 */
	public QT(final List<String> tags) {
		this.iteracoes = 0;
		this.tags = tags;
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
		this.bitsTrocados += (matches.size()*96) + prefixo.length();
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
		return iteracoes;
	}

	/**
	 * Retorna a quantidade de bits trocados em uma consulta.
	 * 
	 * @return Quantidade de bits trocados em uma consulta.
	 */
	public int getBitsTrocados() {
		return bitsTrocados;
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
		
		// Para cada caso de teste, faca:
		for(int i = 100 ; i <= 1000; i+=100) {
			// Carregar as tags na memoria:
			tags = (new TagLoader()).load(i);
			// Criar novo objeto:
			qt = new QT(tags);
			// Executar protocolo:
			qt.run();
			// Adicionar resultado da consulta a uma lista:
			resultados.add(i + "," + qt.getIteracoes() + "," + qt.getBitsTrocados() + "\n");			
		}
		
		// Escrever em arquivo:
		qt.escrever(resultados);
	}
}
