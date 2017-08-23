package wm;

import java.util.List;

import sentence.WordEvent;

public class LowActivationWM extends AbstractWorkingMemory {

	
	public LowActivationWM(int windowSize, int k) {
		super(windowSize, k);
	}

	@Override
	public WordEvent getNextRetrieval(List<WordEvent> leftToRealize) {
		double minAct = Double.MAX_VALUE;
		WordEvent toRetrieve = null;
		for (WordEvent we : leftToRealize) {
			double act = dm.present(we.getWord(), true);
			if (act < minAct) {
				minAct = act;
				toRetrieve = we;
			}
		}
		return toRetrieve;
	}
	@Override
	public boolean step(WordEvent add) {
		boolean ret = super.step(add);
		if (ret) {
			dm.present(add.getWord());
		}
		return ret;
	}

}
