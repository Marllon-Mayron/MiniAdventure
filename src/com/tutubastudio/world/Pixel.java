package com.tutubastudio.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.tutubastudio.entities.Entity;
import com.tutubastudio.entities.Player;
import com.tutubastudio.entities.Chiken;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.main.Game;
import com.tutubastudio.main.Sound;
import com.tutubastudios.itens.Material;

public class Pixel {
	public int x, y,  width, height;
	public Pixel(int x, int y, int width, int height) {
		
		
	}
	public void set(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public static boolean isCollidding(Entity e1, Pixel e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.x, e2.y, e2.width, e2.height);
	
		

		if( e1Mask.intersects(e2Mask)) {
			
			return true;
		}else {
			return false;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		
		g.fillRect(x - Camera.x, y - Camera.y, width, height);
		
	
				
				
	}

}
