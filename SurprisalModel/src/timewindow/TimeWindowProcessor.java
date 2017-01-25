package timewindow;

import java.io.File;

import data.IWordStream;
import data.OnsetPairList;
import data.WordStream;
import util.sys.LineProcessor;

public class TimeWindowProcessor extends LineProcessor<OnsetPairList, TimeWindowList> {

	
	public TimeWindowProcessor() {
		super();
	}
	public TimeWindowProcessor(String inpDir, String outDir, String[] args) {
		super(inpDir, outDir, new TimeWindowList(args));
	}
	@Override
	public int getNumFixedArgs() {
		return 0;
	}

	@Override
	public boolean hasNArgs() {
		return false;
	}

	@Override
	public String getConstructionErrorMsg() {
		return "Needs no additional arguments";
	}

	@Override
	public OnsetPairList getNextData() {
		File f = super.getNextFile();
		if (f == null) {
			return null;
		}
		return OnsetPairList.readFile(f);
	}

	@Override
	public void map(OnsetPairList newData, TimeWindowList threadAggregate) {
		IWordStream ws = new WordStream(newData);
		threadAggregate.add(ConvoWindowList.fromWordStream(ws, threadAggregate.getWindowSize()));
	}

}
