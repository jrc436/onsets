package model;

import java.lang.reflect.InvocationTargetException;

import wm.AbstractWorkingMemory;

public class MemoryFactory {
	private final Class<? extends AbstractWorkingMemory> abs;
	public MemoryFactory(Class<? extends AbstractWorkingMemory> abs) {
		this.abs = abs;
	}
	public AbstractWorkingMemory produce(int windowSize, int k) {
		try {
			return abs.getConstructor(Integer.TYPE, Integer.TYPE).newInstance(windowSize, k);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
