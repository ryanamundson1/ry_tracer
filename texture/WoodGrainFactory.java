package texture;

import java.util.HashMap;

import ryTracer.Loader;

public class WoodGrainFactory extends TextureFactory {

	public Texture createTexture(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("WoodGrain")) return new WoodGrainTexture(Loader.loadColor(args, "light"),Loader.loadColor(args, "dark"), Double.valueOf(args.get("scale_u")),Double.valueOf(args.get("scale_v")), Double.valueOf(args.get("grain")));
		else
			return null;
	}
}
