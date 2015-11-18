package qt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TagLoader {
	
	public List<String> load(int tagNum, int fileNumber) {
		List<String> tags = new ArrayList<String>(tagNum);
		String fileName = "tags/"+tagNum+"/"+fileNumber+".txt";
		String tagID = null;
		
		try{			   
			InputStream fis = new FileInputStream(fileName);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		    
		    for (int i = 0; i < tagNum; i++) {
				tagID = br.readLine();
				if(tagID != null){
					tags.add(tagID);
				}
			}
		    
		    br.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return tags;
	}
}
