package actr;

import java.util.LinkedList;
import java.util.Queue;

import nlp.pmi.PMIDict;

public class SpreadingActivationComputer {;
	public static final int workingMemorySize = 5;
	private static final double attentionalDecay = 0.5; 
	//this doesn't have a closed form analytical solution, given wm and decay, so I solved it for wmsize = 5 and decay = 0.5
	private static final double baseAttention = 0.507936508;
	private final Queue<String> workingMemory;
	private final PMIDict pmi;
	
	public SpreadingActivationComputer(PMIDict pmi) {
		workingMemory = new LinkedList<String>();
		this.pmi = pmi;
	}
	
	public void addWordToWorkingMemory(String newWord) {
		workingMemory.offer(newWord);
		if (workingMemory.size() > workingMemorySize) {
			workingMemory.poll();
		}
	}
	
	public double computeSpreadingActivation(String word) {
		double spreadingActivation = 0;
		int i = 0;
		for (String s : workingMemory) {
			if (s == null) {
				continue;
			}
			double spr = pmi.containsKey(word,s) ? pmi.get(word,s) : 0;
			spreadingActivation += computeWeight(i) * spr;
			i++;
		}
		return spreadingActivation;
	}
	private double computeWeight(int index) {
		return baseAttention * Math.pow(attentionalDecay, index);
	}
	
	//a trick to use will be to compute spreading activation 'late', rather than to compute a full semantic network.
	
	//how to do this? Well, fairly simple:
	//Word1, clearly hasn't received spreading activation
	//Wordi, for the previous k words, compute its semantic relatedness to i. If the semantic relatedness is over a certain threshhold, add the spreading activation
	//These things are symmetric... consider storing in a DOUBLEKEYMAP!
	
	
	//alternatively, we could do the 'big long haul'. First, we create a vocabulary in Switchboard. Then we compute say, the 10 more semantically related words
	//to each one. (Could also do phonetics, but fuck it). 
}
