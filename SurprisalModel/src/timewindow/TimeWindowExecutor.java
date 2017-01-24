package timewindow;

import data.OnsetPairList;
import util.sys.Executor;

public class TimeWindowExecutor extends Executor<TimeWindowProcessor, OnsetPairList, TimeWindowList> {

	public TimeWindowExecutor() {
		super("buildtimewindows", 5, TimeWindowProcessor.class, OnsetPairList.class, TimeWindowList.class);
	}
	public static void main(String[] args) {
		TimeWindowExecutor twe = new TimeWindowExecutor();
		twe.initializeFromCmdLine(args);
		twe.run();
	}

}
