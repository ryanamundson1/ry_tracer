package texture;

import ryTracer.Color;

public class WoodGrainTexture extends Texture {
	
	Color light;
	Color dark;
	double grain_scale = 50;
	
	public WoodGrainTexture(Color l, Color d, double su, double sv, double grn){
		light = l;
		dark = d;
		u_scale = su;
		v_scale = sv;
		grain_scale = grn;
	}

	@Override
	public Color getTexel(double u, double v) {
		   double radius;
		   double angle;
		   int grain;
		   u = u*u_scale;
		   v = v*v_scale;
		   radius = grain_scale*Math.sqrt(u*u+v*v);
		   angle = Math.PI / 2;
		   radius = radius + 2*Math.sin(20*angle+v/150);
		   grain = (int) (Math.round(radius) % 60);
		   if (grain < 40)
		      return light;
		   else
		      return dark;
		
	}

}
