package com.tutubastudio.entities.npc;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tutubastudio.entities.Entity;
import com.tutubastudio.entities.Player;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.main.Game;
import com.tutubastudio.world.Camera;
import com.tutubastudio.world.Tree;


public class Npc extends Entity{
	private int frames = 0, MaxFrames = 15, index= 0,  MaxIndex = 2;
	public int LGNPC;
	private static int right_dir = 0, left_dir = 1;
	private static int dir = right_dir;
	private boolean selected = false;
	public static int conversa = 0;
	public static int conversaMensagem = 0;
	public static int maxMensagem = 0;
	
	public static String[][] messages = new String[5][10];
	
	private boolean start = true;
	public static boolean ShowMessageNpc = false;

	public static BufferedImage NPC_NPC = Game.spritesheetNPC.getSprite(0,0,16,16);
	
	
	private BufferedImage[] stopRightNpc;
	private BufferedImage[] stopLeftNpc;
	

	
	

	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		//FRASES

		//ANIMAÇÕES
		stopLeftNpc = new BufferedImage[3];
		stopRightNpc = new BufferedImage[3];
		
		for(int i =0; i < 3; i++) {
			stopLeftNpc[i] = Game.spritesheetNPC.getSprite(0 + (i*32), 0 , 32, 32);
			stopRightNpc[i] = Game.spritesheetNPC.getSprite(0 + (i*32), 32 , 32, 32);
		}
		
		messages[0][0] = "você: Ola"; 
		messages[0][1] = "Velhinho: Você pode matar algumas gali-\nnhas para mim?";
		messages[0][2] = "você: Ok!";
		messages[0][3] = "";
		
		messages[1][0] = "Velhinho: Irei esperar...";
		messages[1][1] = "Velhinho: Que demora...";
		messages[1][2] = "";
		
		messages[2][0] = "voce: Pronto senhor";
		messages[2][1] = "Velhinho: Obrigado!";

	}
	
    
	public void tick() {
		if(Messages.messageBallon == true) {
			if(Player.interact == true) {
				if(conversaMensagem < maxMensagem) {
					conversaMensagem ++;
					Player.interact = false;
				}
			}
		}

		if(Math.abs(Game.player.getX()-x) < 32 && Math.abs( Game.player.getY()-y) < 32) {
			
			if(Game.player.getX() > x) {
				
				dir = left_dir;
			}else {
				dir = right_dir;
				
			}	
		}
		if (start) {
			frames ++;
			if(frames == MaxFrames) {
				frames = 0;
				index++;
				if (start == false) {
					index = 0;
				}
				   if (index > MaxIndex) {
					   index = 0;
					   
				   }
			}
			
		}
		
		
		CheckInterations();
		changeLolcalBallon();
		functionMessage();
		
	}
	public void changeLolcalBallon() {
		if(Messages.playerTalk == false) {
			Messages.xMensagem = this.getX()-20 - Camera.x;
			Messages.yMensagem = this.getY()-32 - Camera.y;
		}else {
			Messages.xMensagem = Game.player.getX()-20 - Camera.x;
			Messages.yMensagem = Game.player.getY()-32 - Camera.y;
		}
	}
	
	public void functionMessage() {
		if(conversa == 0){
			maxMensagem = 4;
			switch (conversaMensagem) {
				case 0:
					Messages.playerTalk = true;
						
					break;
				case 1:
					Messages.playerTalk = false;
						
					break;
				case 2:
				
					Messages.playerTalk = true;
					break;
				case 3:
					if(Missoes.guardarMissao(1) != 0) {
						
					}if(Missoes.guardarMissao(2) != 0) {
						
					}
					Messages.messageBallon = false;
					Player.stateNum = 0;
					conversaMensagem = 0;
					conversa++;
					break;
			}	
		}else if(conversa == 1) {
			switch (conversaMensagem) {
				case 0:
				Messages.playerTalk = false;
				
				break;
				case 1:
				Messages.playerTalk = false;
				
				break;
			case 2:
				Messages.messageBallon = false;
				Player.stateNum = 0;
				conversaMensagem = 0;
				break;
				
			}	
		}
	}
	public void CheckInterations() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player) {
				if(Entity.isColliddingMaskRange(e, this)) {
					if(Tree.isChooseObject(this, Game.player)) {
						if(Messages.messageBallon == false) {
							this.selected = true;
							Player.cursor = 3;
						}else {
							this.selected = false;
							
						}
						if(Player.interact == true) {
							if(Messages.messageBallon == false) {
								Messages.messageBallon = true;
								Player.stateNum = 4;
								Player.interact = false;
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
		
		if(selected == true) {
			g.drawImage(UI.selectedObject, this.getX() - Camera.x, this.getY() - Camera.y + 11, null);
		}
		if(start == true) {
			if(dir == right_dir) {
					g.drawImage(stopRightNpc[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else {
				g.drawImage(stopLeftNpc[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
		g.setColor(Color.blue);
		//g.fillRect(this.getX() + actionRangeX - Camera.x, this.getY()+ actionRangeY  - Camera.y, actionRangeWidth, actionRangeHeight);
	}
	public void render2(Graphics g) {
		
		g.setFont(new Font("arial", Font.BOLD, 3*UI.div));
		Color bb = new Color(64, 41, 22);
		g.setColor(bb);
		if(Messages.messageBallon == true) {
			Messages.drawString(g,messages[conversa][conversaMensagem],(Messages.xMensagem+12)*UI.div, (Messages.yMensagem+13)*UI.div);
		}
		
	}
	

}

