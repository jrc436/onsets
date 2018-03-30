package wm;

import dm.DeclarativeMemory;
import sentence.Sentence;
import sentence.WordEvent;
import strategy.IStrategy;

public abstract class AbstractWorkingMemory extends BaseWorkingMemory implements IStrategy {

	protected DeclarativeMemory dm;
	private static final double smallDouble = 0.001;
	private double lastObsOnset = 0;
	private double lastExpOnset = 0;
	// Time required before processing this retrieval buffer.
	private double requiredRetrievalTime = -1;
	private double elapsedTime = 0;
	private WordEvent retrievalBuffer = null;
	
	private double remainingSpeakingTime = -1; // technically should have a buffer probably..
	
	
	public AbstractWorkingMemory(int windowSize, int k) {
		super(windowSize, k);
	}

	public void initialize(Sentence s) {
//		// Some amount of planning likely occurs before we start the transcript.
//		for (int i = 0; i < k; i++) {
//			step(s, 0.05);
//			elapsedTime = 0;
//		}
	}

	// Set goal buffer.
	protected void startRetrieval(Sentence s) {
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

	protected void processRetrievalBuffer() {
		if (retrievalBuffer != null && requiredRetrievalTime < 0) {
			if (!isFull()) {
				currentContents.add(retrievalBuffer.getWord());
			}
			retrievalBuffer = null;
		}
	}
	protected void elapseTime(double time) {
		requiredRetrievalTime -= time;
		remainingSpeakingTime -= time;
		elapsedTime += time;
		dm.step(time);
	}
	public double getTotalElapsedTime() {
		return elapsedTime;
	}
	public double getLastObsOnset() {
		return lastObsOnset;
	}
	// hack
	public void setLastOnsets(WordEvent we) {
		this.lastObsOnset = this.getElapsedTime();
		this.lastExpOnset = we.getOnset();
	}
	public double getLastExpOnset() {
		return lastExpOnset;
	}
	
	// the tracing model 
	public WordEvent step(Sentence sentence, double timeStep) {
		processRetrievalBuffer(); // lhs
		WordEvent event = sentence.getNextWord();
		WordEvent toReturn = getTotalElapsedTime() > event.getOnset() ? event : null; // hacky lhs, still needs to be updated
		startRetrieval(sentence); // rhs
		if (toReturn != null && !toReturn.isClosed()) {
			if (!currentContents.contains(toReturn.getWord())) {
				toReturn = null; // still waiting to retrieve 
				//throw new RuntimeException(toReturn + " is not in workingMemory: "+currentContents+" or a closed class word");
			}
			else {
				dm.present(toReturn.getWord());
				currentContents.remove(toReturn.getWord()); // rhs
			}
		}
		elapseTime(timeStep);
		return toReturn;
	}
	public WordEvent timeStep(Sentence sentence, double timeStep) {
		processRetrievalBuffer(); // lhs
		startRetrieval(sentence); // rhs
		// nextevent can't be null anymore...
		WordEvent nextEvent = sentence.getNextWord();
		if (remainingSpeakingTime <= 0 || !currentContents.contains(nextEvent)) {
			// a dud, we can't output anything
			nextEvent = null;
		}
		else {
			remainingSpeakingTime = nextEvent.getDuration();
			currentContents.remove(nextEvent.getWord());
			dm.present(nextEvent.getWord());
		}
		elapseTime(timeStep);
		return nextEvent;
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
		boolean firstWord = this.lastObsOnset == -1;
		// If it's the first word, elapse a small double for the math to work. Otherwise, elapse the time between the words. If no word is going to be spoken,
		// elapse the time until that retrieval is finished.
		double elapsedTime = smallDouble + (willSpeakWord ? firstWord ? 0.0 : nextWord.getOnset() - this.lastObsOnset : requiredRetrievalTime);
		if (willSpeakWord) {
			this.lastObsOnset = nextWord.getOnset();
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
	protected double getElapsedTime() {
		return this.elapsedTime;
	}

	protected DeclarativeMemory getDM() {
		return dm;
	}
}
