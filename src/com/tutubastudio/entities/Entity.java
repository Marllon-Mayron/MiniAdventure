
package com.tutubastudio.entities;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;

import javax.imageio.ImageIO;

import com.tutubastudio.entities.Entity;
import com.tutubastudio.graphics.Win;
import com.tutubastudio.main.Game;
import com.tutubastudio.world.Camera;
import com.tutubastudio.world.Stone;
import com.tutubastudio.world.Tree;
import com.tutubastudio.world.World;

import com.tutubastudios.itens.Material;

public class Entity {
	
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(7 * 16, 16, 16, 16);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(144, 16, 16, 16);
	public static BufferedImage TREE_OBJ = Game.spritesheet.getSprite(32, 0, 32, 32);
	public static BufferedImage STONE_OBJ = Game.spritesheet.getSprite(32, 32, 32, 32);
	
	public static BufferedImage BAU_OBJ = Game.spritesheet.getSprite(96, 16, 16, 16);
	public static BufferedImage CRATE_OBJ = Game.spritesheet.getSprite(0, 32, 16, 16);
	
	public static BufferedImage CASA1_OBJ = Game.spritesheet.getSprite(0, 240, 112, 80);
	public static BufferedImage PIT_OBJ = Game.spritesheet.getSprite(0, 208, 48, 32);
	
	
	private boolean add = false;
	// Posição da Arma
	public static BufferedImage MAPA_MP = Game.spritesheetMapa.getSprite(0, 0, 432, 320);
	public static BufferedImage LAYER_MP = Game.spritesheetLayer.getSprite(0, 0, 320, 320);
	//VARIAVEIS PADRÃO PARA TODAS ENTIDADES DO JOGO
	public double x;
	public double y;
	protected int width;
	protected int height;
	public int depth;
	protected BufferedImage sprite;
	//VALORES DE UMA HITBOX
	protected int maskxBiome, maskyBiome, mwidthBiome, mheightBiome;
	public int maskx;
	public int masky;
	public int mwidth;
	public int mheight;
	public double startX, startY, finalX, finalY;
	public double startDepthX, startDepthY, finalDepthX, finalDepthY;
	public int maskx2;
	public int masky2;
	public int mwidth2;
	public int mheight2;
	public boolean hasLine = false;
	public boolean hasLineDepth = false;
	public boolean right, up, left, down, sudeste, sudoeste, noroeste, nordeste;
	public int dir, dirSave, right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3, sudeste_dir = 4, sudoeste_dir = 5, noroeste_dir = 6, nordeste_dir = 7;
	public boolean dirBack = false;
	private boolean addAllItens = false;
	
	
	
	
	//VALORES DE UMA MASCARA DE DEPTH
	public int depthX, depthY,mwidthDepth,mheightDepth, depthX2, depthY2,mwidthDepth2, mheightDepth2;
	//VALORES DE UM RANGE DE AÇÃO 
	public int actionRangeX, actionRangeY, actionRangeWidth, actionRangeHeight;
	public double speed;
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskx = 0;
		this.masky = 0;
		this.mwidth = 0;
		this.mheight = 0;
		
		this.finalX = 0;
		this.finalY = 0;
		this.startX = 0;
		this.startY = 0;
		this.hasLine = false;
		
		this.finalDepthX = 0;
		this.finalDepthY = 0;
		this.startDepthX = 0;
		this.startDepthY = 0;
		this.hasLineDepth = false;
		
		
		
		
	}
	//DEFINIR HITBOX
	
	public void setMask(int maskx, int masky, int mwidth, int mheight, int maskx2, int masky2, int mwidth2, int mheight2) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
		
		this.maskx2 = maskx2;
		this.masky2 = masky2;
		this.mwidth2 = mwidth2;
		this.mheight2 = mheight2;
	}
	//DEFINIR BOX DE DPTH
	public void setDepth(int depth ,int depthX, int depthY, int mwidthDepth, int mheightDepth, int depthX2, int depthY2, int mwidthDepth2, int mheightDepth2) {
		this.depth = depth;
		this.depthX = depthX;
		this.depthY = depthY;
		this.mwidthDepth = mwidthDepth;
		this.mheightDepth = mheightDepth;
		
		this.depthX2 = depthX2;
		this.depthY2 = depthY2;
		this.mwidthDepth2 = mwidthDepth2;
		this.mheightDepth2 = mheightDepth2;
	}
	//DEFINIR BOX DE AÇÃO
	public void setActionRange(int actionX, int actionY, int actionWidth, int actionHeight) {
		this.actionRangeX = actionX;
		this.actionRangeY = actionY;
		this.actionRangeWidth = actionWidth;
		this.actionRangeHeight = actionHeight;
	}
	//DEFINIR TAMANHO DE UM BIOMA
	public void setBiomeTerrain(int maskxBiome, int maskyBiome, int mwidthBiome, int mheightBiome) {
		this.maskxBiome = maskxBiome;
		this.maskyBiome = maskyBiome;
		this.mwidthBiome = mwidthBiome;
		this.mheightBiome = mheightBiome;
	}
	//DEFINIR INICIO E FIM DE UMA LINHA
	public void setLine(boolean hasLine, double startX, double startY, double finalX, double finalY) {
		this.startX = startX;
		this.startY = startY;
		this.finalX = finalX;
		this.finalY = finalY;
		this.hasLine = hasLine;
		
	}
	public void setLineDepth(boolean hasLine, int depth, double startX, double startY, double finalX, double finalY) {
		this.startDepthX = startX;
		this.startDepthY = startY;
		this.finalDepthX = finalX;
		this.finalDepthY = finalY;
		this.hasLineDepth = hasLine;
		this.depth = depth;
		
	}
	//METODO PARA ORDEM DE RENDERIZAÇÃO
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>(){
		
		@Override
		public int compare(Entity n0, Entity n1) {
			if (n1.depth < n0.depth) 
				return +1;
				
			if (n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	public static void isColliddingWithObjects(Entity en) {
		if(en.sudeste == true) {
			 en.y -= en.speed;
			 en.x -= en.speed;
		 }else if(en.sudoeste == true) {
			 en.y -= en.speed;
			 en.x += en.speed;
		 }else if(en.noroeste == true) {
			 en.y += en.speed;
			 en.x += en.speed;
		 }else if(en.nordeste == true) {
			 en.y += en.speed;
			 en.x -= en.speed;
		 }else if(en.right == true) {
			 en.x -= en.speed; 
		 }else if(en.left == true) {
			 en.x += en.speed;
		 }else if(en.up == true) {
			 en.y += en.speed;
		 }else if(en.down == true) {
			 en.y -= en.speed;
		 }
		 
	}
	public static void dirBack(Entity en, int dir) {
		if(dir == 1) {
			 en.x -= en.speed;
		 }else if(dir == 2) {
			 en.x += en.speed;
		 }else if(dir == 3) {
			 en.y += en.speed;		
		 }else if(dir == 4) {
			 en.y -= en.speed;
		 }else if(dir == 5) {
			 en.y -= en.speed;
			 en.x -= en.speed; 
		 }else if(dir == 6) {
			 en.y -= en.speed;
			 en.x += en.speed; 
		 }else if(dir == 7) {
			 en.x += en.speed; 
			 en.y += en.speed;
		 }else if(dir == 8) {
			 en.x -= en.speed; 
			 en.y += en.speed;
		 }
	}
	
	public static void tchikenColliddingWithObjects(Chiken tchik) {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity en = Game.entities.get(i);
			if(en instanceof Chiken) {
				if(en == tchik) {
					if(tchik.dir == tchik.right_dir) {
						tchik.x -= tchik.speed;
						tchik.stateNum =0;
					}else if(tchik.dir == tchik.left_dir) {
						tchik.x += tchik.speed;
						tchik.stateNum =0;	
					}if(tchik.dir == tchik.up_dir) {
						tchik.y += tchik.speed;
						tchik.stateNum =0;
					}else if(tchik.dir == tchik.down_dir) {
						tchik.y -= tchik.speed;
						tchik.stateNum =0;
					}
					
				}
			}
		}
	}
	public boolean predictCollision(Entity e1, double x , double y) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx + (int)x, e1.getY() + e1.masky + (int)y, e1.mwidth, e1.mheight);
		for (int i = 0; i < Game.entities.size(); i++) {
			
			Entity e = Game.entities.get(i);

			if (e == Game.player) {
				continue;
			}
			if (e == this) {
				continue;
			}if(e instanceof Material) {
				continue;
			}
			
			Rectangle eMask = new Rectangle(e.getX() + e.maskx, e.getY() + e.masky, e.mwidth, e.mheight);
			Rectangle eMask2 = new Rectangle(e.getX() + e.maskx2, e.getY() + e.masky2, e.mwidth2, e.mheight2);
		
			if( e1Mask.intersects(eMask) || e1Mask.intersects(eMask2)) {
				return true;
				
			}
		}
		return false;
	}
	public static void isPushObjects(Entity e) {
		
		if(Game.player.right == true) {
			
			double temp = Game.random.nextInt(4);
			if(e.predictCollision(e, 5, temp) == false) {
				Game.player.x -= Game.player.speed/2;
				e.x = e.x + 5; 
				e.y = e.y + temp;
			}
			
			
		}else if(Game.player.left == true) {
			double temp = Game.random.nextInt(4);
			if(e.predictCollision(e, -5, temp) == false) {
				Game.player.x += Game.player.speed/2;
				e.x = e.x - 5; 
				e.y = e.y + temp;
			}
			
				
		}if(Game.player.up == true) {
			double temp = Game.random.nextInt(4);
			if(e.predictCollision(e, temp, -5) == false) {
				Game.player.y += Game.player.speed - 0.5;
				e.y = e.y - 5;
				e.x = e.x + temp;
			}
			 
			
			
		}else if(Game.player.down == true) {
			double temp = Game.random.nextInt(4);
			if(e.predictCollision(e, temp, 5) == false) {
				e.y = e.y + 5; 
				e.x = e.x + temp;
				Game.player.y -= Game.player.speed - 0.5;
			}
			
			
		}
	}
	public int getDepth() {
		return this.depth;
	}
	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void tick() {
		

		
		
	}
	public static boolean CheckArea(Entity en1) {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Stone) {
				//COLISÃO
				if(Entity.isCollidding(en1, e)) 
					return true;	
			}
			if(e instanceof Tree) {
				if(Entity.isCollidding(en1, e)) 
					return true;
			}
		}
		return false;
	}
	
	public static boolean isColliddingActionRange(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.actionRangeX, e1.getY() + e1.actionRangeY, e1.actionRangeWidth, e1.actionRangeHeight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.actionRangeX, e2.getY() + e2.actionRangeY, e2.actionRangeWidth, e2.actionRangeHeight);

		return e1Mask.intersects(e2Mask);
	}public static boolean isColliddingMaskRange(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.actionRangeX , e2.getY() + e2.actionRangeY, e2.actionRangeWidth, e2.actionRangeHeight);

		return e1Mask.intersects(e2Mask);
	}
	//VERIFICAR SE ESTÁ DENTRO DA ARÉA DO BIOMA
	public static boolean isColliddingBiome(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskxBiome, e2.getY() + e2.maskyBiome, e2.mwidthBiome, e2.mheightBiome);
		return e1Mask.intersects(e2Mask);
	}
	//VERIFICAR SE ESTÁ ATRÁS DE ALGO
	public static boolean isColliddingDepth(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.depthX, e1.getY()+e1.depthY, e1.mwidthDepth, e1.mheightDepth);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.depthX, e2.getY()+e2.depthY, e2.mwidthDepth, e2.mheightDepth);
		Rectangle e2Mask2 = new Rectangle(e2.getX() + e2.depthX2, e2.getY()+e2.depthY2, e2.mwidthDepth2, e2.mheightDepth2);
		
		if( e1Mask.intersects(e2Mask) || e1Mask.intersects(e2Mask2)) {
			return true;
		}else {
			return false;
		}
	}
	//VERIFICAR SE ESTÁ COLIDINDO
	public static boolean isCollidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
		Rectangle e2Mask2 = new Rectangle(e2.getX() + e2.maskx2, e2.getY() + e2.masky2, e2.mwidth2, e2.mheight2);
		

		if( e1Mask.intersects(e2Mask) || e1Mask.intersects(e2Mask2)) {
			
			return true;
		}else {
			return false;
		}
	}
	public static boolean isColliddingCoordenate(Entity e1, int x , int y) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(x, y, 1, 1);
		if( e1Mask.intersects(e2Mask)) {
			return true;
		}else {
			return false;
		}
	}

	
	//OBJETOS COM LINHAS
	public static boolean isColliddingLine(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Line2D.Double e2Mask = new Line2D.Double(e2.getX() + e2.startX, e2.getY() + e2.startY, e2.getX() + e2.finalX, e2.getY()+ e2.finalY );
		return e1Mask.intersectsLine(e2Mask);
	}public static boolean isColliddingLineDepth(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Line2D.Double e2Mask = new Line2D.Double(e2.getX() + e2.startDepthX, e2.getY() + e2.startDepthY, e2.getX() + e2.finalDepthX, e2.getY()+ e2.finalDepthY );
		if(e2.hasLine == true) {
			return e1Mask.intersectsLine(e2Mask);
		}else {
			return false;
		}
		
	}public static boolean isChooseObject(Entity e2,Player  e1) {
		
		int tempx = e2.getX() + e2.maskx;
		int tempy =  e2.getY() + e2.masky;
		//System.out.println(tempx +"a"+tempy);
		//System.out.println(e1.mx +"a"+e1.my);
		if((e1.mx >= tempx && e1.mx <= (tempx + e2.mwidth))&&(e1.my >= tempy && e1.my <= (tempy + e2.mheight))) {
			return true;
		}else {
			return false;
		}
		
	}public double calculateDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	
	

	public void render(Graphics g) {
		g.setColor(Color.red);
		
			
		//g.fillRect(this.getX() + actionRangeX - Camera.x, this.getY()+ actionRangeY  - Camera.y, actionRangeWidth, actionRangeHeight);
		//g.fillRect(this.getX() + depthX - Camera.x, this.getY()+depthY - Camera.y, mwidthDepth, mheightDepth);
		
		//g.fillRect(this.getX() + maskxBiome - Camera.x, this.getY()+ maskyBiome - Camera.y, mwidthBiome, mheightBiome);
		//g.fillRect(this.getX() + maskx2 - Camera.x, this.getY()+ masky2 - Camera.y, mwidth2, mheight2);
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Chiken) {
				
			}else {
				//g.setColor(Color.red);
				//g.fillRect(e.getX() + e.depthX - Camera.x, e.getY()+e.depthY - Camera.y, e.mwidthDepth, e.mheightDepth);
				//g.setColor(Color.blue);
				//g.fillRect(e.getX() + e.maskx - Camera.x, e.getY() + e.masky - Camera.y, e.mwidth, e.mheight);
				//g.fillRect(e.getX() + e.actionRangeX - Camera.x, e.getY()+ e.actionRangeY  - Camera.y, e.actionRangeWidth, e.actionRangeHeight);
			}
			
			// g.drawLine((int)(this.getX()  + startX- Camera.x), (int)(this.getY()+ startY - Camera.y), (int)(this.getX()+finalX- Camera.x), (int)(this.getY()+finalY- Camera.y));
			
			
		}
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		if(this.hasLine == true) {
			g.drawLine((int)(this.getX()  + startX- Camera.x), (int)(this.getY()+ startY - Camera.y), (int)(this.getX()+finalX- Camera.x), (int)(this.getY()+finalY- Camera.y));
			
			
		}
		
			
			
		
		
		
		
	}

	public void render2(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
