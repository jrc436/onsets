package actr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import actdelay.ActDelay;
import data.DelayEvent;
import data.DelayWord;
import data.IWordStream;
import ngrams.UnigramModel;
import nlp.pmi.PMIDict;

public class ProcessRunner {
	
	private double convoElapsedTime = 0.0;

	public double totalTime() { 
		return convoElapsedTime + base.getPriorExposure();
	}
	//the strings are the ngrams, the doubles are the most recent k presentation timestamps.
	//the most recent timestamp is always stored at 0, the least recent at k
	private final BaseWordActivationComputer base;
	private final double negD;
	private final int k;
	private final PMIDict pmi;
	
	public ProcessRunner(double negD, int k, double pcs, UnigramModel u, PMIDict pmi) {
		this.base = new BaseWordActivationComputer(k, pcs, u);
		this.negD = negD;
		this.k = k;
		this.pmi = pmi;
	}
	public List<ActDelay> realizeSentence(IWordStream sentence) {
		List<ActDelay> actDelays = new ArrayList<ActDelay>();
		DeclarativeMemory nGramPresentations = new DeclarativeMemory(k, negD, base, pmi);
		// first need to parse the sentence into n-grams
		Iterator<DelayEvent> delays = sentence.getEvents();
		while (delays.hasNext()) {
			DelayEvent delay = delays.next();
			if (delay instanceof DelayWord) {
				DelayWord dw = (DelayWord) delay;
				double act = nGramPresentations.present(dw.getWord(), totalTime());
				actDelays.add(new ActDelay(act, dw.getDelay()));
			}
			else {
				nGramPresentations.addDelayEventToWM();
			}
			convoElapsedTime += delay.getElapsedTime();
			nGramPresentations.decayPresentations(delay.getElapsedTime());
		}
		return actDelays;
	}
}
