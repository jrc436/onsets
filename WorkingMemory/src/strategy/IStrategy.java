package strategy;

import java.util.List;

import input.WordEvent;

public interface IStrategy {
	public WordEvent getNextRetrieval(List<WordEvent> leftToRealize);
}
