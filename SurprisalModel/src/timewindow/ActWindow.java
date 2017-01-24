package timewindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ActWindow {
	private final List<Double> precedingTimeWindows;
	private final double activation;
	private final String word;
	public ActWindow(Queue<Double> precedingWindow, double activation, String wordid) {
		this.word = wordid;
		this.precedingTimeWindows = new ArrayList<Double>(precedingWindow);
		this.activation = activation;
	}
	public String toString() {
		String timeWindowString = "";
		for (double d : precedingTimeWindows) {
			timeWindowString += d + ",";
		}
		if (precedingTimeWindows.size() > 0) {
			timeWindowString = timeWindowString.substring(0, timeWindowString.length()-1);
		}
		return word + "," + activation + "," + precedingTimeWindows;
	}
}
