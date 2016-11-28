package evaluation;

import optimizers.DoublePair;
import optimizers.RsquaredEvaluator;
import optimizers.VariableName;
import optimizers.VariableSet;

public class RsquaredLM extends RsquaredEvaluator<LMPair> {
	public RsquaredLM(VariableSet vs) {
		super(vs);
	}
	@Override
	protected DoublePair getDoublePair(LMPair dat) {
		LM lm = new LM(vs.getValue(VariableName.lm_corr), vs.getValue(VariableName.lm_intercept));
		return new DoublePair(dat.getY(), lm.predict(dat.getX()));
	}
}
