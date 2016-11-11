package actr;

import ngrams.UnigramModel;

public class BaseWordActivationComputer {
	private static final double expYears = 15;
	private static final double pcs = 0.3; //percent speaking
	
	private static final double speaking_rate = 196.0; //cite swbd speaking rate paper
	private static final double words_per_ngram = 1.0; //pulled from thin air, but I believe this should always be 1.0, see speaking_rate_eff
	private static final double year_seconds = 31557600; //365.25 * 24 * 3600	
	//the first division is to convert it into words / second. The second division is to convert it into ngrams / second. 
		//Realistically, as we're really using this to compute the TIME that's passing, NOT the ngrams, words_per_ngram shoudl always be 1.0
	
	private static final double speaking_rate_eff = (speaking_rate / 60.0) / words_per_ngram;
	
	
	private final int k;
	private final double priorExposureTime; 
	private final UnigramModel model;
	
	public BaseWordActivationComputer(int k, UnigramModel m) {
		this.priorExposureTime = year_seconds * expYears;
		this.k = k;
		this.model = m;
	}
	public double getPriorExposure() {
		return priorExposureTime;
	}
			
	//exposure_seconds / N is one "interval". assume the first occurrence of that ngram (corresponding to t_n) is t_0 + "interval"
	//assume the last occurrences of that ngram, are t_current - i * interval
	public Chunk initChunk(String chunk, double totalTime) {
		int numPresentations = calculateNumPresentations(model.get(chunk));
		Chunk c = new Chunk(k, numPresentations, calculateInitTime(numPresentations, totalTime));		
		//populate k most recent exposures
		addBaseRecency(c, totalTime, numPresentations);
		return c;
	}
	
	private int calculateNumPresentations(double ngramPrior) {
		//subtracting k because those will be added when we add them as baserecency
		int numPresentations = (int) Math.round(priorExposureTime * ngramPrior * speaking_rate_eff * pcs);
		return Math.max(1, Math.max(k, numPresentations)); //need at least depth presentations for it to really make sense...
															   //need at least one presentation for the math to work at all!
	}
	
	private double calculateInitTime(int numPresentations, double totalTime) {
		double N = (double) numPresentations;
		double interval = priorExposureTime / (N+2); //see addbaserecency
		return totalTime - interval; //one interval "after" totaltime.
	}
	private void addBaseRecency(Chunk c, double totalTime, int numPresentations) {
		double N = (double) numPresentations;
		double interval = priorExposureTime / (N+2); //add two, because we're starting at one interval from t0 and ending one interval before the convo
		for (int i = 0; i < k; i++) {
			double j = (double) (i+1);
			c.addPresentation((totalTime - priorExposureTime) + j * interval, false); //these presentations were already added to the numPresentations, so don't need to be added again
		}
	}
	
}
