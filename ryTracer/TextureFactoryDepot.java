package ryTracer;

import java.util.ArrayList;
import java.util.HashMap;

import texture.Texture;
import texture.TextureFactory;

public class TextureFactoryDepot {
static ArrayList<TextureFactory> tFactory;
	
	TextureFactoryDepot(){
		tFactory = new ArrayList<TextureFactory>();
	}
	
	public void registerFactoryDepot(TextureFactory in){
		tFactory.add(in);	
	}
	
	public Texture createObject(String name, HashMap<String, String> args){
		Texture newTexture = null;
		for (TextureFactory factories: tFactory){
			newTexture = factories.createTexture(name, args);
			if (newTexture != null) break;
		}
		return newTexture;
	}
}
