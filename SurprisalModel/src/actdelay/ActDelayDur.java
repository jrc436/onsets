package actdelay;

import java.util.Queue;

public class ActDelayDur extends ActDelay {
	private final double[] prevDiffs;
	private final String wordid;
//	private final int wordIdx;
//	private final int sentIdx;
	public ActDelayDur(ActDelay actd, Queue<Double> prevDiffs, String wordid) {//, String wordid, int wordIdx) {
		super(actd);
		this.prevDiffs = new double[prevDiffs.size()];
		int i = 0;
		for (double d : prevDiffs) {
			this.prevDiffs[prevDiffs.size()-i-1] = d;
			i++;
		}
		this.wordid = wordid;
	}
	public String alttoString() {
		String durs = "";
		for (double d : prevDiffs) {
			durs += d +",";
		}
		return super.toString()+","+durs + wordid;
	}
	public String toString() {
		String oldstr = alttoString();
		String[] parts = oldstr.split(",");
		String activation = parts[0];
		String word = parts[parts.length-1];
		String newline = System.getProperty("line.separator");
		String toReturn = "";
		for (int i = 1; i < parts.length-1; i++) {
			int idx = i-1;
			String line = activation+","+parts[i]+","+idx+","+word;
			toReturn += line + newline;
		}
		if (!toReturn.isEmpty()) {
			toReturn = toReturn.substring(0, toReturn.length()-1);
		}
		return toReturn;
	}
}
