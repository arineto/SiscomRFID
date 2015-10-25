package dfsa;

public class DFSA {
	
	public Estimator estimator;
	public int tagNumber;
	private boolean finished;
	private int totalCollisionSlots;
	private int totalEmptySlots;
	private long totalTime;
	private Tag tags[];
	
	public DFSA(Estimator estimator, int tagNumber){
		this.estimator = estimator;
		this.tagNumber = tagNumber;
		this.tags = new Tag[this.tagNumber];
		this.finished = false;
		this.totalCollisionSlots = 0;
		this.totalEmptySlots = 0;
		this.totalTime = 0;
		
		Tag tag;
		for(int i=0; i<this.tagNumber; i++){
			tag = new Tag();
			tags[i] = tag;
		}
	}

	public void run() throws InterruptedException{
		
		int[] frame;
		int slotsNum;
		int lastFrameCollisionSlots = 0;
		int lastFrameSuccessSlots = 0;
		
		long startTime = System.currentTimeMillis();
		while(!this.isFinished()){
			
			int collisionSlots = 0;
			int emptySlots = 0;
			int successSlots = 0;
			
			slotsNum = estimator.estimate(lastFrameSuccessSlots, lastFrameCollisionSlots);
			frame = new int[slotsNum];
			
			System.out.println("Iniciando fram com "+slotsNum+" slots");
			
			for(int i=0; i<this.tags.length; i++){
				frame = tags[i].alive(frame);
			}
			
			for(int i=0; i<frame.length; i++){
				if(frame[i] == -1){
					// A collision happened
					collisionSlots += 1;
				}
				else if(frame[i] == 0){
					// An empty slot happened
					emptySlots += 1;
				}
				else{
					// A tag was identified
					successSlots += 1;
					this.muteTag(frame[i]);
					System.out.println("Tag com ID="+frame[i]+" identificada");
				}
			}
			
			lastFrameCollisionSlots = collisionSlots;
			lastFrameSuccessSlots = successSlots;
			
			this.updateCollisionSlots(collisionSlots);
			this.updateEmptySlots(emptySlots);
			
			if( (collisionSlots == 0) && (successSlots == 0) ){
				this.finish();
			}
			
			System.out.println("Frame finalizado");
			System.out.println("========================================================================================");

		}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		this.updateTotalTime(totalTime);
		
	}
	
	public void muteTag(int tagID){
		for(int i=0; i<tags.length; i++){
			if(this.tags[i].getID() == tagID){
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
	
	private void updateTotalTime(long time){
		this.totalTime = time;
	}
	
	public long getTotalTime(){
		return this.totalTime;
	}
	
}
