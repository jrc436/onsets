package timewindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import actr.AbstractProcessRunner;
import dm.DeclarativeMemory;
import ngrams.UnigramModel;
import nlp.pmi.PMIDict;

public class WindowProcessRunner extends AbstractProcessRunner<TimeWindowList, ActWindow> {
	public WindowProcessRunner(double negD, int k, double wps, UnigramModel u, PMIDict pmi, int numPrev, Map<String, Double> wordDurs) {
		super(negD, k, wps, u, pmi, numPrev, wordDurs);
	}

	@Override
	public List<ActWindow> realizeSentenceCritical(TimeWindowList sentences, DeclarativeMemory nGramPresentations) {
		List<ActWindow> retval = new ArrayList<ActWindow>();
		for (ConvoWindowList sentence : sentences) {
			for (TimeWindow tw : sentence) {
				Map<String, Double> twmap = super.getSubMap(tw.getWords());
				for (String word : tw.getWords()) {
					double act = nGramPresentations.present(word);
					retval.add(new ActWindow(super.getPrevious(), act, word));
				}
				super.addPrevious(tw.getNormalizedSpeakingRate(twmap));
				elapseTime(tw.getDuration());
				nGramPresentations.step(tw.getDuration());
			}
		}
		return retval;
	}
}
