package sentence;

import util.collections.Pair;

public class BufferProcess {
	private double remainingTime;
	private WordEvent word;
	private final BufferCallback bufferCallBack;
	public BufferProcess() {
		this(null);
	}
	public BufferProcess(BufferCallback bcb) {
		this.bufferCallBack = bcb;
		this.remainingTime = 0.0;
	}
	public void startProcess(WordEvent word, double remainingTime) {
		this.word = word;
		this.remainingTime = remainingTime;
	}
	public boolean isFinished() {
		return remainingTime <= 0.0;
	}
	public void elapseTime(double time) {
		remainingTime -= time;
	}
	public WordEvent getWord() {
		return word;
	}
	public void flushBuffer() {
		this.remainingTime = 0;
		this.word = null;
	}
	public boolean processIfFinished(Sentence s) {
		if (bufferCallBack == null) {
			throw new UnsupportedOperationException("Need to specify a callback. Otherwise, use methods directly.");
		}
		if (isFinished()) {
			Pair<WordEvent, Double> wordAndDuration = bufferCallBack.getNextBufferState(s);
			if (wordAndDuration == null) {
				flushBuffer();
			}
			else {
				startProcess(wordAndDuration.one(), wordAndDuration.two());
			}
			return true;
		}
		return false;
	}
}
