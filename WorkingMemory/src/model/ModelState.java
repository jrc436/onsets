package model;

public class ModelState {
	private final String mem1content;
	private final String mem2content;
	private final String realizationWord;
	private final double obsRealizationTime;
	private static final String delim = "$-$";
	public ModelState(String mem1String, String mem2String, String realizationWord, double realizationTime) {
		mem1content = mem1String;
		mem2content = mem2String;
		this.realizationWord = realizationWord;
		this.obsRealizationTime = realizationTime;
	}
	public String toString() {
		return realizationWord + delim + obsRealizationTime + delim + mem1content + delim + mem2content;
	}
}
