package wm;

import java.util.Iterator;
import java.util.List;

import sentence.WordEvent;

public class IncrementalWM extends AbstractWorkingMemory {

	public IncrementalWM(int windowSize, int k) {
		super(windowSize, k);
	}

	@Override
	public WordEvent getNextRetrieval(List<WordEvent> choices) {
		Iterator<String> contents = this.getMemoryContents();
		for (WordEvent s : choices) {
			dm.present(s.getWord(), true);
			//just choose the first one we haven't already retrieved
			if (!contents.hasNext() || !contents.next().equals(s.getWord())) {
				return s;
			}
		}
		//have already retrieved everything.. presumably at the end.. that's fine
		return null;
	}

}
