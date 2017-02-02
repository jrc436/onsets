package actr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import ngrams.UnigramModel;
import nlp.pmi.PMIDict;

public abstract class AbstractProcessRunner<F, K> {
	private final int numPrev;
	private double convoElapsedTime = 0.0;
	private final Queue<Double> previousTimes;

	public double totalTime() { 
		return convoElapsedTime + base.getPriorExposure();
	}
	//the strings are the ngrams, the doubles are the most recent k presentation timestamps.
	//the most recent timestamp is always stored at 0, the least recent at k
	private final BaseWordActivationComputer base;
	private final double negD;
	private final int k;
	private final PMIDict pmi;
	private final Map<String, Double> wordDurs;
	
	public AbstractProcessRunner(double negD, int k, double wps, UnigramModel u, PMIDict pmi, int numPrev, Map<String, Double> wordDurs) {
		this.base = new BaseWordActivationComputer(k, wps, u);
		this.negD = negD;
		this.k = k;
		this.pmi = pmi;
		this.numPrev = numPrev;
		this.wordDurs = wordDurs;
		this.previousTimes = new LinkedList<Double>();
	}
	public List<K> realizeSentence(F sentence) {
		DeclarativeMemory dm = new DeclarativeMemory(k, negD, base, pmi);
		initializePreviousTimes();
		return realizeSentenceCritical(sentence, dm);
	}
	protected abstract List<K> realizeSentenceCritical(F sentence, DeclarativeMemory dm);
	protected void elapseTime(double time) {
		convoElapsedTime += time;
	}
	protected void initializePreviousTimes() {
		for (int i = 0; i < numPrev; i++) {
			previousTimes.offer(convoElapsedTime);
		}
	}
	public double getWordDuration(String s) {
		if (wordDurs == null) {
			return 0.0;
		}
		else if (!wordDurs.containsKey(s)) {
			System.err.println("Incomplete word duration dictionary, lacking: "+s);
			return -77.0;
		}
		return wordDurs.get(s);
	}
	protected void addPrevious(double time) {
		previousTimes.offer(time);
		previousTimes.poll();
	}
	protected Queue<Double> getPrevious() {
		return previousTimes;
	}
	protected Map<String, Double> getSubMap(List<String> words) {
		if (wordDurs == null) {
			return null;
		}
		Map<String, Double> map = new HashMap<String, Double>();
		for (String word : words) {
			map.put(word, getWordDuration(word));
		}
		return map;
	}
}
