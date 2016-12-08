package durations;

import java.util.List;

import util.generic.data.GenericMap;
import util.sys.DataType;

public class WordDuration extends GenericMap<String, List<Double>> {
	private static final long serialVersionUID = -3664481860661315775L;
	public WordDuration() {
		super();
	}
	public WordDuration(WordDuration other) {
		super(other);
	}
	
	@Override
	public DataType deepCopy() {
		return new WordDuration(this);
	}

	@Override
	protected String entryString(Entry<String, List<Double>> ent) {
		double avg = 0.0;
		for (double d : ent.getValue()) {
			avg += d;
		}
		avg /= ent.getValue().size();
		return ent.getKey()+","+avg;
	}
	@Override
	public String getHeaderLine() {
		return "word,avgDuration";
	}
}
