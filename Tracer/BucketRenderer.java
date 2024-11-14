package Tracer;

import java.awt.image.WritableRaster;

import ryTracer.Camera;
import ryTracer.Color;




public class BucketRenderer implements Runnable{
	public int height;
	public int width;
	public int offset_h;
	public int offset_w;
	double aspect;
	int bucketSize;
	WritableRaster raster;
	Camera cam;
	int superSamples;
	Tracer myTracer;
	Renderer controller;
	
	public BucketRenderer(int h, int w, int o_h, int o_w, int bSize,  double asp,  WritableRaster ras, int hbuckets, int wbuckets, Camera camera, int samples, Renderer cont){
		height = h;
		width = w;
		offset_h = o_h-bSize;
		offset_w = o_w-bSize;
		aspect = asp;
		raster = ras;
		cam = camera;
		superSamples = samples;
		bucketSize = bSize;
		controller = cont;
		
	}
	
	public void run() {
		myTracer = new Tracer(cam, aspect);
		for(int j = offset_w; j < offset_w+bucketSize; j++){
			for (int i= offset_h; i < offset_h+bucketSize; i++){
				if((i%(bucketSize)+j%(bucketSize))==0){
					System.out.print(".");
				}
				if ((i<height)&&(j<width)){
				Color c = new Color (0,0,0);
				//for simple grid sampling
				double smpl = 1/(superSamples+1);
				for (int k = 1; k <= superSamples ; k ++){ 
					for (int l = 1; l <= superSamples ; l++){ 
						c = c.add( Tracer.Trace(((i*superSamples+ (k-1))/(superSamples+k*smpl))/width, ((j*superSamples +(l-1))/(superSamples +l*smpl))/height));
					}
				}
				c.divide(superSamples*superSamples);
				c = c.clamp();
				double[] d = {c.r*Renderer.BIT_MAP_CONST, c.g*Renderer.BIT_MAP_CONST, c.b*Renderer.BIT_MAP_CONST, 1};
				writeRaster(i, j , d);
				}
			}
			
		}
		controller.decrementRunningWorkers();
		
	}
	
	public void writeRaster(int i, int j, double[] d){
		raster.setPixel(i ,j, d);
	}

}
