package actdelay;

import optimizers.DoublePair;

public class ActDelay extends DoublePair {
	private final double activation;
	private final double delay;
	public ActDelay(double activation, double delay) {
		super(activation, delay);
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
	public static ActDelay fromString(String s) {
		String[] parts = s.split(",");
		if (parts.length != 2) {
			throw new IllegalArgumentException("String: "+s+" does not encode an actdelay, wrong number of commas");
		}
		try {
			double activation = Double.parseDouble(parts[0]);
			double delay = Double.parseDouble(parts[1]);
			return new ActDelay(activation, delay);
		}
		catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Error parsing actdelay, csv don't encod ea double: "+s);
		}
	}
}
