package charts;

public class Chart {
	
	private String title;
	private String Estimator;
	private String axisX;
	private String axisY;
	private int [][] data;
	
	public Chart(String title, String axisX, String axisY, int[][] data) {
		
		this.title = title;
		this.Estimator = Estimator;
		this.axisX = axisX;
		this.axisY = axisY;
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEstimator() {
		return Estimator;
	}

	public void setEstimator(String Estimator) {
		this.Estimator = Estimator;
	}

	public String getAxisX() {
		return axisX;
	}

	public void setAxisX(String axisX) {
		this.axisX = axisX;
	}

	public String getAxisY() {
		return axisY;
	}

	public void setAxisY(String axisY) {
		this.axisY = axisY;
	}

	public int[][] getData() {
		return data;
	}

	public void setData(int[][] data) {
		this.data = data;
	}
	
}
