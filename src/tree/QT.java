package tree;

import java.util.LinkedList;
import java.util.Queue;

public class QT implements TreeAlgorithm{
	
	int tagsNum = 0;
	int totalBitsTag = 0;
	int totalBitsReader = 0;
	int totalIterations = 0;
		
	public void run(Tag[] tags){
		
		this.tagsNum = tags.length;
		
		String prefix;
		Queue<String> prefixes = new LinkedList<String>();
		int answers;
		
		prefixes.add("0");
		prefixes.add("1");
		
		while(!prefixes.isEmpty()){
			
			prefix = prefixes.remove();
			answers = queryTags(tags, prefix);
			
			if(answers > 1){
				prefixes.add(prefix+"0");
				prefixes.add(prefix+"1");
			}
			
			totalIterations += 1;
			totalBitsReader += prefix.length();
			totalBitsTag += answers * 96;	
		}
		
	}
	
	public int queryTags(Tag[] tags, String prefix){
		int answers = 0;
		
		for(Tag tag : tags){
			if(tag.matchPrefix(prefix, 96) != null){
				answers += 1;
			}
		}
		
		return answers;
	}
	
	public int getTotalBitsTag(){
		return this.totalBitsTag/this.tagsNum;
	}
	
	public int getTotalBitsReader(){
		return this.totalBitsReader;
	}
	
	public int getTotalIterations(){
		return this.totalIterations;
	}
	
}
