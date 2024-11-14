package ryTracer;

import java.util.ArrayList;
import java.util.HashMap;

import meshes.Poly;
import meshes.Tri;

import texture.UVMapper;
import texture.UVMapperFactory;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;

public class Loader {
	
	public static Point3d loadPoint3d(HashMap<String, String> map, String attr){
		String[] pointString = (map.get(attr)).split(",");
		return new Point3d(Double.valueOf(pointString[0]),Double.valueOf(pointString[1]),Double.valueOf(pointString[2]) );
	}
	
	public static Color loadColor(HashMap<String, String> map, String attr){
		String[] pointString = (map.get(attr)).split(",");
		return new Color(Double.valueOf(pointString[0]),Double.valueOf(pointString[1]),Double.valueOf(pointString[2]) );
	}
	
	public static Vector3d loadVector3d(HashMap<String, String> map, String attr){
		String[] pointString = (map.get(attr)).split(",");
		return new Vector3d(Double.valueOf(pointString[0]),Double.valueOf(pointString[1]),Double.valueOf(pointString[2]) );
	}

	public static Point3d objPoint(String string) {
		String[] pointString = string.split(" ");
		return new Point3d(Double.valueOf(pointString[0]),Double.valueOf(pointString[2]),Double.valueOf(pointString[1]) );
		
	}

	public static Poly objFace(String string, ArrayList<Point3d> points, ArrayList<Vector3d> normals, ArrayList<double[]> uvs, boolean isPoly) {
		String[] faceString = string.split(" ");
			String[][] vertslist = new String[4][3];
			for (int j =0 ; j < 3; j++){
				int partnum = 0;
					String[] partslist1 = faceString[0].split("/");
					String[] partslist2 = faceString[1].split("/");
					String[] partslist3 = faceString[2].split("/");
					String[] partslist4 = faceString[3].split("/");
					vertslist[0][j] = partslist1[partnum];
					vertslist[1][j] = partslist2[partnum];
					vertslist[2][j] = partslist3[partnum];
					vertslist[3][j] = partslist4[partnum];
					partnum++;
			}
		//for(int j = 0; j <4; j++){
		//	System.out.println("" + vertslist[j][0]+ " "+ vertslist[j][1]+ " "+ vertslist[j][2]);
		//}
		
		return new Poly(points.get(Integer.valueOf(vertslist[0][0])-1), points.get(Integer.valueOf(vertslist[1][0])-1), points.get(Integer.valueOf(vertslist[2][0])-1), points.get(Integer.valueOf(vertslist[3][0])-1),
				normals.get(Integer.valueOf(vertslist[0][2])-1), normals.get(Integer.valueOf(vertslist[1][2])-1), normals.get(Integer.valueOf(vertslist[2][2])-1), normals.get(Integer.valueOf(vertslist[3][2])-1),
				uvs.get(Integer.valueOf(vertslist[0][1])-1), uvs.get(Integer.valueOf(vertslist[1][1])-1), uvs.get(Integer.valueOf(vertslist[2][1])-1), uvs.get(Integer.valueOf(vertslist[3][1])-1));
	}

	public static double[] objUV(String string) {
		String[] pointString = string.split(" ");
		double[] a = new double[2];
		a[0] = Double.valueOf(pointString[0]);
		a[1] = Double.valueOf(pointString[1]);
		return a;
	}

	public static Vector3d objVector(String string) {
		String[] pointString = string.split(" ");
		return new Vector3d(Double.valueOf(pointString[0]),Double.valueOf(pointString[2]),Double.valueOf(pointString[1]) );
		
	}

	public static Tri objFace(String string, ArrayList<Point3d> points, ArrayList<Vector3d> normals, ArrayList<double[]> uvs) {
		String[] faceString = string.split(" ");
		String[][] vertslist = new String[3][3];
		for (int j =0 ; j < 3; j++){
			int partnum = 0;
				String[] partslist1 = faceString[0].split("/");
				String[] partslist2 = faceString[1].split("/");
				String[] partslist3 = faceString[2].split("/");
				vertslist[0][j] = partslist1[partnum];
				vertslist[1][j] = partslist2[partnum];
				vertslist[2][j] = partslist3[partnum];
				partnum++;
		}
	return new Tri(points.get(Integer.valueOf(vertslist[1][0])-1), points.get(Integer.valueOf(vertslist[0][0])-1), points.get(Integer.valueOf(vertslist[2][0])-1),
			normals.get(Integer.valueOf(vertslist[1][2])-1), normals.get(Integer.valueOf(vertslist[0][2])-1), normals.get(Integer.valueOf(vertslist[2][2])-1),
			uvs.get(Integer.valueOf(vertslist[1][1])-1), uvs.get(Integer.valueOf(vertslist[0][1])-1), uvs.get(Integer.valueOf(vertslist[2][1])-1));
	}

}
