package actdelay;

import java.util.Queue;

public class ActDelayDur {
	private final ActDelay actd;
	private final double[] prevDiffs;
	private final String wordid;
//	private final int wordIdx;
//	private final int sentIdx;
	public ActDelayDur(ActDelay actd, Queue<Double> prevDiffs, String wordid) {//, String wordid, int wordIdx) {
		this.actd = actd;
		this.prevDiffs = new double[prevDiffs.size()];
		int i = 0;
		for (double d : prevDiffs) {
			this.prevDiffs[prevDiffs.size()-i] = d;
			i++;
		}
		this.wordid = wordid;
//		this.wordIdx = wordIdx;
	}
	public String toString() {
		String durs = "";
		for (double d : prevDiffs) {
			durs += d +",";
		}
		return actd.toString()+","+durs + wordid;
	}
}
