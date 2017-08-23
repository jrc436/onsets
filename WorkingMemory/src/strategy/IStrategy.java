package strategy;

import java.util.List;

import sentence.WordEvent;

public interface IStrategy {
	public WordEvent getNextRetrieval(List<WordEvent> leftToRealize);
}
