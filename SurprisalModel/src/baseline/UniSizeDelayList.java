package baseline;

import util.generic.data.GenericList;
import util.sys.DataType;

public class UniSizeDelayList extends GenericList<UniSizeDelay> {
	public UniSizeDelayList() {
		super();
	}
	public UniSizeDelayList(UniSizeDelayList other) {
		super(other);
	}
	private static final long serialVersionUID = -5943232420846913291L;

	@Override
	public DataType deepCopy() {
		return new UniSizeDelayList(this);
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
		return "UniSizeDelayList requires no args";
	}

}
