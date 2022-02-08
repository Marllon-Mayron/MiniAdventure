package com.tutubastudio.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tutubastudio.entities.Entity;

public class OutsideObjects extends Entity{
	

	public OutsideObjects(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
       
	}
	
    public void setMask(int maskx, int masky, int mwidth, int mheight, int depth) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
		this.depth = depth;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	
	public void tick() {
		
		
		
	}
	
	
	
	
	public void render(Graphics g) {
		
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky - Camera.y, mwidth, mheight);
		//g.fillRect(this.getX() + maskx2 - Camera.x, this.getY()+ masky2 - Camera.y, mwidth2, mheight2);
		//g.fillRect(this.getX() + depthX - Camera.x, this.getY()+ depthY - Camera.y, mwidthDepth, mheightDepth);
		
		if(this.hasLine == true) {
			g.drawLine((int)(this.getX()  + startX- Camera.x), (int)(this.getY()+ startY - Camera.y), (int)(this.getX()+finalX- Camera.x), (int)(this.getY()+finalY- Camera.y));			
		}if(this.hasLineDepth == true) {
		//g.drawLine((int)(this.getX()  + startDepthX- Camera.x), (int)(this.getY()+ startDepthY - Camera.y), (int)(this.getX()+finalDepthX- Camera.x), (int)(this.getY()+finalDepthY- Camera.y));
			
		}
			
	}
	


	
}
