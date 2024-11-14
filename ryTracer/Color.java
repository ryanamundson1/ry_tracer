package ryTracer;

public class Color {
	public double r;
	public double g;
	public double b;
	
	public Color(double red, double green, double blue){
		r = red;
		g = green;
		b = blue;
	}
	
	public Color clamp(){
		return new Color (Math.min(r, 1.0), Math.min(g, 1.0),Math.min(b, 1.0));
	}
	
	public String toString(){
		return ("red: " + r + " green: " + g + " blue: " +b);
	}

	public Color add(Color c) {
		return new Color (r + c.r, g + c.g, b + c.b);
	}

	public void divide(int superSamples) {
		r /= superSamples;
		g /= superSamples;
		b /= superSamples;
		
	}

	public Color mult(double d) {
		return new Color (r * d, g * d, b * d);
	}
	public Color clone() {
		return new Color (r, g, b);
	}

	public Color pow(double d) {
		return new Color ( Math.pow(r , d), Math.pow(g , d), Math.pow(b , d));
	}

	public Color mult(Color c) {
		return new Color (r * c.r, g * c.r, b * c.b) ;
	}

}
