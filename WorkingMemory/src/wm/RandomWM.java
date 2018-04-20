package wm;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Iterator;

import sentence.WordEvent;

public class RandomWM extends AbstractWorkingMemory {
	private final Random r;
	public RandomWM(int windowSize, int k) {
		super(windowSize, k);
		r = new Random();
	}

	@Override
	public WordEvent getNextRetrieval(List<WordEvent> leftToRealize) {
		WordEvent toRetrieve = null; 
		List<WordEvent> randomSelect = new ArrayList<WordEvent>();
		Iterator<String> contents = this.getMemoryContents();
		Set<String> contains = new HashSet<String>();
		while (contents.hasNext()) {
			contains.add(contents.next());
		}
		for (WordEvent we : leftToRealize) {
			double act = dm.present(we.getWord(), true);
			if (contains.contains(toRetrieve.getWord())) {
				continue; // just parallelism
			}
			randomSelect.add(toRetrieve);
		}
		if (!randomSelect.isEmpty()) {
			toRetrieve = randomSelect.get(r.nextInt(randomSelect.size()));
		}
		return toRetrieve;
	}

}
