package ryTracer;

import java.util.ArrayList;
import java.util.HashMap;

public class CameraFactoryDepot {
	static ArrayList<CameraFactory> cFactory;
	
	CameraFactoryDepot(){
		cFactory = new ArrayList<CameraFactory>();
	}
	
	public void registerFactoryDepot(CameraFactory in){
		cFactory.add(in);	
	}
	
	public Camera createObject(String name, HashMap<String, String> args){
		Camera newCamera = null;
		for (CameraFactory factories: cFactory){
			newCamera = factories.createCamera(name, args);
			if (newCamera != null) break;
		}
		return newCamera;
	}
}