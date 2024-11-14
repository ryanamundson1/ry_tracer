package texture;

import java.util.HashMap;

import ryTracer.Loader;

public class UVTextureFactory extends TextureFactory {

	public Texture createTexture(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("UVTexture")) return new UVTexture();
		else
			return null;
	}
}
