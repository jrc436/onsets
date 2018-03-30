package wm;

import java.util.List;
import java.util.Random;

import sentence.WordEvent;

public class RandomWM extends AbstractWorkingMemory {
	private final Random r;
	public RandomWM(int windowSize, int k) {
		super(windowSize, k);
		r = new Random();
	}

	@Override
	public WordEvent getNextRetrieval(List<WordEvent> leftToRealize) {
		return leftToRealize.get(r.nextInt(leftToRealize.size()));
	}

}
