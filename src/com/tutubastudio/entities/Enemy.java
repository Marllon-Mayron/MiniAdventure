package com.tutubastudio.entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.tutubastudio.main.Game;
import com.tutubastudio.world.Camera;
import com.tutubastudio.world.World;

public class Enemy extends Entity {

	private int speed = 0;

	private int maskX = 8, maskY = 8, maskW = 10, maskH = 10;
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;
	private int life = 4;
	private boolean isDamage = false;
	private int damageFrames = 10, damageCurrent = 0;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		// Frames de Anima��o
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(112 + 16, 16, 16, 16);
	}

	public void tick() {
		depth = 2;
		if (this.isColliddingWithPlayer() == false) {

			if (x < Game.player.getX() ) {
				x += speed;
			} else if (x > Game.player.getX() ) {
				x -= speed;
			}

			if (y < Game.player.getY()) {
				y += speed;
			} else if (y > Game.player.getY()) {
				y -= speed;
			}
		} else {
			// Estamos colidindo
			if (Game.random.nextInt(100) < 10) {
				Game.player.life -= Game.random.nextInt(3);
				Game.player.isDamage = true;
			}
		}

		// Animation
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		if(life == 1) {
			
		}
		if (life <= 0) {
			destroySelf();
			return;
		}
		
		if(isDamage) {
			this.damageCurrent++;
			if(this.damageCurrent == this.damageFrames) {
				this.damageCurrent = 0;
				this.isDamage = false;
			}
		}
	}
	//APAGAR ESSE INIMIGO
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	/**
	public void collidingBullet() {
		for (int i = 0; i < Game.bulletShoot.size(); i++) {
			Entity e = Game.bulletShoot.get(i);
			if (e instanceof BulletShoot) {
				if (Entity.isCollidding(this, e)) {
					isDamage = true;
					life--;
					Game.bulletShoot.remove(i);
					return;
				}
			}
		}
	}**/

	public boolean isColliddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskX, this.getY() + maskY, maskW, maskH);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);
	}

	public boolean isCollidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskX, ynext + maskY, maskW, maskH); // Classe que cria
																							// retangulos fict�cios para
																							// testar colis�es.
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);

			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskX, e.getY() + maskY, maskW, maskH);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}

		}

		return false;
	}

	public void render(Graphics g) {
		if(!isDamage) {
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			g.drawImage(Entity.ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
	}
}
