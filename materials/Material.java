package materials;

import java.util.ArrayList;
import java.util.HashMap;

import Tracer.HitRecord;

import ryTracer.Color;
import ryTracer.Light;
import texture.Texture;
import texture.UVMapper;

public abstract class Material {
	static public final double BIAS = .0000001;
	
	Color diffuse;
	Color opacity;
	double kD;
	long matId;
	Texture diffuseTex;
	Texture opacityTex;
	
	ArrayList<Light> lights;
	
	public abstract Color calcColor(HitRecord hit);
	
	public String toString(){
		return ("Material id=" + matId );
	}

	public void printType() {
		System.out.println(this);
		
	}
	public void setDiffuse(Texture t){
		diffuseTex = t;
	}

	public long getId() {
		return matId;
	}

}
