package model;

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
		return 0.05;
	}
	
	@Override
	public void step() {
		wm.startRetrieval(s);
		WordEvent we = s.getNextWord();
		ModelState toAdd = new ModelState(wm.toString(), pm.toString(), we.getWord(), wm.getElapsedTime());
		// A word was spoken!
		if (wm.step(we)) {
			md.add(toAdd);
			s.speakWord();
			System.out.println(we);
		}
		// No further processing is needed. We'll just use this step to move forward the memory pointer to the next retrieval.
		pm.step(we);
		
	}

}
