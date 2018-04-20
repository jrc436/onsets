package model;

import optimizers.DoublePair;
import sentence.Sentence;
import sentence.WordEvent;
import wm.AbstractWorkingMemory;
import wm.PrevWordMem;

public class TracerModel implements IModel {

	private final PrevWordMem pm;
	private final AbstractWorkingMemory wm;
	private final Sentence s;
	private final ModelData md;
	public TracerModel(PrevWordMem pm, AbstractWorkingMemory wm, Sentence s) {
		this.pm = pm;
		this.wm = wm;
		this.s = s;
		md = new ModelData();
	}
	//mehhh structure but ok
	public ModelData stepThrough() {
		while (s.hasNextWord()) {
			step();
		}
		return md;
	}
	// 50 ms, the common time step of an ACT-R model
	protected double getTimeStep() {
		return 0.001;
	}
	
	@Override
	public void step() {
		// A word was spoken!
		WordEvent spokenWord = wm.timeStep(s, getTimeStep()); 
		if (spokenWord != null) {
			md.add(new ModelStateTime(wm.toString(), spokenWord.getWord(), 
					new DoublePair(wm.getLastObsOnset(), wm.getTotalElapsedTime()), // observed is 
					new DoublePair(wm.getLastExpOnset(), spokenWord.getOnset())));
			//s.speakWord();
			pm.step(spokenWord);
			wm.setLastOnsets(spokenWord);
		}
		// No further processing is needed. We'll just use this step to move forward the memory pointer to the next retrieval.
		
		
	}

}
