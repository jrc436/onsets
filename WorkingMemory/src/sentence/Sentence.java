package sentence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import util.sys.DataType;
import util.sys.FileWritable;

public class Sentence implements DataType {
	private final Queue<WordEvent> allEvents;
	public Sentence() {
		this.allEvents = new LinkedList<WordEvent>();
	}
	public Sentence(Sentence other) {
		this();
		for (WordEvent we : other.allEvents) {
			allEvents.add(we);
		}
	}
	public static Sentence readSentence(File fi, Set<String> closedClass) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(fi.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Sentence s = new Sentence();
		for (String line : lines) {
			WordEvent we = WordEvent.fromString(line, closedClass);
			if (we != null) {
				s.allEvents.add(we);
			}
		}
		return s;
	}
	public WordEvent getNextWord() {
		return allEvents.peek();
	}
	public void speakWord() {
		allEvents.poll();
	}
	public boolean hasNextWord() {
		return allEvents.peek() != null;
	}
	public List<WordEvent> getRetrievableWords(int windowSize) {
		List<WordEvent> events = new ArrayList<WordEvent>();
		int i = 0;
		for (WordEvent we : allEvents) {
			if (i >= windowSize) {
				break;
			}
			if (!we.isClosed()) {
				events.add(we);
			}
			i++;
		}
		return events;
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
		return "needs no args";
	}
	@Override
	public Iterator<String> getStringIter() {
		return FileWritable.<WordEvent, Queue<WordEvent>>iterBuilder(allEvents);
	}
	@Override
	public DataType deepCopy() {
		return new Sentence(this);
	}
}
