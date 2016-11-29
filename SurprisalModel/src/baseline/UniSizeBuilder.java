package baseline;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;

import data.AltWordStream;
import data.DelayEvent;
import data.DelayWord;
import data.IWordStream;
import data.OnsetPairList;
import ngrams.UnigramModel;
import util.sys.LineProcessor;

public class UniSizeBuilder extends LineProcessor<OnsetPairList, UniSizeDelayList>{
	private final UnigramModel um;
	public UniSizeBuilder() {
		super();
		this.um = null;
	}
	public UniSizeBuilder(String inp, String out, String[] args) {
		super(inp, out, new UniSizeDelayList());
		UnigramModel m = null;
		try {
			m = UnigramModel.readFile(Paths.get(args[0]));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		um = m;
	}
	@Override
	public int getNumFixedArgs() {
		return 1;
	}

	@Override
	public boolean hasNArgs() {
		return false;
	}

	@Override
	public String getConstructionErrorMsg() {
		return "UniSizeBuilder needs the path to the UnigramModel";
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
	public void map(OnsetPairList newData, UniSizeDelayList threadAggregate) {
		IWordStream ws = new AltWordStream(newData);
		Iterator<DelayEvent> delays = ws.getEvents();
		while (delays.hasNext()) {
			DelayEvent delay = delays.next();
			if (delay instanceof DelayWord) {
				DelayWord dw = (DelayWord) delay;
				threadAggregate.add(new UniSizeDelay(dw.getWord(), dw.getDelay(), um.get(dw.getWord())));
			}
		}
	}
}
