package actr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ngrams.UnigramModel;
import nlp.pmi.DictReformatter;
import nlp.pmi.PMIDict;
import optimizers.VariableName;
import optimizers.VariableSet;
import util.generic.data.GenericList;
import util.sys.DataType;
import util.sys.LineProcessor;

public abstract class AbstractSentenceProcessor<F extends DataType, K, E extends GenericList<K>> extends LineProcessor<F, E> {
	private final double negD;
	private final int k;
	private final UnigramModel u;
	private final PMIDict pmi;
	private final double wps;
	private final static double wps_const = 5.0;
	private final static double negd_const = -0.35;
	private final static int kconst = 5;
	public AbstractSentenceProcessor() {
		this.negD = 0;
		this.k = 0;
		this.u = null;
		this.pmi = null;
		this.wps = wps_const;
	}
	public AbstractSentenceProcessor(String input, String output, String[] args) {
		super(input, output);
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
		this.pmi = DictReformatter.readOversizeDict(args[2]);
		super.setInitialValue(constructAggregate());
	}
	public AbstractSentenceProcessor(String input, String output, AbstractSentenceProcessor<F, K, E> other, VariableSet vs) {
		super(input, output);
		this.pmi = other.pmi;
		this.u = other.u;
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
		return "SentenceProcessor first needs the path to the unigram model for computing bll, secondly the word durations file, and finally, the PMI Path";
	}
	protected abstract E constructAggregate();
	protected abstract AbstractProcessRunner<F, K> buildRunner(double negD, int k, double wps, UnigramModel u, PMIDict pmi);
	@Override
	public abstract F getNextData();

	@Override
	public void map(F newData, E threadAggregate) {
		AbstractProcessRunner<F, K> runner = buildRunner(negD, k, wps, u, pmi);
		threadAggregate.addAll(runner.realizeSentence(newData));
	}

}
