package com.tutubastudio.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tutubastudio.entities.Entity;
import com.tutubastudio.main.Game;
import com.tutubastudio.world.Camera;

public class Win extends Entity {
	//POSIÇÕES DAS WIN DO GAME
	//WIN DO INVENTARIO
	public static boolean invLokc = true;
	public static boolean missionLokc = true;
	public static int Xinv = (Game.WIDTH/2)/2;
	public static int Yinv = (Game.HEIGHT/2)/2;
	public static int Xmis = (Game.WIDTH/2)/2;
	public static int Ymis = (Game.HEIGHT/2)/2;
	public Win(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
		
		
		
	}
	public void render(Graphics g) {
		
		
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		g.setColor(Color.red);
			
			g.fillRect(Xinv - Camera.x, Win.Yinv - Camera.y, mwidth, mheight);	
		
		
		
	}
		

	

}
