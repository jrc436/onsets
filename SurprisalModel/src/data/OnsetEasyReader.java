package data;

import java.util.Iterator;

import util.data.xml.XmlDocReadable;
import util.data.xml.XmlElement;
import util.data.xml.XmlLayer;

//should probably be done with line executors of size 1, to ensure conversations stay intact
public class OnsetEasyReader extends XmlLayer<OnsetPairList> {
	public OnsetEasyReader() {
		super();
	}
	public OnsetEasyReader(String inpDir, String outDir) {
		super(inpDir, outDir, new OnsetPairList());
	}
	@Override
	public void map(XmlDocReadable newData, OnsetPairList threadAggregate) {
		Iterator<XmlElement> els = newData.getElements();
		els = els.next().getChildren();
		while (els.hasNext()) {
			XmlElement el = els.next();
			String word = el.get("orth");
			if (!el.containsKey("nite:start")) {
				continue;
			}
			double start = Double.parseDouble(el.get("nite:start"));
			double end = Double.parseDouble(el.get("nite:end"));
			threadAggregate.add(new OnsetPair(start, end, word));
		}
	}

//	@Override
//	public void reduce(OnsetPairList threadAggregate) {
//		synchronized(processAggregate) {
//			processAggregate.addAll(threadAggregate);
//		}
//	}
	
}
