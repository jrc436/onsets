package evaluation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import util.collections.GenericList;
import util.sys.DataType;

public class LMPairList extends GenericList<LMPair> {

	private static final long serialVersionUID = 2425460232144721859L;

	public LMPairList() {
		super();
	}
	public LMPairList(LMPairList other) {
		super(other);
	}
	public LMPairList(File f) {
		super();
		try {
			List<String> lines = Files.readAllLines(f.toPath());
			for (String line : lines) {
				this.add(LMPair.fromString(line));
			}
		}
		catch (IOException io) {
			System.err.println(io.getMessage());
			throw new IllegalArgumentException();
		}
	}
	@Override
	public DataType deepCopy() {
		return new LMPairList(this);
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
		return "lmpairlist doesn't presently have any arguments";
	}

}
