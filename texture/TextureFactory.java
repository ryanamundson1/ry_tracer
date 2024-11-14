package texture;

import java.util.HashMap;

public abstract class TextureFactory {

	public abstract Texture createTexture(String name, HashMap<String, String> args);

}
