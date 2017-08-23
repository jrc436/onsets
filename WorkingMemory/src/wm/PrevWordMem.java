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

	@Override
	public boolean step(WordEvent realizedWord) {
		this.addWord(realizedWord.getWord());
		return true;
	}
	public String toString() {
//		String toReturn = "";
//		for (String word : contents) {
//			toReturn += word + ",";
//		}
//		return toReturn.substring(0, toReturn.length()-1);
		String toReturn = "";
		if (currentContents.size() > 0) {
			toReturn = this.currentContents.get(currentContents.size()-1);
		}
		return toReturn;
	}

}
