package evaluation;

import actdelay.ActDelay;
import dm.SuccessfulRetrievalModel;
import optimizers.DoublePair;
import optimizers.RsquaredEvaluator;
import optimizers.VariableName;
import optimizers.VariableSet;

public class RsquaredRetrievalTime extends RsquaredEvaluator<ActDelay> {
	public RsquaredRetrievalTime(VariableSet vs) {
		super(vs);
	}
	@Override
	public double evaluate(ActDelay ad) {
		SuccessfulRetrievalModel sm = new SuccessfulRetrievalModel(vs.getValue(VariableName.rt_intercept), vs.getValue(VariableName.cutoffK));
		return Math.pow(sm.getRetrievalTime(ad.getActivation()) - ad.getDelay(), 2);
	}
	@Override
	protected DoublePair getDoublePair(ActDelay dat) {
		SuccessfulRetrievalModel sm = new SuccessfulRetrievalModel(vs.getValue(VariableName.rt_intercept), vs.getValue(VariableName.cutoffK));
		return new DoublePair(sm.getRetrievalTime(dat.getActivation()), dat.getDelay());
	}

}
