package timewindow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import util.generic.data.GenericList;
import util.sys.DataType;

public class TimeWindowList extends GenericList<ConvoWindowList> {
	
	private static final long serialVersionUID = -8657005507130874088L;
	private final double windowSize;
	public double getWindowSize() {
		return windowSize;
	}
	public TimeWindowList() {
		super();
		this.windowSize = 0;
	}
	public TimeWindowList(String[] args) {
		super();
		this.windowSize = Double.parseDouble(args[0]);
	}
	public TimeWindowList(double windowSize) {
		super();
		this.windowSize = windowSize;
	}
	public TimeWindowList(List<ConvoWindowList> l) {
		super(l);
		this.windowSize = l.size() == 0 ? 0.0 : l.get(0).getDuration();
	}

	public TimeWindowList(TimeWindowList other) {
		super(other);
		this.windowSize = other.windowSize;
	}
	public static TimeWindowList fromFile(File f) {
		List<ConvoWindowList> cwll = new ArrayList<ConvoWindowList>();
		int i = 0;
		try {
			List<String> lines = Files.readAllLines(f.toPath());
			for (String line : lines) {
				i++;
				cwll.add(ConvoWindowList.fromString(line));
			}
		} catch (IOException|IllegalArgumentException e) {
			System.err.println("File:"+f+", line:"+i);
			e.printStackTrace();
			System.exit(1);
		}
		return new TimeWindowList(cwll);
	}

	@Override
	public DataType deepCopy() {
		return new TimeWindowList(this);
	}

	@Override
	public int getNumFixedArgs() {
		return 1;
	}

	@Override
	public boolean hasNArgs() {
		return false;
	}

	@Override
	public String getConstructionErrorMsg() {
		return "Requires the duration window";
	}

}
