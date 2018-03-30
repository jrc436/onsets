package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dm.BaseWordActivationComputer;
import dm.DeclarativeMemory;
import ngrams.UnigramModel;
import nlp.pmi.DictReformatter;
import nlp.pmi.PMIDict;
import sentence.Sentence;
import util.sys.LineProcessor;
import wm.AbstractWorkingMemory;
import wm.PMISpreadAct;
import wm.PrevWordMem;

public class ModelProcessor extends LineProcessor<Sentence, ModelData> {
	private final Set<String> closedClassWords;
	private static final int k = 5;
	private static final double negD = -0.16;
	private static final int windowSize = 5;
	private final PMISpreadAct pmis;
	private final BaseWordActivationComputer bwac;
	private final MemoryFactory memFac;
	public ModelProcessor() {
		super();
		this.closedClassWords = null;
		this.pmis = null;
		this.bwac = null;
		this.memFac = null;
	}
	@SuppressWarnings("unchecked")
	public ModelProcessor(String inpDir, String outDir, String[] paths) { 
		super(inpDir, outDir, new ModelData());
		String pathToClosed = paths[2];
		String pathToPMI = paths[1]; 
		String pathToUni = paths[0];
		this.closedClassWords = readFile(pathToClosed);
		PMIDict pmi = null;
		pmi = DictReformatter.readOversizeDict(pathToPMI); 
		this.pmis = new PMISpreadAct(pmi);
		this.bwac = new BaseWordActivationComputer(k, UnigramModel.readFile(Paths.get(pathToUni)));
		try {
			this.memFac = new MemoryFactory((Class<? extends AbstractWorkingMemory>) Class.forName(paths[3].trim()));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to create Memory Factory from class: "+paths[3]);
		}
	}
	private static Set<String> readFile(String path)  {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Set<String> words = new HashSet<String>();
		for (String line : lines) {
			for (String word : line.split("\\s+")) {
				words.add(word);
			}
		}
		return words;
	}
	@Override
	public int getNumFixedArgs() {
		return 4;
	}
	@Override
	public boolean hasNArgs() {
		return false;
	}
	@Override
	public String getConstructionErrorMsg() {
		return "needs the path to the closed set words, the path to the pmi dict, and the path to the unigram model, and the memory path";
	}
	@Override
	public Sentence getNextData() {
		File f = super.getNextFile();
		if (f == null) {
			return null;
		}
		return Sentence.readSentence(f, closedClassWords);
	}
	@Override
	public void map(Sentence newData, ModelData threadAggregate) {
		AbstractWorkingMemory wm = memFac.produce(windowSize, k);
		PrevWordMem pm = new PrevWordMem(windowSize, 1);
		DeclarativeMemory dm = new DeclarativeMemory(k, negD, bwac, pmis, wm);
		wm.setDM(dm);
		wm.initialize(newData);
		TracerModel model = new TracerModel(pm, wm, newData);
		ModelData run = model.stepThrough();
		threadAggregate.addAll(run);
	}
}
