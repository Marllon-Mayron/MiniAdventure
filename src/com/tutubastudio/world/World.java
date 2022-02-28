package com.tutubastudio.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.tutubastudio.entities.*;
import com.tutubastudio.entities.npc.Npc;
import com.tutubastudio.graphics.Spritesheet;
import com.tutubastudio.main.Game;
import com.tutubastudios.itens.Material;

public class World {
	
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	public boolean WorldColision(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			//Calcular os pixels do mapa
			int[] pixels = new int[map.getWidth() * map.getHeight()];

			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx = 0; xx<map.getWidth(); xx++) {
				for(int yy = 0; yy<map.getHeight(); yy++) {
					int pixelAtual = pixels[xx +(yy * map.getWidth())];
					if(pixelAtual == 0xFF0046a1) {
						Pixel px = new Pixel(xx, yy , 1, 1);
						px.set(xx, yy, 1, 1);
						Game.pixel.add(px);
					}

				}
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		//Game.player.diagonal = true;
		Game.player.changeDir = true;
		
		return false;
	}
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			//Calcular os pixels do mapa
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();

			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx = 0; xx<map.getWidth(); xx++) {
				for(int yy = 0; yy<map.getHeight(); yy++) {
					
					int pixelAtual = pixels[xx +(yy * map.getWidth())];
					if(pixelAtual == 0xFF00ffdd) {
						Entity MAPA = new Entity(xx, yy, 16, 16, Entity.MAPA_MP);
						Entity LAYER = new Entity(xx, yy, 16, 16, Entity.LAYER_MP);
						Game.entities.add(MAPA);
						Game.entities.add(LAYER);
					}else if(pixelAtual == 0xFF0026FF) {
						generatePlayer(xx, yy);
					}else if(pixelAtual == 0xFFfdffd4) {
						generateChiken(xx, yy);
					}else if(pixelAtual == 0xFF00007d) {
						generateNpc1(xx,yy);
					}
					//OBJETOS NO JOGO================================================================================================
					else if (pixelAtual == 0xFFe6a800) {
						generateChest(xx,yy);
						
					}else if (pixelAtual == 0xFFba8247) {
						generateCrate(xx,yy);
						
					}else if(pixelAtual == 0xFF004717) {
						generateTree(xx,yy);
					}else if(pixelAtual == 0xFF5b8039) {
						generateTree2(xx,yy);
					}else if(pixelAtual == 0xFF147a2c) {
						generateTree3(xx,yy);
					}else if(pixelAtual == 0xFF539c00) {
						generateBush(xx,yy);
					}else if(pixelAtual == 0xFF3f7500) {
						generateBush2(xx,yy);
					}
					
					//ESTRUTURAS===========================================================================================================
					else if(pixelAtual == 0xFFff2ee0){
						generateCasa1(xx,yy);
						
					} else if(pixelAtual == 0xFFff58e0){
						generatePit(xx,yy);
						
					} 
					//INIMIGOS-------------------------------------------------------------------------------------------------------
					else if(pixelAtual == 0xFFFF0000){
						//INIMIGO 1
						Enemy enemyObj = new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(enemyObj);
						Game.enemies.add(enemyObj);
					} 
					//---------------------------------------------------------------------------------------------------------------
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void generatePlayer(int x, int y) {
		Game.player.setActionRange(10,14,12,12);
		Game.player.setMask(13,20,7,3,0,0,0,0);
		Game.player.setDepth(y,11,9,10,13,0,0,0,0);
		Game.player.setX(x);
		Game.player.setY(y);
	}
	public void generateChiken(int x, int y) {
		Chiken tchiken = new Chiken(x, y, 16, 16, null);
		
		tchiken.setMask(13,18,6,3,0,0,0,0);
		tchiken.setDepth(y,12,4,8,2,0,0,0,0);
		tchiken.setActionRange(9, 15,14,8);
		Game.entities.add(tchiken);
		Game.animals.add(tchiken);
	}
	public void generateNpc1(int x, int y) {
		Npc npc = new Npc(x, y, 16, 16,Npc.NPC_NPC);
		npc.setMask(13,20,7,3,0,0,0,0);
		npc.setDepth(y,11,9,10,2,0,0,0,0);
		npc.setActionRange(9, 15,14,8);
		Game.entities.add(npc);
	}
	public void generateCrate(int x, int y) {
		Crate crate = new Crate(x, y, 16, 16, Entity.CRATE_OBJ);
		crate.setMask(3,10,9,4,0,0,0,0);
		crate.setDepth(y-6,3,0,9,1,0,0,0,0);
		crate.setActionRange(3, 8,9,4);
		Game.entities.add(crate);
	}
	public void generateChest(int x, int y) {
		Bau chest = new Bau(x, y, 16, 16, Entity.BAU_OBJ);
		chest.setMask(4,10,9,3,0,0,0,0);
		chest.setDepth(y,5,0,7,1,0,0,0,0);
		chest.setActionRange(3, 9,11,5);
		Game.entities.add(chest);
	}
	public void generateTree(int x, int y) {
		Tree treeSimple = new Tree(x, y, 16, 16,Entity.TREE_OBJ);
		treeSimple.setMask(12,25,8,5,0,0,0,0);
		treeSimple.setDepth(y+32,3, 0, 28, 17,0,0,0,0);	
		treeSimple.setActionRange(10, 23,12,9);
		Game.entities.add(treeSimple);
		
	}public void generateTree2(int x, int y) {
		if(y >= 0  &&  y <= 16) {
			int a = Game.random.nextInt(32);
			int b = Game.random.nextInt(16);
			y = y - a + b;
		}
		if(x >= 0  &&  x <= 16) {
			int a = Game.random.nextInt(32);
			int b = Game.random.nextInt(16);
			x = x - a + b;
		}
		Tree2 treeSimple = new Tree2(x, y, 16, 16,Entity.TREE_OBJ);
		treeSimple.setMask(14,41,5,3,0,0,0,0);
		treeSimple.setDepth(y+45,5, 3, 25, 25,0,0,0,0);	
		treeSimple.setActionRange(10, 38,12,9);
		Game.entities.add(treeSimple);
		
	}public void generateTree3(int x, int y) {
		Tree3 treeSimple = new Tree3(x, y, 16, 16,Entity.TREE_OBJ);
		treeSimple.setMask(21,41,7,4,0,0,0,0);
		treeSimple.setDepth(y+46,7, 0, 35, 28,0,0,0,0);	
		treeSimple.setActionRange(18, 38,12,9);
		Game.entities.add(treeSimple);
		
	}public void generateBush(int x, int y) {
		Bush bush = new Bush(x, y, 16, 16,null);
		bush.setMask(0,0,0,0,0,0,0,0);
		bush.setDepth(y+10,2,-2, 11, 1,0,0,0,0);
		bush.setActionRange(3, 7,10,4);
		Game.entities.add(bush);
		
	}public void generateBush2(int x, int y) {
		Bush2 bush = new Bush2(x, y, 16, 16,null);
		bush.setMask(0,0,0,0,0,0,0,0);
		bush.setDepth(y+10,6,-2, 19, 1,0,0,0,0);
		bush.setActionRange(6, 7,19,4);
		Game.entities.add(bush);
		
	}
	public void generateCasa1(int x, int y) {
		Structures casa = new Structures(x, y, 16, 16,Entity.CASA1_OBJ);
		casa.setMask(0,0,0,0,0,0,0,0);
		casa.setDepth(y+69,6, 6, 97, 33,0,0,0,0);	
		//casa.setActionRange(10, 23,12,9);
		Game.entities.add(casa);
	}public void generatePit(int x, int y) {
		Structures pit = new Structures(x, y, 16, 16,Entity.PIT_OBJ);
		pit.setMask(0,0,0,0,0,0,0,0);
		pit.setDepth(y+30,3, 1, 39, 9,0,0,0,0);	
		//casa.setActionRange(10, 23,12,9);
		Game.entities.add(pit);
	}
	public static void restartGame(String level) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
		return;
	}
	public void render(Graphics g) {
		
		
	}
}
