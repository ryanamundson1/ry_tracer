package ryTracer;

public class Jitterer {
	double amount;
	
	public Jitterer(double a){
		amount = a;
	}
	
	public double getJitter(){
		return ((Math.random()-.5)*2) * amount;
	}

}
