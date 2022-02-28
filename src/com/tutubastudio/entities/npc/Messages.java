package com.tutubastudio.entities.npc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tutubastudio.graphics.UI;
import com.tutubastudio.main.Game;
import com.tutubastudio.world.Camera;

public class Messages {
	public static boolean messageBallon = false;
	public static boolean zoom = false;
	public static boolean playerTalk = false;
	
	public static BufferedImage ballon;
	public static int xMensagem;
	public static int yMensagem;
	public static int temp2  = Game.redimensionar;
	public Messages() {
		ballon = UI.spritesheetUI.getSprite(0, 32, 80, 48);
		
	}public void tick() {
		if(messageBallon == true) {
			if(zoom == false) {
				int temp  = Game.redimensionar/2;
				
				
				while(!(Game.redimensionar < temp)) {
					Game.redimensionar--;
					UI.foundSize();
				}
				
				zoom = true;
			}
			
		}else {
			Game.redimensionar = temp2;	
			UI.foundSize();
			zoom = false;
		}
	}//METODOS PRA ORGANIZAR FOMRATOS DE TEXTO
	public static void drawString (Graphics g, String text, int x, int y) {
        for (String line: text.split ("\n")) {
        	  g.drawString (line, x, y += g.getFontMetrics (). getHeight ());
        	
        }
          
    }
	public static void drawtabString (Graphics g, String text, int x, int y) {
        for (String line: text.split ("\t"))
            g.drawString (line, x += g.getFontMetrics (). getHeight (), y);
    }
	public void render(Graphics g) {
		if(Messages.messageBallon == true) {
			g.drawImage(Messages.ballon,xMensagem, yMensagem, null);	
		}
	}


}
