package evaluation;

import optimizers.DoublePair;

public class LMPair extends DoublePair {
	private final double x;
	private final double y;
	public LMPair(double x, double y) {
		super(x, y);
		this.x = x;
		this.y = y;
	}
	public LMPair(DoublePair dp) {
		this(dp.getFirst(), dp.getSecond());
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public static LMPair fromString(String s) {
		return new LMPair(DoublePair.fromString(s));
	}
}
