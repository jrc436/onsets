package wm;

import dm.DeclarativeMemory;
import sentence.BufferProcess;
import sentence.Sentence;
import sentence.WordEvent;
import strategy.IStrategy;
import util.collections.OrderedPair;
import util.collections.Pair;

public abstract class AbstractWorkingMemory extends BaseWorkingMemory implements IStrategy {

	protected DeclarativeMemory dm;

	private double lastObsOnset = 0;
	private double lastExpOnset = 0;

	
	private double elapsedTime = 0;
	
	private final BufferProcess retrievalBuffer;
	private final BufferProcess outputBuffer;
	
	
	public AbstractWorkingMemory(int windowSize, int k) {
		super(windowSize, k);
		retrievalBuffer = new BufferProcess(this::finishedRetrievalCallback);
		outputBuffer = new BufferProcess(this::finishedSpeakingCallback);
	}

	public void initialize(Sentence s) {
//		// Some amount of planning likely occurs before we start the transcript.
//		for (int i = 0; i < k; i++) {
//			step(s, 0.05);
//			elapsedTime = 0;
//		}
	}
	// BufferCallback for Retrieval, external validations should take place first
	protected Pair<WordEvent, Double> finishedRetrievalCallback(Sentence s) {
		// Retrieval has completed, so we will add it to working memory
		if (!isFull() && retrievalBuffer.getWord() != null) {
			wmContents.add(retrievalBuffer.getWord().getWord());
		}
		
		// Begin the next retrieval, if any exist
		WordEvent next = this.getNextRetrieval(s.getRetrievableWords(getWindowSize()));
		if (next != null) {
			double rt = dm.directRetrieval(next.getWord());
			return new OrderedPair<WordEvent, Double>(next, rt);
		}
		return null;
	}
	// BufferCallback for Output
	protected Pair<WordEvent, Double> finishedSpeakingCallback(Sentence s) {
		WordEvent nextWord = s.getNextWord();
		// needs to be in memory to speak
		if (nextWord != null) {
			// if it's not closed, it NEEDS to be contained to be spoken! otherwise we have to wait to retrieve it
			if (wmContents.contains(nextWord.getWord())) {
				dm.present(nextWord.getWord()); // we'll handle the presentation as it's being spoken
				wmContents.remove(nextWord.getWord()); // done with it
			}
			// if it's closed it can be spoken without entering memory, and has no effect on memory. 
						// this represents a "proceduralized" word
			else if (!nextWord.isClosed()){
				return null;
			}
			s.speakWord(); // well, start speaking it
			double st = nextWord.getDuration();		
			return new OrderedPair<WordEvent, Double>(nextWord, st);
		}
		return null;
	}
	
	protected void elapseTime(double time) {
		retrievalBuffer.elapseTime(time);
		outputBuffer.elapseTime(time);
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
	// this MAY have bugs!
	public WordEvent wordStep(Sentence sentence, double timeStep) {
		retrievalBuffer.processIfFinished(sentence);
		WordEvent event = sentence.getNextWord();
		WordEvent toReturn = getTotalElapsedTime() > event.getOnset() ? event : null; // hacky lhs, still needs to be updated
		if (toReturn != null && !toReturn.isClosed()) {
			if (!wmContents.contains(toReturn.getWord())) {
				toReturn = null; // still waiting to retrieve 
				//throw new RuntimeException(toReturn + " is not in workingMemory: "+currentContents+" or a closed class word");
			}
			else {
				dm.present(toReturn.getWord());
				wmContents.remove(toReturn.getWord()); // rhs
			}
		}
		elapseTime(timeStep);
		return toReturn;
//		retrievalBuffer.processIfFinished(sentence);
//		WordEvent event = sentence.getNextWord();
//		WordEvent toReturn = getTotalElapsedTime() > event.getOnset() ? event : null; // hacky lhs, still needs to be updated
//		if (toReturn != null && !event.isClosed()) {
//			if (!wmContents.contains(event.getWord())) {
//				toReturn = null; // still waiting to retrieve 
//				//throw new RuntimeException(toReturn + " is not in workingMemory: "+currentContents+" or a closed class word");
//			}
//			else {
//				dm.present(toReturn.getWord());
//				wmContents.remove(toReturn.getWord()); // rhs
//			}
//		}
//		elapseTime(timeStep);
//		return toReturn;
	}
	public WordEvent timeStep(Sentence sentence, double timeStep) {
		retrievalBuffer.processIfFinished(sentence);
		boolean finished = outputBuffer.processIfFinished(sentence);
		elapseTime(timeStep);
		return finished ? outputBuffer.getWord() : null;
	}

	@Override
	public boolean step(WordEvent nextWord) {
		throw new UnsupportedOperationException("This is no longer supported as a base-level method");
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
