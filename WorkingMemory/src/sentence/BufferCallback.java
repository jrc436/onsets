package sentence;

import util.collections.Pair;

@FunctionalInterface
public interface BufferCallback {
	Pair<WordEvent, Double> getNextBufferState(Sentence s);
}
