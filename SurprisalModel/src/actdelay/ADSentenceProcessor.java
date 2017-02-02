package actdelay;

import java.io.File;
import java.util.Map;

import actr.AbstractProcessRunner;
import actr.AbstractSentenceProcessor;
import actr.WordStreamRunner;
import data.OnsetPairList;
import data.WordStream;
import ngrams.UnigramModel;
import nlp.pmi.PMIDict;
import optimizers.VariableSet;

public class ADSentenceProcessor extends AbstractSentenceProcessor<WordStream, ActDelay, ActDelayList> {
	public ADSentenceProcessor() {
		super();
	}
	public ADSentenceProcessor(String input, String output, String[] args) {
		super(input, output, args);
	}
	public ADSentenceProcessor(String input, String output, ADSentenceProcessor other, VariableSet vs) {
		super(input, output, other, vs);
	}

	@Override
	public WordStream getNextData() {
		File f = super.getNextFile();
		if (f == null) {
			return null;
		}
		return new WordStream(OnsetPairList.readFile(f));
	}
	
	@Override
	protected ActDelayList constructAggregate(String[] inpArgs) {
		return new ActDelayList();
	}

	@Override
	protected AbstractProcessRunner<WordStream, ActDelay> buildRunner(double negD, int k, double wps, UnigramModel u, PMIDict pmi, int numPrev, Map<String, Double> durs) {
		return new WordStreamRunner(negD, k, wps, u, pmi, numPrev, durs);
	}

}
