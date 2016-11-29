package evaluation;

import optimizers.EvalRunner;

public class LMBaseEvaluator extends EvalRunner<LMPair, LMPairList> {
	//length 1.212433749482789E7
	//uni 2910934.0105477353
	public LMBaseEvaluator() {
		super();
	}
	public LMBaseEvaluator(String inpDir, String outDir, String[] args) {
		super(inpDir, outDir, new RsquaredLM(LM.getLMVS(Double.parseDouble(args[1]), Double.parseDouble(args[0]))), LMPairList.class);
	}
	@Override
	public int getNumFixedArgs() {
		return 2;
	}
	@Override
	public String getConstructionErrorMsg() {
		return "LM Evaluator needs the intercept and the correlation";
	}

}
