package dfsa;

public class DFSA {
	
	private Tag tags[];
	public int tagNumber;
	private boolean finished;
	public Estimator estimator;	
	
	private int totalSlots;
	private int totalSuccessSlots;
	private int totalCollisionSlots;
	private int totalEmptySlots;
	
	
	public DFSA(Estimator estimator, int tagNumber){
		this.estimator = estimator;
		this.tagNumber = tagNumber;
		this.tags = new Tag[this.tagNumber];
		this.finished = false;
		
		this.totalSlots = 0;
		this.totalCollisionSlots = 0;
		this.totalCollisionSlots = 0;
		this.totalEmptySlots = 0;
		
		Tag tag;
		for(int i=0; i<this.tagNumber; i++){
			tag = new Tag();
			tags[i] = tag;
		}
	}

	public void run(){
		
		int[] frame;
		int slotsNum = 0;
		
		int lastSuccesSlots = 0;
		int lastEmptySlots = 0;
		int lastCollisionSlots = 0;
		
		while(!this.isFinished()){

			int successSlots = 0;
			int emptySlots = 0;
			int collisionSlots = 0;			
			
			slotsNum = estimator.estimate(lastSuccesSlots, lastEmptySlots, lastCollisionSlots);
			
			frame = new int[slotsNum];
			
			for(int i=0; i<this.tags.length; i++){
				frame = tags[i].alive(frame);
			}
			
			for(int i=0; i<frame.length; i++){
				if(frame[i] == -1){
					collisionSlots += 1;
				}
				else if(frame[i] == 0){
					emptySlots += 1;
				}
				else{
					successSlots += 1;
					this.muteTag(frame[i]);
				}
			}
			
			lastSuccesSlots = successSlots;
			lastEmptySlots = emptySlots;
			lastCollisionSlots = collisionSlots;
			
			this.updateCollisionSlots(collisionSlots);
			this.updateEmptySlots(emptySlots);
			this.updateTotalSlots(slotsNum);
			this.updateTotalSuccessSlots(successSlots);
			
			if( (collisionSlots == 0) && (successSlots == 0) ){
				this.finish();
			}
		}
	}
	
	//Adicionado && !this.tags[i].isMuted() so para se tiver tags com a mesma ID ele procurar a que nao foi silenciada
	public void muteTag(int tagID){
		for(int i=0; i<tags.length; i++){
			if(this.tags[i].getID() == tagID && !this.tags[i].isMuted()){
				this.tags[i].mute();
				break;
			}
		}
	}
	
	private boolean isFinished(){
		return this.finished;
	}
	
	private void finish(){
		this.finished = true;
	}
	
	private void updateCollisionSlots(int collisionSlots){
		this.totalCollisionSlots += collisionSlots;
	}
	
	public int getTotalCollisionSlots(){
		return this.totalCollisionSlots;
	}
	
	private void updateEmptySlots(int emptySlots){
		this.totalEmptySlots += emptySlots;
	}
	
	public int getTotalEmptySlots(){
		return this.totalEmptySlots;
	}
	
	public void updateTotalSlots(int slots){
		this.totalSlots += slots;
	}
	
	public int getTotalSlots(){
		return this.totalSlots;
	}
	
	public void updateTotalSuccessSlots(int successSlots){
		this.totalSuccessSlots += successSlots;
	}
	
	public int getTotalSuccessSlots(){
		return this.totalSuccessSlots;
	}
}
