package timewindow;

import java.io.File;

import util.sys.LineProcessor;

public class ActWindowProcessor extends LineProcessor<TimeWindowList, ActWindowList> {

	@Override
	public int getNumFixedArgs() {
		return 0;
	}

	@Override
	public boolean hasNArgs() {
		return false;
	}

	@Override
	public String getConstructionErrorMsg() {
		return null;
	}

	@Override
	public TimeWindowList getNextData() {
		File f = super.getNextFile();
		if (f == null) {
			return null;
		}
		return TimeWindowList.fromFile(f);
	}

	@Override
	public void map(TimeWindowList newData, ActWindowList threadAggregate) {
		
	}
}
