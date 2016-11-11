package data;

public class Disfluency implements DelayEvent {
	private final double elapsedTime;
	public Disfluency(double elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	@Override
	public double getElapsedTime() {
		return elapsedTime;
	}

}
