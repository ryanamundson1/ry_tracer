package texture;

import java.util.HashMap;

import ryTracer.Loader;

public class CheckerBoardFactory extends TextureFactory {

	public Texture createTexture(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("CheckerBoard")) return new CheckerBoardTexture(Loader.loadColor(args, "light"),Loader.loadColor(args, "dark"), Double.valueOf(args.get("scale_u")),Double.valueOf(args.get("scale_v")));
		else
			return null;
	}
}
