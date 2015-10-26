package dfsa;

public class EomLee implements Estimator{
	
	private double beta = 0;
	private double gama = 2;
	
	double beta_ = 0;
	double ebeta = 0;

	public int estimate(int lastFrameSize, int successSlots, int collisionSlots){
		
		if(lastFrameSize == 0){
			return 1;
		}
		
		beta = (double) lastFrameSize / ( (gama * collisionSlots) + successSlots );
		
		beta_ = (double) 1 / beta;
		ebeta = Math.pow(Math.E, 0-beta_);
		
		gama = (double) (1 - ebeta) / (beta * (1 - ((1 + beta_)*ebeta)) );
		
		return (int) gama * collisionSlots;
	}
	
}
