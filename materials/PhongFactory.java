package materials;

import java.util.HashMap;

import ryTracer.Loader;

public class PhongFactory implements MaterialFactory{

	public Material createMaterial(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("phong"))
			return new Phong(Loader.loadColor(args, "diffuse"),Loader.loadColor(args, "opacity"),Loader.loadColor(args, "specular"),Double.valueOf(args.get("roughness")), Long.valueOf(args.get("id")),
					Double.valueOf(args.get("ks")), Double.valueOf(args.get("kd")));
		else
			return null;
	}

}
