//package sentence;
//
//import java.io.File;
//import java.nio.file.Files;
//import java.util.List;
//
//import util.generic.data.GenericList;
//import util.sys.DataType;
//
//public class SentenceList extends GenericList<Sentence> {
//
//
//	private static final long serialVersionUID = 3657124424457257137L;
//
//	public SentenceList() {
//		super();
//	}
//	
//	public SentenceList(SentenceList other) {
//		super(other);
//	}
//	public static SentenceList fromFile(File f) {
//		List<String> lines = Files.readAllLines(f.toPath());
//		for (String line : lines) {
//			Sentence.fromString(line);
//		}
//	}
//	
//	@Override
//	public DataType deepCopy() {
//		return new SentenceList(this);
//	}
//
//	@Override
//	public int getNumFixedArgs() {
//		return 0;
//	}
//
//	@Override
//	public boolean hasNArgs() {
//		return false;
//	}
//
//	@Override
//	public String getConstructionErrorMsg() {
//		return "has no args";
//	}
//
//}
