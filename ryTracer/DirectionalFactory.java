package ryTracer;

import java.util.HashMap;

public class DirectionalFactory implements LightFactory {

	public Light createLight(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("directionalLight"))
			return new DirectionalLight(Loader.loadVector3d(args, "direction"),Loader.loadColor(args, "color"), Double.valueOf(args.get("intensity")),Boolean.valueOf(args.get("shadows")) );
		else
			return null;
	}

}
