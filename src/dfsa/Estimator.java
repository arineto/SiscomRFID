package dfsa;

public interface Estimator {
	int estimate(int success, int empty, int collision);
}
