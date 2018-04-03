package model;

import sentence.Sentence;
import util.sys.Executor;

public class ModelExecutor extends Executor<ModelProcessor, Sentence, ModelData> {

	public ModelExecutor() {
		super("runmodel", 25, ModelProcessor.class, Sentence.class, ModelData.class);
	}
	public static void main(String[] args) {
		ModelExecutor me = new ModelExecutor();
		me.initializeFromCmdLine(args);
		me.run();
	}

}
