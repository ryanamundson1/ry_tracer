package meshes;

import java.util.HashMap;

import ryTracer.Loader;


public class PlaneFactory implements MeshFactory {

	public Mesh createMesh(String meshName) {
		
		if(meshName.equalsIgnoreCase("plane"))
			return new Plane();
		else
			return null;
	}
	
	public Mesh createMesh(String meshName, HashMap<String,String> args) {
		
		if(meshName.equalsIgnoreCase("plane"))
			return new Plane(Loader.loadColor(args, "color"), Loader.loadVector3d(args, "normal"), Loader.loadPoint3d(args, "origin"),Long.valueOf(args.get("matId")));
		else
			return null;
	}

}