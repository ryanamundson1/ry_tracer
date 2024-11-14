package texture;

import java.util.HashMap;


public final class UVMapperFactory extends TextureFactory{

	@Override
	public UVMapper createTexture(String name, HashMap<String, String> args) {
		if (name.equalsIgnoreCase("spherical")) return new SphericalMapper();
		if (name.equalsIgnoreCase("planer")) return new PlanerMapper();
		return null;
	}
}
