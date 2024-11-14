package ryTracer;

import java.util.HashMap;

public class PerspectiveCameraFactory implements CameraFactory {

	public Camera createCamera(String name, HashMap<String, String> args) {
		if(name.equalsIgnoreCase("perspectiveCamera"))
			return new PerpectiveCamera(Loader.loadPoint3d(args, "eye"),Loader.loadPoint3d(args, "lookat"),Loader.loadVector3d(args, "up"), Double.valueOf(args.get("fov")), Double.valueOf(args.get("f_stop")),Double.valueOf(args.get("focal_length")),Integer.valueOf(args.get("dof_samples")) );
		else
			return null;
	}

}
