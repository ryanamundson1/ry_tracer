package materials;

import Tracer.HitRecord;
import ryTracer.Color;
import ryTracer.Light;
import ryTracer.Scene;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;

public class Phong extends Material{
	Color specular;
	double kS;
	double rough;
	
	Phong(Color diff, Color op, Color spec, double r, long i, double ks, double kd){
		diffuse = diff;
		opacity = op;
		specular = spec;
		kS= ks;
		kD = kd;
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
		tempD = tempD.mult(calcDiff(hit));
		tempD = tempD.mult(kD);
		tempS = tempS.mult(calcSpec(hit));
		tempS = tempS.mult(kS);
		tempD = tempD.add(tempS);
		return tempD;
		
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
