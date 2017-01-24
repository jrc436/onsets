package timewindow;

import util.generic.data.GenericList;
import util.sys.DataType;

public class ActWindowList extends GenericList<ActWindow> {

	private final int precedingWindowNum;
	private static final long serialVersionUID = 572599790997990860L;

	public ActWindowList() {
		super();
		this.precedingWindowNum = 0;
	}
	public ActWindowList(ActWindowList other) {
		super(other);
		this.precedingWindowNum = other.precedingWindowNum;
	}
	
	@Override
	public DataType deepCopy() {
		return new ActWindowList(this);
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
		return "requires no args";
	}

}
