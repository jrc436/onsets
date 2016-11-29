package actdelay;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import actr.ProcessRunner;
import data.AltWordStream;
import data.IWordStream;
import data.OnsetPairList;
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
	private final double pcs;
	public SentenceProcessor() {
		this.negD = 0;
		this.k = 0;
		this.u = null;
		this.pmi = null;
		this.pcs = 0.3;
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
		this.pcs = 0.3;
		this.u = u;
		this.pmi = DictReformatter.readOversizeDict(args[3]);
	}
	public SentenceProcessor(String input, String output, SentenceProcessor other, VariableSet vs) {
		super(input, output, new ActDelayList());
		this.pmi = other.pmi;
		this.u = other.u;
		this.negD = vs.getValue(VariableName.negd);
		this.k = (int) Math.round(vs.getValue(VariableName.recencyK));
		this.pcs = vs.getValue(VariableName.percent_speaking);
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
		ProcessRunner pr = new ProcessRunner(negD, k, this.pcs, u, pmi);
		IWordStream sent = new AltWordStream(newData);
		threadAggregate.addAll(pr.realizeSentence(sent));
	}

}
