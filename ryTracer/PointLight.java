package ryTracer;

import java.util.ArrayList;

import meshes.Mesh;

import Tracer.HitRecord;

import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;

public class PointLight extends Light {
	Point3d location;
	Color color;
	ArrayList<Mesh> mObjs = new ArrayList<Mesh>();
	
	public PointLight(Point3d loc, Color col, double i, boolean sdw){
		location = loc;
		color = col;
		intensity = i;
		shadows = sdw;
		mObjs = Scene.getObjects();
	}
	
	public PointLight(Point3d loc, Color col, double i, boolean sdw, int sam, double amt){
		location = loc;
		color = col;
		intensity = i;
		shadows = sdw;
		softSamples = sam;
		spread = amt;
		jitter = new Jitterer(spread);
		mObjs = Scene.getObjects();
	}

	public void printType() {
		System.out.println("PointLight: Location= "+ location+ " Color=" + color + " Intensity=" +intensity);

	}

	public Vector3d getVector(HitRecord hit) {
		return VectorMath.subtract( location, hit.hitPoint);
	}

	public Color getColor() {
		return color;
	}
	
	public double getIntensity(HitRecord hit) {
		
		double averageIntensity = 0;
		Point3d pt = VectorMath.add(hit.hitPoint, VectorMath.multiply(new Point3d(BIAS, BIAS,BIAS), hit.normal));
		Vector3d lVec = VectorMath.subtract( location, pt);
		double maxDist = lVec.length();
		lVec.normalize();
		Vector3d light_right = VectorMath.cross(  lVec, new Vector3d(0,0,1));
        Vector3d light_up = VectorMath.cross(  lVec, light_right);
        light_right.normalize();
        light_up.normalize();
		if (softSamples == 0){
		
			for(Mesh currentObj : mObjs){
				HitRecord hit2 = currentObj.hitTest(pt, lVec, hit.bounces, hit.ior);
				if (hit2.distance > 0 && hit2.distance < maxDist) return 0; 
			}
			return getFalloffItensity(hit.distance, intensity);
		}
		
		else {
			
			for (int i = 1; i < softSamples; i++){
				Vector3d newVec = VectorMath.add(VectorMath.multiply(jitter.getJitter(), light_up) , VectorMath.multiply(jitter.getJitter(),light_right));
				lVec = VectorMath.subtract( location, VectorMath.add(pt, newVec));
				maxDist = lVec.length();
				lVec.normalize();
				boolean gotHit = false;
					for(Mesh currentObj : mObjs){
						HitRecord hit2 = currentObj.hitTest(pt, lVec, hit.bounces, hit.ior);
						if (hit2.distance > 0 && hit2.distance < maxDist)
							{
							gotHit = true; 
							break;
							}	
					}
					if(gotHit == true)
						averageIntensity +=0;
					else
					averageIntensity += intensity;
					if ((i == SAMPLESHADOWS) && (averageIntensity == BIAS)){return 0;}
			}
			averageIntensity= averageIntensity/softSamples;
		}
		return getFalloffItensity(maxDist, averageIntensity);
	}
	
	double getFalloffItensity(double dist, double intensity){
		if (falloff.equalsIgnoreCase("none")){
			return intensity;
		}
		else if (falloff.equalsIgnoreCase("linear")){
			return intensity*(1/dist);
		}
		else if (falloff.equalsIgnoreCase("square")){
			return intensity*(1/(dist*dist));
		}
		else if (falloff.equalsIgnoreCase("cubic")){
			return intensity*(1/(dist*dist*dist));
		}
		else return 0;
	}


}
