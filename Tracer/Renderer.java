package Tracer;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import ryTracer.Camera;
import ryTracer.Color;

public class Renderer {
	
	public final static int BIT_MAP_CONST = 255;
	public final static int MAX_THREADS = 4;
	public final static int BUCKET_SIZE = 100;
	int runningWorkers = 0;

	ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
	int superSamples = 2;
	
	File f;
	BufferedImage img;
	ImageIO io;
	Tracer myTracer;
	
	
	public void render(int height, int width, double aspect, String bname, Camera camera, int samples){
		
		
		f = new File(bname);
		img = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
		//for (String a :ImageIO.getWriterFormatNames())
			//System.out.println(a);
		WritableRaster raster = img.getRaster();
		superSamples = samples;
		myTracer = new Tracer(camera, aspect);
		int hbuckets = (int) (height/BUCKET_SIZE)+2;
		int wbuckets = (int) (width/BUCKET_SIZE)+2;
		for(int i = 1; i <= hbuckets; i++){
			for (int j= 1; j <= wbuckets; j++){
				incrementRunningWorkers();
				this.threadPool.execute(new BucketRenderer(height, width, BUCKET_SIZE*i, BUCKET_SIZE*j, BUCKET_SIZE,  aspect,  raster, hbuckets, wbuckets, camera, superSamples, this));
			}
//		 wait for all worker threads to terminate
		waitForWorkers();
		}
		img.setData(raster);
		try {
			ImageIO.write(img, "bmp", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

	
	public void setSamples(int samp){
		superSamples = samp;
	}
	
	public synchronized void waitForWorkers()
	{
		// wait for all worker threads to terminate
		while(runningWorkers > 0) {
			try {
				wait();
			}
			catch(InterruptedException e) {
			}
		}
	}


	public synchronized void incrementRunningWorkers() {
		
		runningWorkers++;
	}


	public synchronized void decrementRunningWorkers() {
		runningWorkers--;
		if (runningWorkers == 0)
				notifyAll();
	}
}
