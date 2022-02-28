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

public class Crate extends Entity{

	
	private boolean isBrake = false;
	private boolean drop = false;
	public static int dropId = 10;
	boolean generateDrop = false;
	private boolean selected = false;
	// (Animações)-------------------------------------------------------------------------------------------------------------------
	
		private int frames = 0, maxFrames = 12, index = 0, maxIndex = 2;
		
		//IMAGENS
		private BufferedImage crate;
		private BufferedImage[] crateDestroy = new BufferedImage[4];
		
		//
		
	public Crate(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		crate = Game.spritesheet.getSprite(0, 32, 16, 16);
		
		for (int i = 0; i < 4; i++) {
			crateDestroy[i] = Game.spritesheetAnimations.getSprite(0 + (i * 16), 32, 16, 16);
		}
		
	}
	public void tick() {

		if(drop == true) {
			drop = false;
		}
		//gerar um time de animação aleatorio;
		
		//ESTADO DE CADA VIDA--------------------------------------------------------------------------------------------------------
		if(isBrake == true) {
			
			if( generateDrop == false) {	
				//QUANTIDADE DE DROPS POSSIVEIS
				int qntDrop = Game.random.nextInt(5);
				if(qntDrop > 0) {
					for(int i = 0; i < qntDrop; i++) {
						
						
						//VALOR DO ID DO ITEM
						
						//PROBABILIDADES EM %
						//10%
						int drop = 0;
						
						while(!(drop == 1)) {
							//CORDENADAS ALEATRÓRIAS PARA ITEM NÃO CAIR NO MESMO LUGAR
							int xtemp = Game.random.nextInt(16) + this.getX();
							int ytemp = Game.random.nextInt(16) + this.getY();
							if(Game.random.nextInt(100) < 50) {
								//gold
								Material.droppingItens( 6, xtemp, ytemp,0,2);
								drop++;
							}else {
								Material.droppingItens( 9, xtemp, ytemp,3,1);
								drop++;
							}
							
						}
						
						
					}generateDrop = true;
				}
			}
				
				
		}
		if(isBrake == true) {
			this.setDepth(0,0,0,0,0,0,0,0,0);
			this.setMask(0,0,0,0,0,0,0,0);
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
					
				}
			}
		}
		//----------------------------------------------------------------------------------------------------------------------------
		CheckInterationsChest();
		//CheckInterationsTreeDrop();
		//RODANDO AS ANIMAÇÕES--------------------------------------------------------------------------------------------------------
		
		//----------------------------------------------------------------------------------------------------------------------------
	}
	
	
	
	
	public void CheckInterationsChest() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player) {
				if(Crate.isColliddingMaskRange(this, e)) {
					if(Chiken.isChooseObject(this, Game.player)) {
						this.selected = true;
						if(Player.action == true ) {
							
							if(isBrake == false) {
								Sound.brokenCrate.play(Sound.volume);
								
							}
							
							isBrake = true;
							
						}else {
							
						}
						
					}else {
						this.selected = false;
						
					}	
				}else {
					this.selected = false;
					
				}
				
			}
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		
		//g.fillRect(this.getX() + actionRangeX - Camera.x, this.getY()+ actionRangeY  - Camera.y, actionRangeWidth, actionRangeHeight);
		
		if(isBrake == true) {
			g.drawImage(crateDestroy[2], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else {
			if(selected == true) {
				g.drawImage(UI.selectedObject, this.getX() - Camera.x - 8, this.getY() - Camera.y, null);
			}
			g.drawImage(crate, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		
		
		//g.fillRect(this.getX() + depthX - Camera.x, this.getY()+depthY - Camera.y, mwidthDepth, mheightDepth);
		
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky - Camera.y, mwidth, mheight);	
			
				
				
				
	}

}
