package meshes;

import java.util.HashMap;

public class ObjMeshFactory  implements MeshFactory{

	public Mesh createMesh(String meshName, HashMap<String, String> args) {
		if(meshName.equalsIgnoreCase("objmesh"))
			return new ObjMesh(args.get("filename"),Long.valueOf(args.get("matId")));
		else
			return null;
	}

}
