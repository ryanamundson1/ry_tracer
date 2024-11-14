package meshes;

import java.util.HashMap;

import ryTracer.Loader;
public class BoxFactory  implements MeshFactory{
	
public Mesh createMesh(String meshName, HashMap<String,String> args) {
	
	if(meshName.equalsIgnoreCase("box"))
		return new Box( Loader.loadPoint3d(args, "minPoint"),Loader.loadPoint3d(args, "maxPoint"), Long.valueOf(args.get("matId")));
	else
		return null;
	}
}
