package evaluation;

public class LM {
	private static final double unicorr = -4.342923;
	private static final double uniintercept = 0.635068;
	private static final double sizecorr = -0.005112;
	private static final double sizeintercept = 0.629435;
	private final double corr;
	private final double intercept;
	public LM(double corr, double intercept) {
		this.corr = corr;
		this.intercept = intercept;
	}
	public double predict(double inp) {
		return intercept + corr * inp;
	}
	public static LM getUniDelayLM() {
		return new LM(unicorr, uniintercept);
	}
	public static LM getSizeLM() {
		return new LM(sizecorr, sizeintercept);
	}
}
