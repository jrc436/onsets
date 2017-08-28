package wm;

import dm.DeclarativeMemory;
import sentence.Sentence;
import sentence.WordEvent;
import strategy.IStrategy;

public abstract class AbstractWorkingMemory extends BaseWorkingMemory implements IStrategy {

	protected DeclarativeMemory dm;
	private static final double smallDouble = 0.001;
	private double lastOnset = -1;
	// Time required before processing this retrieval buffer.
	private double requiredRetrievalTime = -1;
	private double elapsedTime = 0;
	private WordEvent retrievalBuffer = null;

	public AbstractWorkingMemory(int windowSize, int k) {
		super(windowSize, k);
	}

	public void initialize(Sentence s) {
		int i = 0;
		while (i < k / 2 + k % 2) {
			startRetrieval(s);
			i++;
			requiredRetrievalTime = -1;
			processRetrievalBuffer();
		}
	}

	// Set goal buffer.
	public void startRetrieval(Sentence s) {
		if (requiredRetrievalTime < 0) {
			if (retrievalBuffer != null) {
				throw new RuntimeException("State error. Attempting a retrieval before the previous retrieval: " + retrievalBuffer + " has been processed.");
			}
			WordEvent next = this.getNextRetrieval(s.getRetrievableWords(getWindowSize()));
			if (next != null) {
				double rt = dm.directRetrieval(next.getWord());
				requiredRetrievalTime = rt;
				retrievalBuffer = next;
			}
		}
	}

	private void processRetrievalBuffer() {
		if (retrievalBuffer != null && requiredRetrievalTime < 0) {
			currentContents.add(retrievalBuffer.getWord());
			retrievalBuffer = null;
		}
	}
	private void elapseTime(double time) {
		requiredRetrievalTime -= time;
		elapsedTime += time;
	}
	public double getElapsedTime() {
		return elapsedTime;
	}

	@Override
	public boolean step(WordEvent nextWord) {
		// retrievalBuffer could be null. realizedWord should never be null.
		
		
		
		if (!nextWord.isClosed() && !currentContents.contains(nextWord.getWord()) && !nextWord.equals(retrievalBuffer)) {
			throw new RuntimeException(nextWord.toString() + " is not in workingMemory: "+currentContents.toString()+" or a closed class word");
		}
		// The only case where there isn't an error is if it's still being retrieved. In any other case, we expect them to theoretically re-order their sentence
		// in this case, we expect them to have a disfluency or take longer to speak the previous part. So a word is spoken unless we're still retrieving.
		boolean willSpeakWord = !nextWord.equals(retrievalBuffer);
		boolean firstWord = this.lastOnset == -1;
		// If it's the first word, elapse a small double for the math to work. Otherwise, elapse the time between the words. If no word is going to be spoken,
		// elapse the time until that retrieval is finished.
		double elapsedTime = smallDouble + (willSpeakWord ? firstWord ? 0.0 : nextWord.getOnset() - this.lastOnset : requiredRetrievalTime);
		if (willSpeakWord) {
			this.lastOnset = nextWord.getOnset();
			currentContents.remove(nextWord.getWord());
		}
		dm.step(elapsedTime);
		elapseTime(elapsedTime);
		processRetrievalBuffer();
		if (!willSpeakWord) {
			System.out.println("Not spoken: " + nextWord);
		}
		return willSpeakWord;
	}

	public void setDM(DeclarativeMemory dm) {
		this.dm = dm;
	}

	protected DeclarativeMemory getDM() {
		return dm;
	}
}
