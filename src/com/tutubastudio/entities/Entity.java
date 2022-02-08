
package com.tutubastudio.entities;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.image.BufferedImage;
import java.util.Comparator;

import com.tutubastudio.entities.Entity;
import com.tutubastudio.graphics.Win;
import com.tutubastudio.main.Game;
import com.tutubastudio.world.Camera;
import com.tutubastudio.world.Stone;
import com.tutubastudio.world.Tree;
import com.tutubastudios.itens.Material;

public class Entity {
	
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(7 * 16, 16, 16, 16);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(144, 16, 16, 16);
	public static BufferedImage TREE_OBJ = Game.spritesheet.getSprite(32, 0, 32, 32);
	public static BufferedImage STONE_OBJ = Game.spritesheet.getSprite(32, 32, 32, 32);
	
	public static BufferedImage BAU_OBJ = Game.spritesheet.getSprite(96, 16, 16, 16);
	public static BufferedImage CRATE_OBJ = Game.spritesheet.getSprite(0, 32, 16, 16);
	
	public static BufferedImage FANCEWOOD_down_OBJ1 = Game.spritesheet.getSprite(144, 32, 16, 16);
	public static BufferedImage FANCEWOOD_down_OBJ2s = Game.spritesheet.getSprite(160, 32, 16, 16);
	public static BufferedImage FANCEWOOD_down_OBJ2 = Game.spritesheet.getSprite(144, 16, 16, 16);
	public static BufferedImage FANCEWOOD_down_OBJ3 = Game.spritesheet.getSprite(176, 32, 16, 16);
	public static BufferedImage FANCEWOOD_left_OBJ1 = Game.spritesheet.getSprite(208, 48, 16, 16);
	public static BufferedImage FANCEWOOD_left_OBJ2 = Game.spritesheet.getSprite(208, 64, 16, 16);
	public static BufferedImage FANCEWOOD_left_OBJ3 = Game.spritesheet.getSprite(208, 80, 16, 16);
	public static BufferedImage FANCEWOOD_right_OBJ1 = Game.spritesheet.getSprite(192, 48, 16, 16);
	public static BufferedImage FANCEWOOD_right_OBJ2 = Game.spritesheet.getSprite(192, 64, 16, 16);
	public static BufferedImage FANCEWOOD_right_OBJ3 = Game.spritesheet.getSprite(192, 80, 16, 16);
	public static BufferedImage FANCEWOOD_DIAGONAL_OBJ = Game.spritesheet.getSprite(176, 48, 16, 16);
	public static BufferedImage FANCEWOOD_leftCORNER_OBJ = Game.spritesheet.getSprite(192, 32, 16, 16);
	public static BufferedImage FANCEWOOD_rightCORNER_OBJ = Game.spritesheet.getSprite(208, 32, 16, 16);
	
	
	// Posição da Arma
	public static BufferedImage MAPA_MP = Game.spritesheetMapa.getSprite(0, 0, 320, 320);
	//VARIAVEIS PADRÃO PARA TODAS ENTIDADES DO JOGO
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	public int depth;

	protected BufferedImage sprite;
	//VALORES DE UMA HITBOX
	protected int maskxBiome, maskyBiome, mwidthBiome, mheightBiome;
	protected int maskx, masky, mwidth, mheight;
	protected double startX, startY, finalX, finalY;
	protected double startDepthX, startDepthY, finalDepthX, finalDepthY;
	protected int maskx2, masky2, mwidth2, mheight2;
	protected boolean hasLine = false;
	protected boolean hasLineDepth = false;
	
	private boolean addAllItens = false;
	
	
	
	
	//VALORES DE UMA MASCARA DE DEPTH
	public int depthX, depthY,mwidthDepth,mheightDepth, depthX2, depthY2,mwidthDepth2, mheightDepth2;
	//VALORES DE UM RANGE DE AÇÃO 
	public int actionRangeX, actionRangeY, actionRangeWidth, actionRangeHeight;
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
	public static void isColliddingWithObjects() {
		if(Game.player.right == true) {
			Game.player.x -= Player.speed;
			
		}else if(Game.player.left == true) {
			Game.player.x += Player.speed;
				
		}if(Game.player.up == true) {
			Game.player.y += Player.speed;
			
		}else if(Game.player.down == true) {
			Game.player.y -= Player.speed;
			
		}
	}public static void isPushObjects(Entity e) {
		
		if(Game.player.right == true) {
			
			double temp = Game.random.nextInt(4) + e.y;
			Game.player.x -= Player.speed/2;
			e.x = e.x + 5; 
			e.y = temp;
			
		}else if(Game.player.left == true) {
			double temp = Game.random.nextInt(4) + e.y;
			Game.player.x += Player.speed/2;
			e.x = e.x - 5; 
			e.y = temp;
				
		}if(Game.player.up == true) {
			double temp = Game.random.nextInt(4) + e.x;
			Game.player.y += Player.speed - 0.5;
			e.y = e.y - 5; 
			e.x = temp;
			
		}else if(Game.player.down == true) {
			double temp = Game.random.nextInt(4) + e.x;
			e.y = e.y + 5; 
			Game.player.y -= Player.speed - 0.5;
			e.x = temp;
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
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.actionRangeX, e2.getY() + e2.actionRangeY, e2.actionRangeWidth, e2.actionRangeHeight);

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
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		if(this.hasLine == true) {
			g.drawLine((int)(this.getX()  + startX- Camera.x), (int)(this.getY()+ startY - Camera.y), (int)(this.getX()+finalX- Camera.x), (int)(this.getY()+finalY- Camera.y));
			
			
		}
		
			for (int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				//g.fillRect(e.getX() + e.maskx - Camera.x, e.getY() + e.masky - Camera.y, e.mwidth, e.mheight);
				// g.drawLine((int)(this.getX()  + startX- Camera.x), (int)(this.getY()+ startY - Camera.y), (int)(this.getX()+finalX- Camera.x), (int)(this.getY()+finalY- Camera.y));
				
				
			}
			
		
		
		
		
	}
}
