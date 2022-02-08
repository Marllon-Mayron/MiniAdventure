package com.tutubastudio.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tutubastudio.entities.Entity;
import com.tutubastudio.entities.Player;
import com.tutubastudio.entities.Tchiken;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.main.Game;
import com.tutubastudio.main.Sound;
import com.tutubastudios.itens.Material;

public class Bau extends Entity{

	
	private boolean isOpen = false;
	private boolean selected = false;
	private boolean drop = false;
	public static int dropId = 10;
	boolean generateDrop = false;
	// (Animações)-------------------------------------------------------------------------------------------------------------------
	
		private int frames = 0, maxFrames = 0, index = 0, maxIndex = 1;
		private int framesDamage = 0, maxFramesDamage = 6, indexDamage = 0, maxIndexDamage = 3;
		//IMAGENS
		private BufferedImage chest;
		private BufferedImage openChest;
		private BufferedImage[] openingChest;
		//
		
	public Bau(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		chest = Game.spritesheet.getSprite(96, 16, 16, 16);
		openChest = Game.spritesheet.getSprite(112, 16, 16, 16);
		for (int i = 0; i < 2; i++) {
			//tree[i] = Game.spritesheetAnimations.getSprite(0 + (i * 32), 0, 32, 32);
		}
		
		
	}
	
	public void tick() {
		
		if(drop == true) {
			drop = false;
		}
		//gerar um time de animação aleatorio;
		
		//ESTADO DE CADA VIDA--------------------------------------------------------------------------------------------------------
		if(isOpen == true) {
			
			if( generateDrop == false) {	
				//QUANTIDADE DE DROPS POSSIVEIS
				int qntDrop = 1 ;//Game.random.nextInt(3);
				if(qntDrop > 0) {
					for(int i = 0; i < qntDrop; i++) {
						
						//CORDENADAS ALEATRÓRIAS PARA ITEM NÃO CAIR NO MESMO LUGAR
						int xtemp = Game.random.nextInt(16) + this.getX();
						
						//VALOR DO ID DO ITEM
						
						//PROBABILIDADES EM %
						
						
						int drop = 0;
						while(!(drop == 1)) {
							if(Game.random.nextInt(100) > 0) {
								//MAÇA
								int n = 4;
								Material.droppingItens( n, xtemp, this.getY()+14,3,0);
								
								Material.droppingItens( 5, xtemp+8, this.getY()+14,6,0);
								drop++;
							}
							
						}
						
						
					}generateDrop = true;
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
				if(Bau.isColliddingActionRange(this, e)) {
					if(Tchiken.isChooseObject(this, Game.player)) {
						this.selected = true;
						if(Player.interact == true) {
							if(Player.key > 0) {
								if(isOpen == false) {
									Sound.insertKey.play(Sound.volume);	
									//CRIO UMA TRHED SÓ PARA ESPERAR O TEPO CERTO DE ABRIR O BAU
									new Thread() {
										public void run() {
											Sound.insertKey.play(Sound.volume);	
											try {
												Thread.sleep(3500);
												Sound.openChest.play(Sound.volume);
											} catch (InterruptedException e1) {
												
												e1.printStackTrace();
											}
											
											if(isOpen == false) {
												Player.key--;
												isOpen = true;
											}
										}
										
									}.start();		
								}
							}
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
		
		if(isOpen == false) {
			if(selected == true) {
				g.drawImage(UI.selectedObject, this.getX() - Camera.x-7, this.getY() - Camera.y, null);
			}
			g.drawImage(chest, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else {
			g.drawImage(openChest, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		//g.fillRect(this.getX() + depthX - Camera.x, this.getY()+depthY - Camera.y, mwidthDepth, mheightDepth);
		
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky - Camera.y, mwidth, mheight);	
			
				
				
				
	}

}
