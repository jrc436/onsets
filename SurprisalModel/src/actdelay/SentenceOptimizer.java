package actdelay;

import data.OnsetPairList;
import optimizers.ValleyClimber;

public class SentenceOptimizer extends ValleyClimber<ActDelay, SentenceProcessor, OnsetPairList, ActDelayList> {
	public SentenceOptimizer() {
		super(15, "sentopt", SentenceProcessor.class, OnsetPairList.class, ActDelayList.class);
	}
	public static void main(String[] args) {
		SentenceOptimizer so = new SentenceOptimizer();
		so.initializeFromCmdLine(args);
		so.run();
	}
}
