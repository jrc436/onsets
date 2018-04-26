package wm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dm.SuccessfulRetrievalModel;
import input.WordEvent;

public class TimingWM extends AbstractWorkingMemory {
	private final SuccessfulRetrievalModel m;
	public TimingWM(int windowSize, int k) {
		super(windowSize, k);
		this.m = new SuccessfulRetrievalModel();
	}

	@Override
	public WordEvent getNextRetrieval(List<WordEvent> leftToRealize) {
		// we want to retrieve the word such that we minimize the time between 
		// (now + retrieval time) - (word's onset time)
		double minWastedTime = Double.MAX_VALUE;
		WordEvent toRetrieve = null;
		Iterator<String> contents = this.getMemoryContents();
		Set<String> contains = new HashSet<String>();
		while (contents.hasNext()) {
			contains.add(contents.next());
		}
		for (WordEvent we : leftToRealize) {
			double act = dm.present(we.getWord(), true);
			if (contains.contains(we.getWord())) {
				continue;
			}
			double expectedRetrievalTime = m.getRetrievalTime(act);
			// how to figure out now time!
			double wastedTime = we.getOnset() - (this.getElapsedTime() + expectedRetrievalTime);
			if (wastedTime < minWastedTime) {
				toRetrieve = we;
				minWastedTime = wastedTime;
			}
			
		}
		return toRetrieve;
	}

}
