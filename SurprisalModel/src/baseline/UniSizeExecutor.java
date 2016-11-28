package baseline;

import data.OnsetPairList;
import util.sys.Executor;

public class UniSizeExecutor extends Executor<UniSizeBuilder, OnsetPairList, UniSizeDelayList> {

	public UniSizeExecutor() {
		super("unisizebuilder", 12, UniSizeBuilder.class, OnsetPairList.class, UniSizeDelayList.class);
	}
	public static void main(String[] args) {
		UniSizeExecutor use = new UniSizeExecutor();
		use.initializeFromCmdLine(args);
		use.run();
	}
}
