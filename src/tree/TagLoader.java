package tree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class TagLoader {
	
	public Tag[] load(int tagNum, int fileNumber){
		
		Tag[] tags = new Tag[tagNum];
		String fileName = "tags/"+tagNum+"/"+fileNumber+".txt";
		String tagID;
		
		try{
		   
			InputStream fis = new FileInputStream(fileName);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		    
		    for (int i = 0; i < tags.length; i++) {
				tagID = br.readLine();
				if(tagID != null){
					tags[i] = new Tag(tagID);
				}
			}
		    
		    br.close();
		    
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return tags;
	}
	
	public static void main(String[] args) {
		TagLoader loader = new TagLoader();
		Tag[] tags = loader.load(1000, 1);
		for (int i = 0; i < tags.length; i++) {
			System.out.println(tags[i].getID());
		}
	}
}
