package texture;

import ryTracer.Color;

public class UVTexture extends Texture {

	@Override
	public Color getTexel(double u, double v) {
		return new Color (u , 1 , v);
	}

}
