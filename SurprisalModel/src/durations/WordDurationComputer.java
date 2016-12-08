package durations;

import java.io.File;
import java.util.ArrayList;

import data.OnsetPair;
import data.OnsetPairList;
import util.sys.FileProcessor;

public class WordDurationComputer extends FileProcessor<OnsetPairList, WordDuration> {
	public WordDurationComputer() {
		super();
	}
	public WordDurationComputer(String inpDir, String outDir) {
		super(inpDir, outDir, new WordDuration());
	}
	@Override
	public int getNumFixedArgs() {
		return 0;
	}

	@Override
	public boolean hasNArgs() {
		return false;
	}

	@Override
	public String getConstructionErrorMsg() {
		return "WordDurationComputer needs no arguments";
	}

	@Override
	public OnsetPairList getNextData() {
		File f = super.getNextFile();
		if (f == null) {
			return null;
		}
		return OnsetPairList.readFile(f);
	}

	@Override
	public void map(OnsetPairList newData, WordDuration threadAggregate) {
		for (OnsetPair op : newData) {
			if (!threadAggregate.containsKey(op.getWord())) {
				threadAggregate.put(op.getWord(), new ArrayList<Double>());
			}
			threadAggregate.get(op.getWord()).add(op.getEnd() - op.getOnset());
		}
	}
	@Override
	public void reduce(WordDuration threadAggregate) {
		synchronized(processAggregate) {
			for (String word : threadAggregate.keySet()) {
				if (processAggregate.containsKey(word)) {
					processAggregate.get(word).addAll(threadAggregate.get(word));
				}
				else {
					processAggregate.put(word, threadAggregate.get(word));
				}
			}
		}
	}

}
