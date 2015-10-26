package dfsa;

public interface Estimator {
	
	int estimate(int lasFrameSize, int successSlots, int colisionSlots);

}
