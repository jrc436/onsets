package wm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sentence.WordEvent;

public class HighActivationWM extends AbstractWorkingMemory {
	
	
	public HighActivationWM(int windowSize, int k) {
		super(windowSize, k);
	}

	@Override
	public WordEvent getNextRetrieval(List<WordEvent> leftToRealize) {
		double maxAct = 0;
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
			if (act > maxAct) {
				maxAct = act;
				toRetrieve = we;
			}
		}
		return toRetrieve;
	}
}

