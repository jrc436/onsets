package actr;

import java.util.HashMap;

import nlp.pmi.PMIDict;

public class DeclarativeMemory extends HashMap<String, Chunk> {		
	private final int depth;
	private final double negD;
	
	private final BaseWordActivationComputer bwac;
	private final SpreadingActivationComputer sac;
	public DeclarativeMemory(int k, double negD, BaseWordActivationComputer bwac, PMIDict pmi) {
		this.depth = k;	
		this.negD = negD;
		this.bwac = bwac;
		this.sac = new SpreadingActivationComputer(pmi);
	}
	
	//the ngramPrior and totalTime are just to init the chunk if necessary
	//t_n = total_lifetime
	//t_k = lifetime of 1 to k
	//n = total presentations
	//k = depth
	//sum of times from i = 1 to k (instead of n) + (n - k)(t_n^(1-d) - t_k^(1-d)) / [ (1-d)(t_n - t_k) ]
	public double getBaseActivation(String sf) {
		if (!this.containsKey(sf)) {
			throw new IllegalArgumentException("No chunk found for: "+sf);
		}
		Chunk c = super.get(sf);
		double t_n = c.t_n();
		double t_k = c.t_k();
		double n = c.getTotalPresentations();
		double k = (double) this.depth;
		double dc = 1.0 + negD;
		double kact = c.calculateKActivation(negD);
		if (kact < 0 || n < k || t_n < t_k || t_k < 0 || t_n < 0) {
			System.out.println("hi");
		}
		double nact = ( (n-k) * (Math.pow(t_n, dc) - Math.pow(t_k, dc)) ) / ( dc * (t_n - t_k) );
		if (nact < 1.0) {
			System.out.println("hmm...");
		}
//		if (check <= 0.0 || (check + 1.0) <= .0000001) {
//			System.err.println("Houston we have a problem.");
//		}
		return Math.log(nact + kact);
	}
	public double getSpreadingActivation(String sf) {
		return sac.computeSpreadingActivation(sf);
	}
	
	public double present(String word, double totalTime) {
		if (!super.containsKey(word)) {
			super.put(word, bwac.initChunk(word, totalTime));
		}
		double baseAct = getBaseActivation(word);
		double spreadAct = getSpreadingActivation(word);
		super.get(word).addPresentation();
		sac.addWordToWorkingMemory(word);
		if (baseAct + spreadAct < 0) {
			System.out.println("hi");
		}
		return baseAct + spreadAct;
	}
	public void addDelayEventToWM() {
		sac.addWordToWorkingMemory(null);
	}
	
	public void decayPresentations(double time) {
		for (Chunk c : super.values()) {
			c.decay(time);
		}
	}
	
	private static final long serialVersionUID = 8099792661937011374L;
	
}
