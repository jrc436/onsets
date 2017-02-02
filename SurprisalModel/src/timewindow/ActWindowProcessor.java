package timewindow;

import java.io.File;
import java.util.Map;

import actr.AbstractProcessRunner;
import actr.AbstractSentenceProcessor;
import ngrams.UnigramModel;
import nlp.pmi.PMIDict;

public class ActWindowProcessor extends AbstractSentenceProcessor<TimeWindowList, ActWindow, ActWindowList> {

	public ActWindowProcessor() {
		super();
	}
	public ActWindowProcessor(String input, String output, String[] args, String[] timedur) {
		super(input, output, args, timedur);
	}
	
	@Override
	public TimeWindowList getNextData() {
		File f = super.getNextFile();
		if (f == null) {
			return null;
		}
		return TimeWindowList.fromFile(f);
	}

	@Override
	protected ActWindowList constructAggregate(String[] inpArgs) {
		return new ActWindowList();
	}

	@Override
	protected AbstractProcessRunner<TimeWindowList, ActWindow> buildRunner(double negD, int k, double wps, UnigramModel u, PMIDict pmi, int numPrev, Map<String, Double> durs) {
		return new WindowProcessRunner(negD, k, wps, u, pmi, numPrev, durs);
	}
}
