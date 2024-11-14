package ryTracer;

import java.util.HashMap;

public interface LightFactory {

	abstract Light createLight(String name, HashMap<String, String> args);

}
