package texture;

import java.util.HashMap;

public class BitmapTextureFactory extends TextureFactory {

	public Texture createTexture(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("BitmapTexture")) return new BitmapTexture(args.get("filename"),  Double.valueOf(args.get("scale_u")),Double.valueOf(args.get("scale_v")));
		else
			return null;
	}
}