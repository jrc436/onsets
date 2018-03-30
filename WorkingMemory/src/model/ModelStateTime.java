package model;

import optimizers.DoublePair;

public class ModelStateTime {
	private final String memContent;
	private final String realizationWord;
	// onset onset is the difference between the two onsets...
	// the reason we care about this is so that we don't "double penalize" models once they get off a bit
	private final double obsOnsetOnset;
	private final double expectedOnsetOnset;
	private static final String delim = "$-$";
	public ModelStateTime(String memString, String realizationWord, DoublePair obs, DoublePair expected) {
		this.memContent = memString;
		this.realizationWord = realizationWord;
		this.obsOnsetOnset = obs.getDifference();
		this.expectedOnsetOnset = expected.getDifference();
	}
	public String toString() {
		return realizationWord + delim + obsOnsetOnset + delim + memContent + delim + expectedOnsetOnset;
	}
}
