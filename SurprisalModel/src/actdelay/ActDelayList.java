package actdelay;

import util.collections.GenericList;
import util.sys.DataType;

public class ActDelayList extends GenericList<ActDelay> {

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
	public DataType deepCopy() {
		return new ActDelayList(this);
	}
	public String getHeaderLine() {
		return "activation,delay";
	}

}
