package dfsa;

public class EomLee implements Estimator{
	
	private double beta = 0;
	private double last_beta = 0;
	private double gama = 2;
	private double last_gama = 2;
	
	private double threshold = 0.001;
	
	double beta_ = 0;
	double ebeta = 0;

	public int estimate(int lastFrameSize, int successSlots, int collisionSlots){
		
		if(lastFrameSize == 0){
			return 64;
		}else{
			do{

				beta = (double) lastFrameSize / ( (last_gama * collisionSlots) + successSlots );
				beta_ = (double) 1 / beta;
				ebeta = 1 / Math.pow(Math.E, beta_);
				gama = (double) (1 - ebeta) / (beta * (1 - ((1 + beta_)*ebeta)) );
				
				last_beta = beta;
				last_gama = gama;
				
			}while( (last_gama - gama) >= threshold );

			return (int) gama * collisionSlots;
		}
	}
	
	public int estimateTags(int lastFrameSize, int successSlots, int collisionSlots){
		
		if(lastFrameSize == 1){
			return 1;
		}
		
		return (int) (lastFrameSize / beta);
	}
}
