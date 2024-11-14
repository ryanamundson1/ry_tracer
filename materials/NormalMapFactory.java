package materials;
import java.util.HashMap;

public class NormalMapFactory implements MaterialFactory{

	public Material createMaterial(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("normalmap"))
			return new NormalMap( Long.valueOf(args.get("id")));
		else
			return null;
	}

}