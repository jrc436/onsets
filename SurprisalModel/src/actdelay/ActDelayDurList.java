package actdelay;

import util.generic.data.GenericList;
import util.sys.DataType;

public class ActDelayDurList extends GenericList<ActDelayDur> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4933535167499846297L;

	public ActDelayDurList() {
		super();
	}
	public ActDelayDurList(ActDelayDurList other) {
		super(other);
	}
	@Override
	public DataType deepCopy() {
		return new ActDelayDurList(this);
	}

	@Override
	public int getNumFixedArgs() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasNArgs() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getConstructionErrorMsg() {
		return "has no args";
	}
	@Override
	public String getHeaderLine() {
		return "activation,delay,duration,word";
	}

}
