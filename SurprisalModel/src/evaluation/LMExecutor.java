package evaluation;

import util.data.DoubleList;
import util.sys.Executor;

public class LMExecutor extends Executor<LMBaseEvaluator, LMPairList, DoubleList> {
	//length: 288865.8233272552
	//uni: 2910934.0105477353
	public LMExecutor() {
		super("lmcompute", 6, LMBaseEvaluator.class, LMPairList.class, DoubleList.class);
	}
	public static void main(String[] args) {
		LMExecutor lme = new LMExecutor();
		lme.initializeFromCmdLine(args);
		lme.run();
		System.out.println(lme.getProcessor().sumAvgEvaluations());
	}
	//length 0.3640107456074812
	//uni 3.668178005236799
}
