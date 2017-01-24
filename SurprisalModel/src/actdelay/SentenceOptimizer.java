package actdelay;

import data.WordStream;
import optimizers.ValleyClimber;

public class SentenceOptimizer extends ValleyClimber<ActDelay, ADSentenceProcessor, WordStream, ActDelayList> {
	public SentenceOptimizer() {
		super(15, "sentopt", ADSentenceProcessor.class, WordStream.class, ActDelayList.class);
	}
	public static void main(String[] args) {
		SentenceOptimizer so = new SentenceOptimizer();
		so.initializeFromCmdLine(args);
		so.run();
	}
}
