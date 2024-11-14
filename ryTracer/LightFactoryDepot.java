package ryTracer;

import java.util.ArrayList;
import java.util.HashMap;

public class LightFactoryDepot {
	static ArrayList<LightFactory> lFactory;
	
	LightFactoryDepot(){
		lFactory = new ArrayList<LightFactory>();
	}
	
	public void registerFactoryDepot(LightFactory in){
		lFactory.add(in);	
	}
	
	public Light createObject(String name, HashMap<String, String> args){
		Light newLight = null;
		for (LightFactory factories: lFactory){
			newLight = factories.createLight(name, args);
			if (newLight != null) break;
		}
		return newLight;
	}
}
