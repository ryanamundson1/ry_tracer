package ryTracer;

import java.util.HashMap;

public interface CameraFactory {

	abstract Camera createCamera(String name, HashMap<String, String> args);

}
