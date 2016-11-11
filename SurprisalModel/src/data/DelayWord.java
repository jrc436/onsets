package data;

public class DelayWord implements DelayEvent {
	private final double delay;
	private final String word;
	private final double elapsedTime;
	public DelayWord(String word, double delay, double elapsedTime) {
		this.word = word.toLowerCase();
		this.delay = delay;
		this.elapsedTime = elapsedTime;
	}
	//get the amount of time since the previous word ended
	public double getElapsedTime() {
		return elapsedTime;
	}
	public String getWord() {
		return word;
	}
	public double getDelay() {
		return delay;
	}
}
