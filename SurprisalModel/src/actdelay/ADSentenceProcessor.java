package actdelay;

import java.io.File;

import actr.AbstractProcessRunner;
import actr.AbstractSentenceProcessor;
import data.IWordStream;
import data.OnsetPairList;
import data.WordStream;
import ngrams.UnigramModel;
import nlp.pmi.PMIDict;
import optimizers.VariableSet;

public class ADSentenceProcessor extends AbstractSentenceProcessor<WordStream, ActDelay, ActDelayList> {

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
	

//	@Override
//	public void map(OnsetPairList newData, ActDelayList threadAggregate) {
//		ProcessRunner pr = new ProcessRunner(negD, k, this.wps, u, pmi, 0);
//		IWordStream sent = new WordStream(newData);
//		threadAggregate.addAll(pr.realizeSentence(sent));
//	}

	@Override
	protected ActDelayList constructAggregate() {
		return new ActDelayList();
	}

	@Override
	protected AbstractProcessRunner<WordStream, ActDelay> buildRunner(double negD, int k, double wps, UnigramModel u, PMIDict pmi) {
		// TODO Auto-generated method stub
		return null;
	}

}
