package com.tutubastudio.entities;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import com.tutubastudio.entities.Player.states;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.main.Game;
import com.tutubastudio.main.Sound;
import com.tutubastudio.world.Camera;
import com.tutubastudio.world.OutsideObjects;
import com.tutubastudio.world.Pixel;
import com.tutubastudio.world.Tree;
import com.tutubastudio.world.World;
import com.tutubastudios.itens.Material;

public class Chiken extends Entity {

	public double speed = 0.6;
	public boolean enterState = true;
	public enum states{STOP, WALK,ANGRY, OBSERVE, SCRATCH, ATTACK,DAMAGE, DIE, CHD, CVD;}
	public  int stateNum;
	//int newState = 0;
	//DIREÇÕES POSSIVEIS
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = Game.random.nextInt(3);
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private int framesDamage = 0, maxFramesDamage = 5, indexDamage = 0, maxIndexDamage = 3;
	private int framesScratch = 0, maxFramesScratch = 5, indexScratch = 0, maxIndexScratch = 3;
	private int framesStop = 0, maxFramesStop = 12, indexStop = 0, maxIndexStop = 2;
	private int framesState = 0, maxFramesState = 4, indexState = 0, maxIndexState = 2;
	private int framesDie = 0, maxFramesDie = 6, indexDie = 0, maxIndexDie = 2;
	private int stateIndex = 0, stateMaxIndex = 16;
	private int framesAttack = 0, maxFramesAttack = 8, indexAttack = 0, maxIndexAttack = 2;
	private boolean showStatusSignal = false;
	
	private int life = 4 , maxLife = 4;
	private boolean selected = false;
	private double damageAttack = 2;
	private boolean damageAnimation = false;
	private boolean movedAnimation = false;
	private boolean attackAnimation = false;
	private boolean dieAnimation = false;
	private boolean scratchAnimation = false;
	private boolean generateDrop = false;
	private boolean changeWalk = true;
	//POSIÇÕES PARA DIFERENTES CALCULOS DE MOVIMENTAÇÕES
	private int xwalk;
	private int ywalk;
	public int xAngry;
	public int yAngry;
	public boolean moveDiagonal = true;
	//VARIAVEIS PARA DECIDIR DIREÇÕES ALEATORIAS 
	boolean decideDirection;
	public int decide;
	//PONTO DA MASCARA DE COLISÃO
		public int colPointX;
		public int colPointY;
	private int fullTummy = 0;
	private int stateMoviment;
	
	public int maskxObj, mwidthObj, xObj, maskyObj, mheightObj, yObj;

	//IMAGENS
	
	BufferedImage[] rightStop = new BufferedImage[3];
	BufferedImage[] leftStop = new BufferedImage[3];
	BufferedImage[] upStop = new BufferedImage[3];
	BufferedImage[] downStop = new BufferedImage[3];
	
	BufferedImage[] rightAttack = new BufferedImage[3];
	BufferedImage[] leftAttack = new BufferedImage[3];
	BufferedImage[] upAttack = new BufferedImage[3];
	BufferedImage[] downAttack = new BufferedImage[3];
	
	BufferedImage[] right = new BufferedImage[4];
	BufferedImage[] left = new BufferedImage[4];
	BufferedImage[] up = new BufferedImage[4];
	BufferedImage[] down = new BufferedImage[4];
	
	BufferedImage[] rightScratch = new BufferedImage[4];
	BufferedImage[] leftScratch = new BufferedImage[4];
	BufferedImage[] upScratch = new BufferedImage[4];
	BufferedImage[] downScratch = new BufferedImage[4];
	
	BufferedImage[] rightDie = new BufferedImage[3];
	BufferedImage[] leftDie = new BufferedImage[3];
	BufferedImage[] upDie = new BufferedImage[3];
	BufferedImage[] downDie = new BufferedImage[3];
	
	BufferedImage[] rightDamage = new BufferedImage[4];
	BufferedImage[] leftDamage = new BufferedImage[4];
	BufferedImage[] upDamage = new BufferedImage[4];
	BufferedImage[] downDamage = new BufferedImage[4];
	
	BufferedImage[] exclamacao = new BufferedImage[3];
	
	//CONTAGEM
		Timer timer = new Timer();
		//DEFINIR QUNATO TEMPO
		
		
		TimerTask contagem = new TimerTask() {

			@Override
			//OQ VAI ACONTECER
			public void run() {
				if(stateNum == 0 || stateNum == 1||stateNum == 4) {
					//SE ESTIVER VIVO, GERA ALEATORIAMENTE ALGUNS COMPORTAMENTOS
					
					int n = Game.random.nextInt(100);
					if(n > 0 && n <= 20) {
						//scratch
						
						stateNum = 4;
					}else if(n > 20 && n <= 66){
						//walk
						stateNum = 1;
						
					}else{
						//stop
						stateNum = 0;
					}
				}
				
				
			}
			
			
		};
	
	public Chiken(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		//SETANDO UM TEMPO ALEATORIO PARA O OBJETO FAZER SUAS AÇÕES------------------------------------------------------------------------
		long SEGUNDOS = (800 * 10);
		int n = Game.random.nextInt(20);
		if(n > 10) {
			SEGUNDOS = (800 * n);
		}else {
			SEGUNDOS = (800 * 10);
		}
		//---------------------------------------------------------------------------------------------------------------------------------
		timer.scheduleAtFixedRate(contagem, 0, SEGUNDOS);
		
		
		
		
	}

	public void tick() {
		//VARIAVEIS PARA DIZER ONDE O OBJETO VAI ALEATORIAMENTE QUANDO ESTIVER ANDANDO
		
		colPointX = this.getX()+16;
		colPointY = this.getY()+16;
		if(isCollidding(this) == false) {
			xAngry = 0;
			yAngry = 0;
		}else {
			
		}
		depth = (int) y;
		//ANIMAÇÕES
		for (int i = 0; i < 3; i++) {
			upStop[i] = Game.spritesheetAnimal.getSprite(0 + (i * 32), 32, 32, 32);
			downStop[i] = Game.spritesheetAnimal.getSprite(0 + (i * 32), 0, 32, 32);
			rightStop[i] = Game.spritesheetAnimal.getSprite(0 + (i * 32), 96, 32, 32);
			leftStop[i] = Game.spritesheetAnimal.getSprite(0 + (i * 32), 64, 32, 32);
			
			upAttack[i] = Game.spritesheetAnimal.getSprite(352 + (i * 32), 32, 32, 32);
			downAttack[i] = Game.spritesheetAnimal.getSprite(352 + (i * 32), 0, 32, 32);
			rightAttack[i] = Game.spritesheetAnimal.getSprite(352 + (i * 32), 96, 32, 32);
			leftAttack[i] = Game.spritesheetAnimal.getSprite(352 + (i * 32), 64, 32, 32);
			
			upDie[i] = Game.spritesheetAnimal.getSprite(448 + (i * 32), 32, 32, 32);
			downDie[i] = Game.spritesheetAnimal.getSprite(448 + (i * 32), 0, 32, 32);
			rightDie[i] = Game.spritesheetAnimal.getSprite(448 + (i * 32), 64, 32, 32);
			leftDie[i] = Game.spritesheetAnimal.getSprite(448 + (i * 32), 96, 32, 32);
			
			exclamacao[i] = Game.spritesheetState.getSprite(0 + (i * 16), 16, 16, 16);
			
		}for (int i = 0; i < 4; i++) {
			
			up[i] = Game.spritesheetAnimal.getSprite(96 + (i * 32), 32, 32, 32);
			down[i] = Game.spritesheetAnimal.getSprite(96 + (i * 32), 0, 32, 32);
			right[i] = Game.spritesheetAnimal.getSprite(96 + (i * 32), 64, 32, 32);
			left[i] = Game.spritesheetAnimal.getSprite(96 + (i * 32), 96, 32, 32);
			
			upScratch[i] = Game.spritesheetAnimal.getSprite(544 + (i * 32), 32, 32, 32);
			downScratch[i] = Game.spritesheetAnimal.getSprite(544  + (i * 32), 0, 32, 32);
			rightScratch[i] = Game.spritesheetAnimal.getSprite(544  + (i * 32), 64, 32, 32);
			leftScratch[i] = Game.spritesheetAnimal.getSprite(544  + (i * 32), 96, 32, 32);
			
			upDamage[i] = Game.spritesheetAnimal.getSprite(224 + (i * 32), 32, 32, 32);
			downDamage[i] = Game.spritesheetAnimal.getSprite(224 + (i * 32), 0, 32, 32);
			rightDamage[i] = Game.spritesheetAnimal.getSprite(224 + (i * 32), 96, 32, 32);
			leftDamage[i] = Game.spritesheetAnimal.getSprite(224 + (i * 32), 64, 32, 32);
		}
		//QUANDO A VIDA ZERA, CHAMA O ESTADO DE MORTO
		if(life == 0) {
			stateNum = 7;
			
		}
		//CHAMAR OS METODOS
		CheckInterationsPlayer();
		CheckInterationsObjects();
		stateMachine();
		CheckInterationsPixel();
		
	}
	//STATES MACHINE
	public void stateMachine() {
			
		String acao = null;
		if (stateNum == 0){
			acao = "STOP";
		}else if(stateNum == 1) {
			acao = "WALK";
		}else if(stateNum == 2) {
			acao = "ANGRY";
		}else if(stateNum == 3) {
			acao = "OBSERVE";
		}else if(stateNum == 4) {
			acao = "SCRATCH";
		}else if(stateNum == 5) {
			acao = "ATTACK";
		}else if(stateNum == 6) {
			acao = "DAMAGE";
		}else if(stateNum == 7) {
			acao = "DIE";
		}else if(stateNum == 8) {
			acao = "CHD";
		}else if(stateNum == 9) {
			acao = "CVD";
		}

		//System.out.println(acao);
		switch(states.valueOf(acao)) {
			case STOP:
				
				changeWalk = false;
				stop();
				break;
			case WALK:
				
				walk();
				break;
			case ANGRY:
				isCollidding(this);
				
					angry();
				
					
				
				
				break;
			case OBSERVE:
				
				obsever();
				break;
			
			case SCRATCH:
				scratch();
				
				break;
			case ATTACK:
				
				attack();
				break;
			case DAMAGE:
				damage();
				break;
			case DIE:
				die();
				
				break;
			case CHD:
				changeHorizontalDirection();
				break;
			case CVD:
				changeVerticalDirection();
				break;
		default:
			break;
		}		
	}
	
	public void stop() {
		//FICAR PARADO
		changeWalk = true;
		movedAnimation = false;
		if(movedAnimation == false){
			framesStop++;
			if (framesStop == maxFramesStop) {
				framesStop = 0;
				indexStop++;
				if (indexStop > maxIndexStop) {
					indexStop = 0;
				}
			}
		}
		setState(exitStop());
	}
	public void walk() {
		//ANDAR POR AI
		if(changeWalk == true ) {
			xwalk = this.getX()-16+Game.random.nextInt(32);
			ywalk = this.getY()-32+Game.random.nextInt(64);
			changeWalk = false;
		}
		if (movedAnimation) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		if (y < ywalk-1) {
			dir = down_dir;
			movedAnimation = true;
			y += speed-0.4 ;
			
		}else if (y > ywalk+1) {
			dir = up_dir;
			movedAnimation = true;
			y -= speed-0.4 ;
			
		}else if (x < xwalk-1) {
			dir = right_dir;
			movedAnimation = true;
			x += speed-0.4 ;
			
		}else if (x > xwalk+1) {
			dir = left_dir;
			movedAnimation = true;
			x -= speed-0.4 ;
			
		}else {
			if(movedAnimation == true) {
				dir = Game.random.nextInt(3);
				movedAnimation = false;
				stateNum = 0;
			}
		}
		setState(exitWalk());
	}
	public void angry() {
		//CORRER ATRAS DO PLAYER
		
		movedAnimation = true;
		
		if (movedAnimation) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		if(Entity.isColliddingMaskRange(Game.player, this) == false  && damageAnimation == false && dieAnimation == false) {
			//DIREITA
			if (colPointX < Game.player.colPointX) {
				
				//SE NÃO ESTIVER COLIDINDO COM NENHUM OJBETO
				if(isCollidding(this) == false){
					//SE ESTIVER MUITO PROXIMO AUMENTA A VELOCIDADE 
					//decideDirection = false;	
					if(this.calculateDistance(colPointX, colPointY, Game.player.colPointX, Game.player.colPointY) < 30){
						x += speed +0.2;
					}else {
						x += speed ;
					}
					
					
					
					dir = right_dir;
					movedAnimation = true;
				//SE ESTIVER COLIDINDO, TROCA DE EIXO
				}else {
					if(this.calculateDistance(colPointX, colPointY, Game.player.colPointX, Game.player.colPointY) < 30){
						x -= speed +0.2;
					}else {
						x -= speed ;
					}
					isCollidding(this);
					if(stateNum != 8) {
						stateNum = 9;
					}
				}
			//ESQUERDA
			}else if (colPointX >  Game.player.colPointX) {
				if(isCollidding(this) == false) {
					//decideDirection = false;
					if(this.calculateDistance(colPointX, colPointY, Game.player.colPointX, Game.player.colPointY) < 30){
						x -= speed +0.2;
					}else {
						x -= speed ;
					}
					
					dir = left_dir;
					
					movedAnimation = true;
				}else {
					if(this.calculateDistance(colPointX, colPointY, Game.player.colPointX, Game.player.colPointY) < 30){
						x += speed +0.2;
					}else {
						x += speed ;
					}
					isCollidding(this);
					if(stateNum != 8) {
						stateNum = 9;
					}
				}
			
				
			}
			if(colPointY <  Game.player.colPointY ) {
				if(isCollidding(this) == false) {
					//decideDirection = false;
					if(this.calculateDistance(colPointX, colPointY, Game.player.colPointX, Game.player.colPointY) < 30){
						y += speed + 0.2;	
					}else {
						y += speed;
					}
					
					
					movedAnimation = true;
					
					dir = down_dir;
					
				}else {
					if(this.calculateDistance(colPointX, colPointY, Game.player.colPointX, Game.player.colPointY) < 30){
						y -= speed + 0.2;	
					}else {
						y -= speed;
					}
					isCollidding(this);
					if(stateNum != 9) {
						stateNum = 8;
					}

				}
				
				
			}else if(colPointY> Game.player.colPointY+2) {
				if(isCollidding(this) == false && CheckInterationsPixel() == false) {
					//decideDirection = false;
					if(this.calculateDistance(colPointX, colPointY, Game.player.colPointX, Game.player.colPointY) < 30){
						y -= speed + 0.2;	
					}else {
						y -= speed;
					}
					movedAnimation = true;
					
					
					dir = up_dir;
					
				}else {
					if(this.calculateDistance(colPointX, colPointY, Game.player.colPointX, Game.player.colPointY) < 30){
						y += speed + 0.2;	
					}else {
						y += speed;
					}
					if(CheckInterationsPixel() == false) {
						isCollidding(this);
						if(stateNum != 9) {
							stateNum = 8;
						}
					}
					
					
				}
				
				
			}
		}else {
			attackAnimation = true;
		}
		
		setState(exitAngry());
	}
	public void obsever() {
		//OLHAR PRA ONDE O PLAYER ANDA
		showStatusSignal = true;
		indexStop = 2;
		framesState++;
		//FAZENDO O ESTADO DE EXCLAMAÇÃO FICAR SE MOVENDO
		
		
		if(stateIndex == 8) {
			stateMoviment = 0;
		}if(stateIndex == stateMaxIndex) {
			stateIndex = 0;
			stateMoviment = 1;
		}
		
		if (framesState == maxFramesState) {
			framesState = 0;
			
			indexState++;
			stateIndex++;
			
			if (indexState >= maxIndexState) {
				indexState = maxIndexState;
				
				
			}
		}
		//==========================================
		
		if ((int)x < Game.player.getX()) {
			dir = left_dir;
		}if ((int)x > Game.player.getX()) {
			dir = right_dir;
		}if(y < Game.player.getY()-16) {
			dir = down_dir;
		}if (y > Game.player.getY()+16) {
			dir = up_dir;
		}
		setState(exitObsever());
		
	}
	
	public void scratch() {
		//CISCAR, ENCHER A BARRIGA E ENCHER A VIDA
		scratchAnimation = true;
		if(indexScratch >= maxIndexScratch) {
			if(Game.random.nextInt(100) > 70){
				stateNum = 0;
				scratchAnimation = false;
				indexScratch = 0;
			}else {
				indexScratch = 0;
			}
		}
		//ANIMAÇÃO
		framesScratch++;
		if (framesScratch == maxFramesScratch) {
			framesScratch = 0;
			if(indexScratch <= maxIndex) {
				indexScratch++;
			}
			if (indexScratch == maxIndexScratch) {
				fullTummy ++;
				if(fullTummy >= 10) {
					if(!(life+1 > maxLife)) {
						life++;
						fullTummy = 0;
					}
				}
			}
		}			
		setState(exitScratch());
	}
	public void attack() {
		//ATACANDO
		attackAnimation = true;
		if(attackAnimation == true) {
			framesAttack++;
			if (framesAttack == maxFramesAttack) {
				framesAttack = 0;
				indexAttack++;
				
				if (indexAttack > maxIndexAttack) {
					attackAnimation = false;
					if(Entity.isColliddingMaskRange(Game.player, this) == true) {
						//Game.player.life-=damageAttack;
					}
					
					indexAttack = 0;
				}
			}
		}		
		setState(exitAttack());
	}
	public void damage() {
		//SOFRENDO DANO
		
		if(damageAnimation == true) {
			if(indexDamage == 0) {
				Sound.tchikenHit.play(Sound.volume);
			}
			framesDamage++;
			if (framesDamage == maxFramesDamage) {
				framesDamage = 0;
				indexDamage++;
				
				if (indexDamage > maxIndexDamage) {
					if(life != 0) {
						life-=Player.attackDamage;
					}
					damageAnimation = false;
					
					indexDamage = 0;
				}
			}
		}
		setState(exitDamage());
	}
	public void die() {
		dieAnimation = true;
		if(dieAnimation) {
			setDepth(0,0,0,0,0,0,0,0,0);
			if(indexDie != 2) {
				framesDie++;
			}
			if (framesDie == maxFramesDie) {
				framesDie = 0;
				
				indexDie++;
				if (indexDie > maxIndexDie) {
					indexDie = 2;	
				}
			}
		}
		if(generateDrop == true) {	
			//QUANTIDADE DE DROPS POSSIVEIS
			int qntDrop = Game.random.nextInt(3);
			if(qntDrop > 0) {
				for(int i = 0; i < qntDrop; i++) {
					//VALOR DO ID DO ITEM
					int n = 0;
					if(Game.random.nextInt(100) < 50) {
						//pena
						n = 7;
						Material.droppingItens( n, this.getX()+11, this.getY()+10,2,2);
					//35%
					}else{
						//frango
						n = 8;
						Material.droppingItens( n, this.getX()+11, this.getY()+10,3,2);
					}	
				}generateDrop = false;
			}
			destroySelf();
			return;
		}
	}
	
	public int exitStop() {
		int newState = stateNum;
			if(outputDefault() == newState) {
				if((this.calculateDistance(this.getX()+16, this.getY()+16, Game.player.getX()+16, Game.player.getY()+16) < 50)&&(indexDie != 2)){
					newState = 3;
				}
				return newState;
			}else {
				//if()
				return outputDefault();
			}
		
	}
	public int exitWalk() {
		
		int newState = stateNum;
		//SE BATER EM ALGO
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Entity) {
				if(e instanceof Material == false) {
					//COLISÃO
					if (e == this) {
						continue;
					}
					if(isCollidding(this)) {
						tchikenColliddingWithObjects(this);
						newState = 0;
						
					}if(OutsideObjects.isColliddingLine(this, e)) {
						//isColliddingWithObjects();
						newState = 0;
					}
				}	
			}	
		}
		//SE O PLAYER SE APROXIMAR
		if((this.calculateDistance(this.getX()+16, this.getY()+16, Game.player.getX()+16, Game.player.getY()+16) < 50)&&(indexDie != 2)){
			newState = 3;
		}
		if(outputDefault() == newState) {
			return newState;
		}else {
			return outputDefault();
		}
	}
	public int exitAngry() {
		int newState = stateNum;
		
		if(outputDefault() == newState) {
			//SE DISTANCIAR MUITO
			if(!(this.calculateDistance(this.getX()+16, this.getY()+16, Game.player.getX()+16, Game.player.getY()+16) < 60)&&(indexDie != 2)) {
				newState = 0;
			//SE CHEGAR PERTO DO PLAYER
			}if(Entity.isColliddingMaskRange(Game.player, this) == true) {
				newState = 5;
			}
			//SE BATER EM ALGO
			if(isCollidding(this) == true){
				//angryColliddingWithObjects(this);
				//newState = 0;
			}
			return newState;
		}else {
			return outputDefault();
		}
	}
	public int exitObsever() {
		int newState = stateNum;
		
		if(outputDefault() == newState) {
			if(!(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()+24) < 50)&&(indexDie != 2)) {
				newState = 0;
				showStatusSignal = false;
				indexState = 0;
			}
			
			return newState;
		}else {
			showStatusSignal = false;
			indexState = 0;
			
			return outputDefault();
		}
		
	}
	public int exitScratch() {
		
		int newState = stateNum;
		if(outputDefault() == newState) {
			return newState;
		}else {
			return outputDefault();
		}
		
		
	}
	public int exitAttack(){
		
		int newState = stateNum;
		if(attackAnimation == false) {
			if(outputDefault() == newState) {
				if(!(Entity.isColliddingMaskRange(Game.player, this) == true)) {
					newState = 2;
				}
				return newState;
			}else {
				return outputDefault();
			}
		}
		return newState;
	}
	public int exitDamage() {
		int newState = stateNum;
		
		if(outputDefault() == newState) {
			if(damageAnimation == false) {
				newState = 5;
			}
			return newState;
		}else {
			return outputDefault();
		}
	}
	public int outputDefault() {
		//CONDIÇÕES PAD~ROES DE PARAR ESTADOS
		int newState = stateNum;
		if(dieAnimation == true) {
			//MORRER
			newState = 7;
		}else if(damageAnimation == true) {
			//SOFRER DANO
			newState = 6;
		}
		return newState;
	}
	public void setState(int newState) {
		if(newState != stateNum) {
			enterState = true;
			
		}
		stateNum = newState;
	}
	//APAGAR ESSE INIMIGO
	public void destroySelf() {
		Game.entities.remove(this);
	}
	public void CheckInterationsObjects() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Entity) {
				
				//isCollidding(this);
			}
		}
	}
	public void CheckInterationsPlayer() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player) {
				//COLISÃO
				if(Chiken.isCollidding(this, e)) {
					if(indexDie != 2 && stateNum != 2 && stateNum != 5) {
						isPushObjects(this);
					}
				}
				if(Chiken.isColliddingActionRange(this, e)) {
					
					if(Chiken.isChooseObject(this, Game.player)) {
						if(this.stateNum != 7) {
							Player.cursor =1;
						}else {
							if(generateDrop == false) {
								
								Player.cursor =2;
							}
							
						}
						
						this.selected = true;
						if(Player.action == true) {
							if(life - Player.attackDamage <= 0) {
								dieAnimation = true;
							}else {
								
								damageAnimation = true;
							}
							if(indexDie == 2) {
								generateDrop = true;
								
							}
						}
					}else {
						Player.cursor = 0;
						this.selected = false;
					}
				}else {
					
					this.selected = false;
				}
				
			}
		}
	}
	public void changeHorizontalDirection() {
		//RODAR A ANIMAÇÃO DE MOVIMENTAÇÃO
		isCollidding(this);
		movedAnimation = true;
		if (movedAnimation) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		//ANTES DE SE MOVER COMPARAR A POSIÇÃO PRA IR NA DIREÇÃO MAIS PERTO
		if(decideDirection == false) {
			if(colPointX < Game.player.colPointX) {
				decide = 1;
				decideDirection = true;
				if(dir == up_dir) {
					y += speed;
				}else {
					y -= speed;
				}
				
			}else if(colPointX > Game.player.colPointX) {
				decide = 100;
				decideDirection = true;
					
			}else {
				decideDirection = true;
				decide = Game.random.nextInt(100);
			}
			
		}else {
			if(decide > 50){
				//IR ATÉ O FINAL DA COLISÃO COM O ITEM, CONTA COM O TAMANHO DA MASK DESSE OBJETO, E QUANDO CHEGAR, ELE DESCE
				if(this.getX()+ this.maskx + this.mwidth < xObj + maskxObj) {
					if(colPointY < Game.player.colPointY) {
						dir = down_dir;
						y += speed;
						if(this.getY()+ this.masky > yObj + maskyObj + mheightObj ) {
							decideDirection = false;
							//stateNum = 2;
						}
						
					}else if(colPointY > Game.player.colPointY){
						dir = up_dir;
						y -= speed;
						if(this.getY()+ this.masky + this.mheight < yObj + maskyObj ) {
							decideDirection = false;
							stateNum = 2;
						}
					}else {
						decideDirection = false;
						stateNum = 2;
					}

				}else {
					dir = left_dir;
					x -= speed;
				}
				
			}else {
				if(this.getX()+ this.maskx > xObj + maskxObj + mwidthObj ) {
					if(colPointY < Game.player.colPointY) {
						dir = down_dir;
						y += speed;
						if(this.getY()+ this.masky > yObj + maskyObj + mheightObj ) {
							decideDirection = false;
							stateNum = 2;
						}
						
					}else if(colPointY > Game.player.colPointY){
						dir = up_dir;
						y -= speed;
						if(this.getY()+ this.masky + this.mheight < yObj + maskyObj ) {
							decideDirection = false;
							stateNum = 2;
						}
					}else {
						decideDirection = false;
						stateNum = 2;
					}	
				}else {
					dir = right_dir;
					x += speed;
				}
			}
		}	
	}
	public void changeVerticalDirection() {
		//RODAR A ANIMAÇÃO DE MOVIMENTAÇÃO
		if(isCollidding(this) == true) {
			
		}
		movedAnimation = true;
		if (movedAnimation) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}		
				
		if(decideDirection == false) {
			if(colPointY < Game.player.colPointY) {
				decide = 1;
				decideDirection = true;
				if(dir == left_dir) {
					x += speed;
				}else {
					x -= speed;
				}
				
			}else if(colPointY > Game.player.colPointY) {
				decide = 100;
				decideDirection = true;
				if(dir == left_dir) {
					x += speed;
				}else {
					x -= speed;
				}
					
			}else {
				decide = Game.random.nextInt(100);
				decideDirection = true;
				if(dir == left_dir) {
					x += speed;
				}else {
					x -= speed;
				}
			}
			
		}else {
			if(decide < 50){
				if(this.getY()+ this.masky > yObj + maskyObj + mheightObj ) {
					if(colPointX < Game.player.colPointX) {
						dir = right_dir;
						x += speed;
						if(this.getX()+ this.maskx > xObj + maskxObj + mwidthObj ) {
							decideDirection = false;
							stateNum = 2;
						}
						
					}else if(colPointX > Game.player.colPointX) {
						dir = left_dir;
						x -= speed;
						if(this.getX()+ this.maskx + this.mwidth < xObj + maskxObj) {
							decideDirection = false;
							stateNum = 2;
						}
					}else {
						decideDirection = false;
						stateNum = 2;
					}
					
				}else{
					dir = down_dir;
					y += speed;
				}
			}else {
				if(this.getY()+ this.masky + this.mheight < yObj + maskyObj ) {
					if(colPointX < Game.player.colPointX) {
						dir = right_dir;
						x += speed;
						if(this.getX()+ this.maskx > xObj + maskxObj + mwidthObj ) {
							decideDirection = false;
							stateNum = 2;
						}
						
					}else if(colPointX > Game.player.colPointX) {
						dir = left_dir;
						x -= speed;
						if(this.getX()+ this.maskx + this.mwidth < xObj + maskxObj) {
							decideDirection = false;
							stateNum = 2;
						}
					}else {
						decideDirection = false;
						stateNum = 2;
					}
				}else {
					dir = up_dir;
					y -= speed;
				}
			}
		}	
	}

	public boolean isColliddingWithPlayer() {

		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);
	}
	//COLISÃO PROPRIA DE CADA ENTIDADE
	public boolean isCollidding(Entity e1) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e1Limite = new Rectangle(e1.getX(), e1.getY(), 32, 32);
		boolean retorno = false;
		moveDiagonal = true;
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
			if( e1Limite.intersects(eMask) || e1Limite.intersects(eMask2)) {
				moveDiagonal = false;
			}
			if( e1Mask.intersects(eMask) || e1Mask.intersects(eMask2)) {
				//System.out.println("colidindo com o objeto:"+e);
				
				mwidthObj = e.mwidth;
				maskxObj = e.maskx;
				xObj = e.getX();
				
				mheightObj = e.mheight;
				maskyObj = e.masky;
				yObj = e.getY();
				return true;
				
			}else {
				
				retorno = false;
			}
		}
		return retorno;
	}
	public boolean CheckInterationsPixel() {
		for(int i = 0; i < Game.pixel.size(); i++) {
			Pixel e = Game.pixel.get(i);
			if(Pixel.isCollidding(this, e)) {
				return Pixel.isCollidding(this, e);
			}
		}
		return false;
	}
	public void render(Graphics g) {
		g.setColor(Color.blue);
		//g.fillRect(this.getX() + actionRangeX - Camera.x, this.getY()+ actionRangeY  - Camera.y, actionRangeWidth, actionRangeHeight);
		if(selected == true) {
			if(indexDie == 2) {
				if(dir == right_dir || dir == sudeste_dir || dir == nordeste_dir) {
					g.drawImage(UI.selectedObject, this.getX()+8 - Camera.x, this.getY() - Camera.y+9 , null);
					
				}else if(dir == left_dir || dir == sudoeste_dir || dir == noroeste_dir) {
					g.drawImage(UI.selectedObject, this.getX()-8 - Camera.x, this.getY() - Camera.y+9 , null);
					
				}else if(dir == up_dir) {
					g.drawImage(UI.selectedObject, this.getX() - Camera.x+1, this.getY() - Camera.y+14 , null);
				}else {
					g.drawImage(UI.selectedObject, this.getX() - Camera.x, this.getY() - Camera.y+3 , null);
				}
				
			}else {
				g.drawImage(UI.selectedObject, this.getX() - Camera.x, this.getY() - Camera.y+9 , null);
			}
			
		}
		if (dir == right_dir || dir == sudeste_dir || dir == nordeste_dir) {
			if(dieAnimation == true) {
				g.drawImage(rightDie[indexDie], this.getX() - Camera.x, this.getY() - Camera.y, null);
				this.setMask(19,13,9,10,0,0,0,0);
				
			}else {
				if(damageAnimation == true) {
					g.drawImage(rightDamage[indexDamage], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					if(attackAnimation == true) {
						g.drawImage(rightAttack[indexAttack], this.getX() - Camera.x, this.getY() - Camera.y, null);
					}else {
						if(movedAnimation == true) {		
							g.drawImage(right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}else if(movedAnimation == false && scratchAnimation == false){
							g.drawImage(rightStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}else {
							g.drawImage(rightScratch[indexScratch], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}
					}
					
				}
			}
			
			
			
		}else if(dir == left_dir || dir == sudoeste_dir || dir == noroeste_dir) {
			if(dieAnimation == true) {
				g.drawImage(leftDie[indexDie], this.getX() - Camera.x, this.getY() - Camera.y, null);
				this.setMask(4,15,9,7,0,0,0,0);
			}else {
				if(damageAnimation == true) {
					g.drawImage(leftDamage[indexDamage], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					if(attackAnimation == true) {
						g.drawImage(leftAttack[indexAttack], this.getX() - Camera.x, this.getY() - Camera.y, null);
					}else {
						if(movedAnimation == true) {
							g.drawImage(left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}else if(movedAnimation == false && scratchAnimation == false ){
							g.drawImage(leftStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}else {
							g.drawImage(leftScratch[indexScratch], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}
					}
					
				}
			}
			
			
			
		}else if(dir == up_dir) {
			if(dieAnimation == true) {
				g.drawImage(upDie[indexDie], this.getX() - Camera.x, this.getY() - Camera.y, null);
				this.setMask(15,13,8,9,0,0,0,0);
			}else {
				if(damageAnimation == true) {
					
					g.drawImage(upDamage[indexDamage], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					if(attackAnimation == true) {
						g.drawImage(upAttack[indexAttack], this.getX() - Camera.x, this.getY() - Camera.y, null);
					}else {
						if(movedAnimation == true) {
							g.drawImage(up[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}else if(movedAnimation == false && scratchAnimation == false) {
							g.drawImage(upStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}else {
							g.drawImage(upScratch[indexScratch], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}
					}
					
				}
			}
		
		}else if(dir == down_dir) {
			if(dieAnimation == true) {
				g.drawImage(downDie[indexDie], this.getX() - Camera.x, this.getY() - Camera.y, null);
				this.setMask(12,5,8,9,0,0,0,0);
			}else {
				if(damageAnimation == true) {
					g.drawImage(downDamage[indexDamage], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					if(attackAnimation == true) {
						g.drawImage(downAttack[indexAttack], this.getX() - Camera.x, this.getY() - Camera.y, null);
					}else {
						if(movedAnimation == true) {
							g.drawImage(down[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}else if(movedAnimation == false && scratchAnimation == false){
							g.drawImage(downStop[indexStop], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}else {
							g.drawImage(downScratch[indexScratch], this.getX() - Camera.x, this.getY() - Camera.y, null);
						}
					}
					
				}
			}
			
			
			
		}
		if(showStatusSignal == true) {
			
			g.drawImage(exclamacao[indexState], this.getX()+8 - Camera.x, ((this.getY()-4) +stateMoviment) - Camera.y, null);
		}
		g.setColor(Color.red);
		
		
		g.setColor(Color.blue);
		//g.fillRect(this.getX() + this.maskx - Camera.x, this.getY() + this.masky - Camera.y, this.mwidth, this.mheight);
	}
}
