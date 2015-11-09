package tree;

import java.util.Stack;

public class QwT implements TreeAlgorithm{

	int totalBitsTag = 0;
	int totalBitsReader = 0;
	int totalIterations = 0;
	int tagsNum = 0;
	String tagID = "";
	
		
	public void run(Tag[] tags){
		
		this.tagsNum = tags.length;
		
		String prefix;
		String lastPrefix = "";
		Stack<String> prefixes = new Stack<String>();
		int answers = 0;
		int windowSize = 1;
		
		prefixes.push("0");
		prefixes.push("1");
		
		while(!prefixes.isEmpty()){
			
			prefix = prefixes.pop();
			windowSize = calculateWindow(answers, prefix, lastPrefix, windowSize);
			answers = queryTags(tags, prefix, windowSize);
			
			if(answers == 1){
				if(prefix.length() + windowSize < 96){
					prefixes.push(prefix + tagID);
				}
			}else if(answers > 1){
				prefixes.push(prefix+"0");
				prefixes.push(prefix+"1");
			}
			
			lastPrefix = prefix;
			
			totalIterations += 1;
			totalBitsReader += prefix.length();
			totalBitsTag += answers * windowSize;
		}
		
	}
	
	public int queryTags(Tag[] tags, String prefix, int windowSize){
		int answers = 0;
		
		for(Tag tag : tags){
			tagID = tag.matchPrefix(prefix, windowSize);
			if(tagID != null){
				answers += 1;
			}
		}
		
		return answers;
	}
	
	public int calculateWindow(int answers, String prefix, String lastPrefix, int lastWindow){
		int window = 1;
		
		if((answers == 1) && (lastPrefix.length() < prefix.length())){
			double k = prefix.length();
			double ebeta = Math.pow(Math.E, (0.5*k));
			window = (int) (k*(1 - (1 / ebeta)));
		}else if((answers != 1) && (lastPrefix.length() < prefix.length())){
			window = lastWindow;
		}else if((answers >= 0) && (lastPrefix.length() >= prefix.length())){
			window = 1;
		}
		
		if(prefix.length() + window > 96){
			window = 96 - prefix.length();
		}
		
		return window;
	}
	
	public int getTotalBitsTag(){
		return this.totalBitsTag/tagsNum;
	}
	
	public int getTotalBitsReader(){
		return this.totalBitsReader;
	}
	
	public int getTotalIterations(){
		return this.totalIterations;
	}
	
}
