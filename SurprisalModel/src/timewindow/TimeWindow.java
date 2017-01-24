package timewindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.DelayEvent;
import data.DelayWord;

public class TimeWindow  {
	private final double duration;
	private final List<String> words;
	public TimeWindow(double duration, List<String> words) {
		this.duration = duration;
		this.words = words;
	}
	public List<String> getWords() {
		return words;
	}
	public double getDuration() {
		return duration;
	}
	public double getSpeakingRate() {
		return ((double) words.size()) / duration;
	}
	public double getNormalizedSpeakingRate(Map<String, Double> durations) {
		return ((double) words.size()) / getNormalizedDuration(durations);
	}
	public double getNormalizedDuration(Map<String, Double> map) {
		double diff = 0.0;
		for (String word : words) {
			diff -= map.get(word);
		}
		return duration - diff;
	}
	public static List<TimeWindow> parseDelayEvents(List<DelayEvent> events, double windowParse) {
		List<TimeWindow> tw = new ArrayList<TimeWindow>();
		double runTime = 0.0;
		List<String> words = new ArrayList<String>();
		for (DelayEvent de : events) {
			if (runTime + de.getElapsedTime() > windowParse) {
				double timeLeft = windowParse - runTime;
				List<String> newWords = new ArrayList<String>(); 
				//in this case, we're assigning it to this interval
				if (de.getElapsedTime()/2 < timeLeft && de instanceof DelayWord) {
					words.add(((DelayWord)de).getWord());
				}
				//otherwise we'll assign it to the next
				else if (de instanceof DelayWord) {
					newWords.add(((DelayWord)de).getWord());
				}
				tw.add(new TimeWindow(windowParse, words));
				words = newWords;
				runTime = de.getElapsedTime() - timeLeft;
				continue;
			}
			if (de instanceof DelayWord) {
				words.add(((DelayWord)de).getWord());
			}
			runTime += de.getElapsedTime();
		}
		return tw;
	}
	private static final String delim = "-::-";
	public String toString() {
		String words = "";
		for (String word : this.words) {
			words = word + delim;
		}
		if (!words.isEmpty()) {
			words = words.substring(0, words.length()-delim.length());
		}
		return this.words.size()+","+duration+","+words;
	}
	public static TimeWindow fromString(String s) {
		String[] parts = s.split(",");
		if (parts.length != 3) {
			throw new IllegalArgumentException("String: "+s+" does not contain a TimeWindow");
		}
		String[] words = parts[2].split(delim);
		int expwordsize = Integer.parseInt(words[0]);
		if (expwordsize != words.length) {
			throw new IllegalArgumentException("String: "+s+" has a mismatch between actual and expected words");
		}
		List<String> wordlist = new ArrayList<String>();
		for (String word : words) {
			wordlist.add(word);
		}
		double duration = Double.parseDouble(parts[1]);
		return new TimeWindow(duration, wordlist);
	}
}
