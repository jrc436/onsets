package data;

import java.util.Iterator;

import util.sys.DataType;

public interface IWordStream extends DataType {
	public Iterator<DelayEvent> getEvents();
}
