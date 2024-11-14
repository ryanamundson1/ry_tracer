package materials;

import java.util.HashMap;

import ryTracer.Loader;

public class DiffuseChromeFactory implements MaterialFactory{

	public Material createMaterial(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("diffuseChrome"))
			return new DiffuseChrome(Loader.loadColor(args, "diffuse"),Loader.loadColor(args, "opacity"),Loader.loadColor(args, "specular"),Double.valueOf(args.get("roughness")), Long.valueOf(args.get("id")),
					Double.valueOf(args.get("ks")), Double.valueOf(args.get("kd")), Double.valueOf(args.get("kr")), Integer.valueOf(args.get("samples")), Double.valueOf(args.get("spread")));
		else
			return null;
	}

}