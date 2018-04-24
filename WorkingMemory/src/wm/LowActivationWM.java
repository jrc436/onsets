package wm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sentence.WordEvent;

public class LowActivationWM extends AbstractWorkingMemory {
	
	
	public LowActivationWM(int windowSize, int k) {
		super(windowSize, k);
	}

	@Override
	public WordEvent getNextRetrieval(List<WordEvent> leftToRealize) {
		double minAct = Double.MAX_VALUE;
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
			if (act < minAct) {
				minAct = act;
				toRetrieve = we;
			}
		}
		return toRetrieve;
	}
}
