package materials;

import java.util.HashMap;

public interface MaterialFactory {

	public abstract Material createMaterial(String name, HashMap<String, String> args);

}
