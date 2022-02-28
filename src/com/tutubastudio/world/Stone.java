package com.tutubastudio.world;

import java.awt.image.BufferedImage;

import com.tutubastudio.main.Game;
import com.tutubastudio.entities.Entity;
import com.tutubastudio.entities.Player;

public class Stone extends Entity {
	public int life = 3;
	
	public Stone(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
	}
	
	public void tick() {
		
		if(life == 0) {
			 destroySelf();
			   return;
		}
		CheckInterationsTree();
	}
	
	
	public void destroySelf() {
		Game.entities.remove(this);
	
	}
	
	public void CheckInterationsTree() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player) {
				if(Stone.isColliddingActionRange(this, e)) {
					if(Player.action == true) {
						life--;
					}
				}
			}
		}
		
	}

}
