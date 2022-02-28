package com.tutubastudio.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tutubastudio.main.Game;
import com.tutubastudio.main.Sound;
import com.tutubastudios.itens.Material;
import com.tutubastudio.entities.Entity;
import com.tutubastudio.entities.Player;
import com.tutubastudio.graphics.UI;

public class Tree3 extends Entity {
	public int life = 4;
	private boolean isDamage = false;
	private boolean decideTime = false;
	private boolean selected = false;
	private boolean drop = false;
	public static int dropId = 10;
	boolean generateDrop = false;
	
	// (Animações)-------------------------------------------------------------------------------------------------------------------
	
	private int frames = 0, maxFrames = 0, index = 0, maxIndex = 1;
	private int framesDamage = 0, maxFramesDamage = 7, indexDamage = 0, maxIndexDamage = 3;
	//IMAGENS
	private BufferedImage[] tree;
	private BufferedImage[] damageTree;
	private BufferedImage cuttedTree;

	//
	
	
	public Tree3(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		damageTree = new BufferedImage[4];
		tree = new BufferedImage[4];
		
		for (int i = 0; i < 4; i++) {
			damageTree[i] = Game.spritesheetAnimations.getSprite(128 + (i * 48),48, 32, 48);
		}for (int i = 0; i < 4; i++) {
			tree[i] = Game.spritesheetAnimations.getSprite(0 + (i * 48), 144, 48, 48);
		}
		cuttedTree = Game.spritesheet.getSprite(32, 0, 16, 16);	
	}
	
	
	public void tick() {
		
		if(drop == true) {
			drop = false;
		}
		//gerar um time de animação aleatorio;
		if(decideTime == false) {
			while(!(decideTime == true)) {
				maxFrames = Game.random.nextInt(400);
				if(maxFrames > 120) {
					decideTime = true;
				}
			}
		}
		//ESTADO DE CADA VIDA--------------------------------------------------------------------------------------------------------
		if(life == 1) {
			
			this.setDepth(depth,12,17+16,5,1,0,0,0,0);
			if( generateDrop == false) {	
				//QUANTIDADE DE DROPS POSSIVEIS
				int qntDrop = Game.random.nextInt(2);
				//VALOR DO ID DO ITEM
				
				
				
				
				if(qntDrop > 0) {
					for(int i = 0; i < qntDrop; i++) {
						
						//CORDENADAS ALEATRÓRIAS PARA ITEM NÃO CAIR NO MESMO LUGAR
						int xtemp = Game.random.nextInt(32) + this.getX();
						int ytemp = Game.random.nextInt(32) + this.getY();
						int n = 0;
						//PROBABILIDADES EM %
						//10%
						if(Game.random.nextInt(100) < 25) {
							//MAÇA
							n = 2;
							Material.droppingItens( n, xtemp, ytemp,1,1);
						//35%
						}else if(Game.random.nextInt(100) < 35){
							//GALHO
							n = 3;
							Material.droppingItens( n, xtemp, ytemp,1,2);
						//65%
						}
					}
					//ITEM PADRÃO DE DROPAR 100% DE CHANCE
					int xtemp = Game.random.nextInt(32) + this.getX();
					int ytemp = Game.random.nextInt(32) + this.getY();
					Material.droppingItens( 1, xtemp, ytemp,1,0);
					
					
					
					generateDrop = true;
				}
				
				
			}
				
			
				
				
		}
		
		if(life == 0 && drop == false) {
			 destroySelf();
			   return;
		}
		//----------------------------------------------------------------------------------------------------------------------------
		CheckInterationsTree();
		//CheckInterationsTreeDrop();
		//RODANDO AS ANIMAÇÕES--------------------------------------------------------------------------------------------------------
		if(isDamage == true) {
			framesDamage++;
			if (framesDamage == maxFramesDamage) {
				framesDamage = 0;
				indexDamage++;
				if(indexDamage == maxIndexDamage-1) {
					Sound.baterMadeira.play(Sound.volume);
				}
				if (indexDamage >= maxIndexDamage) {
					
					life--;
					
					isDamage = false;
					indexDamage = 0;
				}
			}
		}else{
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
	}
	
	
	public void destroySelf() {
		Game.entities.remove(this);
		
	}
	
	public void CheckInterationsTree() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player) {
				if(Tree3.isColliddingMaskRange(e, this)) {
					if(Tree3.isChooseObject(this, Game.player)) {
						this.selected = true;
						if(Player.action == true) {
							isDamage = true;
							
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
		
		
		if(selected == true) {
			g.drawImage(UI.selectedObject, this.getX()+7 - Camera.x, this.getY() - Camera.y + 34, null);
		}
		if(life > 1) {
			if (!isDamage) {
				g.drawImage(tree[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else {
				g.drawImage(damageTree[indexDamage], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}else {
			g.drawImage(cuttedTree, this.getX()+8 - Camera.x, this.getY()+32 - Camera.y, null);
		}
		
		//g.fillRect(this.getX() + depthX - Camera.x, this.getY()+depthY - Camera.y, mwidthDepth, mheightDepth);
		
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky - Camera.y, mwidth, mheight);	
			
		//g.fillRect(this.getX() + actionRangeX - Camera.x, this.getY()+ actionRangeY  - Camera.y, actionRangeWidth, actionRangeHeight);		
				
				
	}




}
