package texture;

import ryTracer.Color;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;

public class PlanerMapper extends UVMapper {

	public PlanerMapper(){
		type = "planer";
	}
	@Override
	public double[] getUVs(Point3d pt, Vector3d normal, double d) {
		
		double[] uvs = new double[2];
		uvs[0] = pt.x;
		uvs[1] = pt.y;
		return uvs;
	}

	@Override
	public Color getTexel(double u, double v) {
		return null;
	}

}
