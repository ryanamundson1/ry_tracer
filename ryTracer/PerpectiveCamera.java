package ryTracer;

import vector.utilities.Point3d;
import vector.utilities.Vector3d;

public class PerpectiveCamera extends Camera {
	
	PerpectiveCamera(Point3d e, Point3d l, Vector3d u, Double f, Double fstp, double fcl, int dof_s){
		eye = e;
		lookat = l;
		up = u;
		fov = f;
		f_stop = fstp;
		focal_length = fcl;
		dof_samples = dof_s;
	}

	void printType() {
		System.out.println("PerspectiveCamera: Location= "+ eye+ " Up=" + up +" lookat="+lookat+ " FOV=" +fov);

	}

}
