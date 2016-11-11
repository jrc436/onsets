package actdelay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.sys.DataType;
import util.sys.FileWritable;

public class ActDelayList extends ArrayList<ActDelay> implements DataType {

	public ActDelayList() {
		super();
	}
	public ActDelayList(ActDelayList other) {
		super(other);
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
	public Iterator<String> getStringIter() {
		return FileWritable.<ActDelay, List<ActDelay>>iterBuilder(this, ActDelay::toString);
	}

	@Override
	public DataType deepCopy() {
		return new ActDelayList(this);
	}
	public String getHeaderLine() {
		return "activation,delay";
	}

}
