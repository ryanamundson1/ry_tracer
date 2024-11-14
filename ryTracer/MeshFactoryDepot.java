package ryTracer;

import java.util.ArrayList;
import java.util.HashMap;

import meshes.Mesh;
import meshes.MeshFactory;

public class MeshFactoryDepot {
	static ArrayList<MeshFactory> mFactory;
	
	MeshFactoryDepot(){
		mFactory = new ArrayList<MeshFactory>();
	}
	
	public void registerFactoryDepot(MeshFactory in){
		mFactory.add(in);	
	}
	
	public Mesh createObject(String name, HashMap<String, String> args){
		Mesh newMesh = null;
		for (MeshFactory factories: mFactory){
			newMesh = factories.createMesh(name, args);
			if (newMesh != null) break;
		}
		return newMesh;
	}
}
