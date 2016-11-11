package actdelay;

import data.OnsetPairList;
import util.sys.Executor;

public class SentenceExecutor extends Executor<SentenceProcessor, OnsetPairList, ActDelayList> {

	public SentenceExecutor() {
		super("sentences", 4, SentenceProcessor.class, OnsetPairList.class, ActDelayList.class);
	}
	public static void main(String[] args) {
		SentenceExecutor se = new SentenceExecutor();
		se.initializeFromCmdLine(args);
		se.run();
	}
}
