package ryTracer;

import Tracer.HitRecord;


public abstract class Light implements Traceable {
	static public final double BIAS = .00001;
	static final int SAMPLESHADOWS = 4;
	double intensity;
	int softSamples = 0;
	double spread = 0;
	Color color;
	boolean shadows = false;
	Jitterer jitter;
	String falloff = "none";
	
	
	abstract void printType();

	public double getIntensity() {
		return intensity;
	}
	
	public double getIntensity(HitRecord hit) {
		return intensity;
	}
	
	public boolean getShadows() {
		return shadows;
	}

	public abstract Color getColor();

	public void setFalloff(String string) {
		falloff = string;
		
	}
	
	
}
