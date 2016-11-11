package ngrams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

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
			String gramString = parts[1].toLowerCase();
			double val = Double.parseDouble(parts[0]);
			m.put(gramString, val);
		}
		if (!m.containsKey(unkString)) {
			throw new IllegalArgumentException("LM without unk won't work!");
		}
		return m;
	}
	public Double get(String key) {
		if (!this.containsKey(key)) {
			return super.get(unkString);
		}
		return super.get(key);
	}

}
