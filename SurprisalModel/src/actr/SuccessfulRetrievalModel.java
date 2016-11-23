package actr;

public class SuccessfulRetrievalModel {
	private final double intercept;
	private final double cutoff;
	public SuccessfulRetrievalModel(double intercept, double cutoff) {
		this.intercept = intercept;
		this.cutoff = cutoff;
	}
	public double getRetrievalTime(double activation) {
		return intercept + 1 / activation - cutoff * Math.exp(-1 * cutoff * activation) / (1 - Math.exp(-1 * cutoff * activation));
	}
}
