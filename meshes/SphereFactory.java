package meshes;

import java.util.HashMap;

import ryTracer.Loader;


public class SphereFactory implements MeshFactory {
	
	public Mesh createMesh(String meshName, HashMap<String,String> args) {
		
		if(meshName.equalsIgnoreCase("sphere"))
			return new Sphere(Double.valueOf(args.get("radius")), Loader.loadPoint3d(args, "location"),Loader.loadColor(args, "color")
					,Long.valueOf(args.get("matId")) );
			else return null;
	}

}
