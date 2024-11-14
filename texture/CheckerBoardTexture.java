package texture;

import ryTracer.Color;

public class CheckerBoardTexture extends Texture{
	
	Color light;
	Color dark;

	public CheckerBoardTexture(Color l, Color d, double su, double sv){
		light = l;
		dark = d;
		u_scale = su;
		v_scale = sv;
		
	}
	public Color getTexel(double u, double v) {
		u = Math.abs(u/u_scale);
		v = Math.abs(v/v_scale);
		if(((u%1<.5)&&(v%1<.5))||(u%1>.5)&&(v%1>.5))
			return light;
		else return dark;
	}

}
