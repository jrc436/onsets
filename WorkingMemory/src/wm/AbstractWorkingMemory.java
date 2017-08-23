package wm;

import dm.DeclarativeMemory;
import sentence.Sentence;
import sentence.WordEvent;
import strategy.IStrategy;

public abstract class AbstractWorkingMemory extends BaseWorkingMemory implements IStrategy {

	protected DeclarativeMemory dm;
	
	private double lastOnset = -1;
	// Time required before processing this retrieval buffer.
	private double requiredRetrievalTime = -1;
	private WordEvent retrievalBuffer = null;
	
	public AbstractWorkingMemory(int windowSize, int k) {
		super(windowSize, k);
	}
	public void initialize(Sentence s) {
		int i = 0;
		while (i < k / 2 + k % 2) {
			startRetrieval(s);
			i++;
			processRetrievalBuffer();
		}
	}
	// Set goal buffer.
	public void startRetrieval(Sentence s) {
		if (requiredRetrievalTime < 0) {
			if (retrievalBuffer != null) {
				throw new RuntimeException("State error. Attempting a retrieval before the previous retrieval: "+retrievalBuffer+" has been processed.");
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
	@Override
	public boolean step(WordEvent realizedWord) {
		if (!realizedWord.isClosed() && !currentContents.contains(realizedWord.getWord())) {
			return false;
		}
		double elapsedTime = this.lastOnset == -1 ? 0.01 : realizedWord.getOnset() - this.lastOnset;
		this.lastOnset = realizedWord.getOnset();
		currentContents.remove(realizedWord.getWord());
		dm.step(elapsedTime);
		requiredRetrievalTime -= elapsedTime;	
		processRetrievalBuffer();
		return true;
	}
	public void setDM(DeclarativeMemory dm) {
		this.dm = dm;
	}
	protected DeclarativeMemory getDM() {
		return dm;
	}
}
