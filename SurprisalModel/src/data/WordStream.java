package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.sys.DataType;

public class WordStream implements IWordStream {
	private List<DelayEvent> delayWords;
	private final OnsetPairList dataType;
	public WordStream(OnsetPairList os) {
		if (os.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.dataType = os;
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
	@Override
	public Iterator<DelayEvent> getEvents() {
		return delayWords.iterator();
	}
	@Override
	public DataType deepCopy() {
		return new WordStream(dataType);
	}
	@Override
	public int getNumFixedArgs() {
		return dataType.getNumFixedArgs();
	}
	@Override
	public boolean hasNArgs() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getConstructionErrorMsg() {
		return dataType.getConstructionErrorMsg()+": the core of wordstream";
	}
	@Override
	public Iterator<String> getStringIter() {
		return dataType.getStringIter();
	}
}
