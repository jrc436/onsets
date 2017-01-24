package actdelay;

import data.WordStream;
import util.sys.Executor;

public class SentenceExecutor extends Executor<ADSentenceProcessor, WordStream, ActDelayList> {

	public SentenceExecutor() {
		super("sentences", 4, ADSentenceProcessor.class, WordStream.class, ActDelayList.class);
	}
	public static void main(String[] args) {
		SentenceExecutor se = new SentenceExecutor();
		se.initializeFromCmdLine(args);
		se.run();
	}
}
