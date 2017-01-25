package timewindow;

import util.sys.Executor;

public class ActWindowExecutor extends Executor<ActWindowProcessor, TimeWindowList, ActWindowList> {

	public ActWindowExecutor() {
		super("actwindows", 5, ActWindowProcessor.class, TimeWindowList.class, ActWindowList.class);
	}
	
	public static void main(String[] args) {
		ActWindowExecutor ace = new ActWindowExecutor();
		ace.initializeFromCmdLine(args);
		ace.run();
	}

}
