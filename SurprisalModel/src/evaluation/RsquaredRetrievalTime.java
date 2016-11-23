package evaluation;

import actdelay.ActDelay;
import actr.SuccessfulRetrievalModel;
import optimizers.Evaluator;
import optimizers.VariableName;
import optimizers.VariableSet;

public class RsquaredRetrievalTime extends Evaluator<ActDelay> {
	public RsquaredRetrievalTime(VariableSet vs) {
		super(vs);
	}
	@Override
	public double evaluate(ActDelay ad) {
		SuccessfulRetrievalModel sm = new SuccessfulRetrievalModel(vs.getValue(VariableName.rt_intercept), vs.getValue(VariableName.cutoffK));
		return Math.pow(sm.getRetrievalTime(ad.getActivation()) - ad.getDelay(), 2);
	}

}
