package timewindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import actr.AbstractProcessRunner;
import actr.DeclarativeMemory;
import ngrams.UnigramModel;
import nlp.pmi.PMIDict;

public class WindowProcessor extends AbstractProcessRunner<ConvoWindowList, ActWindow> {
	public WindowProcessor(double negD, int k, double wps, UnigramModel u, PMIDict pmi, int numPrev,
			Map<String, Double> wordDurs) {
		super(negD, k, wps, u, pmi, numPrev, wordDurs);
	}

	@Override
	public List<ActWindow> realizeSentenceCritical(ConvoWindowList sentence, DeclarativeMemory nGramPresentations, Map<String, Double> wordDurs) {
		List<ActWindow> retval = new ArrayList<ActWindow>();
		for (TimeWindow tw : sentence) {
			for (String word : tw.getWords()) {
				double act = nGramPresentations.present(word, totalTime());
				retval.add(new ActWindow(super.getPrevious(), act, word));
			}
			if (wordDurs != null) {
				super.addTime(tw.getNormalizedSpeakingRate(wordDurs));
			}
			else {
				super.addTime(tw.getDuration());
			}
			elapseTime(tw.getDuration());
			nGramPresentations.decayPresentations(tw.getDuration());
		}
		return retval;
	}
}
