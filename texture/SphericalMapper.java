package texture;

import ryTracer.Color;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;

public class SphericalMapper extends UVMapper {

	public SphericalMapper(){
		type = "spherical";
	}
	@Override
	public double[] getUVs(Point3d pt, Vector3d top, double d) {
		
		double[] uvs = {0,0};
		double dist  = (pt.x*pt.x + pt.y*pt.y + pt.z*pt.z);
		if (dist > 0){
			if (pt.x !=0 && pt.y != 0 ){
				uvs[0] = 0.5 * (1-Math.atan2(pt.x, pt.y) * (1/Math.PI));
				}
	
			uvs[1]= Math.acos(pt.z/ (dist) )* (1/Math.PI);
		}
		return uvs;
	}

	@Override
	public Color getTexel(double u, double v) {
		return null;
	}

}
