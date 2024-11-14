package Tracer;

import java.util.ArrayList;

import materials.Material;
import meshes.Mesh;

import ryTracer.Camera;
import ryTracer.Color;
import ryTracer.Jitterer;
import ryTracer.Scene;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;

public class Tracer {
	static public final double BIAS = .0000001;
	public final static int MAX_BOUNCES = 10;
	static public final double AIR_IOR = 1.03;
	
	static Camera  cam =  null;
	
	static Point3d eye = null;
	static Vector3d view = null;
	static Vector3d cam_up = null;
	static Vector3d cam_right = null;
	static double fov;
	static double aspect;
	static double f_stop;
	static double focal_distance;
	static double f_diam;
	static int dofSamples;
	static Jitterer jitter;
	
	static double cofc;
	static double hyperd;
	
	static double h;
	static double w;
	
	static double rectFOVh;
	static double rectFOVw;
	
	public Tracer(Camera camera, double asp) {
		cam = camera;
		eye = cam.getEye();
		view = VectorMath.subtract( cam.getLookat(), eye);
		focal_distance = view.length();
		view.normalize();
		
		aspect = asp;
		fov = camera.getFOV();
		f_stop = camera.getF_STOP();
		
		f_diam = fov/f_stop;
		dofSamples = camera.getDOF();
		
		cofc = camera.getFocal()/1000;
		
		//double frame = fov*aspect;
		
		
		//this might have to be reversed
	    cam_right = VectorMath.cross(  view, cam.getUp());

        cam_up = VectorMath.cross(  view, cam_right);
        
        h = focal_distance;//Math.tan(Math.toRadians(fov)/2);
		w = h * aspect; 
		rectFOVh = (2*Math.atan(Math.toRadians(fov))* focal_distance); 
		rectFOVw = (2*Math.atan(Math.toRadians(fov)*aspect) * focal_distance); 
        jitter = new Jitterer(cofc*f_diam);
	}
	public static Color Trace(double d, double e) {
		
		
		Vector3d current_vec = VectorMath.add(VectorMath.multiply((rectFOVh*(1-2*d)/2), cam_up) , VectorMath.multiply(rectFOVw*(1-2*e)/2,cam_right));
		//current_vec = VectorMath.add(
		
		if (dofSamples <= 0){
			Point3d new_eye = VectorMath.add( current_vec, VectorMath.add(VectorMath.multiply(focal_distance,view), eye));
			Vector3d new_vec = VectorMath.subtract( new_eye, eye );
			new_vec.normalize();
			return Trace(eye, new_vec, 0);
		}
		else {
			Color myColor = new Color(0,0,0); 
			for (int i = 0; i < dofSamples; i++){
				Point3d focal_point = VectorMath.add( current_vec, VectorMath.add(VectorMath.multiply(focal_distance,view), eye));
				Vector3d newVec = VectorMath.add(VectorMath.multiply(jitter.getJitter(), cam_up) , VectorMath.multiply(jitter.getJitter(),cam_right));
				Point3d jitter_eye = VectorMath.add(eye, newVec);
				Vector3d new_vec = VectorMath.subtract( focal_point, jitter_eye );
				new_vec.normalize();
				myColor = myColor.add(Trace(jitter_eye, new_vec, 0));
				
				}
			myColor.divide(dofSamples);
			return myColor;
			}
		
	}
	public static Color Trace(Point3d eye, Vector3d view, int bounces) {
		ArrayList<Mesh> meshes = Scene.getObjects();
		double closestDist = Double.MAX_VALUE;
		Mesh closestMesh= null;
		HitRecord minHit = null;
		for(Mesh currentObj : meshes){
			HitRecord hit = currentObj.hitTest(eye, view, bounces, AIR_IOR);
			Double dist /*t-value from parametic eq*/ = hit.distance;
			if((closestMesh==null || dist<closestDist) && dist > BIAS){
				closestDist = dist;
				closestMesh = currentObj;
				minHit = hit;
			}
		}
		if(closestDist < Double.MAX_VALUE){
			if (closestMesh.getMatId() < 1)return closestMesh.getColor();
			else{
				ArrayList<Material> mats = Scene.getMats();
					for (Material mat : mats){
						if (mat.getId()==closestMesh.getMatId()){
							Color c = mat.calcColor(minHit);
							return c;
					}
				}
				return new Color(0,0,0);
			}
		
		
		}
		else return new Color(0,0,0);
	}
}
