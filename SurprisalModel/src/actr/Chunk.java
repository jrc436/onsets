package actr;

public class Chunk {
	private final double[] recentP;
	private int totalP;
	private double initialTime;
	public Chunk(int k, int totalP, double initialTime) {
		recentP = new double[k];
		for (int i = 0; i < recentP.length; i++) {
			recentP[i] = Double.MAX_VALUE;
		}
		this.totalP = totalP;
		this.initialTime = initialTime;
	}
	public int getTotalPresentations() {
		return totalP;
	}
	
	public void addInitInfo(int totalP, double initialTime) {
		this.totalP += totalP; //don't want to override the ones already there!
		this.initialTime = initialTime; 
	}
	
	public double t_k() {
		if (recentP.length == 0) {
			return 0;
		}
		return recentP[recentP.length-1]; //returns the least recent still tracked timestamp as desired by the equation
	}
	
	//this time refers to how many seconds it's been in existence. 
	//since all times are in the frame of "how many seconds ago", initialTime is good enough 
	public double t_n() {
		return initialTime;
	}
	public double calculateKActivation(double negD) {
		double activation = 0.0;
		for (double time : recentP) {
			activation += Math.pow(time, negD);
		}
		return activation;
	}
	
	public void addPresentation() {
		addPresentation(0.0, true);
	}
	
	public void addPresentation(double t, boolean counts) {
		for (int i = 0; i < recentP.length; i++) {
			//times here refer to "how many seconds ago"
			if (recentP[i] > t) {
				//insert and shift.
				double toInsert = t;
				for (int j = i; j < recentP.length; j++) {
					double tmp = recentP[j];
					recentP[j] = toInsert;
					toInsert = tmp; 
				}
				//at the end, toInsert should be the element that gets "bumped"
				break;
			}
		}
		if (counts) {
			totalP++;
		}
	}
	
	public void decay(double time) {
		for (int i = 0; i < recentP.length; i++) {
			recentP[i] += time;
		}
		initialTime += time;
	}
}
