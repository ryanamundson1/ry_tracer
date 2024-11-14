package texture;

import ryTracer.Color;

public abstract class Texture {
	
	double u_scale = 1;
	double v_scale = 1;
	
	public abstract Color getTexel (double u, double v);

}
