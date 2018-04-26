package sentence;

import input.WordEvent;
import util.collections.Pair;

@FunctionalInterface
public interface BufferCallback {
	Pair<WordEvent, Double> getNextBufferState(Sentence s);
}
