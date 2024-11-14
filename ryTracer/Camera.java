package ryTracer;

import vector.utilities.Point3d;
import vector.utilities.Vector3d;

public abstract class Camera {
	
	Point3d eye = null;
	Point3d lookat = null;
	Vector3d up = null; 
	double fov;
	double f_stop;
	double focal_length;
	int dof_samples = 0;
	
	abstract void printType();
	
	public Point3d getEye(){
		return eye;
	}
	public Point3d getLookat(){
		return lookat;
	}
	public Vector3d getUp(){
		return up;
	}
	public double getFOV(){
		return fov;
	}
	public int getDOF(){
		return dof_samples;
	}
	public double getF_STOP(){
		return f_stop;
	}
	public double getFocal(){
		return focal_length;
	}
}
