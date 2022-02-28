package com.tutubastudio.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.plaf.nimbus.State;

import com.tutubastudio.entities.npc.Messages;
import com.tutubastudio.entities.npc.Npc;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.graphics.Win;
import com.tutubastudio.main.Game;
import com.tutubastudio.main.Sound;


import com.tutubastudio.world.*;
import com.tutubastudios.itens.ImageEquiped;
import com.tutubastudios.itens.Material;

public class Player extends Entity {
	//DECLARAÇÃO DE VARIAVEIS========================================================================================================
	// (Movementos)
	public boolean enterState = true;
	public enum states{STOP, WALK, ACTION, GET, TALK;}
	public static int stateNum;
	public boolean changeDir = true;
	public static int tecla;

	public boolean diagonal = true;
	//DIREÇÕES POSSIVEIS
	public int diagonal_dir = right_dir;

	//PONTO DA MASCARA DE COLISÃO
	public int colPointX;
	public int colPointY;
	public static boolean changeSpeedTemp;
	
	// (Animações)-------------------------------------------------------------------------------------------------------------------
	private boolean movedAnimation = false;
	//frames e indexs
	private int frames = 0, maxFrames = 6, index = 0, maxIndex = 5;
	private int framesAction = 0, maxFramesAction = 5, indexAction = 0, maxIndexAction = 4;
	private int framesGet = 0, maxFramesGet = 8, indexGet = 0, maxIndexGet = 3;
	private int framesStop = 0, maxFramesStop = 12, indexStop = 0, maxIndexStop = 2;
	
	//CRIANDO UMA ARRAY DE IMAGENS
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage[] rightPlayerAction;
	private BufferedImage[] leftPlayerAction;
	private BufferedImage[] upPlayerAction;
	private BufferedImage[] downPlayerAction;
	
	private BufferedImage[] rightPlayerGive;
	private BufferedImage[] leftPlayerGive;
	private BufferedImage[] upPlayerGive;
	private BufferedImage[] downPlayerGive;
	
	private BufferedImage[] rightPlayerStop;
	private BufferedImage[] leftPlayerStop;
	private BufferedImage[] upPlayerStop;
	private BufferedImage[] downPlayerStop;
	public BufferedImage[] cursorImage;
	Entity entyTemp = null;
	private BufferedImage playerDamage;
	//-------------------------------------------------------------------------------------------------------------------------------
	// Gun and Damage
	public boolean isDamage = false;
	private int damageFrames = 0;
	
	//Ações
	//SÓ ACONTECE QUANDO TERMINA A ANIMAÇÃO
	public static boolean action = false;
	public static boolean get = false;
	public static boolean interact = false;
	//CONTROLE ANIMAÇÕES DE AÇÕES 
	public boolean actionClick = false;
	public boolean actionAnimation = false;
	public boolean getAnimation = false;
	//AÇÕES DE WIN
	public boolean openInventory = false;
	public boolean openMission = false;
	//VALORES PADÕRES=================================================================================================================
	//POSIÇÃO DO MOUSE----------------------------------------------------------------------------------------------------------------
	//PARA ENTIDADES NO MAPA
	public static int cursor = 0;
	public static int posCamX = 16, posCamY = 16;
	public int mx, my;
	//PARA ENTIDADES FIXAS NA JANELA
	public int Winmx, Winmy;
	// LIFE, MANA
	public double life = 5, maxLife = 10, lifeRec = 0.005, lifeBase = 10;
	public static double damage = 1;
	public double mana = 10, maxMana = 10, manaRec = 0.005, manaBase = 10;
	public static double attackDamage = 2, damageBase = 2;;
	
	//================================================================================================================================
	//---------------------------------------------------------------------------------------------------------------------------------
	//VELOCIDADE DO PLAYER
	public static double speedTemp = 1;
	public static double speedBase = 1;
	//ITENS============================================================================================================================
	public static int gold = 0;
	public static int wood = 0;
	public static int apple = 0;
	public static int stick = 0;
	public static int chiken = 0;
	public static int cakeCoffe = 0;
	public static int key = 0;
	//EQUIPAVEIS
	//0 CAPACETE, 1 ARMADURA, 2 LUVA, 3 ARMA, 4 BOTA, 5 ESPECIAL
	public static int[] equipedId = new int[5];
	
	
	//===============================================================================================================================
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		stateNum = 0;
		//DECLARAÇÃO DE ANIMAÇÕES, QUANTIDADE DE FRAMES E LOCALIZAÇÃO DAS IMAGENS----------------------------------------------------
		rightPlayer = new BufferedImage[6];
		leftPlayer = new BufferedImage[6];
		upPlayer = new BufferedImage[6];
		downPlayer = new BufferedImage[6];
		
		rightPlayerAction = new BufferedImage[5];
		leftPlayerAction = new BufferedImage[5];
		upPlayerAction = new BufferedImage[5];
		downPlayerAction = new BufferedImage[5];
		
		rightPlayerGive = new BufferedImage[4];
		leftPlayerGive = new BufferedImage[4];
		downPlayerGive = new BufferedImage[4];
		upPlayerGive = new BufferedImage[4];
		
		rightPlayerStop = new BufferedImage[3];
		leftPlayerStop = new BufferedImage[3];
		upPlayerStop = new BufferedImage[3];
		downPlayerStop = new BufferedImage[3];
		cursorImage = new BufferedImage[10];
		speed = 1;
		
		for(int i = 0; i < 5; i++) {
			equipedId[i] = 0;
		}
			
		//---------------------------------------------------------------------------------------------------------------------------
		
	}
	public void padrao() {
		Game.player.setActionRange(10,14,12,12);
		speed = speedBase;
		speedTemp = speed;
		attackDamage = damageBase;
		maxLife = lifeBase;
		maxMana = manaBase;
		
	}
	
	public void tick() {
	/**
		System.out.println("right "+right);
		System.out.println("left "+left);
		System.out.println("up "+up);
		System.out.println("down "+down );
		System.out.println("sudeste "+sudeste);
		System.out.println("sudoeste "+sudoeste);
		System.out.println("noroeste "+noroeste);
		System.out.println("nordeste "+nordeste);
		
		///diagonal = changeDir;
	**/	
		
		
		depth = (int) y+32;
		
		
		if(checarCursor() == false) {
			cursor = 0;
		}
		
		//(LOCALIZAÇÃO X, LOCALIZAÇÃO Y, LARGURA, ALTURA)
		playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);
		//ANDANDO
		for (int i = 0; i < 6; i++) {
			upPlayer[i] = Game.spritesheetPlayer.getSprite(96 + (i * 32), 32, 32, 32);
			downPlayer[i] = Game.spritesheetPlayer.getSprite(96 + (i * 32), 0, 32, 32);
			rightPlayer[i] = Game.spritesheetPlayer.getSprite(96 + (i * 32), 64, 32, 32);
			leftPlayer[i] = Game.spritesheetPlayer.getSprite(96 + (i * 32), 96, 32, 32);
		}
		//PARADO
		for (int i = 0; i < 3; i++) {
			rightPlayerStop[i] = Game.spritesheetPlayer.getSprite(0 + (i * 32), 64, 32, 32);
			leftPlayerStop[i] = Game.spritesheetPlayer.getSprite(0 + (i * 32), 96, 32, 32);
			upPlayerStop[i] = Game.spritesheetPlayer.getSprite(0 + (i * 32), 32, 32, 32);
			downPlayerStop[i] = Game.spritesheetPlayer.getSprite(0 + (i * 32), 0, 32, 32);
		}
			
		
		//AÇÃO 1
		for (int i = 0; i < 5; i++) {
			if(equipedId[1] == 5) {
				
				rightPlayerAction[i] = Game.spritesheetPlayer.getSprite(576 + (i * 32), 64, 32, 32);
				leftPlayerAction[i] = Game.spritesheetPlayer.getSprite(576 + (i * 32), 96, 32, 32);
				upPlayerAction[i] = Game.spritesheetPlayer.getSprite(576 + (i * 32), 32, 32, 32);
				downPlayerAction[i] = Game.spritesheetPlayer.getSprite(576 + (i * 32), 0, 32, 32);
			}else {
				rightPlayerAction[i] = Game.spritesheetPlayer.getSprite(288 + (i * 32), 64, 32, 32);
				leftPlayerAction[i] = Game.spritesheetPlayer.getSprite(288 + (i * 32), 96, 32, 32);
				upPlayerAction[i] = Game.spritesheetPlayer.getSprite(288 + (i * 32), 32, 32, 32);
				downPlayerAction[i] = Game.spritesheetPlayer.getSprite(288 + (i * 32), 0, 32, 32);
			}
			
		}
			
		for (int i = 0; i < 4; i++) {
			rightPlayerGive[i] = Game.spritesheetPlayer.getSprite(448 + (i * 32), 64, 32, 32);
			leftPlayerGive[i] = Game.spritesheetPlayer.getSprite(448 + (i * 32), 96, 32, 32);
			upPlayerGive[i] = Game.spritesheetPlayer.getSprite(448 + (i * 32), 32, 32, 32);
			downPlayerGive[i] = Game.spritesheetPlayer.getSprite(448 + (i * 32), 0, 32, 32);
			
		}	
		for (int i = 0; i < 4; i++) {
			cursorImage[i] = Game.spritesheetCursor.getSprite(0+(i * 36), 0, 36, 36);
		}
		
		colPointX = this.getX()+16;
		colPointY = this.getY()+16;
		
		//VARIAVEIS COM INICIALIZAÇÃO PADRÃO-----------------------------------------------------------------------------------------
	
			
		movedAnimation = false;
		
		//BUFFS DE ITENS
		//CAPACETES
		
		//System.out.println(UI.slotMetodo);
		//INTERAÇÕES COM MOUSE=======================================================================================================
		if(UI.mouseDragged == false) {
			for(int i = 0; i < UI.moveSlot.length; i++) {
				UI.moveSlot[i] = false;
			}
		}
		
		//ANIMAÇÕES DE DANOS---------------------------------------------------------------------------------------------------------
		if (isDamage) {
			this.damageFrames++;
			if (this.damageFrames == 8) {
				this.damageFrames = 0;
				this.isDamage = false;
			}
		}
		//---------------------------------------------------------------------------------------------------------------------------														// RESETAR GAME
		if (life <= 0) {
			life = 0;
			Game.gameState = "GAME_OVER";
		}
		//---------------------------------------------------------------------------------------------------------------------------												// CONFIGURAÇAO DA CAMERA
		
		//---------------------------------------------------------------------------------------------------------------------------
		//CHAMAR METODOS-------------------------------------------------------------------------------------------------------------
		Camera();
		CheckInterationsEntity();
		CheckInterationsAnimal();
		stateMachine();	
		CheckInterationsPixel();
		//---------------------------------------------------------------------------------------------------------------------------
						
	}
	//STATES MACHINE
	public void stateMachine() {
		
		
			String acao = null;
			if (stateNum == 0){
				acao = "STOP";
			}else if(stateNum == 1) {
				acao = "WALK";
			}else if(stateNum == 2) {
				acao = "ACTION";
			}else if(stateNum == 3) {
				acao = "GET";
			}else if(stateNum == 4) {
				acao = "TALK";
			}	
			
			//System.out.println(acao);
			switch(states.valueOf(acao)) {
				case STOP:
					
					stop();
					break;
				case WALK:
					
						
					movedDiagonal(); 
					
					break;
				case ACTION:
					
					attack();
					break;
				case GET:
					
					get();
					break;
				case TALK:
					
					talk();
					break;
			}
			
	
	}
	//STATES

	//METODOS========================================================================================================================
	//ESTADOS
	public void stop() {

		if(movedAnimation == false) {
			Sound.passos.stop();
			framesStop++;
			if (framesStop == maxFramesStop) {
				framesStop = 0;
				indexStop++;
				if (indexStop > maxIndexStop) {
					indexStop = 0;
				}
			}
		}
		//RECUPERAÇÃO DE MANA E VIDA
		if(!(mana >= maxMana)) {
			if(actionAnimation == false ) {
				mana+=manaRec;
			}
			
		}if(!(life >= maxLife)) {
			
				//life+=lifeRec;
		}
		setState(exitStop());
	}
	public void movedDiagonal(){
		//MOVIMETAÇÃO
		playerWalk();
		if(noroeste == true) {
			movedAnimation = true;
			
			if(actionAnimation == false) {
				
				dir = 6;
				
				x -= speed;
				y -= speed;
			}else {
				x -= speed/2;
			}
		}else if(nordeste == true) {
			movedAnimation = true;
			
			if(actionAnimation == false) {
				
				dir = 7;
				
				x += speed;
				y -= speed;
			}else {
				x -= speed/2;
			}
		}else if(sudeste == true) {
			movedAnimation = true;
			if(actionAnimation == false) {
				dir = 4;
				
				x += speed;
				y += speed;
			}else {
				x += speed/2;	
			}		
		}else if(sudoeste == true) {
			movedAnimation = true;
			if(actionAnimation == false) {
				dir = 5;
				
				x -= speed;
				y += speed;
			}else {
				x += speed/2;	
			}		
		}else if(up) {
				
			movedAnimation = true;
			
			if(actionAnimation == false) {
				dir = up_dir;
				y -= speed;
			}else {
				y -= speed/2;	
			}		
		}else if(right) {
			
			movedAnimation = true;
			if(actionAnimation == false) {
				dir = right_dir;
				
				x += speed;
			}else {
				x += speed/2;	
			}	
		}else if(down) {
			movedAnimation = true;
			
			if(actionAnimation == false) {
				dir = down_dir;
				y += speed;
			}else {
				y += speed/2;
			}			
				
		}else if (left) {
				
			movedAnimation = true;
			
			if(actionAnimation == false) {
				
				dir = left_dir;
				
				x -= speed;
				
			}else {
				x -= speed/2;
			}	
					
		}
		
		
		//animação
		if (movedAnimation) {
			if(index == 0) {
				Sound.passos.play(Sound.volume-5);	
				
			}
			
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		setState(exitMoved());
		
	}
	public void attack() {
		
		if(Game.player.mx > Game.player.getX()+22) {
			Game.player.dir = Game.player.right_dir;
		}else if(Game.player.mx < Game.player.getX()+10) {
			Game.player.dir = Game.player.left_dir;
		}else if(Game.player.my < Game.player.getY()+23) {
			Game.player.dir = Game.player.up_dir;
		}else if(Game.player.my > Game.player.getY()+9) {
			Game.player.dir = Game.player.down_dir;
		}
		if (actionAnimation) {
			framesAction++;
			if(indexAction == 0) {
				
				Sound.soco.play(Sound.volume);
			}
			if (framesAction == maxFramesAction) {
				framesAction = 0;
				indexAction++;
			}
			
		}
		setState(exitAttack());
	}
	public void get() {
		if(getAnimation){
			framesGet++;
			if (framesGet == maxFramesGet) {
				framesGet = 0;
				indexGet++;
				if(indexGet > maxIndexGet-2) {
					get = true;
					
				}
				
			}
		}
		setState(exitGet());
	}
	public void talk() {
		getAnimation= false;
		if(movedAnimation == false) {
			Sound.passos.stop();
			framesStop++;
			if (framesStop == maxFramesStop) {
				framesStop = 0;
				indexStop++;
				if (indexStop > maxIndexStop) {
					indexStop = 0;
				}
			}
		}
		if(!(mana >= maxMana)) {
			if(actionAnimation == false ) {
				mana+=manaRec;
			}
			
		}if(!(life >= maxLife)) {
			
				//life+=lifeRec;
		}
		
		
		
		setState(exitTalk());
		
	}
	public int exitStop() {
		int newState = stateNum;
		if(getAnimation == false) {
			if(right) {
				newState = 1;
			}else if(left) {
				newState = 1;
			}else if(up) {
				newState = 1;
			}else if(down) {
				newState = 1;
			}
		}
		
		
		if(actionClick == true ) {
			if(Game.player.actionAnimation == false && Game.player.mana > 0) {
				Game.player.actionAnimation = true;
				actionClick = false;
				newState =2;
			}
		}if(getAnimation == true) {
			newState = 3;
		}
		
		return newState;
		
		
	}
	public int exitMoved() {
		
		int newState = stateNum;
		if(right == false && left == false && up == false && down == false && sudeste == false &&sudoeste == false &&noroeste == false && nordeste == false) {
			newState = 0;
		}
		
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Entity) {
				if(e instanceof Chiken == false && e instanceof Material == false) {
					//COLISÃO
					if (e == this) {
						continue;
					}
					if(Entity.isCollidding(this, e)) {
						isColliddingWithObjects(this);
						
						
					}
				}
				
			}
				
		}if(getAnimation == true) {
			newState = 3;
		}if(actionClick == true) {
			if(Game.player.actionAnimation == false && Game.player.mana > 0) {
				Game.player.actionAnimation = true;
				actionClick = false;
				newState =2;
			}
		}
			
		return newState;
	}
	public int exitGet() {
		int newState = stateNum;
		if (indexGet > maxIndexGet) {
			indexGet = 0;
			getAnimation = false;
			get = false;
			newState = 0;
		}
		
		return newState;
	}
	public int  exitAttack() {

		int newState = stateNum;
		
		if(indexAction > maxIndexAction-2) {
			Player.cameraShake(dir);
			action = true;
			Game.player.actionClick = false;
			
			
		}
		if (indexAction > maxIndexAction-1) {
			Player.cameraUnshake();
		}
		if (indexAction > maxIndexAction) {
			indexAction = 0;
			actionAnimation = false;
			//mana--;
			
			action = false;
			newState = 0;
		}
		
		return newState;
	}
	public int exitTalk() {
		int newState = stateNum;
		
		return newState;
	}
	
	public void setState(int newState) {
		if(newState != stateNum) {
			enterState = true;
			
		}
		stateNum = newState;
	}
	//CHECAR COLISÃO COM OBJETOS-----------------------------------------------------------------------------------------------------
	public void CheckInterationsNpc() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Npc) {
				
				//DEPTH
				if(Chiken.isColliddingDepth(this, e)) {
					depth = e.depth - 1;
					
				}
				//RANGE DE AÇÃO
				
			}
		}
	}
	public void playerWalk() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Bush || e instanceof Bush2) {
				if(Entity.isColliddingMaskRange(this, e)) {
					if(Player.stateNum == 1) {
						Sound.bush.play(Sound.volume-10);
						if(changeSpeedTemp == false) {
							entyTemp = e;
							speedTemp = Game.player.speed;
							Game.player.speed = Game.player.speed-0.3;
							changeSpeedTemp = true;
						}
					}
				}else {
					if(entyTemp == e) {
						changeSpeedTemp = false;
						Game.player.speed = speedTemp;
					}
				}
				
			}
		}
		
	}
	public void CheckInterationsAnimal() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Chiken) {
				
				//DEPTH
				if(Chiken.isColliddingDepth(this, e)) {
					depth = e.depth - 1;
					
				}
				//RANGE DE AÇÃO
				
			}
		}
	}
	public void CheckInterationsPixel() {
		for(int i = 0; i < Game.pixel.size(); i++) {
			Pixel e = Game.pixel.get(i);
			if(Pixel.isCollidding(this, e)) {
				isColliddingWithObjects(this);
			}
		}
	}
	public void CheckInterationsEntity() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			//OBJESTOS QUE NÃO POSSO COLIDIR
			if(e == this) {
				continue;
			}if(e instanceof Material) {
				continue;
			}
			//
			if(e instanceof Entity) {
				if(Entity.isColliddingDepth(this, e)) {
					
					
					if(e.depth < depth) {
						depth = e.depth -1;
					}
				}
				//RANGE DE AÇÃO
				
			}else {
				 
			}
		}
	}
	//METODO PARA RESSETAR CURSOR
	
	public boolean checarCursor() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(Entity.isChooseObject(e, this)) {
				return true;
			}
		}
		return false;
		
	}
	public void Camera() {
		Camera.x = (int) Camera.clamp(this.getX()+16 - (Game.WIDTH / 2), 0,World.WIDTH - Game.WIDTH);
		Camera.y = (int) Camera.clamp(this.getY()+16 - (Game.HEIGHT / 2), 0,World.HEIGHT - Game.HEIGHT);
	}
	public static void cameraShake(int dir) {
		if(dir == 0 || dir == 4 || dir == 7) {
			Camera.x = Camera.x - 1;
			
		}else if(dir == 1 || dir == 5 || dir == 6) {
			Camera.x = Camera.x + 1;
			
		}else if(dir == 2) {
			Camera.y = Camera.y + 1;
		}else if(dir == 3) {
			Camera.y = Camera.y - 1;
		}
		
		
		
		
	}
	public static void cameraUnshake() {
		Player.posCamX = 0;
		Player.posCamY = 0;
		
		
	}
	//===============================================================================================================================
	
	//ANIMAÇÕES DO PLAYER
	public void render(Graphics g) {
		g.setColor(Color.blue);
		
		
		//g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, 32, 32);
		//g.fillRect(Winmx  , Winmy , 5, 5);
		//g.fillRect(Win.Xinv  , Win.Yinv , 69, 120);
		//g.fillRect(mx  - Camera.x, my  - Camera.y, 5, 5);
		//g.fillRect(this.getX() + actionRangeX - Camera.x, this.getY()+ actionRangeY  - Camera.y, actionRangeWidth, actionRangeHeight);
		//g.fillRect(this.getX() + depthX - Camera.x, this.getY()+depthY - Camera.y, mwidthDepth, mheightDepth);
		//g.fillRect(this.getX() + this.maskx - Camera.x, this.getY() + this.masky - Camera.y, this.mwidth, this.mheight);
		//(SE NÃO TOMAR DANO)
		if (dir == right_dir || dir == sudeste_dir || dir == nordeste_dir ) {
			this.setMask(13,20,7,3,0,0,0,0);
			if (!isDamage) {
				if(actionAnimation == true) {
					g.drawImage(rightPlayerAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(equipedId[0] > 0) {g.drawImage(ImageEquiped.righthelmetAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);}
					if(equipedId[3] > 0) {g.drawImage(ImageEquiped.rightweaponAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);}

				}else if(getAnimation) {
					g.drawImage(rightPlayerGive[indexGet], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(equipedId[0] > 0) {g.drawImage(ImageEquiped.righthelmetGive[indexGet], this.getX() - Camera.x, this.getY() - Camera.y, null);}
				}else{
					if(movedAnimation == true) {
						this.setMask(15,20,7,3,0,0,0,0);
						g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
						if(equipedId[0] > 0) {g.drawImage(ImageEquiped.righthelmet[index], this.getX() - Camera.x, this.getY() - Camera.y, null);}
						if(equipedId[3] > 0) {g.drawImage(ImageEquiped.rightweapon[index], this.getX() - Camera.x, this.getY() - Camera.y, null);}

					}else {
						g.drawImage(rightPlayerStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);
						if(equipedId[0] > 0) {g.drawImage(ImageEquiped.righthelmetStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);}
						if(equipedId[3] > 0) {g.drawImage(ImageEquiped.rightweaponStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);}
					}	
				}
			}else {
				g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			
			
		}else if(dir == left_dir || dir == sudoeste_dir || dir == noroeste_dir) {
			this.setMask(13,20,7,3,0,0,0,0);
			if (!isDamage) {
				if(actionAnimation == true) {
					g.drawImage(leftPlayerAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(equipedId[0] > 0) {g.drawImage(ImageEquiped.lefthelmetAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);}
					if(equipedId[3] > 0) {g.drawImage(ImageEquiped.leftweaponAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);}

				}else if(getAnimation) {
					g.drawImage(leftPlayerGive[indexGet], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(equipedId[0] > 0) {g.drawImage(ImageEquiped.lefthelmetGive[indexGet], this.getX() - Camera.x, this.getY() - Camera.y, null);}

				}else {
					if(movedAnimation == true) {
						this.setMask(11,20,7,3,0,0,0,0);
						g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
						if(equipedId[0] > 0) {g.drawImage(ImageEquiped.lefthelmet[index], this.getX() - Camera.x, this.getY() - Camera.y, null);}
						if(equipedId[3] > 0) {g.drawImage(ImageEquiped.leftweapon[index], this.getX() - Camera.x, this.getY() - Camera.y, null);}

					}else {
						g.drawImage(leftPlayerStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);
						if(equipedId[0] > 0) {g.drawImage(ImageEquiped.lefthelmetStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);}
						if(equipedId[3] > 0) {g.drawImage(ImageEquiped.leftweaponStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);}

					}
				}
			}else {
				g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			
			
		}else if(dir == up_dir) {
			this.setMask(13,20,7,3,0,0,0,0);
			if (!isDamage) {
				if(actionAnimation == true) {
					g.drawImage(upPlayerAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(equipedId[0] > 0) {g.drawImage(ImageEquiped.uphelmetAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);}
					if(equipedId[3] > 0) {g.drawImage(ImageEquiped.upweaponAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);}

				}else if(getAnimation) {
					g.drawImage(upPlayerGive[indexGet], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(equipedId[0] > 0) {g.drawImage(ImageEquiped.uphelmetGive[indexGet], this.getX() - Camera.x, this.getY() - Camera.y, null);}

				}else {
					if(movedAnimation == true) {
						g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
						if(equipedId[0] > 0) {g.drawImage(ImageEquiped.uphelmet[index], this.getX() - Camera.x, this.getY() - Camera.y, null);}
						if(equipedId[3] > 0) {g.drawImage(ImageEquiped.upweapon[index], this.getX() - Camera.x, this.getY() - Camera.y, null);}

					}else {
						g.drawImage(upPlayerStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);
						if(equipedId[0] > 0) {g.drawImage(ImageEquiped.uphelmetStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);}
						if(equipedId[3] > 0) {g.drawImage(ImageEquiped.upweaponStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);}

					}
				}
				
			}else {
				g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			
		}else if(dir == down_dir) {
			this.setMask(13,20,7,3,0,0,0,0);
			if (!isDamage) {
				if(actionAnimation == true) {
					g.drawImage(downPlayerAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(equipedId[0] > 0) {g.drawImage(ImageEquiped.downhelmetAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);}
					if(equipedId[3] > 0) {g.drawImage(ImageEquiped.downweaponAction[indexAction], this.getX() - Camera.x, this.getY() - Camera.y, null);}

				}else if(getAnimation) {
					g.drawImage(downPlayerGive[indexGet], this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(equipedId[0] > 0) {g.drawImage(ImageEquiped.downhelmetGive[indexGet], this.getX() - Camera.x, this.getY() - Camera.y, null);}

				}else {
					if(movedAnimation == true) {
						g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
						if(equipedId[0] > 0) {g.drawImage(ImageEquiped.downhelmet[index], this.getX() - Camera.x, this.getY() - Camera.y, null);}
						if(equipedId[3] > 0) {g.drawImage(ImageEquiped.downweapon[index], this.getX() - Camera.x, this.getY() - Camera.y, null);}

					}else {
						g.drawImage(downPlayerStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);
						if(equipedId[0] > 0) {g.drawImage(ImageEquiped.downhelmetStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);}
						if(equipedId[3] > 0) {g.drawImage(ImageEquiped.downweaponStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);}

					}
				}
			//(TOMAR DANO)
			}else {
				g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
				
			
		}
	
	
	}

}
