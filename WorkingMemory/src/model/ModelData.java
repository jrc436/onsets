package model;

import util.generic.data.GenericList;
import util.sys.DataType;

public class ModelData extends GenericList<ModelState> {

	private static final long serialVersionUID = -7976721655685605174L;

	public ModelData() {
		super();
	}
	public ModelData(ModelData other) {
		super(other);
	}
	
	@Override
	public DataType deepCopy() {
		return new ModelData(this);
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
	
	@Override
	public String getFooterLine() {
		return "END";
	}

}
