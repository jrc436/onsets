package actdelay;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import actr.ProcessRunner;
import data.IWordStream;
import data.OnsetPairList;
import data.WordStream;
import ngrams.UnigramModel;
import nlp.pmi.DictReformatter;
import nlp.pmi.PMIDict;
import optimizers.VariableName;
import optimizers.VariableSet;
import util.sys.LineProcessor;

public class SentenceProcessorDur extends LineProcessor<OnsetPairList, ActDelayDurList> {
	private final double negD;
	private final int k;
	private final UnigramModel u;
	private final PMIDict pmi;
	private final Map<String, Double> wordDurs;
	private final double wps;
	private final static double wps_const = 5.0;
	private final static double negd_const = -0.35;
	private final static int kconst = 5;
	public SentenceProcessorDur() {
		this.negD = 0;
		this.k = 0;
		this.u = null;
		this.pmi = null;
		this.wps = wps_const;
		this.wordDurs = null;
	}
	public SentenceProcessorDur(String input, String output, String[] args) {
		super(input, output, new ActDelayDurList());
		this.negD = negd_const;
		this.k = kconst;
		UnigramModel u = null;
		Map<String, Double> w = new HashMap<String, Double>();
		try {
			u = UnigramModel.readFile(Paths.get(args[1]));
			List<String> lines = Files.readAllLines(Paths.get(args[0]));
			for (int i = 0; i < lines.size(); i++) {
				if (i == 0) {
					continue; //skip the poop header
				}
				String[] p = lines.get(i).split(",");
				w.put(p[0], Double.parseDouble(p[1]));
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.wps = wps_const;
		this.u = u;
		this.wordDurs = w;
		this.pmi = DictReformatter.readOversizeDict(args[2]);
	}
	public SentenceProcessorDur(String input, String output, SentenceProcessorDur other, VariableSet vs) {
		super(input, output, new ActDelayDurList());
		this.pmi = other.pmi;
		this.u = other.u;
		this.wordDurs = other.wordDurs;
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
		return "SentenceProcessorDur first needs a path to the word durations file, "
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
	public void map(OnsetPairList newData, ActDelayDurList threadAggregate) {
		ProcessRunner pr = new ProcessRunner(negD, k, this.wps, u, pmi);
		IWordStream sent = new WordStream(newData);
		threadAggregate.addAll(pr.realizeSentence(sent, wordDurs));
	}

}
