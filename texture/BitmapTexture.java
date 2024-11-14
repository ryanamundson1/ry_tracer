package texture;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import filters.BiLinearFilter;

import ryTracer.Color;
import vector.utilities.Point2d;

public class BitmapTexture extends Texture {
	final int BIT_MAP_CONST = 255;
	
	String file_name;
	BufferedImage img;
	WritableRaster wr;
	int h;
	int w;
	
	public BitmapTexture(String file_name, double su, double sv){
		File f = new File(file_name);
		u_scale = su;
		v_scale = sv;
		try {
			img = ImageIO.read(f);
			wr = img.getRaster();
			h = img.getHeight()-1;
			w = img.getWidth()-1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("" + h + " " + w);
	}

	@Override
	public Color getTexel(double u, double v) {
		//Color returnColor = new Color(0,0,0);
		if (u < 0){u = 1-u;}
		if (v < 0){v = 1-v;}
		double du = h*u*u_scale;
		double dv = w*v*v_scale;
		
		double dh = Math.floor(du%h);
		double dw = Math.floor(dv%w);
		
		double[] pixelArray = new double[3];
		wr.getPixel((int)dh ,(int)dw ,pixelArray);
		Color a = new Color(pixelArray[0]/BIT_MAP_CONST,pixelArray[1]/BIT_MAP_CONST,pixelArray[2]/BIT_MAP_CONST );
		wr.getPixel((int)Math.abs((du+1)%h) ,(int)dw ,pixelArray);
		Color b = new Color(pixelArray[0]/BIT_MAP_CONST,pixelArray[1]/BIT_MAP_CONST,pixelArray[2]/BIT_MAP_CONST );
		wr.getPixel((int)dh ,(int)Math.abs((dw+1)%w) ,pixelArray);
		Color c = new Color(pixelArray[0]/BIT_MAP_CONST,pixelArray[1]/BIT_MAP_CONST,pixelArray[2]/BIT_MAP_CONST );
		wr.getPixel((int)(int)Math.abs((du+1)%h) ,(int)Math.abs((dw+1)%w) ,pixelArray);
		Color d = new Color(pixelArray[0]/BIT_MAP_CONST,pixelArray[1]/BIT_MAP_CONST,pixelArray[2]/BIT_MAP_CONST );
		return BiLinearFilter.Filter(a,b,c,d, new Point2d((du - Math.floor(du)),(dv- Math.floor(dv))));
	}

}
