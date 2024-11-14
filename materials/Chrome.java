package materials;

import ryTracer.Color;
import ryTracer.Light;
import ryTracer.Scene;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;
import Tracer.HitRecord;
import Tracer.Tracer;

public class Chrome extends Material {
	
	Color specular;
	double kS;
	double kR;
	double rough;
	
	Chrome(Color diff, Color op, Color spec, double r, long i, double ks, double kd, double kr){
		diffuse = diff;
		opacity = op;
		specular = spec;
		kS= ks;
		kD = kd;
		kR = kr;
		rough = r;
		matId = i;	
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
		lights = Scene.getLights();
		if (hit.bounces  > Tracer.MAX_BOUNCES){
			return diffuse;
		}
		Vector3d ref = VectorMath.multiply(2*VectorMath.dot( hit.view,hit.normal), hit.normal);
		ref = VectorMath.subtract( hit.view, ref);
		ref.normalize();
		Point3d pt = VectorMath.add(hit.hitPoint, VectorMath.multiply(new Point3d(BIAS, BIAS,BIAS), ref));
		Color myRef = Tracer.Trace(pt, ref, hit.bounces++);
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
