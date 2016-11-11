package actdelay;

public class ActDelay {
	private final double activation;
	private final double delay;
	public ActDelay(double activation, double delay) {
		this.activation = activation;
		this.delay = delay;
	}
	public double getActivation() {
		return activation;
	}
	public double getDelay() {
		return delay;
	}
	public String toString() {
		return activation+","+delay;
	}
}
