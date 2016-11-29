package baseline;

public class UniSizeDelay {
	private final double unigramscore;
	private final int wordlength;
	private final double delay;
	private final String word;
	public UniSizeDelay(String word, double delay, double unigramscore) {
		this.unigramscore = unigramscore;
		this.wordlength = word.length();
		this.delay = delay;
		this.word = word;
	}
	public String toString() {
		return word+","+this.unigramscore + "," + this.wordlength + "," + this.delay;
		//return this.wordlength + "," + this.delay;
	}
	public double getDelay() {
		return delay;
	}
	public int getLength() {
		return wordlength;
	}
	public double getUni() {
		return unigramscore;
	}
	public static UniSizeDelay fromString(String entry) {
		String[] parts = entry.split(",");
		try {
			double uni = Double.parseDouble(parts[1]);
			//double l = Double.parseDouble(parts[2]);
			double del = Double.parseDouble(parts[3]);
			return new UniSizeDelay(parts[0], uni, del);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			throw new IllegalArgumentException("Doesn't represent a valid unisizedelay");
		}
	}
}
