package wm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import input.WordEvent;

public class TimingWM extends AbstractWorkingMemory {
	public TimingWM(int windowSize, int k) {
		super(windowSize, k);
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
			if (contains.contains(we.getWord())) {
				continue;
			}
			double expectedRetrievalTime = dm.directRetrieval(we.getWord(), true);
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
