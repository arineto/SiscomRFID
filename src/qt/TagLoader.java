package qt;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TagLoader {
	
	private String readFile(String fileName) {
		List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.get(0);
	}
	
	public List<String> load(int tagNum) {		
		List<String> tags = new ArrayList<String>(tagNum);
		String fileName = null;
		String tagID = null;
		
		for(int i = 1; i <= tagNum; i++){
			fileName = tagNum + "/" + i;
			tagID = this.readFile(fileName);
			tags.add(tagID);
		}
		
		return tags;
	}
}
