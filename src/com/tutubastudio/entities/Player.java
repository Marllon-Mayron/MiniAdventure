package com.tutubastudio.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.plaf.nimbus.State;

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
	public enum states{STOP, WALK, ACTION, GET;}
	public static int stateNum;
	
	public boolean right, up, left, down;
	//DIREÇÕES POSSIVEIS
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	
	
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
	//VALORES PADÕRES=================================================================================================================
	//POSIÇÃO DO MOUSE----------------------------------------------------------------------------------------------------------------
	//PARA ENTIDADES NO MAPA
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
	public static double speed = 1;
	public static double speedBase = 1;
	//ITENS============================================================================================================================
	public static int gold = 0;
	public static int wood = 0;
	public static int apple = 0;
	public static int stick = 0;
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
		
		
		
		for(int i = 0; i < 5; i++) {
			equipedId[i] = 0;
		}
			
		//---------------------------------------------------------------------------------------------------------------------------
		
	}
	public void padrao() {
		
		
		speed = speedBase;
		attackDamage = damageBase;
		maxLife = lifeBase;
		maxMana = manaBase;
		
	}
	
	public void tick() {
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
		//RECUPERAÇÃO DE MANA
		
		
		
		//VARIAVEIS COM INICIALIZAÇÃO PADRÃO-----------------------------------------------------------------------------------------
		
		depth = (int) y;	
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
		Camera.x = (int) Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = (int) Camera.clamp(this.getY() - (Game.HEIGHT / 2.5), 0, World.HEIGHT * 16 - Game.HEIGHT);
		//---------------------------------------------------------------------------------------------------------------------------
		//CHAMAR METODOS-------------------------------------------------------------------------------------------------------------
		this.CheckInterationsChest();
		this.CheckInterationsTree();
		this.CheckInterationsStone();
		this.CheckInterationsOutsideObjects();
		this.CheckInterationsBiome();
		this.CheckInterationsAnimal();
		//this.moved();
		//this.attack();
		stateMachine();		
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
			}			
			switch(states.valueOf(acao)) {
				case STOP:
					System.out.println("parado");
					stop();
					break;
				case WALK:
				    System.out.println("movendo");
					moved();
					break;
				case ACTION:
					System.out.println("atacando");
					attack();
					break;
				case GET:
					System.out.println("pegando");
					get();
					break;
			}
	
	}
	//STATES

	//METODOS========================================================================================================================
	//ESTADOS
	public void stop() {

		int newState = stateNum;
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
		setState(exitStop());
	}
	public void moved() {
		//MOVIMETAÇÃO
		
		if(right) {
			
			movedAnimation = true;
			if(actionAnimation == false) {
				dir = right_dir;
				x += speed;
			}else {
				x += speed/2;	
			}	
		}else if (left) {
			
			movedAnimation = true;
				
			if(actionAnimation == false) {
				dir = left_dir;
				x -= speed;
			}else {
				x -= speed/2;
			}
				
		}if(up) {
			
			movedAnimation = true;
				
			if(actionAnimation == false) {
				dir = up_dir;
				y -= speed;
			}else {
				y -= speed/2;	
			}	
		} else if(down) {
			
			movedAnimation = true;
				
			if(actionAnimation == false) {
				dir = down_dir;
				y += speed;
			}else {
				y += speed/2;
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
		Game.player.actionClick = true;
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
	
	public int exitStop() {
		int newState = stateNum;
		if(getAnimation == false) {
			if(right && World.isFree((int) (x + speed), this.getY())) {
				newState = 1;
			}else if(left && World.isFree((int) (x - speed), this.getY())) {
				newState = 1;
			}else if(up && World.isFree(this.getX(), (int) (y - speed))) {
				newState = 1;
			}else if(down && World.isFree(this.getX(), (int) (y + speed))) {
				newState = 1;
			}
		}
		
		if(actionClick == true) {
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
		if(right == false && left == false && up == false && down == false) {
			newState = 0;
		}
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Entity) {
				if(e instanceof Tchiken == false && e instanceof Material == false) {
					//COLISÃO
					if (e == this) {
						continue;
					}
					if(OutsideObjects.isCollidding(this, e)) {
						isColliddingWithObjects();
						
					}if(OutsideObjects.isColliddingLine(this, e)) {
						//isColliddingWithObjects();
						
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
			action = true;
			Game.player.actionClick = false;
			
			
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
	
	public void setState(int newState) {
		if(newState != stateNum) {
			enterState = true;
			
		}
		stateNum = newState;
	}
	//CHECAR COLISÃO COM OBJETOS-----------------------------------------------------------------------------------------------------
	public void CheckInterationsAnimal() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Tchiken) {
				
				//DEPTH
				if(Tchiken.isColliddingDepth(this, e)) {
					depth = e.depth - 1;
					
				}
				//RANGE DE AÇÃO
				
			}
		}
	}
	public void CheckInterationsChest() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Bau) {
				//COLISÃO
				if(Bau.isCollidding(this, e)) {
					isColliddingWithObjects();
					
				}
				//DEPTH
				if(Bau.isColliddingDepth(this, e)) {
					depth = e.depth - 1;
					
				}
				//RANGE DE AÇÃO
				
			}else if(e instanceof Crate) {
				//COLISÃO
				if(Crate.isCollidding(this, e)) {
					isColliddingWithObjects();
					
				}
				//DEPTH
				if(Crate.isColliddingDepth(this, e)) {
					depth = e.depth - 1;
					
				}
				//RANGE DE AÇÃO
				
			}
		}
	}public void CheckInterationsTree() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Tree) {
				//COLISÃO
				if(Tree.isCollidding(this, e)) {
					isColliddingWithObjects();
					
				}
				//DEPTH
				if(Tree.isColliddingDepth(this, e)) {
					depth = e.depth-1;
					
					
				}
				//RANGE DE AÇÃO
				
			}
		}
	}public void CheckInterationsStone() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Stone) {
				//COLISÃO
				if(Stone.isCollidding(this, e)) {
					isColliddingWithObjects();
					
				}
				//DEPTH
				if(Stone.isColliddingDepth(this, e)) {
					depth = e.depth - 1;
					
				}
				//RANGE DE AÇÃO
				
			}
		}
	}public void CheckInterationsOutsideObjects() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof OutsideObjects) {
				//COLISÃO
				if(OutsideObjects.isCollidding(this, e)) {
					isColliddingWithObjects();
					
				}if(OutsideObjects.isColliddingLine(this, e)) {
					if(e.hasLine == true) {
						isColliddingWithObjects();
					}
					
					
				}
				//DEPTH
				if(OutsideObjects.isColliddingDepth(this, e)) {
					depth = e.depth - 1;
					
				}if(OutsideObjects.isColliddingLineDepth(this, e)) {
					if(e.hasLine == true) {
						depth = e.depth - 1;
					}
					
				}
				//RANGE DE AÇÃO
				
			}
		}
	}
	//CHECAR SE PLAYER ESTÁ NO BIOMA
	
	public void CheckInterationsBiome() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Biomes) {
				if(Biomes.isColliddingBiome(this, e)) {
					
					Sound.forest.loop(Sound.volumeAmbiente);
					 
				}else {
					
					Sound.forest.stop();
					
				}
			}
		}
	}
	
	
	//===============================================================================================================================
	
	//ANIMAÇÕES DO PLAYER
	public void render(Graphics g) {
		g.setColor(Color.blue);
		//g.fillRect(Game.player.mx - Camera.x, Game.player.my - Camera.y, 5, 5);
		//g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, 32, 32);
		//g.fillRect(Winmx  , Winmy , 5, 5);
		//g.fillRect(Win.Xinv  , Win.Yinv , 69, 120);
		//g.fillRect(mx  - Camera.x, my  - Camera.y, 5, 5);
		//g.fillRect(this.getX() + actionRangeX - Camera.x, this.getY()+ actionRangeY  - Camera.y, actionRangeWidth, actionRangeHeight);
		//g.fillRect(this.getX() + depthX - Camera.x, this.getY()+depthY - Camera.y, mwidthDepth, mheightDepth);
		
		//(SE NÃO TOMAR DANO)
		if (dir == right_dir) {
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
			
			
		}else if(dir == left_dir) {
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
			//
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
