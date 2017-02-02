package timewindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class ActWindow {
	private final List<Double> precedingTimeWindows;
	private final double activation;
	private final String word;
	public ActWindow(Queue<Double> precedingWindow, double activation, String wordid) {
		this.word = wordid;
		this.precedingTimeWindows = new ArrayList<Double>(precedingWindow);
		this.activation = activation;
	}
	public String alttoString() {
		String timeWindowString = "";
		for (double d : precedingTimeWindows) {
			timeWindowString += d + ",";
		}
		if (precedingTimeWindows.size() > 0) {
			timeWindowString = timeWindowString.substring(0, timeWindowString.length()-1);
		}
		return word + "," + activation + "," + timeWindowString;
	}
	public String toString() {
		String oldstr = alttoString();
		String[] parts = oldstr.split(",");
		String word = parts[0];
		String activation = parts[1];
		String newline = System.getProperty("line.separator");
		String toReturn = "";
		int rando = new Random().nextInt(parts.length-3)+2;
		int idx = rando-2;
		toReturn += idx+","+word+","+activation+","+parts[rando];
//		for (int i = 2; i < parts.length; i++) {
//			int idx = i-2;
//			String line = idx+","+word+","+activation+","+parts[i];
//			toReturn += line + newline;
//		}
//		if (!toReturn.isEmpty()) {
//			toReturn = toReturn.substring(0, toReturn.length()-1);
//		}
		return toReturn;
	}
}
