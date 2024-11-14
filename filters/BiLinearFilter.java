package filters;

import ryTracer.Color;
import vector.utilities.Point2d;

public class BiLinearFilter {
	
	public static Color Filter(Color a,Color b,Color c,Color d , Point2d filtered){
		a = a.mult(filtered.x*filtered.y);
		c =c.mult((1-filtered.x)*filtered.y);
		b = b.mult(filtered.x*(1-filtered.y));
		d = d.mult((1-filtered.x)*(1-filtered.x));
		a = a.add(b);
		a = a.add(c);
		a= a.add(d);
		
		return a;
	}

}
