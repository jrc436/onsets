package actdelay;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import util.generic.data.GenericList;
import util.sys.DataType;

public class ActDelayList extends GenericList<ActDelay> {

	public ActDelayList() {
		super();
	}
	public ActDelayList(ActDelayList other) {
		super(other);
	}
	public ActDelayList(File f) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error reading file: "+f);
			System.exit(1);
		}
		boolean firstLine = true;
		for (String line : lines) {
			if (firstLine) {
				firstLine = false;
				continue;
			}
			this.add(ActDelay.fromString(line));
		}
	}
	public static ActDelayList fromFile(File f) throws IOException {
		List<String> lines = Files.readAllLines(f.toPath());
		ActDelayList adl = new ActDelayList();
		for (String line : lines) {
			adl.add(ActDelay.fromString(line));
		}
		return adl;
	}
	private static final long serialVersionUID = 2666778742110702037L;

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
		return "ActDelayList has no starting arguments";
	}

	@Override
	public DataType deepCopy() {
		return new ActDelayList(this);
	}
	public String getHeaderLine() {
		return "activation,delay";
	}

}
