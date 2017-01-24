package timewindow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.DelayEvent;
import data.IWordStream;

public class ConvoWindowList extends ArrayList<TimeWindow> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6831791614383503903L;
	private final double duration;
	public ConvoWindowList() {
		super();
		this.duration = 0;
	}
	public double getDuration() {
		return duration;
	}
	public ConvoWindowList(List<TimeWindow> li) {
		super(li);
		this.duration = li.size() == 0 ? 0.0 : li.get(0).getDuration();
	}
	public static ConvoWindowList fromWordStream(IWordStream iws, double windowSize) {
		ArrayList<DelayEvent> ev = new ArrayList<DelayEvent>();
		Iterator<DelayEvent> it = iws.getEvents();
		while (it.hasNext()) {
			ev.add(it.next());
		}
		return new ConvoWindowList(TimeWindow.parseDelayEvents(ev, windowSize));
	}
	private static final String thisDelim = "-'':''-";
	public String toString() {
		String retval = "";
		for (TimeWindow tw : this) {
			retval += tw.toString() + thisDelim;
		}
		retval = retval.substring(0, retval.length()-thisDelim.length());
		return retval;
	}
	public static ConvoWindowList fromString(String s) {
		String[] arr = s.split(thisDelim);
		List<TimeWindow> li = new ArrayList<TimeWindow>();
		for (String a : arr) {
			li.add(TimeWindow.fromString(a));
		}
		return new ConvoWindowList(li);
	}
}
