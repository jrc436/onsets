package sentence;

import java.util.Set;

public class WordEvent {
	//words are in the following groups: realized, last-k-realized, in-wm, in-window, in-sentence
	//don't need to keep track of realized, use k=5 for wm, and last-k, use window=12 for in-window (cite acl paper)
	//for 'sentence', can just use the full conversation, like previously
	private final String word;
	private final double duration;
	private final double onset;
	private final boolean isClosed;
	private static final String noiseString = "[noise]";
	public WordEvent(String word, double onset, double duration, boolean isClosed) {
		this.word = word;
		this.isClosed = isClosed;
		this.duration = duration;
		this.onset = onset;
	}
	public static WordEvent fromString(String s, Set<String> closedClass) {
		String[] parts = s.split(",");
		String wordPart = parts[0];
		if (wordPart.equals(noiseString)) {
			return null;
		}
		wordPart = wordPart.replace("[", "").replace("]", "");
		double startTime = Double.parseDouble(parts[1]);
		double endTime = Double.parseDouble(parts[2]);
		boolean isClosed = closedClass.contains(wordPart);
		//closedClass.contains(wordPart);
		return new WordEvent(wordPart, startTime, endTime - startTime, isClosed);
	}
	public String toString() {
		return word;
	}
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof WordEvent)) {
			return false;
		}
		return this.word.equals(((WordEvent)other).word);
	}
	@Override
	public int hashCode() {
		return word.hashCode();
	}
	public String getWord() {
		return word;
	}
	public boolean isClosed() {
		return isClosed;
	}
	public double getDuration() {
		return duration;
	}
	public double getOnset() {
		return onset;
	}
}
