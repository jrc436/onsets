package actdelay;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import actr.ProcessRunner;
import data.AltWordStream;
import data.IWordStream;
import data.OnsetPairList;
import data.WordStream;
import ngrams.UnigramModel;
import nlp.pmi.DictReformatter;
import nlp.pmi.PMIDict;
import optimizers.VariableName;
import optimizers.VariableSet;
import util.sys.LineProcessor;

public class SentenceProcessor extends LineProcessor<OnsetPairList, ActDelayList> {
	private final double negD;
	private final int k;
	private final UnigramModel u;
	private final PMIDict pmi;
	private final double wps;
	private final static double wps_const = 5.0;
	private final static double negd_const = -0.35;
	private final static int kconst = 5;
	private final boolean alternate;
	public SentenceProcessor() {
		this.negD = 0;
		this.k = 0;
		this.u = null;
		this.pmi = null;
		this.wps = wps_const;
		this.alternate = false;
	}
	public SentenceProcessor(String input, String output, String[] args) {
		super(input, output, new ActDelayList());
		this.negD = negd_const;
		this.k = kconst;
		UnigramModel u = null;
		alternate = Boolean.parseBoolean(args[0]);
		try {
			u = UnigramModel.readFile(Paths.get(args[1]));
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.wps = wps_const;
		this.u = u;
		this.pmi = DictReformatter.readOversizeDict(args[2]);
	}
	public SentenceProcessor(String input, String output, SentenceProcessor other, VariableSet vs) {
		super(input, output, new ActDelayList());
		this.pmi = other.pmi;
		this.u = other.u;
		this.alternate = other.alternate;
		this.negD = vs.getValue(VariableName.negd);
		this.k = (int) Math.round(vs.getValue(VariableName.recencyK));
		this.wps = vs.getValue(VariableName.words_per_second);
	}
	@Override
	public int getNumFixedArgs() {
		return 3;
	}

	@Override
	public boolean hasNArgs() {
		return false;
	}

	@Override
	public String getConstructionErrorMsg() {
		return "SentenceProcessor first needs a boolean value representing whether to use delays or intervals, "
				+ "then the path to the unigram model for computing bll, and finally, the PMI Path";
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
		ProcessRunner pr = new ProcessRunner(negD, k, this.wps, u, pmi);
		IWordStream sent = alternate ? new AltWordStream(newData) : new WordStream(newData);
		threadAggregate.addAll(pr.realizeSentence(sent));
	}

}
