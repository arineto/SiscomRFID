package tree;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TagLoader {
	
	public Tag[] load(int tagNum){
		
		Tag[] tags = new Tag[tagNum];
		String fileName;
		String tagID;
		
		for(int i=1; i<=tagNum; i++){
			fileName = tagNum + "/" + i;
			tagID = this.readFile(fileName);
			tags[i-1] = new Tag(tagID);
		}
		
		return tags;
	}
	
	public String readFile(String fileName){
		List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.get(0);
	}

}
