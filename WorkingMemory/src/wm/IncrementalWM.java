package wm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sentence.WordEvent;

public class IncrementalWM extends AbstractWorkingMemory {

	public IncrementalWM(int windowSize, int k) {
		super(windowSize, k);
	}

	@Override
	public WordEvent getNextRetrieval(List<WordEvent> choices) {
		Iterator<String> contents = this.getMemoryContents();
		Set<String> memSyms = new HashSet<String>();
		while (contents.hasNext()) {
			memSyms.add(contents.next());
		}
		for (WordEvent s : choices) {
			dm.present(s.getWord(), true);
			if (!memSyms.contains(s.getWord())) {
				return s; //just choose the first one we haven't already retrieved
			}
		}
		//have already retrieved everything.. presumably at the end.. that's fine
		return null;
	}

}
