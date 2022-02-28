package com.tutubastudios.itens;

import java.awt.image.BufferedImage;

import com.tutubastudio.entities.Entity;
import com.tutubastudio.entities.Player;
import com.tutubastudio.graphics.Spritesheet;
import com.tutubastudio.main.Game;

public class ImageEquiped {
	
	//CRIANDO UMA ARRAY DE IMAGENS
		public static BufferedImage[] righthelmetStop = new BufferedImage[3];
		public static BufferedImage[] lefthelmetStop = new BufferedImage[3];
		public static BufferedImage[] uphelmetStop = new BufferedImage[3];
		public static BufferedImage[] downhelmetStop = new BufferedImage[3];
		
		public static BufferedImage[] righthelmet = new BufferedImage[6];
		public static BufferedImage[] lefthelmet = new BufferedImage[6];
		public static BufferedImage[] uphelmet = new BufferedImage[6];
		public static BufferedImage[] downhelmet = new BufferedImage[6];
		
		public static BufferedImage[] righthelmetAction = new BufferedImage[5];
		public static BufferedImage[] lefthelmetAction = new BufferedImage[5];
		public static BufferedImage[] uphelmetAction = new BufferedImage[5];
		public static BufferedImage[] downhelmetAction = new BufferedImage[5];
		
		public static BufferedImage[] righthelmetGive = new BufferedImage[4];
		public static BufferedImage[] lefthelmetGive = new BufferedImage[4];
		public static BufferedImage[] uphelmetGive = new BufferedImage[4];
		public static BufferedImage[] downhelmetGive = new BufferedImage[4];
		
		public static BufferedImage[] rightweaponStop = new BufferedImage[3];
		public static BufferedImage[] leftweaponStop = new BufferedImage[3];
		public static BufferedImage[] upweaponStop = new BufferedImage[3];
		public static BufferedImage[] downweaponStop = new BufferedImage[3];
		
		public static BufferedImage[] rightweapon = new BufferedImage[6];
		public static BufferedImage[] leftweapon = new BufferedImage[6];
		public static BufferedImage[] upweapon = new BufferedImage[6];
		public static BufferedImage[] downweapon = new BufferedImage[6];
		
		public static BufferedImage[] rightweaponAction = new BufferedImage[5];
		public static BufferedImage[] leftweaponAction = new BufferedImage[5];
		public static BufferedImage[] upweaponAction = new BufferedImage[5];
		public static BufferedImage[] downweaponAction = new BufferedImage[5];
		
		
		public static Spritesheet EquipedHelmet;
		public static Spritesheet EquipedWeapon;

	public ImageEquiped() {
		
		// TODO Auto-generated constructor stub
		EquipedHelmet = new Spritesheet("/image/spritesheetEquiped.png");
		EquipedWeapon = new Spritesheet("/image/EquipedArmas.png");
		System.out.println("aa");
		
		
		//PARADO
		
			
		
	}
	public void tick() {
		for (int i = 0; i < 3; i++) {
			if(Player.equipedId[0] == 4) {
				righthelmetStop[i] = EquipedHelmet.getSprite(0 + (i * 32), 64, 32, 32);
				lefthelmetStop[i] = EquipedHelmet.getSprite(0 + (i * 32), 96, 32, 32);
				uphelmetStop[i] = EquipedHelmet.getSprite(0 + (i * 32), 32, 32, 32);
				downhelmetStop[i] = EquipedHelmet.getSprite(0 + (i * 32), 0, 32, 32);
				
			}if(Player.equipedId[3] == 5) {
				
				rightweaponStop[i] = EquipedWeapon.getSprite(0 + (i * 32), 64, 32, 32);
				leftweaponStop[i] = EquipedWeapon.getSprite(0 + (i * 32), 96, 32, 32);
				upweaponStop[i] = EquipedWeapon.getSprite(0 + (i * 32), 32, 32, 32);
				downweaponStop[i] = EquipedWeapon.getSprite(0 + (i * 32), 0, 32, 32);
			}
		}
		for (int i = 0; i < 6; i++) {
			if(Player.equipedId[0] == 4) {
				righthelmet[i] = EquipedHelmet.getSprite(96 + (i * 32), 64, 32, 32);
				lefthelmet[i] = EquipedHelmet.getSprite(96 + (i * 32), 96, 32, 32);
				uphelmet[i] = EquipedHelmet.getSprite(96 + (i * 32), 32, 32, 32);
				downhelmet[i] = EquipedHelmet.getSprite(96 + (i * 32), 0, 32, 32);
			}if(Player.equipedId[3] == 5) {
				rightweapon[i] = EquipedWeapon.getSprite(96 + (i * 32), 64, 32, 32);
				leftweapon[i] = EquipedWeapon.getSprite(96 + (i * 32), 96, 32, 32);
				upweapon[i] = EquipedWeapon.getSprite(96 + (i * 32), 32, 32, 32);
				downweapon[i] = EquipedWeapon.getSprite(96 + (i * 32), 0, 32, 32);
			}
			
		}for (int i = 0; i < 5; i++) {
			if(Player.equipedId[0] == 4) {
				righthelmetAction[i] = EquipedHelmet.getSprite(288 + (i * 32), 64, 32, 32);
				lefthelmetAction[i] = EquipedHelmet.getSprite(288 + (i * 32), 96, 32, 32);
				uphelmetAction[i] = EquipedHelmet.getSprite(288 + (i * 32), 32, 32, 32);
				downhelmetAction[i] = EquipedHelmet.getSprite(288 + (i * 32), 0, 32, 32);
			}if(Player.equipedId[3] == 5) {
				rightweaponAction[i] = EquipedWeapon.getSprite(576 + (i * 32), 64, 32, 32);
				leftweaponAction[i] = EquipedWeapon.getSprite(576 + (i * 32), 96, 32, 32);
				upweaponAction[i] = EquipedWeapon.getSprite(576 + (i * 32), 32, 32, 32);
				downweaponAction[i] = EquipedWeapon.getSprite(576 + (i * 32), 0, 32, 32);
			}
			
		}
		for (int i = 0; i < 4; i++) {
			if(Player.equipedId[0] == 4) {
				righthelmetGive[i] = EquipedHelmet.getSprite(448 + (i * 32), 64, 32, 32);
				lefthelmetGive[i] = EquipedHelmet.getSprite(448 + (i * 32), 96, 32, 32);
				uphelmetGive[i] = EquipedHelmet.getSprite(448 + (i * 32), 32, 32, 32);
				downhelmetGive[i] = EquipedHelmet.getSprite(448 + (i * 32), 0, 32, 32);
			}
			
		}
			
		
		
	}

	

}
