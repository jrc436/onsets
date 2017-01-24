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
import ngrams.UnigramModel;
import nlp.pmi.PMIDict;

public class WordStreamRunner extends AbstractProcessRunner<WordStream, ActDelayDur> {
	public WordStreamRunner(double negD, int k, double wps, UnigramModel u, PMIDict pmi, int numPrev,
			Map<String, Double> wordDurs) {
		super(negD, k, wps, u, pmi, numPrev, wordDurs);
	}
//	public List<ActDelay> realizeSentence(IWordStream sentence) {
//		List<ActDelay> actDelays = new ArrayList<ActDelay>();
//		DeclarativeMemory nGramPresentations = new DeclarativeMemory(k, negD, base, pmi);
//		// first need to parse the sentence into n-grams
//		Iterator<DelayEvent> delays = sentence.getEvents();
//		while (delays.hasNext()) {
//			DelayEvent delay = delays.next();
//			if (delay instanceof DelayWord) {
//				DelayWord dw = (DelayWord) delay;
//				double act = nGramPresentations.present(dw.getWord(), totalTime());
//				actDelays.add(new ActDelay(act, dw.getDelay()));
//			}
//			else {
//				nGramPresentations.addDelayEventToWM();
//			}
//			elapseTime(delay.getElapsedTime());
//			nGramPresentations.decayPresentations(delay.getElapsedTime());
//		}
//		return actDelays;
//	}
//	public List<ActWindow> realizeSentence(ConvoWindowList sentence) {
//		return realizeSentence(sentence, null);
//	}

	@Override
	public List<ActDelayDur> realizeSentenceCritical(WordStream sentence, DeclarativeMemory nGramPresentations, Map<String, Double> wordDurs) {
		List<ActDelayDur> actDelays = new ArrayList<ActDelayDur>();
		Iterator<DelayEvent> delays = sentence.getEvents();
		String prevword = "disfluency";
		while (delays.hasNext()) {
			DelayEvent delay = delays.next();
			if (delay instanceof DelayWord) {
				DelayWord dw = (DelayWord) delay;
				double act = nGramPresentations.present(dw.getWord(), totalTime());
				ActDelay ad = new ActDelay(act, dw.getDelay());
				if (!wordDurs.containsKey(dw.getWord())) {
					System.err.println("Incomplete Word Duration Map");
					System.err.println(dw.getWord());
					throw new RuntimeException();
				}
				super.addTime(dw.getDelay() - wordDurs.get(dw.getWord()));
				prevword = dw.getWord();
				actDelays.add(new ActDelayDur(ad, super.getPrevious(), prevword));
			}
			else {
				nGramPresentations.addDelayEventToWM();
				super.addTime(0.0);
				prevword = "disfluency";
			}
			elapseTime(delay.getElapsedTime());
			nGramPresentations.decayPresentations(delay.getElapsedTime());
		}
		return actDelays;
	}
}
