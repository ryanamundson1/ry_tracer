package meshes;

import java.util.HashMap;


public interface MeshFactory {
	
	abstract Mesh createMesh(String meshName, HashMap<String,String> args);

}
