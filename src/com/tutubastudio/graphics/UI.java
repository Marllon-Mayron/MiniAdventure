package com.tutubastudio.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.tutubastudio.entities.Player;
import com.tutubastudio.main.Game;
import com.tutubastudio.world.Camera;
import com.tutubastudios.itens.Material;

public class UI {
	public static BufferedImage health;
	public static BufferedImage inventory;
	
	public static BufferedImage[] slot = new BufferedImage[22];
	public static BufferedImage slotSelected;
	
	public static BufferedImage inventoryWin;
	public static BufferedImage craft;
	public static Spritesheet spritesheetUI;
	public static BufferedImage hud;
	public static BufferedImage selectedObject;

	
	public static boolean mouseDragged = false;
	public static boolean[] moveSlot = new boolean[22];
	public static int slotStart = 0;
	public static boolean dropping = false;
	//[9] NUMERO DO SLOT |POSIÇÕES [2] 0 = x, 1 = y
	public int[][] slot1Pos = new int[22][2];
	public static int slotMetodo;
	public static boolean[] slotFree = new boolean[22];
	public static int[] idItemSlot = new int[22];
	public static String[] typeItem = new String[5];
	public static int wt;
	public static int ht;
	//VARIAVEIS TEMPORARIAS
	public static BufferedImage slotTemp;
	public static int idItemTemp;
	public static boolean slotFreeTemp;
	public static int nSlot;
	public static boolean change;
	public static boolean itemOptions;
	public static boolean showDesc;
	public static boolean useItem;

	public UI(){
		spritesheetUI = new Spritesheet("/spritesheetUI.png");
		//ICONES
		health = spritesheetUI.getSprite(0, 0, 32, 32);
		inventory = spritesheetUI.getSprite(0, 32, 32, 32);
		craft = spritesheetUI.getSprite(0, 64, 32, 32);
		//WINDOWNS
		inventoryWin = spritesheetUI.getSprite(96, 16, 163, 98);
		slotSelected  = spritesheetUI.getSprite(96, 0, 16, 16);
		hud  = spritesheetUI.getSprite(0,0,96, 32);
		selectedObject = spritesheetUI.getSprite(0, 96, 32, 16);
		
		
		for(int i = 0; i < 22; i++) {
			slotFree[i] = true;
		}
		
		
		
		typeItem[0] = "Capacete";	
		typeItem[1] = "Armadura";	
		typeItem[2] = "Luva";	
		typeItem[3] = "Arma";	
		typeItem[4] = "Bota";	

	}
	public void render(Graphics g) {
		
		Color green1 = new Color(46, 184, 73);
		Color green2 = new Color(24, 158, 51);
		Color green3 = new Color(21, 138, 44);
		Color blue = new Color(0, 132, 209);
		Color blue2 = new Color(0, 107, 168);
		g.drawImage(hud, Game.WIDTH*2/100,Game.HEIGHT*2/100, null);
		//g.fillRect((Game.WIDTH*2/100)+30, Game.HEIGHT*2/100+8, 53,8);
		g.setColor(green1);
		g.fillRect((Game.WIDTH*2/100)+31, Game.HEIGHT*4/100+6,(int)((Game.player.life/Game.player.maxLife)*53)-1,1);
		g.setColor(green2);
		g.fillRect((Game.WIDTH*2/100)+31, (Game.HEIGHT*4/100+6)+1,(int)((Game.player.life/Game.player.maxLife)*53)-1,1);
		g.setColor(green3);
		g.fillRect((Game.WIDTH*2/100)+31, (Game.HEIGHT*4/100+6)+2,(int)((Game.player.life/Game.player.maxLife)*53)-1,3);
		g.setColor(blue);
		g.fillRect((Game.WIDTH*2/100)+31, (Game.HEIGHT*11/100)-1,(int)((Game.player.mana/Game.player.maxMana)*36)-1,1);
		g.setColor(blue2);
		g.fillRect((Game.WIDTH*2/100)+31, (Game.HEIGHT*11/100),(int)((Game.player.mana/Game.player.maxMana)*36)-1,1);
		
		
		
		
		
		
		//ABRIR INVENTARIO
		if(Game.player.openInventory == true) {
			g.drawImage(inventoryWin, Win.Xinv,Win.Yinv, null);
			//POSIÇÃO DO SLOT
			
			//PRIMEIRA FILEIRA
			slot1Pos[0][0] = Win.Xinv+5;
			slot1Pos[0][1] = Win.Yinv+22;
			
			slot1Pos[1][0] = Win.Xinv+23;
			slot1Pos[1][1] = Win.Yinv+22;
			
			slot1Pos[2][0] = Win.Xinv+41;
			slot1Pos[2][1] = Win.Yinv+22;
			
			slot1Pos[3][0] = Win.Xinv+59;
			slot1Pos[3][1] = Win.Yinv+22;
			//SEGUNDA FILEIRA
			slot1Pos[4][0] = Win.Xinv+5;
			slot1Pos[4][1] = Win.Yinv+40;
			
			slot1Pos[5][0] = Win.Xinv+23;
			slot1Pos[5][1] = Win.Yinv+40;
			
			slot1Pos[6][0] = Win.Xinv+41;
			slot1Pos[6][1] = Win.Yinv+40;
			
			slot1Pos[7][0] = Win.Xinv+59;
			slot1Pos[7][1] = Win.Yinv+40;
			//TERCEIRA FILEIRA
			slot1Pos[8][0] = Win.Xinv+5;
			slot1Pos[8][1] = Win.Yinv+58;
			
			slot1Pos[9][0] = Win.Xinv+23;
			slot1Pos[9][1] = Win.Yinv+58;
			
			slot1Pos[10][0] = Win.Xinv+41;
			slot1Pos[10][1] = Win.Yinv+58;
			
			slot1Pos[11][0] = Win.Xinv+59;
			slot1Pos[11][1] = Win.Yinv+58;
			//QUARTA
			slot1Pos[12][0] = Win.Xinv+5;
			slot1Pos[12][1] = Win.Yinv+76;
			
			slot1Pos[13][0] = Win.Xinv+23;
			slot1Pos[13][1] = Win.Yinv+76;
			
			slot1Pos[14][0] = Win.Xinv+41;
			slot1Pos[14][1] = Win.Yinv+76;
			
			slot1Pos[15][0] = Win.Xinv+59;
			slot1Pos[15][1] = Win.Yinv+76;
			
			slot1Pos[16][0] = Win.Xinv+109;
			slot1Pos[16][1] = Win.Yinv+21;
			
			slot1Pos[17][0] = Win.Xinv+87;
			slot1Pos[17][1] = Win.Yinv+29;
			
			slot1Pos[18][0] = Win.Xinv+131;
			slot1Pos[18][1] = Win.Yinv+29;
			
			slot1Pos[19][0] = Win.Xinv+85;
			slot1Pos[19][1] = Win.Yinv+49;
			
			slot1Pos[20][0] = Win.Xinv+133;
			slot1Pos[20][1] = Win.Yinv+49;
			
			slot1Pos[21][0] = Win.Xinv+135;
			slot1Pos[21][1] = Win.Yinv+76;
			
			//DENTRO DA WIN DO INVENTARIO
			if(((Game.player.Winmy > Win.Yinv+5) && (Game.player.Winmy < Win.Yinv + 125)) && ((Game.player.Winmx > Win.Xinv) && (Game.player.Winmx < Win.Xinv + 164))) {
				dropping = false;
				change = true;
				showDesc = false;
				
			}else{
				if(mouseDragged == true) {
					for(int i = 0; i < 16; i++) {
						if(moveSlot[i] == true) {
							dropping = true;
						}
					
					}
					
				}
				change = false;
			}
			
			if(Game.player.Winmx > Win.Xinv+5  && Game.player.Winmx < Win.Xinv+21) {
				if(Game.player.Winmy > Win.Yinv+22  && Game.player.Winmy < Win.Yinv+39) {
					g.drawImage(slotSelected, slot1Pos[0][0],slot1Pos[0][1], null);
					//DECLARANDO SPRITE DESSE ITEM PARA A A VARIAVEL TEMPORARIA, PARA NA HORA DE TROCAR SPRITES
					slotTemp = slot[0];
					showDesc = true;
					//VARIAVEL PRA GUARDAR O NUMERO DO SLOT, PARA OS METODOS
					slotMetodo = 0;
					//JOGANDO ID PRA UMA VARIAVEL TEMPORARIA
					idItemTemp = idItemSlot[0];
					slotFreeTemp = slotFree[0];
					//VARIAVEL TEMPORARIA PARA CONTAR QUANTOS SLOTS FALSOS TEM 
					int numFalse = 0;
					//CONTANDO SLOTS E JOGANDO NA MINHA VARIAVEL
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
							
						}
					}
					//PEGANDO ITEM 
					//CONDIÇÃO PARA QUE NENHUM SLOT SEJA PUXADO ENMQUANTO ESTIVER PUXANDO OUTRO
					if(mouseDragged == true && numFalse == 22
							) {
						moveSlot[0] = true;
						slotStart = 1;
					}
					//USAR O ITEM
					if(useItem == true) {
						Material.useItem(idItemTemp, slotMetodo );
						System.out.println("passanmdo o id "+UI.idItemTemp);
						useItem = false;
					}
					
					
					//TROCAR SLOTS, MANDANDO NUMERO DO SLOT PARA FAZER A TROCA LA NO MOUSE REALEASE
					
					
					
				}	
			}else if(Game.player.Winmx > Win.Xinv+23  && Game.player.Winmx < Win.Xinv+40) {
				if(Game.player.Winmy > Win.Yinv+22  && Game.player.Winmy < Win.Yinv+39) {
					g.drawImage(slotSelected, slot1Pos[1][0],slot1Pos[1][1], null);
					slotTemp = slot[1];
					showDesc = true;
					idItemTemp = idItemSlot[1];
					slotFreeTemp = slotFree[1];
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[1] = true;
						slotStart = 2;
					}
					
					slotMetodo = 1;
					if(useItem == true) {
						Material.useItem(idItemTemp, slotMetodo );
						System.out.println("passanmdo o id "+UI.idItemTemp);
						useItem = false;
					}
				}	
			}else if(Game.player.Winmx > Win.Xinv+41  && Game.player.Winmx < Win.Xinv+57) {
				if(Game.player.Winmy > Win.Yinv+22  && Game.player.Winmy < Win.Yinv+39) {
					g.drawImage(slotSelected, slot1Pos[2][0],slot1Pos[2][1], null);
					slotTemp = slot[2];
					showDesc = true;
					idItemTemp = idItemSlot[2];
					slotFreeTemp = slotFree[2];
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[2] = true;
						slotStart = 3;
					}
					slotMetodo = 2;
					if(useItem == true) {
						Material.useItem(idItemTemp, slotMetodo );
						System.out.println("passanmdo o id "+UI.idItemTemp);
						useItem = false;
					}
				}	
			}if(Game.player.Winmx > Win.Xinv+57  && Game.player.Winmx < Win.Xinv+75) {
				if(Game.player.Winmy > Win.Yinv+22  && Game.player.Winmy < Win.Yinv+39) {
					g.drawImage(slotSelected, slot1Pos[3][0],slot1Pos[3][1], null);
					slotTemp = slot[3];
					showDesc = true;
					idItemTemp = idItemSlot[3];
					slotFreeTemp = slotFree[3];
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[3] = true;
						slotStart = 4;
					}
					slotMetodo = 3;
					if(useItem == true) {
						Material.useItem(idItemTemp, slotMetodo );
						System.out.println("passanmdo o id "+UI.idItemTemp);
						useItem = false;
					}
				}	
			}else if(Game.player.Winmx > Win.Xinv+5  && Game.player.Winmx < Win.Xinv+21) {
				if(Game.player.Winmy > Win.Yinv+40  && Game.player.Winmy < Win.Yinv+57) {
					g.drawImage(slotSelected, slot1Pos[4][0],slot1Pos[4][1], null);
					slotTemp = slot[4];
					idItemTemp = idItemSlot[4];
					slotFreeTemp = slotFree[4];
					showDesc = true;
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[4] = true;
						slotStart = 5;
					}
					slotMetodo = 4;
					if(useItem == true) {
						Material.useItem(idItemTemp, slotMetodo );
						System.out.println("passanmdo o id "+UI.idItemTemp);
						useItem = false;
					}
				}	
			}else if(Game.player.Winmx > Win.Xinv+23  && Game.player.Winmx < Win.Xinv+40) {
				if(Game.player.Winmy > Win.Yinv+40  && Game.player.Winmy < Win.Yinv+57) {
					g.drawImage(slotSelected, slot1Pos[5][0],slot1Pos[5][1], null);
					slotTemp = slot[5];
					idItemTemp = idItemSlot[5];
					slotFreeTemp = slotFree[5];
					showDesc = true;
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[5] = true;
						slotStart = 6;
					}
					slotMetodo = 5;
				}	
			}if(Game.player.Winmx > Win.Xinv+41  && Game.player.Winmx < Win.Xinv+57) {
				if(Game.player.Winmy > Win.Yinv+40  && Game.player.Winmy < Win.Yinv+57) {
					g.drawImage(slotSelected, slot1Pos[6][0],slot1Pos[6][1], null);
					slotTemp = slot[6];
					idItemTemp = idItemSlot[6];
					slotFreeTemp = slotFree[6];
					showDesc = true;
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[6] = true;
						slotStart = 7;
					}
					slotMetodo = 6;
				}	
			}else if(Game.player.Winmx > Win.Xinv+57  && Game.player.Winmx < Win.Xinv+75) {
				if(Game.player.Winmy > Win.Yinv+40  && Game.player.Winmy < Win.Yinv+57) {
					g.drawImage(slotSelected, slot1Pos[7][0],slot1Pos[7][1], null);
					slotTemp = slot[7];
					idItemTemp = idItemSlot[7];
					showDesc = true;
					slotFreeTemp = slotFree[7];
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[7] = true;
						slotStart = 8;
					}
					slotMetodo = 7;
				}	
			}else if(Game.player.Winmx > Win.Xinv+5  && Game.player.Winmx < Win.Xinv+21) {
				if(Game.player.Winmy > Win.Yinv+58  && Game.player.Winmy < Win.Yinv+75) {
					g.drawImage(slotSelected, slot1Pos[8][0],slot1Pos[8][1], null);
					slotTemp = slot[8];
					idItemTemp = idItemSlot[8];
					slotFreeTemp = slotFree[8];
					showDesc = true;
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[8] = true;
						slotStart = 9;
					}
					slotMetodo = 8;
				}	
			}else if(Game.player.Winmx > Win.Xinv+23  && Game.player.Winmx < Win.Xinv+40) {
				if(Game.player.Winmy > Win.Yinv+58  && Game.player.Winmy < Win.Yinv+75) {
					g.drawImage(slotSelected, slot1Pos[9][0],slot1Pos[9][1], null);
					showDesc = true;
					slotTemp = slot[9];
					idItemTemp = idItemSlot[9];
					slotFreeTemp = slotFree[9];
					showDesc = true;
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[9] = true;
						slotStart = 10;
					}
					slotMetodo = 9;
				}	
			}if(Game.player.Winmx > Win.Xinv+41  && Game.player.Winmx < Win.Xinv+57) {
				if(Game.player.Winmy > Win.Yinv+58  && Game.player.Winmy < Win.Yinv+75) {
					g.drawImage(slotSelected, slot1Pos[10][0],slot1Pos[10][1], null);
					showDesc = true;
					slotTemp = slot[10];
					idItemTemp = idItemSlot[10];
					slotFreeTemp = slotFree[10];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[10] = true;
						slotStart = 11;
					}
					slotMetodo = 10;
				}	
			}if(Game.player.Winmx > Win.Xinv+59  && Game.player.Winmx < Win.Xinv+76) {
				if(Game.player.Winmy > Win.Yinv+58  && Game.player.Winmy < Win.Yinv+75) {
					g.drawImage(slotSelected, slot1Pos[11][0],slot1Pos[11][1], null);
					showDesc = true;
					slotTemp = slot[11];
					idItemTemp = idItemSlot[11];
					slotFreeTemp = slotFree[11];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[11] = true;
						slotStart = 12;
					}
					slotMetodo = 11;
				}	
			}if(Game.player.Winmx > Win.Xinv+5  && Game.player.Winmx < Win.Xinv+21) {
				if(Game.player.Winmy > Win.Yinv+76  && Game.player.Winmy < Win.Yinv+93) {
					g.drawImage(slotSelected, slot1Pos[12][0],slot1Pos[12][1], null);
					
					slotTemp = slot[12];
					idItemTemp = idItemSlot[12];
					slotFreeTemp = slotFree[12];
					showDesc = true;
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[12] = true;
						slotStart = 13;
					}
					slotMetodo = 12;
				}	
			}if(Game.player.Winmx > Win.Xinv+23  && Game.player.Winmx < Win.Xinv+40) {
				if(Game.player.Winmy > Win.Yinv+76  && Game.player.Winmy < Win.Yinv+93) {
					g.drawImage(slotSelected, slot1Pos[13][0],slot1Pos[13][1], null);
					showDesc = true;
					slotTemp = slot[13];
					idItemTemp = idItemSlot[13];
					slotFreeTemp = slotFree[13];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[13] = true;
						slotStart = 14;
					}
					slotMetodo = 13;
				}	
			}if(Game.player.Winmx > Win.Xinv+41  && Game.player.Winmx < Win.Xinv+57) {
				if(Game.player.Winmy > Win.Yinv+76  && Game.player.Winmy < Win.Yinv+93) {
					g.drawImage(slotSelected, slot1Pos[14][0],slot1Pos[14][1], null);
					showDesc = true;
					slotTemp = slot[14];
					idItemTemp = idItemSlot[14];
					slotFreeTemp = slotFree[14];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[14] = true;
						slotStart = 15;
					}
					slotMetodo = 14;
				}	
			}if(Game.player.Winmx > Win.Xinv+59  && Game.player.Winmx < Win.Xinv+76) {
				if(Game.player.Winmy > Win.Yinv+76  && Game.player.Winmy < Win.Yinv+93) {
					g.drawImage(slotSelected, slot1Pos[15][0],slot1Pos[15][1], null);
					showDesc = true;
					slotTemp = slot[15];
					idItemTemp = idItemSlot[15];
					slotFreeTemp = slotFree[15];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[15] = true;
						slotStart = 16;
					}
					slotMetodo = 15;
				}	
			}if(Game.player.Winmx > Win.Xinv+109  && Game.player.Winmx < Win.Xinv+126) {
				if(Game.player.Winmy > Win.Yinv+21  && Game.player.Winmy < Win.Yinv+38) {
					g.drawImage(slotSelected, slot1Pos[16][0],slot1Pos[16][1], null);
					showDesc = true;
					slotTemp = slot[16];
					idItemTemp = idItemSlot[16];
					slotFreeTemp = slotFree[16];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[16] = true;
						slotStart = 17;
					}
					slotMetodo = 16;
				}	
			}if(Game.player.Winmx > Win.Xinv+87  && Game.player.Winmx < Win.Xinv+104) {
				if(Game.player.Winmy > Win.Yinv+29  && Game.player.Winmy < Win.Yinv+46) {
					g.drawImage(slotSelected, slot1Pos[17][0],slot1Pos[17][1], null);
					showDesc = true;
					slotTemp = slot[17];
					idItemTemp = idItemSlot[17];
					slotFreeTemp = slotFree[17];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[17] = true;
						slotStart = 18;
					}
					slotMetodo = 17;
				}	
			}if(Game.player.Winmx > Win.Xinv+131  && Game.player.Winmx < Win.Xinv+148) {
				if(Game.player.Winmy > Win.Yinv+29  && Game.player.Winmy < Win.Yinv+46) {
					g.drawImage(slotSelected, slot1Pos[18][0],slot1Pos[18][1], null);
					showDesc = true;
					slotTemp = slot[18];
					idItemTemp = idItemSlot[18];
					slotFreeTemp = slotFree[18];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[18] = true;
						slotStart = 19;
					}
					slotMetodo = 18;
				}	
			}if(Game.player.Winmx > Win.Xinv+84  && Game.player.Winmx < Win.Xinv+101) {
				if(Game.player.Winmy > Win.Yinv+49  && Game.player.Winmy < Win.Yinv+66) {
					g.drawImage(slotSelected, slot1Pos[19][0],slot1Pos[19][1], null);
					showDesc = true;
					slotTemp = slot[19];
					idItemTemp = idItemSlot[19];
					slotFreeTemp = slotFree[19];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[19] = true;
						slotStart = 20;
					}
					slotMetodo = 19;
				}	
			}if(Game.player.Winmx > Win.Xinv+132  && Game.player.Winmx < Win.Xinv+149) {
				if(Game.player.Winmy > Win.Yinv+49  && Game.player.Winmy < Win.Yinv+66) {
					g.drawImage(slotSelected, slot1Pos[20][0],slot1Pos[20][1], null);
					showDesc = true;
					slotTemp = slot[20];
					idItemTemp = idItemSlot[20];
					slotFreeTemp = slotFree[20];
					
					int numFalse = 0;
					for(int i = 0; i < moveSlot.length; i++) {
						if(moveSlot[i] == false) {
							numFalse++;
						}
					}
					if(mouseDragged == true && numFalse == 22) {
						moveSlot[20] = true;
						slotStart = 21;
					}
					slotMetodo = 20;
				}	
			}
			//ARRASTAR ITEM DO IVENTARIO====================================================================================================
			if(moveSlot[0] == false) {
				g.drawImage(slot[0], slot1Pos[0][0],slot1Pos[0][1], null);
			}if(moveSlot[1] == false) {
				g.drawImage(slot[1], slot1Pos[1][0],slot1Pos[1][1], null);
			}if(moveSlot[2] == false) {
				g.drawImage(slot[2], slot1Pos[2][0],slot1Pos[2][1], null);
			}if(moveSlot[3] == false) {
				g.drawImage(slot[3], slot1Pos[3][0],slot1Pos[3][1], null);
			}if(moveSlot[4] == false) {
				g.drawImage(slot[4], slot1Pos[4][0],slot1Pos[4][1], null);
			}if(moveSlot[5] == false) {
				g.drawImage(slot[5], slot1Pos[5][0],slot1Pos[5][1], null);
			}if(moveSlot[6] == false) {
				g.drawImage(slot[6], slot1Pos[6][0],slot1Pos[6][1], null);
			}if(moveSlot[7] == false) {
				g.drawImage(slot[7], slot1Pos[7][0],slot1Pos[7][1], null);
			}if(moveSlot[8] == false) {
				g.drawImage(slot[8], slot1Pos[8][0],slot1Pos[8][1], null);
			}if(moveSlot[9] == false) {
				g.drawImage(slot[9], slot1Pos[9][0],slot1Pos[9][1], null);
			}if(moveSlot[10] == false) {
				g.drawImage(slot[10], slot1Pos[10][0],slot1Pos[10][1], null);
			}if(moveSlot[11] == false) {
				g.drawImage(slot[11], slot1Pos[11][0],slot1Pos[11][1], null);
			}if(moveSlot[12] == false) {
				g.drawImage(slot[12], slot1Pos[12][0],slot1Pos[12][1], null);
			}if(moveSlot[13] == false) {
				g.drawImage(slot[13], slot1Pos[13][0],slot1Pos[13][1], null);
			}if(moveSlot[14] == false) {
				g.drawImage(slot[14], slot1Pos[14][0],slot1Pos[14][1], null);
			}if(moveSlot[15] == false) {
				g.drawImage(slot[15], slot1Pos[15][0],slot1Pos[15][1], null);
			}if(moveSlot[16] == false) {
				g.drawImage(slot[16], slot1Pos[16][0],slot1Pos[16][1], null);
			}if(moveSlot[17] == false) {
				g.drawImage(slot[17], slot1Pos[17][0],slot1Pos[17][1], null);
			}if(moveSlot[18] == false) {
				g.drawImage(slot[18], slot1Pos[18][0],slot1Pos[18][1], null);
			}if(moveSlot[19] == false) {
				g.drawImage(slot[19], slot1Pos[19][0],slot1Pos[19][1], null);
			}if(moveSlot[20] == false) {
				g.drawImage(slot[20], slot1Pos[20][0],slot1Pos[20][1], null);
			}if(moveSlot[21] == false) {
				g.drawImage(slot[21], slot1Pos[21][0],slot1Pos[21][1], null);
			}
			if(moveSlot[0] == true && slotStart == 1) {g.drawImage(slot[0], Game.player.Winmx,Game.player.Winmy, null); nSlot = 0;}
			if(moveSlot[1] == true && slotStart == 2) {g.drawImage(slot[1], Game.player.Winmx,Game.player.Winmy, null); nSlot = 1;}
			if(moveSlot[2] == true && slotStart == 3) {g.drawImage(slot[2], Game.player.Winmx,Game.player.Winmy, null); nSlot = 2;}
			if(moveSlot[3] == true && slotStart == 4) {g.drawImage(slot[3], Game.player.Winmx,Game.player.Winmy, null); nSlot = 3;}
			if(moveSlot[4] == true && slotStart == 5) {g.drawImage(slot[4], Game.player.Winmx,Game.player.Winmy, null); nSlot = 4;}
			if(moveSlot[5] == true && slotStart == 6) {g.drawImage(slot[5], Game.player.Winmx,Game.player.Winmy, null); nSlot = 5;}
			if(moveSlot[6] == true && slotStart == 7) {g.drawImage(slot[6], Game.player.Winmx,Game.player.Winmy, null); nSlot = 6;}
			if(moveSlot[7] == true && slotStart == 8) {g.drawImage(slot[7], Game.player.Winmx,Game.player.Winmy, null); nSlot = 7;}
			if(moveSlot[8] == true && slotStart == 9) {g.drawImage(slot[8], Game.player.Winmx,Game.player.Winmy, null); nSlot = 8;}
			if(moveSlot[9] == true && slotStart == 10) {g.drawImage(slot[9], Game.player.Winmx,Game.player.Winmy, null); nSlot = 9;}
			if(moveSlot[10] == true && slotStart == 11) {g.drawImage(slot[10], Game.player.Winmx,Game.player.Winmy, null); nSlot = 10;}
			if(moveSlot[11] == true && slotStart == 12) {g.drawImage(slot[11], Game.player.Winmx,Game.player.Winmy, null); nSlot = 11;}
			if(moveSlot[12] == true && slotStart == 13) {g.drawImage(slot[12], Game.player.Winmx,Game.player.Winmy, null); nSlot = 12;}
			if(moveSlot[13] == true && slotStart == 14) {g.drawImage(slot[13], Game.player.Winmx,Game.player.Winmy, null); nSlot = 13;}
			if(moveSlot[14] == true && slotStart == 15) {g.drawImage(slot[14], Game.player.Winmx,Game.player.Winmy, null); nSlot = 14;}
			if(moveSlot[15] == true && slotStart == 16) {g.drawImage(slot[15], Game.player.Winmx,Game.player.Winmy, null); nSlot = 15;}
			if(moveSlot[16] == true && slotStart == 17) {g.drawImage(slot[16], Game.player.Winmx,Game.player.Winmy, null); nSlot = 16;}
			if(moveSlot[17] == true && slotStart == 18) {g.drawImage(slot[17], Game.player.Winmx,Game.player.Winmy, null); nSlot = 17;}
			if(moveSlot[18] == true && slotStart == 19) {g.drawImage(slot[18], Game.player.Winmx,Game.player.Winmy, null); nSlot = 18;}
			if(moveSlot[19] == true && slotStart == 20) {g.drawImage(slot[19], Game.player.Winmx,Game.player.Winmy, null); nSlot = 19;}
			if(moveSlot[20] == true && slotStart == 21) {g.drawImage(slot[20], Game.player.Winmx,Game.player.Winmy, null); nSlot =20;}
			if(moveSlot[21] == true && slotStart == 22) {g.drawImage(slot[21], Game.player.Winmx,Game.player.Winmy, null); nSlot = 21;}
			
			
			
	
		}
		
		
	}
	public static boolean foundSize() {
		//System.out.println("chamou o metodo de tela");
		
		do{
			wt = (Toolkit.getDefaultToolkit().getScreenSize().width*Game.redimensionar)/100;
			ht = (Toolkit.getDefaultToolkit().getScreenSize().height*Game.redimensionar)/100;
			
			if(Toolkit.getDefaultToolkit().getScreenSize().width%wt == 0){
				//System.out.println("O tamanho ideal para a tela é de "+wt);
				Game.WIDTH = wt;
				Game.HEIGHT = ht;
				Game.sizeLock = true;
				return true;
			}else {
				Game.redimensionar--;
				//System.out.println("repetindo");
			}
		}while(!(Game.sizeLock == false));
		return false;
	}
}
