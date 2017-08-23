package actr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import actdelay.ActDelay;
import actdelay.ActDelayDur;
import data.DelayEvent;
import data.DelayWord;
import data.WordStream;
import dm.DeclarativeMemory;
import ngrams.UnigramModel;
import nlp.pmi.PMIDict;

public class WordStreamRunner extends AbstractProcessRunner<WordStream, ActDelay> {
	public WordStreamRunner(double negD, int k, double wps, UnigramModel u, PMIDict pmi, int numPrev,
			Map<String, Double> wordDurs) {
		super(negD, k, wps, u, pmi, numPrev, wordDurs);
	}
	
	@Override
	public List<ActDelay> realizeSentenceCritical(WordStream sentence, DeclarativeMemory nGramPresentations) {
		List<ActDelay> actDelays = new ArrayList<ActDelay>();
		Iterator<DelayEvent> delays = sentence.getEvents();
		String prevword = "disfluency";
		while (delays.hasNext()) {
			DelayEvent delay = delays.next();
			if (delay instanceof DelayWord) {
				DelayWord dw = (DelayWord) delay;
				double act = nGramPresentations.present(dw.getWord());
				ActDelay ad = new ActDelay(act, dw.getDelay());
				double diff = dw.getDelay();
				double dur = super.getWordDuration(dw.getWord());
				if (dur == -77) {
					dur = diff; //zero that shit out;
				}
				super.addPrevious(diff - dur);
				prevword = dw.getWord();
				actDelays.add(new ActDelayDur(ad, super.getPrevious(), prevword));
			}
			else {
				//nGramPresentations.addDelayEventToWM();
				super.addPrevious(0.0);
				prevword = "disfluency";
			}
			elapseTime(delay.getElapsedTime());
			nGramPresentations.step(delay.getElapsedTime());
		}
		return actDelays;
	}
}
