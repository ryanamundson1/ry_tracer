package materials;

import ryTracer.Color;
import ryTracer.Jitterer;
import ryTracer.Light;
import ryTracer.Scene;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;
import Tracer.HitRecord;
import Tracer.Tracer;

public class DiffuseChrome extends Material {
	
	Color specular;
	double kS;
	double kR;
	double rough;
	double diffuse_spread;
	int diffuse_rays;
	Jitterer jitter;
	
	DiffuseChrome(Color diff, Color op, Color spec, double r, long i, double ks, double kd, double kr, int rays, double spread){
		diffuse = diff;
		opacity = op;
		specular = spec;
		kS= ks;
		kD = kd;
		kR = kr;
		rough = r;
		matId = i;	
		diffuse_rays = rays;
		diffuse_spread= spread;
		jitter = new Jitterer(spread);
		}

	@Override
	public Color calcColor(HitRecord hit) {
		Color tempD;
		if (diffuseTex == null){
			tempD = diffuse.clone();
		}
		else
			tempD = diffuseTex.getTexel(hit.u, hit.v);
		Color tempS = specular.clone();
		tempS = tempS.mult(calcSpec(hit));
		tempD = tempD.mult(calcDiff(hit).mult(1 - kR));
		Color refPart  = calcRef(hit).mult(kR);
		tempD = tempD.add(refPart);
		tempD = tempD.mult(kD);
		tempS = tempS.mult(kS);
		tempD = tempD.add(tempS);
		return tempD;
	}
	
	Color calcRef(HitRecord hit) {
		Vector3d ref = VectorMath.multiply(2*VectorMath.dot( hit.view,hit.normal), hit.normal);
		ref = VectorMath.subtract( hit.view, ref);
		ref.normalize();
		Color myRef = new Color(0,0,0);
		if (hit.bounces  > Tracer.MAX_BOUNCES){
			return diffuse;
		}
		
		Vector3d cross_up = ref;
		Vector3d cross_right = VectorMath.cross(ref, cross_up);
		Vector3d cross_left = VectorMath.cross(ref, cross_right);
		hit.bounces++;
		for (int i = 0; i < diffuse_rays; i++){
			Vector3d newVec = VectorMath.add(VectorMath.add(VectorMath.multiply(jitter.getJitter(), cross_left) , VectorMath.multiply(jitter.getJitter(),cross_right)), ref);
			Point3d pt = VectorMath.add(hit.hitPoint, VectorMath.multiply(new Point3d(BIAS, BIAS,BIAS), hit.normal));
			newVec.normalize();
			Point3d jitter_eye = VectorMath.add(pt, newVec);
			Vector3d new_vec = VectorMath.subtract( jitter_eye, pt );
			new_vec.normalize();
			HitRecord hit2 = hit.object.hitTest(pt, new_vec, hit.bounces, hit.ior);
			if (hit.object.equals(hit2.object) && hit2.distance > 0){
				//System.out.println("Hit self");
				myRef  = myRef.add(diffuse); 
				}
			else myRef = myRef.add(Tracer.Trace(pt, new_vec, hit.bounces));
			
			}
		myRef.divide(diffuse_rays);
		//myRef = Tracer.Trace(pt, ref, hit.bounces++);
		return myRef;
	}

	Color calcSpec(HitRecord hit){
		lights = Scene.getLights();
		Color myColor = new Color(0,0,0);
		//Color tempS = specular.clone();
		for(Light l :lights){
			Vector3d nL = l.getVector(hit);
			Vector3d half = VectorMath.add (nL , hit.view);
			half.normalize();
			Color tempS = l.getColor();
			double lambda= Math.max(VectorMath.dot(half, hit.normal), 0)*l.getIntensity(hit);
			tempS = tempS.mult(Math.pow(lambda, 1/rough));
			myColor = myColor.add(tempS);
		}
		//myColor.clamp();
		return myColor;
	}
	
	Color calcDiff(HitRecord hit){
		lights = Scene.getLights();
		Color myColor = new Color(0,0,0);
		//Color tempD = diffuse.clone();
		for(Light l :lights){
			Vector3d nL = l.getVector(hit);
			nL.normalize();
			double lambda;
			if (l.getShadows()) lambda = VectorMath.dot(hit.normal, nL)*l.getIntensity(hit);
			else lambda = VectorMath.dot(hit.normal, nL)*l.getIntensity();
			lambda = Math.max(0, lambda);
			Color lightCol = l.getColor();
			myColor = myColor.add(lightCol.mult(lambda));
		}
		//myColor = myColor.clamp();
		return myColor;
	}
	
	public String toString(){
		return ("Material id=" + matId + ": diffuse=" + diffuse + " opacity=" + opacity + " specular=" + specular + " rough=" + rough);
	}


}
