package actdelay;

import data.OnsetPairList;
import util.sys.Executor;

public class SentenceExecutorDur extends Executor<SentenceProcessorDur, OnsetPairList, ActDelayDurList> {

	public SentenceExecutorDur() {
		super("sentences", 4, SentenceProcessorDur.class, OnsetPairList.class, ActDelayDurList.class);
	}
	public static void main(String[] args) {
		SentenceExecutorDur se = new SentenceExecutorDur();
		se.initializeFromCmdLine(args);
		se.run();
	}
}
