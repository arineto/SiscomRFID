package dfsa;

public class LowerBound implements Estimator{
	
	public int estimate(int success, int empty, int collision){
		int response;
		if(collision == 0){
			response = 64;
		}else{
			response = 2*collision;
		}
		return response;
	}
}
