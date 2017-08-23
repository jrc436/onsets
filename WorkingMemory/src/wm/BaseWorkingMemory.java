package wm;

import java.util.ArrayList;
import java.util.Iterator;

import sentence.WordEvent;

public abstract class BaseWorkingMemory implements IWorkingMemory {
	protected final ArrayList<String> currentContents; // Can be thought of Goal Buffer in ACT-R
	private final int windowSize;
	protected final int k;
	
	public BaseWorkingMemory(int windowSize, int k) {
		currentContents = new ArrayList<String>();
		this.windowSize = windowSize;
		this.k = k;
	}
	protected int getWindowSize() {
		return windowSize;
	}
	@Override
	public int getSize() {
		return currentContents.size();
	}
	@Override
	public Iterator<String> getMemoryContents() {
		return currentContents.iterator();
	}
	public abstract boolean step(WordEvent realizedWord);
	public String toString() {
		String toReturn = "";
		for (String word : currentContents) {
			toReturn += word + ",";
		}
		if (currentContents.size() > 0) {
			toReturn = toReturn.substring(0, toReturn.length()-1);
		}
		return toReturn;
	}
}
