package meshes;

import java.util.ArrayList;

import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import Tracer.HitRecord;

public class Box extends Mesh{

	ArrayList<Poly> faces = new ArrayList<Poly>();
	
	public void printType() {
		for(Poly face: faces){
			face.printType();
		}
	}
	
	public Box(Point3d minPoint, Point3d maxPoint){
		Point3d vert1 = new Point3d(maxPoint.x, maxPoint.y, maxPoint.z);
		Point3d vert2 = new Point3d(maxPoint.x, maxPoint.y, minPoint.z);
		Point3d vert3 = new Point3d(maxPoint.x, minPoint.y, maxPoint.z);
		Point3d vert4 = new Point3d(maxPoint.x, minPoint.y, minPoint.z);
		Point3d vert5 = new Point3d(minPoint.x, maxPoint.y, maxPoint.z);
		Point3d vert6 = new Point3d(minPoint.x, maxPoint.y, minPoint.z);
		Point3d vert7 = new Point3d(minPoint.x, minPoint.y, maxPoint.z);
		Point3d vert8 = new Point3d(minPoint.x, minPoint.y, minPoint.z);
		Vector3d norm1 = new Vector3d(-1,0,0);
		Vector3d norm2 = new Vector3d(0,-1,0);
		Vector3d norm3 = new Vector3d(0,0,-1);
		Vector3d norm4 = new Vector3d(1,0,0);
		Vector3d norm5 = new Vector3d(0,1,0);
		Vector3d norm6 = new Vector3d(0,0,1);
		
		faces.add( new Poly(vert1, vert2, vert3, vert4, norm4, norm4, norm4, norm4));
		faces.add( new Poly(vert5, vert6, vert1, vert2, norm5, norm5, norm5, norm5));
		faces.add( new Poly(vert3, vert4, vert7, vert8, norm2, norm2, norm2, norm2));
		faces.add( new Poly(vert1, vert3, vert5, vert7, norm6, norm6, norm6, norm6));
		faces.add( new Poly(vert2, vert4, vert6, vert8, norm3, norm3, norm3, norm3));
		faces.add( new Poly(vert8, vert6, vert7, vert5, norm1, norm1, norm1, norm1));
		
	}

	public Box(Point3d minPoint, Point3d maxPoint, Long mId) {
		Point3d vert1 = new Point3d(maxPoint.x, maxPoint.y, maxPoint.z);
		Point3d vert2 = new Point3d(maxPoint.x, minPoint.y, maxPoint.z);
		Point3d vert3 = new Point3d(maxPoint.x, maxPoint.y, minPoint.z);
		Point3d vert4 = new Point3d(maxPoint.x, minPoint.y, minPoint.z);
		Point3d vert5 = new Point3d(minPoint.x, maxPoint.y, maxPoint.z);
		Point3d vert6 = new Point3d(minPoint.x, minPoint.y, maxPoint.z);
		Point3d vert7 = new Point3d(minPoint.x, maxPoint.y, minPoint.z);
		Point3d vert8 = new Point3d(minPoint.x, minPoint.y, minPoint.z);
		Vector3d norm1 = new Vector3d(-1,0,0);
		Vector3d norm2 = new Vector3d(0,-1,0);
		Vector3d norm3 = new Vector3d(0,0,-1);
		Vector3d norm4 = new Vector3d(1,0,0);
		Vector3d norm5 = new Vector3d(0,1,0);
		Vector3d norm6 = new Vector3d(0,0,1);
		
		faces.add( new Poly(vert1, vert2, vert3, vert4, norm4, norm4, norm4, norm4));
		faces.add( new Poly(vert5, vert6, vert1, vert2, norm5, norm5, norm5, norm5));
		faces.add( new Poly(vert3, vert4, vert7, vert8, norm2, norm2, norm2, norm2));
		faces.add( new Poly(vert1, vert3, vert5, vert7, norm6, norm6, norm6, norm6));
		faces.add( new Poly(vert2, vert4, vert6, vert8, norm3, norm3, norm3, norm3));
		faces.add( new Poly(vert8, vert7, vert6, vert5, norm1, norm1, norm1, norm1));
		matId = mId;
	}

	public HitRecord hitTest(Point3d eye, Vector3d view, int bounces, double ior) {
		double closestDist = Double.MAX_VALUE;
		Poly closestPoly= null;
		HitRecord minHit = new HitRecord(-1, null, null, view, null, -1, -1, -1, -1);
		
		for(Poly currentObj : faces){
			HitRecord hit = currentObj.hitTest(eye, view, bounces, ior);
			Double dist /*t-value from parametic eq*/ = hit.distance;
			if((closestPoly==null || dist<closestDist) && dist > MINIMUM){
				closestDist = dist;
				closestPoly = currentObj;
				minHit = hit;
			}
		
		}
		return minHit;
	}

}
