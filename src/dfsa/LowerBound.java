package dfsa;

public class LowerBound implements Estimator{
	
	public int estimate(int success, int empty, int collision){
		
		int lastFrameSize = success + empty + collision;
		
		int response;
		if(lastFrameSize == 0){
			response = 64;
		}else{
			response = 2*collision;
		}
		return response;
	}
}
