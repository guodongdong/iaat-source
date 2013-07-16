package com.iaat.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.iaat.share.ErrorCode;
import com.iaat.share.SystemConfig;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

public class ImageUtils {
	private final static Log log = Log.getLogger(ImageUtils.class);

	public final static String REGIX = ".*.{32}(?:/|\\\\)[^_]+_\\d+_\\d+_\\d+\\..*";
	private ImageUtils() {
	}

	/**
	 * 
	 * ImageProportionZoom()   
	 * 
	 * @param srcPath 原图路径
	 * @param destPath  目标图路径
	 * @param width  目标宽度
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean imageProportionZoom(String srcPath, String destPath, int width,int height) {
		log.info("[ImageUtils.imageProporrionZoom] [begin parameters srcPath = {0},destPath={1},width={2}]",srcPath,destPath,width);
//		Image srcImage = null;
		boolean result=false;
		int imageWidth = 0;
		int imageHeight = 0;
		File _file = new File(srcPath);
		_file.setReadOnly();

		String fileSuffix = _file.getName().substring((_file.getName().indexOf(".") + 1), (_file.getName().length()));
		if(null == destPath) {
			log.warn("[ImageUtils.imageProporrionZoom] [parameters destPath is null]");
			return false;
		}
		if(destPath.indexOf(SystemConfig.getSystemConfig(SystemConfig.FILEUPLOADPATH)) < 0) {
			log.warn("[ImageUtils.imageProporrionZoom] [Incorrect path format]");
			return false;
		}
		if(!destPath.matches(REGIX)) {
			log.warn("[ImageUtils.imageProporrionZoom] [Incorrect path format]");
			return false;
		}
		File destFile = new File(destPath);
		try {
//			srcImage = ImageIO.read(_file);
//			// 得到图片的原始大小， 以便按比例压缩。
//			imageWidth = srcImage.getWidth(null);
//			imageHeight = srcImage.getHeight(null);
//			double proportionZoom = imageWidth * 1.0 / imageHeight;
//			int heigth = (int) Math.round(width / proportionZoom);
//			// 构建图片对象
//			BufferedImage _image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
//			// 绘制缩小后的图
//			_image.getGraphics().drawImage(srcImage, 0, 0, width, heigth, null);
//			// 输出到文件流
//			ImageIO.write(_image, fileSuffix, destFile);
			
			BufferedImage bufferedImage = ImageIO.read(_file);
			// 得到图片的原始大小， 以便按比例压缩。
			imageWidth = bufferedImage.getWidth(null);
			imageHeight = bufferedImage.getHeight(null);
			double proportionZoom = imageHeight * 1.0 / imageWidth;
			float scale = width * 1.0f / imageWidth;
			height = (int) Math.round(width*proportionZoom*1f);
			if (scale > 1f) {
				bufferedImage = draw(bufferedImage, scale, scale);
			}else if(scale > 0 && scale < 1f){
				bufferedImage = shrinkGraphics(bufferedImage, width, height);
			}
			// 输出到文件流
			ImageIO.write(bufferedImage, fileSuffix, destFile);
			
			result=true;
		}catch (IOException e) {
			log.error(e.getMessage());
		}
		return result;
	}
	
	public static BufferedImage shrinkGraphics(BufferedImage bIamge,int width,int height){
		Image iamge = bIamge.getScaledInstance(width, height,Image.SCALE_SMOOTH);
		
		//首先创建一个BufferedImage变量，因为ImageIO写图片用到了BufferedImage变量。
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
		//再创建一个Graphics变量，用来画出来要保持的图片，及上面传递过来的Image变量
		Graphics g = bi.getGraphics();
		g.drawImage(iamge, 0, 0, null);
		g.dispose();
		return bi;
	}

	public static BufferedImage draw(BufferedImage img, float k1, float k2) { 
		BufferedImage image=null;
		if(k1 <1 || k2<1) {
			image=shrink(img,  k1,  k2); 
		}else if(k1 >1 || k2>1){
			image=amplify(img,  k1,  k2);
		}else{
			log.error("[ImageUtils.draw] [param is error]");
			throw new UncheckedException("param is error", ErrorCode.BIZ_ERROR);
		}
		return image;

	}  

	public static BufferedImage amplify(BufferedImage img, float k1, float k2){
//		float ii = 1/k1;     
//		float jj = (1/k2);  
//        int dd = (int)(ii*jj);   
		int imgType = img.getType();  
		int w = img.getWidth();      
		int h = img.getHeight();     
		int m = Math.round(k1*w);    
		int n = Math.round(k2*h);    
		int[] pix = new int[w*h];  
		pix = img.getRGB(0, 0, w, h, pix, 0, w);  
		/*System.out.println(w + " * " + h); 
        System.out.println(m + " * " + n);*/  
		int[] newpix = new int[m*n];  

		for(int j=0; j<h-1; j++){  
			for(int i=0; i<w-1; i++) {  
				int x0 = Math.round(i*k1);  
				int y0 = Math.round(j*k2);  
				int x1, y1;  
				if(i == w-2) {  
					x1 = m-1;  
				} else {  
					x1 = Math.round((i+1)*k1);  
				}                 
				if(j == h-2) {  
					y1 = n-1;  
				} else {  
					y1 = Math.round((j+1)*k2);  
				}                 
				int d1 = x1 - x0;  
				int d2 = y1 - y0;  
				if(0 == newpix[y0*m + x0]) {  
					newpix[y0*m + x0] =  pix[j*w+i];  
				}  
				if(0 == newpix[y0*m + x1]) {  
					if(i == w-2) {  
						newpix[y0*m + x1] = pix[j*w+w-1];  
					} else {  
						newpix[y0*m + x1] =  pix[j*w+i+1];  
					}                     
				}  
				if(0 == newpix[y1*m + x0]){  
					if(j == h-2) {  
						newpix[y1*m + x0] = pix[(h-1)*w+i];  
					} else {  
						newpix[y1*m + x0] =  pix[(j+1)*w+i];  
					}                     
				}  
				if(0 == newpix[y1*m + x1]) {  
					if(i==w-2 && j==h-2) {  
						newpix[y1*m + x1] = pix[(h-1)*w+w-1];  
					} else {  
						newpix[y1*m + x1] = pix[(j+1)*w+i+1];  
					}                     
				}  
				int r, g, b;  
				float c;  
				ColorModel cm = ColorModel.getRGBdefault();               
				for(int l=0; l<d2; l++) {  
					for(int k=0; k<d1; k++) {  
						if(0 == l) {  
							if(j<h-1 && newpix[y0*m + x0 + k] == 0) {  
								c = (float)k/d1;  
								r = cm.getRed(newpix[y0*m + x0]) + (int)(c*(cm.getRed(newpix[y0*m + x1]) - cm.getRed(newpix[y0*m + x0])));//newpix[(y0+l)*m + k]  
								                                                                                                                   g = cm.getGreen(newpix[y0*m + x0]) + (int)(c*(cm.getGreen(newpix[y0*m + x1]) - cm.getGreen(newpix[y0*m + x0])));  
								b = cm.getBlue(newpix[y0*m + x0]) + (int)(c*(cm.getBlue(newpix[y0*m + x1]) - cm.getBlue(newpix[y0*m + x0])));  
								newpix[y0*m + x0 + k] = new Color(r,g,b).getRGB();  
							}  
							if(j+1<h && newpix[y1*m + x0 + k] == 0) {  
								c = (float)k/d1;  
								r = cm.getRed(newpix[y1*m + x0]) + (int)(c*(cm.getRed(newpix[y1*m + x1]) - cm.getRed(newpix[y1*m + x0])));  
								g = cm.getGreen(newpix[y1*m + x0]) + (int)(c*(cm.getGreen(newpix[y1*m + x1]) - cm.getGreen(newpix[y1*m + x0])));  
								b = cm.getBlue(newpix[y1*m + x0]) + (int)(c*(cm.getBlue(newpix[y1*m + x1]) - cm.getBlue(newpix[y1*m + x0])));  
								newpix[y1*m + x0 + k] = new Color(r,g,b).getRGB();  
							}  
						} else {  
							c = (float)l/d2;  
							r = cm.getRed(newpix[y0*m + x0+k]) + (int)(c*(cm.getRed(newpix[y1*m + x0+k]) - cm.getRed(newpix[y0*m + x0+k])));  
							g = cm.getGreen(newpix[y0*m + x0+k]) + (int)(c*(cm.getGreen(newpix[y1*m + x0+k]) - cm.getGreen(newpix[y0*m + x0+k])));  
							b = cm.getBlue(newpix[y0*m + x0+k]) + (int)(c*(cm.getBlue(newpix[y1*m + x0+k]) - cm.getBlue(newpix[y0*m + x0+k])));  
							newpix[(y0+l)*m + x0 + k] = new Color(r,g,b).getRGB();   
						}                 
					}                     
					if(i==w-2 || l==d2-1) {  
						c = (float)l/d2;  
						r = cm.getRed(newpix[y0*m + x1]) + (int)(c*(cm.getRed(newpix[y1*m + x1]) - cm.getRed(newpix[y0*m + x1])));  
						g = cm.getGreen(newpix[y0*m + x1]) + (int)(c*(cm.getGreen(newpix[y1*m + x1]) - cm.getGreen(newpix[y0*m + x1])));  
						b = cm.getBlue(newpix[y0*m + x1]) + (int)(c*(cm.getBlue(newpix[y1*m + x1]) - cm.getBlue(newpix[y0*m + x1])));  
						newpix[(y0+l)*m + x1] = new Color(r,g,b).getRGB();   
					}  
				}  
			}  
		}  

		BufferedImage imgOut = new BufferedImage( m, n, imgType);  

		imgOut.setRGB(0, 0, m, n, newpix, 0, m);          
		return imgOut;  
	}

	public static BufferedImage shrink(BufferedImage img, float k1, float k2) {  

		float ii = 1/k1;    
		float jj = 1/k2;    
		int dd = (int)(ii*jj);   
		int imgType = img.getType();  
		int w = img.getWidth();  
		int h = img.getHeight();  
		int m = (int) (k1*w);  
		int n = (int) (k2*h);  
		int[] pix = new int[w*h];  
		pix = img.getRGB(0, 0, w, h, pix, 0, w);  
		System.out.println(w + " * " + h);  
		System.out.println(m + " * " + n);  
		int[] newpix = new int[m*n];  

		for(int j=0; j<n; j++) {  
			for(int i=0; i<m; i++) {  
				int r = 0, g=0, b=0;  
				ColorModel cm = ColorModel.getRGBdefault();               
				for(int k=0; k<(int)jj; k++) {  
					for(int l=0; l<(int)ii; l++) {  
						r = r + cm.getRed(pix[(int)(jj*j+k)*w + (int)(ii*i+l)]);  
						g = g + cm.getGreen(pix[(int)(jj*j+k)*w + (int)(ii*i+l)]);  
						b = b + cm.getBlue(pix[(int)(jj*j+k)*w + (int)(ii*i+l)]);  
					}  
				}  
				r = r/dd;  
				g = g/dd;  
				b = b/dd;  
				newpix[j*m + i] = 255<<24 | r<<16 | g<<8 | b;  

			}  
		}  

		BufferedImage imgOut = new BufferedImage( m, n, imgType);  

		imgOut.setRGB(0, 0, m, n, newpix, 0, m);              
		return imgOut;  
	} 

}