package materials;

import java.util.HashMap;

import ryTracer.Loader;

public class LambertFactory implements MaterialFactory{

	public Material createMaterial(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("lambert"))
			return new Lambert(Loader.loadColor(args, "diffuse"),Loader.loadColor(args, "opacity"), Long.valueOf(args.get("id")),
				 Double.valueOf(args.get("kd")));
		else
			return null;
	}

}