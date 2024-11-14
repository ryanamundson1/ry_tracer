package ryTracer;

import java.util.ArrayList;

import meshes.Mesh;
import Tracer.HitRecord;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;

public class DirectionalLight extends Light {
	Vector3d direction;
	Color color;
	double intensity;
	ArrayList<Mesh> mObjs = new ArrayList<Mesh>();
	
	public DirectionalLight(Vector3d dir, Color col, double i, boolean sdw){
		direction = dir;
		color = col;
		intensity = i;
		shadows = sdw;
		mObjs = Scene.getObjects();
	}

	public void printType() {
		System.out.println("DirectionalLight: Direction= "+ direction+ " Color=" + color + " Intensity=" +intensity);

	}

	public Vector3d getVector(HitRecord hit) {
		return VectorMath.invert(direction);
	}
	
public double getIntensity(HitRecord hit) {
		
			for(Mesh currentObj : mObjs){
				HitRecord hit2 = currentObj.hitTest(hit.hitPoint, getVector(hit), hit.bounces, hit.ior);
				if (hit2.distance > 0) return 0; 
			}
			return intensity;
}

	@Override
	public Color getColor() {
		return color;
	}

}
