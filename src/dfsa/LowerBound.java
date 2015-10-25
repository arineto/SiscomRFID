package dfsa;

public class LowerBound implements Estimator{
	
	public int estimate(int successSlots, int collisionSlots){
		int response;
		if(collisionSlots == 0){
			response = 50;
		}else{
			response = 2*collisionSlots;
		}
		return response;
	}
	
}
