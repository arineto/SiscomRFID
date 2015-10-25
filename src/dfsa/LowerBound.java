package dfsa;

public class LowerBound implements Estimator{
	
	public int estimate(int successSlots, int collisionSlots){
		int response;
		if( (successSlots == 0) && (collisionSlots == 0)){
			response = 50;
		}else{
			response = successSlots + 2*collisionSlots;
		}
		return response;
	}
	
}
