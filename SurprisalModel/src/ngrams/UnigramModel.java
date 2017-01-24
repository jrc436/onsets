package ngrams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import nlp.util.TextNormalizer;

//how does an ngram model work?
//Well!
//The probability of a given sentence is the joint probability of all of the ngrams in the sentence. Due to performance caps, we normally are really talking about
//5 grams. So given some sentence of length N, do a sliding window of 5grams and sum them. Okay, done.

//Okay, but how do we compute that 5gram probability if it's not there? Obviously, many 5grams probably won't have occurred in any given corpus, but that doesn't
//mean their probability is zero! So we have to compute some kind of estimate... 

//so we have A SINGLE basic functions: COMPUTE_JOINT_PROBABILITY [n | n-1...1]
//                                         1. If in table, lookup
//                                         2. If not in table, COMPUTE_JOINT_PROBABILITY [n | n-1...2] * BOW[n-1 | n-2 ... 1]
//                                                                            NOTE: this isn't SO different than just adding the fuckers, so long as you're consistent
//                                                                            SECONDNOTE: this should be the same process as when the order is too large, such as with a sentence
//                                                                            THIRDNOTE: the important points, is basically you're still just saying P(1...n-1) * P(2...n)... bow is a normalization scheme
                                    

public class UnigramModel extends HashMap<String, Double> {
	private static final long serialVersionUID = 6596387879736780034L;
	private static final String unkString = "<unk>";
	public static UnigramModel readFile(Path p) throws IOException {
		List<String> lines = Files.readAllLines(p);
		UnigramModel m = new UnigramModel();
		for (String line : lines) {
			if (line.trim().isEmpty()) {
				continue;
			}
			else if (line.equals("\\data\\")) {
				System.out.println("Data found");
				continue;
			}
			else if (line.equals("\\end\\")) {
				System.out.println("End of file");
				continue;
			}
			else if (line.contains("ngram")) {
				continue;
			}
			else if (line.contains("\\"+1+"-grams")) {
				continue;
			}
			String[] parts = line.split("\\s");
	
			if (parts.length != 2) {
				System.err.println(line);
				throw new IllegalArgumentException("malformed input");
			}
			//System.out.println(line);
//			List<String> gramStrings = TextNormalizer.normalizeWord(parts[1]);
//			if (gramStrings.size() > 1) {
//				System.err.println(gramStrings);
//				System.err.println(p);
//				System.err.println("This file contains malformatted words");
//				System.err.println("Continuing...");
//			}
//			else if (gramStrings.size() == 0) {
//				continue;
//			}
			double val = Double.parseDouble(parts[0]);
			m.put(TextNormalizer.normalizeWord(parts[1]), val);
		}
		if (!m.containsKey(unkString)) {
			throw new IllegalArgumentException("LM without unk won't work!");
		}
		return m;
	}
	public Double get(String key) {
		double logprob = this.containsKey(key) ? super.get(key) : super.get(unkString);
		return Math.pow(10, logprob);
	}

}
