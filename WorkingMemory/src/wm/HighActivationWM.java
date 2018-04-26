package wm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import input.WordEvent;

public class HighActivationWM extends AbstractWorkingMemory {
	
	
	public HighActivationWM(int windowSize, int k) {
		super(windowSize, k);
	}

	@Override
	public WordEvent getNextRetrieval(List<WordEvent> leftToRealize) {
		Iterator<String> contents = this.getMemoryContents();
		Set<String> contains = new HashSet<String>();
		while (contents.hasNext()) {
			contains.add(contents.next());
		}
		List<WordEvent> sortedByActivation = dm.getMostActivated(leftToRealize);
		int i = sortedByActivation.size() - 1;
		// most activated are at the start
		while (contains.contains(sortedByActivation.get(i))) {
			i--;
		}
		return sortedByActivation.get(i);
	}
}

