package com.tutubastudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.tutubastudio.entities.*;
import com.tutubastudio.graphics.Spritesheet;
import com.tutubastudio.main.Game;
import com.tutubastudios.itens.Material;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			//Calcular os pixels do mapa
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx = 0; xx<map.getWidth(); xx++) {
				for(int yy = 0; yy<map.getHeight(); yy++) {
					
					int pixelAtual = pixels[xx +(yy * map.getWidth())];
					tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					
					if(pixelAtual == 0xFF000000) {
						//Floor
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					} else if(pixelAtual == 0xFFFFFFFF) {
						//Wall
						tiles[xx+(yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					} else if(pixelAtual == 0xFF00ffdd) {
						Entity MAPA = new Entity(xx*16, yy*16, 16, 16, Entity.MAPA_MP);
						
						Game.entities.add(MAPA);
						
					}else if(pixelAtual == 0xFF0026FF) {
						//LOCALIZAÇÃO DO PLAYER
						Game.player.setActionRange(6,7,20,20);
						Game.player.setMask(13,20,7,3,0,0,0,0);
						Game.player.setDepth(yy,11,9,10,13,0,0,0,0);
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						
						
					}else if (pixelAtual == 0xFFe6a800) {
						Bau chest = new Bau(xx*16, yy*16, 16, 16, Entity.BAU_OBJ);
						
						chest.setMask(4,10,9,3,0,0,0,0);
						chest.setDepth(yy,5,0,7,1,0,0,0,0);
						chest.setActionRange(2, 3,13,10);
						
						Game.entities.add(chest);
						
					}else if (pixelAtual == 0xFFba8247) {
						Crate crate = new Crate(xx*16, yy*16, 16, 16, Entity.CRATE_OBJ);
						
						crate.setMask(3,10,9,4,0,0,0,0);
						crate.setDepth(yy,3,0,9,1,0,0,0,0);
						crate.setActionRange(1, 2,13,13);
						
						Game.entities.add(crate);
						
					}else if(pixelAtual == 0xFFfdffd4) {
						
						Tchiken tchiken = new Tchiken(xx*16, yy*16, 16, 16, null);
						
						tchiken.setMask(12,18,8,3,0,0,0,0);
						tchiken.setDepth(yy,12,4,8,2,0,0,0,0);
						tchiken.setActionRange(9, 15,14,8);
						
						Game.entities.add(tchiken);
						Game.animals.add(tchiken);
					}
					//==============================================BIOMAS==========================================================
					//FLORESTA------------------------------------------------------------------------------------------------------
					else if(pixelAtual == 0xFF007317){
						//VARIAVEIS PARA GERAR NUMEROS INCERTOS DE OBJETOS
						int objCont = 0;
						int muita = 20;
						int media = 10;
						int pouca = 5;
						
						//QUANTIDADE MAXIMAS DE TENTATIVAS DE CRIAR UM OBJETO
						int maxObj = 20;	
						//TAMANHO DO BIOMA
						int heightSize = 16*6;
						int widthSize = 16*16;
						
						
						Biomes florest = new Biomes(xx*16, yy*16, 16, 16, null);
						
						florest.setBiomeTerrain(0, 0, widthSize, heightSize);
						Game.entities.add(florest);
						Game.biomes.add(florest);
						do {
							
							int tempposy = 0;
							int tempposx = 0;
							
							//NÃO DEIXA CRIAR ARVORE MUITO PERTO DAS BORDAS
							
							tempposx = Game.random.nextInt(widthSize);
							tempposy = Game.random.nextInt(heightSize);
								
							//DISTANCIAR ARVORES
							
							Tree treeSimple = new Tree(tempposx + xx*16, tempposy+ yy*16, 16, 16, Entity.TREE_OBJ);
							treeSimple.setMask(12,25,9,5,0,0,0,0);
							treeSimple.setDepth(tempposy+32,3, 0, 28, 17,0,0,0,0);	
							treeSimple.setActionRange(7, 21,20,18);
							if( Entity.CheckArea(treeSimple) == false && Game.random.nextInt(100) < 70) {
								//GERAR ARVORE 
								Game.entities.add(treeSimple);
								Game.simpleTrees.add(treeSimple);
								objCont++;
		
							}else {
								System.out.println("Objeto colidiu com outro objeto");
								objCont++;
							}
							//PEDRA
							tempposx = Game.random.nextInt(widthSize);
							tempposy = Game.random.nextInt(heightSize);
							
							Stone stoneSimple = new Stone(tempposx + xx*16, tempposy+ yy*16, 16, 16, Entity.STONE_OBJ);
							stoneSimple.setMask(8,20,16,7,0,0,0,0);
							stoneSimple.setDepth(tempposy+32,8,8,15,1,0,0,0,0);
							//stoneSimple.setActionRange(0, 8,32,24);
							if( Entity.CheckArea(stoneSimple) == false && Game.random.nextInt(100) < 30) {
								//GERAR ARVORE
								
								
								Game.entities.add(stoneSimple);
								Game.simpleStones.add(stoneSimple);
								
								objCont++;
								
										
							}else {
							
								objCont++;
								
							}
								
								
						//DEFINO O QUANTO DE OBJETOS QUERO DENTRO DO MEU BIOMA		
						}while(!((/**ARVORES**/objCont >= maxObj ))/**ARBUSTOS**/);
						//OBJETOS NO MAPA	
					}else if(pixelAtual == 0xFF73502a) {
						
						OutsideObjects FW_down = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_down_OBJ1);
						FW_down.setMask(4,12,16,4,0,0,0,0);	
						FW_down.setDepth(yy,4, 4, 12, 1,0,0,0,0);
						Game.entities.add(FW_down);
						Game.outObject.add(FW_down);
					}else if (pixelAtual == 0xFF5e4223) {
						OutsideObjects FW_down2 = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_down_OBJ2s);
						FW_down2.setMask(0,12,16,4,0,0,0,0);	
						FW_down2.setDepth(yy,0, 4, 12, 1,0,0,0,0);
						Game.entities.add(FW_down2);
						Game.outObject.add(FW_down2);
					}else if (pixelAtual == 0xFF5e4224) {
						OutsideObjects FW_down2 = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_down_OBJ2);
						FW_down2.setMask(0,12,16,4,0,0,0,0);	
						FW_down2.setDepth(yy,0, 4, 12, 1,0,0,0,0);
						Game.entities.add(FW_down2);
						Game.outObject.add(FW_down2);
					}else if (pixelAtual == 0xFF73503a) {
						OutsideObjects FW_down3 = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_down_OBJ3);
						FW_down3.setMask(0,12,12,4,0,0,0,0);
						FW_down3.setDepth(yy,0, 4, 12, 1,0,0,0,0);
						Game.entities.add(FW_down3);
						Game.outObject.add(FW_down3);
					}else if (pixelAtual == 0xFF8f634c) {
						OutsideObjects FW_left1 = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_left_OBJ1);
						FW_left1.setMask(4,6,4,10,0,0,0,0);
						FW_left1.setDepth(yy,4, 0, 4, 1,0,0,0,0);
						
						Game.entities.add(FW_left1);
						Game.outObject.add(FW_left1);
					}else if (pixelAtual == 0xFF7d573e) {
						OutsideObjects FW_left2 = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_left_OBJ2);
						FW_left2.setMask(4,0,4,16,0,0,0,0);
						
						
						Game.entities.add(FW_left2);
						Game.outObject.add(FW_left2);
					}else if (pixelAtual == 0xFF88503a) {
						OutsideObjects FW_leftCorner = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_leftCORNER_OBJ);
						FW_leftCorner.setMask(0,12,12,4,8,0,4,10);
						
						FW_leftCorner.setDepth(yy,0, 8, 8, 1,0,0,0,0);
						Game.entities.add(FW_leftCorner);
						Game.outObject.add(FW_leftCorner);
					}else if (pixelAtual == 0xFF8f6335) {
						OutsideObjects FW_right1 = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_right_OBJ1);
						FW_right1.setMask(8,6,4,10,0,0,0,0);
						FW_right1.setDepth(yy,8, 0, 4, 1,0,0,0,0);
						
						Game.entities.add(FW_right1);
						Game.outObject.add(FW_right1);
					}else if (pixelAtual == 0xFF7d572e) {
						OutsideObjects FW_right2 = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_right_OBJ2);
						FW_right2.setMask(8,0,4,16,0,0,0,0);
						Game.entities.add(FW_right2);
						Game.outObject.add(FW_right2);
					}else if (pixelAtual == 0xFF8f6336) {
						OutsideObjects FW_right3 = new OutsideObjects(xx*16, yy*16-4, 16, 16, Entity.FANCEWOOD_right_OBJ3);
						FW_right3.setMask(8,0,4,14,0,0,0,0);
						
						Game.entities.add(FW_right3);
						Game.outObject.add(FW_right3);
					}else if (pixelAtual == 0xFFb3682b) {
						OutsideObjects FW_dia = new OutsideObjects(xx*16+4, yy*16-6, 16, 16, Entity.FANCEWOOD_DIAGONAL_OBJ);
						FW_dia.setLine(true,0,5,15,17);	
						FW_dia.setLineDepth(true,yy,0,-1,15,8);	
						
						
						Game.entities.add(FW_dia);
						Game.outObject.add(FW_dia);
					}
					
					//OBJETOS NO JOGO============= ===================================================================================
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
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile || 
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile || 
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile || 
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile);
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
		
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
