package com.tutubastudio.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tutubastudio.entities.Entity;
import com.tutubastudio.entities.Player;
import com.tutubastudio.entities.Chiken;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.main.Game;
import com.tutubastudio.main.Sound;
import com.tutubastudios.itens.Material;

public class Bush extends Entity{

	// (Animações)-------------------------------------------------------------------------------------------------------------------
		//IMAGENS
	public static double speedTemp = 1;
		private BufferedImage bush;
		
		//
		
	public Bush(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		bush = Game.spritesheet.getSprite(0, 16, 16, 16);
	}
	public void tick() {
	
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		
		
		g.drawImage(bush, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.fillRect(this.getX() + actionRangeX - Camera.x, this.getY()+ actionRangeY  - Camera.y, actionRangeWidth, actionRangeHeight);
		
		
		
		//g.fillRect(this.getX() + depthX - Camera.x, this.getY()+depthY - Camera.y, mwidthDepth, mheightDepth);
		
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky - Camera.y, mwidth, mheight);	
			
				
				
				
	}

}
