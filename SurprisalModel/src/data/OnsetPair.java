package data;

public class OnsetPair implements Comparable<OnsetPair> {
	private final double onset; //sentence start relative
	private final double end;
	private final String word; 
	public OnsetPair(double onset, double end, String word) {
		this.onset = onset;
		this.end = end;
		this.word = word;
	}
	public double getOnset() {
		return onset;
	}
	public String getWord() {
		return word;
	}
	public double getEnd() {
		return end;
	}
	public boolean isWord() {
		return !(word.isEmpty() || (word.charAt(0) == '[' && word.charAt(word.length()-1) == ']'));	
	}
	@Override
	public int compareTo(OnsetPair arg0) {
		if (this.onset < arg0.onset) {
			return -1;
		}
		else if (this.onset - arg0.onset < 0.0001) {
			return 0;
		}
		return 1;
	}
	public String toString() {
		return word+","+onset+","+end;
	}
}
