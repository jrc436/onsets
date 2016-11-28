package evaluation;

import optimizers.DoublePair;

public class LMPair extends DoublePair {
	private final double x;
	private final double y;
	public LMPair(double first, double second) {
		super(first, second);
		this.x = first;
		this.y = second;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
}
