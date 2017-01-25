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
		return super.toString()+","+durs + wordid;
	}
}
