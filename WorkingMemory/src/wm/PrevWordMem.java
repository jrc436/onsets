package wm;

import input.WordEvent;

public class PrevWordMem extends BaseWorkingMemory {

	
	public PrevWordMem(int windowSize, int k) {
		super(windowSize, k);
	}

	private void addWord(String word) {
		if (wmContents.size() == k) {
			wmContents.remove(0);
		}
		wmContents.add(word);
	}

	//@Override
	public boolean step(WordEvent realizedWord) {
		this.addWord(realizedWord.getWord());
		return true;
	}
	public String toString() {
		String toReturn = "";
		if (wmContents.isEmpty()) {
			return toReturn;
		}
		for (String word : wmContents) {
			toReturn += word + ",";
		}
		return toReturn.substring(0, toReturn.length()-1);
	}

}
