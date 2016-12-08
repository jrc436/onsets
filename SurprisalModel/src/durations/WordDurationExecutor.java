package durations;

import data.OnsetPairList;
import util.sys.Executor;

public class WordDurationExecutor extends Executor<WordDurationComputer, OnsetPairList, WordDuration>{

	public WordDurationExecutor() {
		super("worddurations", 12, WordDurationComputer.class, OnsetPairList.class, WordDuration.class);
	}
	public static void main(String[] args) {
		WordDurationExecutor wde = new WordDurationExecutor();
		wde.initializeFromCmdLine(args);
		wde.run();
	}

}
