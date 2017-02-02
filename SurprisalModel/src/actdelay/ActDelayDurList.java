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
		this.size = 0;
	}
	public ActDelayDurList(String[] size) {
		this(Integer.parseInt(size[0]));
	}
	private final int size;
	public ActDelayDurList(int size) {
		this.size = size;
	}
	public ActDelayDurList(ActDelayDurList other) {
		super(other);
		this.size = other.size;
	}
	@Override
	public DataType deepCopy() {
		return new ActDelayDurList(this);
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
		return "needs the number of previous elements";
	}
	@Override
	public String getHeaderLine() {
//		String durs = "";
//		for (int i = 0; i < size; i++) {
//			durs += "duration"+i + ",";
//		}
		return "activation,delay,index,word";
	}

}
