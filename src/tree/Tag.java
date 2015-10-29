package tree;

public class Tag {

	private String ID;
	
	public Tag(String ID){
		this.ID = ID;
	}
	
	public String getID(){
		return this.ID;
	}
	
	public String matchPrefix(String prefix, int window){
		String response = null;
		
		if(this.ID.startsWith(prefix)){
			if(window == 96){
				response = this.ID;
			}else{
				response = this.ID.substring(prefix.length(), prefix.length() + window);
			}	
		}
		
		return response;
	}
	
}
