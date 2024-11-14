package filters;

public class LinearFilter {
	
	public double average(double a, double b, double c){
		return (a*c+b*c)/(a+b);
	}

}
