package meshes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ryTracer.Loader;

import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import Tracer.HitRecord;

public class ObjMesh extends Mesh implements Loadable{

	ArrayList<Tri> polyList = new ArrayList<Tri>();
	private boolean hasTexCoord;
	private boolean hasNormals;
	private int numVerts;
	private int numNorms;
	private int numFaces;
	private Point3d maxPoint = new Point3d(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
	private Point3d minPoint = new Point3d(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
	private Box BoundingBox;
	
	public ObjMesh(String filename, long mId){
		load(filename);
		matId = mId;
	}
	
	public void printType() {

	}
	

	public HitRecord hitTest(Point3d eye, Vector3d view, int bounces, double ior) {
		double closestDist = Double.MAX_VALUE;
		Tri closestPoly= null;
		HitRecord minHit = new HitRecord(-1, null, null, view, null, -1, -1, -1, -1);
		if (BoundingBox.hitTest(eye, view, bounces, ior).distance < 0){
			return minHit;
		}
		for(Tri currentObj : polyList){
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

	public void load(String filename) {
		File f = new File(filename);
		BufferedReader infile = null;
		try {
			infile = new BufferedReader(new FileReader(f));
		if(infile == null) {
			System.out.println( "can't open file" );
		}
		String line;
		String type;
		ArrayList<Point3d> points = new ArrayList<Point3d>();
		ArrayList<Vector3d> normals = new ArrayList<Vector3d>();
		ArrayList<double[]> uvs = new ArrayList<double[]>();
			while( (line = infile.readLine()) != null ){

					
					
					if (line.length() < 2)
						line  = infile.readLine();
					// Read string into a stream for processing
					//input.str(line);
					//input >> type;   
					type = line;
					
					if( type.substring(0,2).equalsIgnoreCase("v ")){
						numVerts++;
						points.add(Loader.objPoint(type.substring(2)));
					}
			  
					else if( type.substring(0,2).equalsIgnoreCase("vt")){
						hasTexCoord = true;
						uvs.add(Loader.objUV(type.substring(3)));
					}
			  
					else if(type.substring(0,2).equalsIgnoreCase("vn")){
						hasNormals = true;
						normals.add(Loader.objVector(type.substring(3)));
						numNorms++;
					}	
			  
					else if( type == "vp"){
						//cout << "P Vertex found";
						//input >> x >> y;
						//cout << " " << x << ", " << y << ", "  << z << endl;
					}
			  
					else if(  type.substring(0,2).equalsIgnoreCase("f ")){
						if (type.substring(2).split(" ").length==4){
							Poly p = Loader.objFace(type.substring(2), points, normals, uvs, true);
							polyList.add(p.tri1);
							polyList.add(p.tri2);
							
						}
						else{
							polyList.add(Loader.objFace(type.substring(2), points, normals, uvs));
						}
						numFaces++;
					}
			}
			testMaxMin(points);
		}
		catch (FileNotFoundException  e) {
			e.printStackTrace();
			}   
		catch (IOException e1) {
			e1.printStackTrace();
		}
		finally{
			try {
				infile.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	private void testMaxMin(ArrayList<Point3d> points) {
		for(Point3d point:points){
			if(point.x < minPoint.x) minPoint.x = point.x;
			if(point.y < minPoint.y) minPoint.y = point.y;
			if(point.z < minPoint.z) minPoint.z = point.z;
			if(point.x > maxPoint.x) maxPoint.x = point.x;
			if(point.y > maxPoint.y) maxPoint.y = point.y;
			if(point.z > maxPoint.z) maxPoint.z = point.z;
		}
		System.out.println("Max: " + maxPoint + " Min: " + minPoint);
		BoundingBox = new Box(maxPoint, minPoint);
	}
	

}
