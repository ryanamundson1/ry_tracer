package meshes;

import ryTracer.Color;
import texture.UVMapper;

public abstract class Mesh implements Hittable{
	public final double MINIMUM = 0.000001; 
	Color color = null;
	long matId = 0;
	UVMapper map;
	
	public abstract void printType();
	
	public Color getColor() {return color;}
	public long getMatId() {return matId;}
	public void setMap(UVMapper u){this.map = u;}
	
}
