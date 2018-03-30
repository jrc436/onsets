package wm;

import sentence.WordEvent;

public class PrevWordMem extends BaseWorkingMemory {

	
	public PrevWordMem(int windowSize, int k) {
		super(windowSize, k);
	}

	private void addWord(String word) {
		if (currentContents.size() == k) {
			currentContents.remove(0);
		}
		currentContents.add(word);
	}

	//@Override
	public boolean step(WordEvent realizedWord) {
		this.addWord(realizedWord.getWord());
		return true;
	}
	public String toString() {
		String toReturn = "";
		if (currentContents.isEmpty()) {
			return toReturn;
		}
		for (String word : currentContents) {
			toReturn += word + ",";
		}
		return toReturn.substring(0, toReturn.length()-1);
	}

}
