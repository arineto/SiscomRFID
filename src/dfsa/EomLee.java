package dfsa;

public class EomLee implements Estimator{

	public int estimate(int successSlots, int collisionSlots){
		int response;
		if(collisionSlots == 0){
			response = 1;
		}else{
			response = successSlots + 2*collisionSlots;
		}
		return response;
	}
	
}
