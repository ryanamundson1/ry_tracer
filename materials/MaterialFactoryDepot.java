package materials;

import java.util.ArrayList;
import java.util.HashMap;


public class MaterialFactoryDepot {
	static ArrayList<MaterialFactory> mFactory;
	
	public MaterialFactoryDepot(){
		mFactory = new ArrayList<MaterialFactory>();
	}
	
	public void registerFactoryDepot(MaterialFactory in){
		mFactory.add(in);	
	}
	
	public Material createObject(String name, HashMap<String, String> args){
		Material newMaterial = null;
		for (MaterialFactory factories: mFactory){
			newMaterial = factories.createMaterial(name, args);
			if (newMaterial != null) break;
		}
		return newMaterial;
	}
}
