package actdelay;

public class ActDelayDur {
	private final ActDelay actd;
	private final double prevDuration;
	private final String wordid;
	private final int wordIdx;
	public ActDelayDur(ActDelay actd, double prevDuration, String wordid, int wordIdx) {
		this.actd = actd;
		this.prevDuration = prevDuration;
		this.wordid = wordid;
		this.wordIdx = wordIdx;
	}
	public String toString() {
		return actd.toString()+","+prevDuration + "," + wordid + "," + wordIdx;
	}
}
