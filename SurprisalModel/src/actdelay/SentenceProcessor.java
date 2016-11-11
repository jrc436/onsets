package actdelay;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import actr.ProcessRunner;
import data.OnsetPairList;
import data.WordStream;
import ngrams.UnigramModel;
import nlp.pmi.DictReformatter;
import nlp.pmi.PMIDict;
import util.sys.LineProcessor;

public class SentenceProcessor extends LineProcessor<OnsetPairList, ActDelayList> {
	private final double negD;
	private final int k;
	private final UnigramModel u;
	private final PMIDict pmi;
	public SentenceProcessor() {
		this.negD = 0;
		this.k = 0;
		this.u = null;
		this.pmi = null;
	}
	public SentenceProcessor(String input, String output, String[] args) {
		super(input, output, new ActDelayList());
		this.negD = Double.parseDouble(args[0]);
		this.k = Integer.parseInt(args[1]);
		UnigramModel u = null;
		try {
			u = UnigramModel.readFile(Paths.get(args[2]));
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.u = u;
		this.pmi = DictReformatter.readOversizeDict(args[3]);
	}
	@Override
	public int getNumFixedArgs() {
		return 4;
	}

	@Override
	public boolean hasNArgs() {
		return false;
	}

	@Override
	public String getConstructionErrorMsg() {
		return "SentenceProcessor first needs the value of d (as a negative number), then k (the number of recent bll presentations that are saved), "
				+ "then the path to the unigram model for computing bll";
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
	public void map(OnsetPairList newData, ActDelayList threadAggregate) {
		ProcessRunner pr = new ProcessRunner(negD, k, u, pmi);
		WordStream sent = new WordStream(newData);
		threadAggregate.addAll(pr.realizeSentence(sent));
	}

}
