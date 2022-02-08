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
import com.tutubastudio.world.Tree;
import com.tutubastudio.world.World;
import com.tutubastudios.itens.Material;

public class Tchiken extends Entity {

	private double speed = 0.6;
	public boolean enterState = true;
	public enum states{STOP, WALK,RUN, OBSERVE, SCRATCH, ANGRY, DIE;}
	public  int stateNum;
	//int newState = 0;
	//DIREÇÕES POSSIVEIS
	private int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	private int dir = Game.random.nextInt(3);
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private int framesDamage = 0, maxFramesDamage = 5, indexDamage = 0, maxIndexDamage = 3;
	private int framesScratch = 0, maxFramesScratch = 5, indexScratch = 0, maxIndexScratch = 3;
	private int framesStop = 0, maxFramesStop = 12, indexStop = 0, maxIndexStop = 2;
	private int framesDie = 0, maxFramesDie = 6, indexDie = 0, maxIndexDie = 2;
	private int framesAttack = 0, maxFramesAttack = 8, indexAttack = 0, maxIndexAttack = 2;
	private boolean angry = false;
	
	
	private int life = 1, maxLife = 4;
	private boolean selected = false;
	private double damageAttack = 2;
	private boolean damageAnimation = false;
	private boolean movedAnimation = false;
	private boolean attackAnimation = false;
	private boolean dieAnimation = false;
	private boolean scratchAnimation = false;
	private boolean generateDrop = false;
	private boolean changeWalk = true;
	private int xwalk;
	private int ywalk;
	private int fullTummy = 0;
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
	
	//CONTAGEM
		Timer timer = new Timer();
		//DEFINIR QUNATO TEMPO
		
		
		TimerTask contagem = new TimerTask() {

			@Override
			//OQ VAI ACONTECER
			public void run() {
				if(life > 0) {
					
					
					//SE ESTIVER VIVO, GERA ALEATORIAMENTE ALGUNS COMPORTAMENTOS
					
					int n = Game.random.nextInt(100);
					if(n > 0 && n <= 20) {
						//scratch
						
						stateNum = 1;
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
	
	public Tchiken(int x, int y, int width, int height, BufferedImage sprite) {
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
		
		
		depth = (int) y;
		
		for (int i = 0; i < 3; i++) {
			// Frames de Animação
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
		
		
		//SE ESTIVER NO RANGE DO INIMIGO
		if((this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()+24) < 50)&&(indexDie != 2)){
			if (this.isColliddingWithPlayer() == false) {
				
				//SE NÃO TIVER TOMADO UM HIT
				if(angry == false) {
					//OLHAR PRA ONDE O PLAYER ANDA
					movedAnimation = false;
					if ((int)x < Game.player.getX()) {
						dir = left_dir;
					}if ((int)x > Game.player.getX()) {
						dir = right_dir;
					}if(y < Game.player.getY()-16) {
						dir = down_dir;
					}if (y > Game.player.getY()+16) {
						dir = up_dir;
					}
				}else if(Entity.isColliddingMaskRange(Game.player, this) == false && angry == true && damageAnimation == false && dieAnimation == false) {
					//DIREITA
					if (x < Game.player.getX()-7 ) {
						//SE NÃO ESTIVER COLIDINDO COM NENHUM OJBETO
						if(!(isCollidding((int) (x+speed), this.getY()))){
							//SE ESTIVER MUITO PROXIMO AUMENTA A VELOCIDADE 
							if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
								x += speed +0.2;
							}else {
								x += speed ;
							}
							
							dir = right_dir;
							movedAnimation = true;
						//SE ESTIVER COLIDINDO, TROCA DE EIXO
						}else {
							if (y+10 < Game.player.getY()+10) {
								if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
									y += speed + 0.2;	
								}else {
									y += speed;
								}
								if(Game.player.dir == Game.player.down_dir) {
									dir = down_dir;
								}
							}else if (y+10 > Game.player.getY()+14) {
								if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
									y -= speed + 0.2;	
								}else {
									y -= speed;
								}
								if(Game.player.dir == Game.player.up_dir) {
									dir = up_dir;
								}
							}
						}
					//ESQUERDA
					}else if (x > Game.player.getX()+9 ) {
						if(!(isCollidding((int) (x-speed), this.getY()))) {
							
							if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
								x -= speed +0.2;
							}else {
								x -= speed ;
							}
							dir = left_dir;
							
							movedAnimation = true;
						}else {
							if (y+10 < Game.player.getY()+10) {
								if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
									y += speed + 0.2;	
								}else {
									y += speed;
								}
								if(Game.player.dir == Game.player.down_dir) {
									dir = down_dir;
								}
							}else if (y+10 > Game.player.getY()+14) {
								if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
									y -= speed + 0.2;	
								}else {
									y -= speed;
								}
								if(Game.player.dir == Game.player.up_dir) {
									dir = up_dir;
								}
							}
						}
					
						
					}
					
					
					if(y+10 < Game.player.getY()+10) {
						if(!(isCollidding( this.getX(), (int) (y+speed)))) {
							
							if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
								y += speed + 0.2;	
							}else {
								y += speed;
							}
							
							
							movedAnimation = true;
							if(Game.player.dir == Game.player.down_dir) {
								dir = down_dir;
							}
							
							
						}else {
							if (x > Game.player.getX()+9 ) {
								if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
									x -= speed +0.2;
								}else {
									x -= speed ;
								}
								dir = left_dir;
							}else if (x < Game.player.getX()-7 ) {
								if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
									x += speed +0.2;
								}else {
									x += speed ;
								}
								
								dir = right_dir;
							}
						}
						
						
					}else if(y+10 > Game.player.getY()+14) {
						if(! (isCollidding( this.getX(), (int) (y+speed)))) {
							//System.out.println("bbbbbb");
							if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
								y -= speed + 0.2;	
							}else {
								y -= speed;
							}
							
								
							movedAnimation = true;
							
							if(Game.player.dir == Game.player.up_dir) {
								dir = up_dir;
							}
						}else {
							if (x > Game.player.getX()) {
								if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
									x -= speed +0.2;
								}else {
									x -= speed ;
								}
								dir = left_dir;
							}else if (x < Game.player.getX() ) {
								if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 30){
									x += speed +0.2;
								}else {
									x += speed ;
								}
								
								dir = right_dir;
							}else {
								
							}
						}
						
						
					}
				}else {
					attackAnimation = true;
				}
				
			
			
			}
					
		}else {
			
			
		}
		
		

		// Animation
		
		if(life == 0) {
			dieAnimation = true;
		}
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
		
		//ATACANDO
		if(attackAnimation == true) {
			framesAttack++;
			if (framesAttack == maxFramesAttack) {
				framesAttack = 0;
				indexAttack++;
				
				if (indexAttack > maxIndexAttack) {
					attackAnimation = false;
					if(Entity.isColliddingMaskRange(Game.player, this) == true) {
						Game.player.life-=damageAttack;
					}
					
					indexAttack = 0;
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
		
		
		
		CheckInterationsPlayer();
		CheckInterationsObjects();
		stateMachine();
		
	}
	//STATES MACHINE
	public void stateMachine() {
			
		String acao = null;
		if (stateNum == 0){
			acao = "STOP";
		}else if(stateNum == 1) {
			acao = "WALK";
		}else if(stateNum == 2) {
			acao = "RUN";
		}else if(stateNum == 3) {
			acao = "OBSERVE";
		}else if(stateNum == 4) {
			acao = "SCRATCH";
		}else if(stateNum == 5) {
			acao = "ANGRY";
		}
		
		switch(states.valueOf(acao)) {
			case STOP:
				//System.out.println("parado");
				changeWalk = false;
				stop();
				break;
			case WALK:
				//System.out.println("movendo");
				walk();
				break;
			case RUN:
				//System.out.println("correndo");
				//moved();
				break;
			case OBSERVE:
				//System.out.println("OLHANDO");
				//moved();
				break;
			
			case SCRATCH:
				//System.out.println("ciscando");
				//moved();
				break;
			case ANGRY:
				//System.out.println("putinha");
				//moved();
				break;
			case DIE:
				//System.out.println("morto");
				//moved();
				break;
		}		
	}
	public void stop() {
		changeWalk = true;
		angry = false;
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
	}
	public void walk() {
		if(changeWalk == true) {
			xwalk = this.getX()-8+Game.random.nextInt(16);
			ywalk = this.getY()-8+Game.random.nextInt(16);
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
				
			}
		}
		setState(exitWalk());
		
		
	}
	public void scratch() {
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
		if(scratchAnimation == true) {
			if(indexScratch == 0) {
				
			}
			
			framesScratch++;
			if (framesScratch == maxFramesScratch) {
				framesScratch = 0;
				if(indexScratch <= maxIndex) {
					indexScratch++;
				}
				
				
				if (indexScratch == maxIndexScratch) {
					
					fullTummy ++;
					if(fullTummy >= 10) {
						System.out.println("cheio");
						if(!(life+1 > maxLife)) {
							life++;
							fullTummy = 0;
							
						}
						
					}
					//indexScratch=0;
				}else {
					
				}
			}
		}
	}
	public void observe() {
		
	}
	public void die() {
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
	}
	public int exitStop() {
		int newState = stateNum;
		return newState;
		
	}
	public int exitWalk() {
		
		int newState = stateNum;
		
		
		if(isCollidding((int) (y+speed), this.getY())) {
			newState = 0;
		}else if((isCollidding((int) (y-speed), this.getY()))) {
			newState = 0;
		}
		if(isCollidding((int) (x+speed), this.getY())) {
			newState = 0;
		}else if(isCollidding((int) (x-speed), this.getY())){
			newState = 0;
		}
		
		
			
		
		
		return newState;
		
	}public void setState(int newState) {
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
				
				isCollidding((int) (e.x), (int)e.y);
			}
				//COLISÃO
		}
		
		
	}
	public void CheckInterationsPlayer() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player) {
				//COLISÃO
				if(Tchiken.isCollidding(this, e)) {
					if(indexDie != 2) {
						isPushObjects(this);
					}
				}
				if(Tchiken.isColliddingActionRange(this, e)) {
					
					if(Tchiken.isChooseObject(this, Game.player)) {
						this.selected = true;
						if(Player.action == true) {
							if(life - Player.attackDamage <= 0) {
								dieAnimation = true;
							}else {
								angry = true;
								damageAnimation = true;
							}
							if(indexDie == 2) {
								generateDrop = true;
							}
						}
					}else {
						this.selected = false;
					}
				}else {
					this.selected = false;
				}
				
			}
		}
	}
	public boolean isColliddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);
	}

	public boolean isCollidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, mwidth, mheight); // Classe que cria
																							// retangulos fictícios para
																							// testar colisões.
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);

			//CONDIÇÕES A SEREM IGNORADAS
			if (e == Game.player) {
				//continue;
			}
			if (e == this) {
				continue;
			}if(Material.check(e)) {
				continue;
			}
			
			Rectangle targetEnemy = new Rectangle(e.getX() + e.maskx, e.getY() + e.masky, e.mwidth, e.mheight);
			if (enemyCurrent.intersects(targetEnemy)) {
				
				return true;
			}if(Tchiken.isColliddingLine(this, e)) {
				return true;
			}

		}

		return false;
	}

	public void render(Graphics g) {
		g.setColor(Color.blue);
		//g.fillRect(this.getX() + actionRangeX - Camera.x, this.getY()+ actionRangeY  - Camera.y, actionRangeWidth, actionRangeHeight);
		if(selected == true) {
			if(indexDie == 2) {
				if(dir == right_dir) {
					g.drawImage(UI.selectedObject, this.getX()+8 - Camera.x, this.getY() - Camera.y+9 , null);
					
				}else if(dir == left_dir) {
					g.drawImage(UI.selectedObject, this.getX()-8 - Camera.x, this.getY() - Camera.y+9 , null);
					
				}else if(dir == up_dir) {
					g.drawImage(UI.selectedObject, this.getX() - Camera.x, this.getY() - Camera.y , null);
				}else {
					g.drawImage(UI.selectedObject, this.getX()-8 - Camera.x, this.getY() - Camera.y+9 , null);
				}
				
			}else {
				g.drawImage(UI.selectedObject, this.getX() - Camera.x, this.getY() - Camera.y+9 , null);
			}
			
		}
		if (dir == right_dir) {
			if(dieAnimation == true) {
				System.out.println("aaaaaaaaa");
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
			
			
			
		}else if(dir == left_dir) {
			if(dieAnimation == true) {
				System.out.println("aaaa");
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
				System.out.println("aaaa");
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
				System.out.println("aaaa");
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
		g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky - Camera.y, mwidth, mheight);
		
	}
}
