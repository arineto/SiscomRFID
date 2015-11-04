package dfsa;

public class LowerBound implements Estimator{
	
	public int estimate(int lastFrameSize, int successSlots, int collisionSlots){
		int response;
		if(collisionSlots == 0){
			response = 64;
		}else{
			response = 2*collisionSlots;
		}
		return response;
	}
	
	public int estimateTags(int lastFrameSize, int successSlots, int collisionSlots){
		int response;
		if(collisionSlots == 0){
			response = 1;
		}else{
			response = successSlots + (2*collisionSlots);
		}
		return response;
	}
}
