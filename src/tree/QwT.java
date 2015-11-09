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
//			System.out.println(prefix);
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
//			System.out.printf("respostas: %d window: %d total: %d\n", answers, windowSize, answers*windowSize);
		}
		
	}
	
	public int queryTags(Tag[] tags, String prefix, int windowSize){
		int answers = 0;
		String tag_answer = null;
		
		for(Tag tag : tags){
			tag_answer = tag.matchPrefix(prefix, windowSize);
			if(tag_answer != null){
				this.tagID = tag_answer;
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
			window = (int) Math.ceil((k*(1.0 - (1.0 / ebeta))));
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
