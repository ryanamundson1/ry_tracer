package materials;

import java.util.HashMap;

import ryTracer.Loader;

public class GlassFactory implements MaterialFactory{

	public Material createMaterial(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("glass"))
			return new Glass(Loader.loadColor(args, "diffuse"),Loader.loadColor(args, "opacity"),Loader.loadColor(args, "specular"),Double.valueOf(args.get("roughness")), Long.valueOf(args.get("id")),
					Double.valueOf(args.get("ks")), Double.valueOf(args.get("kd")), Double.valueOf(args.get("kr")), Double.valueOf(args.get("filter")), Double.valueOf(args.get("ior")));
		else
			return null;
	}

}