package data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.sys.DataType;
import util.sys.FileWritable;

public class OnsetPairList extends ArrayList<OnsetPair> implements DataType {
	private static final long serialVersionUID = 2069756200215262533L;

	public OnsetPairList() {
		super();
	}
	public OnsetPairList(OnsetPairList other) {
		super(other);
	}
	public static OnsetPairList readFile(File f) {
		List<String> lines = null;;
		try {
			lines = Files.readAllLines(f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		OnsetPairList osl = new OnsetPairList();
		for (String line : lines) {
			String[] parts = line.split(",");
			osl.add(new OnsetPair(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), parts[0]));
		}
		return osl;
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
		return "OnsetPairList requires no args";
	}

	@Override
	public Iterator<String> getStringIter() {
		return FileWritable.<OnsetPair, List<OnsetPair>>iterBuilder(this, OnsetPair::toString);
	}

	@Override
	public DataType deepCopy() {
		return new OnsetPairList(this);
	}

}
