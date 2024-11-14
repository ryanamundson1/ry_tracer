package materials;

import Tracer.HitRecord;
import ryTracer.Color;
import ryTracer.Light;
import ryTracer.Scene;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;

public class Lambert extends Material{
	
	Lambert(Color diff, Color op, long i, double kd){
		diffuse = diff;
		opacity = op;
		kD = kd;
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
		tempD = tempD.mult(calcDiff(hit));
		tempD = tempD.mult(kD);
		return tempD;
		
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

}
