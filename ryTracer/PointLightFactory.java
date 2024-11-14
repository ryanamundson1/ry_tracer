package ryTracer;

import java.util.HashMap;

public class PointLightFactory implements LightFactory {

	public Light createLight(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("pointLight") && !args.containsKey("softSamples"))
			return new PointLight(Loader.loadPoint3d(args, "location"),Loader.loadColor(args, "color"), Double.valueOf(args.get("intensity")), Boolean.valueOf(args.get("shadows")) );
		else if (name.equalsIgnoreCase("pointLight") && args.containsKey("softSamples"))
			return new PointLight(Loader.loadPoint3d(args, "location"),Loader.loadColor(args, "color"), Double.valueOf(args.get("intensity")),
					Boolean.valueOf(args.get("shadows")), Integer.valueOf(args.get("softSamples")),  Double.valueOf(args.get("spread")) );
		else
			return null;
	}

}
