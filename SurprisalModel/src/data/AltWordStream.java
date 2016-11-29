package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AltWordStream implements IWordStream {
	private List<DelayEvent> delayWords;
	public AltWordStream(OnsetPairList os) {
		if (os.isEmpty()) {
			throw new IllegalArgumentException();
		}
		delayWords = new ArrayList<DelayEvent>();
		double lastWordEnd = 0.0;
		for (int i = 0; i < os.size(); i++) {
			OnsetPair first = os.get(i);
			//in general, we will for now just assume both affect the process the same way, but will code it so that it could be changed..
			double onsetDelay = first.getOnset() - lastWordEnd; //this is for computing the amount of time it took to retrieve
			double elapsedTime = first.getEnd() - lastWordEnd; //this is for computing activation decay
			if (first.isWord()) {
				 delayWords.add(new DelayWord(first.getWord(), onsetDelay, elapsedTime));
			}
			else {
				delayWords.add(new Disfluency(elapsedTime));
			}
			lastWordEnd = first.getEnd();
		}
	}
	@Override
	public Iterator<DelayEvent> getEvents() {
		return delayWords.iterator();
	}
}
