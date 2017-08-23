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
	
	@Override
	public void step() {
		wm.startRetrieval(s);
		WordEvent we = s.getNextWord();
		ModelState toAdd = new ModelState(wm.toString(), pm.toString(), we.getWord());
		if (wm.step(we)) {
			md.add(toAdd);
			s.speakWord();
		}
		else {
			throw new RuntimeException(we.toString() + " is not in workingMemory: "+wm.toString()+" or a closed class word");
		}
		pm.step(we);
		
	}

}
