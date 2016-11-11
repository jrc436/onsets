package data;

import util.data.xml.XmlDocReadable;
import util.sys.Executor;

public class OnsetReadExecutor extends Executor<OnsetEasyReader, XmlDocReadable, OnsetPairList> {
	public OnsetReadExecutor() {
		super("onsetread", 5, OnsetEasyReader.class, XmlDocReadable.class, OnsetPairList.class);
	}
	public static void main(String[] args) {
		OnsetReadExecutor ore = new OnsetReadExecutor();
		ore.initializeFromCmdLine(args);
		ore.run();
	}

}
