package dfsa;

import java.util.Random;

public class Tag {

	private int ID;
	private boolean muted;
	private Random rand;
	
	public Tag(){
		rand = new Random();
		
		this.ID = rand.nextInt();
		this.muted = false;
	}
	
	public int[] alive(int[] frame){
		
		if(!this.muted){
			rand = new Random();
			int slot = rand.nextInt(frame.length);
			
			if(frame[slot] == 0){
				frame[slot] = this.ID;
			}else{
				frame[slot] = -1;
			}
		}
		
		return frame;
	}
	
	public void mute(){
		this.muted = true;
	}
	
	public boolean isMuted(){
		return this.muted;
	}
	
	public int getID(){
		return this.ID;
	}
	
}
