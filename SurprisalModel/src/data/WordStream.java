package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WordStream {
	private List<DelayEvent> delayWords;
	public WordStream(OnsetPairList os) {
		if (os.isEmpty()) {
			throw new IllegalArgumentException();
		}
		delayWords = new ArrayList<DelayEvent>();
		double lastWordStart = 0.0;
		double lastWordEnd = 0.0;
		for (int i = 0; i < os.size(); i++) {
			OnsetPair first = os.get(i);
			//for elapsed Time, we care about non-words... they're elapsing time all the same
			//for onset delay, we also care about non-words, but for a different reason: they could be potentially a disfluency that increases onsetDelay
			//either way, we don't want to add a delayword for a non-word
			
			//in general, we will for now just assume both affect the process the same way, but will code it so that it could be changed..
			double onsetDelay = first.getOnset() - lastWordStart; //this is for computing the amount of time it took to retrieve
			double elapsedTime = first.getEnd() - lastWordEnd; //this is for computing activation decay
			if (first.isWord()) {
				 delayWords.add(new DelayWord(first.getWord(), onsetDelay, elapsedTime));
				 lastWordStart = first.getOnset(); 
			}
			else {
				delayWords.add(new Disfluency(elapsedTime));
			}
			lastWordEnd = first.getEnd();
		}
	}
	public Iterator<DelayEvent> getEvents() {
		return delayWords.iterator();
	}
}
