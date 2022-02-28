package com.tutubastudio.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

//Importação dos Packages
import com.tutubastudio.entities.Enemy;
import com.tutubastudio.entities.Entity;
import com.tutubastudio.entities.Player;
import com.tutubastudio.entities.npc.Messages;
import com.tutubastudio.entities.npc.Missoes;
import com.tutubastudio.graphics.Spritesheet;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.graphics.Win;
import com.tutubastudio.main.Sound.Clips;
import com.tutubastudio.world.Camera;
import com.tutubastudio.world.OutsideObjects;
import com.tutubastudio.world.Pixel;
import com.tutubastudio.world.Stone;
import com.tutubastudio.world.Tree;
import com.tutubastudio.world.World;
import com.tutubastudios.itens.ImageEquiped;
import com.tutubastudios.itens.Material;


public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	// Variables
	// Janela e Run Game
	
	public static JFrame frame;
	private boolean isRunning = true;
	public static boolean sizeLock = false;
	private Thread thread;
	public static int redimensionar = 25;
	
	public static int WIDTH = 0, HEIGHT = 0, SCALE = 0;
	
	// Imagens e Gráficos
	private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	private BufferedImage image;
	
	private Graphics g;
	// Entities
	//LISTA DE COISAS NO JOGO
	public static List<Entity> entities;
	public static List<Entity> animals;
	public static List<Enemy> enemies;
	public static List<OutsideObjects> outObject;
	public static List<Material> itensMaterial;
	public static List<Win> windowns;
	public static List<Pixel> pixel;

	//spritesheets
	public static Spritesheet spritesheet;
	public static Spritesheet spritesheetAnimations;
	public static Spritesheet spritesheetMapa;
	public static Spritesheet spritesheetLayer;
	public static Spritesheet spritesheetPlayer;
	public static Spritesheet spritesheetItens;
	public static Spritesheet spritesheetAnimal;
	public static Spritesheet spritesheetState;
	public static Spritesheet spritesheetNPC;
	public static Spritesheet spritesheetCursor;
	
	public static World world;
	public static Player player;
	public static Random random;
	public UI ui;
	public Sound som;
	public Win win;
	public Messages mensagem;
	public ImageEquiped imgEquip;


	
	
	// Game State
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	// Menu
	public Menu menu;
	
	/***/

	// Construtor
	public Game() {
		
		random = new Random();
		
		//PARA CHAMAR O TECLADO E MOUSE
		addKeyListener(this);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		//TELA CHEIA
		this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		//TELA AJUSTAVEL
		//this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		//INICIALIZANDO OBJETOS
		ui = new UI();
		mensagem = new Messages();
		
		
		UI.foundSize();
		
		image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		
		
		//LISTAS
		entities = new ArrayList<Entity>();
		animals = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		outObject = new ArrayList<OutsideObjects>();
		itensMaterial = new ArrayList<Material>();
		windowns = new ArrayList<Win>();
		pixel = new ArrayList<Pixel>();
		
		//IMAGENS
		spritesheet = new Spritesheet("/image/spritesheet.png");
		spritesheetAnimations = new Spritesheet("/image/spritesheetAnimations.png");
		spritesheetPlayer = new Spritesheet("/image/player.png");
		spritesheetMapa = new Spritesheet("/image/mapa.png");
		spritesheetLayer = new Spritesheet("/image/mapaLayer1.png");
		spritesheetItens = new Spritesheet("/image/spritesheetItens.png");
		spritesheetAnimal = new Spritesheet("/image/spritesheetAnimals.png");
		spritesheetState = new Spritesheet("/image/spritesheetState.png");
		spritesheetNPC = new Spritesheet("/image/spritesheetNPC.png");
		spritesheetCursor = new Spritesheet("/image/cursor0.png");
		
		player = new Player(0, 0, 16, 16, spritesheetPlayer.getSprite(64, 96, 32, 32));
		entities.add(player);
		
		world = new World("/image/level1.png");

		
		//win.setMask(0, 0, 114, 80, 0,0,0,0);
		
		win = new Win(0, 0, 0, 0, null);;
		
		entities.add(win);
		
		
		menu = new Menu();
		Missoes.textoMissoes();
		//Material.adicionarItens();
		imgEquip = new ImageEquiped();
		Game.world.WorldColision("/image/teste.png");
	}

	// Criação da Janela
	public void initFrame() {
		frame = new JFrame("Mini-Adventure");
		frame.add(this);
		frame.setResizable(false);// Usuário não irá ajustar janela
		frame.pack();
		
		//frame.setUndecorated(false);
		frame.setLocationRelativeTo(null);// Janela inicializa no centro
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Fechar o programa por completo
		frame.setVisible(true);// Dizer que estará visível
		//icon na tela
		Image imagem = null;
		
		try{
			imagem = ImageIO.read(getClass().getResource("/image/icon.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	 
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image imageCursor = toolkit.getImage(getClass().getResource("/image/null.png"));
		
		Cursor c = toolkit.createCustomCursor(imageCursor, new Point(0,0), "img");
		frame.setCursor(c);
		frame.setIconImage(imagem);
		
	}

	// Threads
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Método Principal
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	// Ticks do Jogo
	public void tick() {

		imgEquip.tick();
		mensagem.tick();
		
		if(UI.foundSize() == true) {
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		}
		if (gameState == "NORMAL") {
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}

			if (enemies.size() <= 0) {
				// System.out.println("Next Level");
				CUR_LEVEL++;
				if (CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
				}
				String newWorld = "level" + CUR_LEVEL + ".png";
				//World.restartGame(newWorld);
			}
		} else if (gameState == "GAME_OVER") {
			framesGameOver++;
			if (framesGameOver == 35) {
				framesGameOver = 0;
				if (showMessageGameOver) {
					showMessageGameOver = false;
				} else {
					showMessageGameOver = true;
				}
			}

			if (restartGame) {
				restartGame = false;
				gameState = "NORMAL";
				CUR_LEVEL = 1;
				String newWorld = "level" + CUR_LEVEL + ".png";
				World.restartGame(newWorld);
			}
		} else if (gameState == "MENU") {
			this.menu.tick();
		}
		
		
		
		
		
		
	}

	// O que será mostrado em tela
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();// Sequência de buffer para otimizar a renderização, lidando com
														// performace gráfica
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		g = image.getGraphics();// Renderizar imagens na tela
		g.setColor(new Color(0, 0, 0));
		//g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

		/* Render do jogo */
		world.render(g);
		
		Collections.sort(entities, Entity.nodeSorter);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
			
		}
		for (int i = 0; i < pixel.size(); i++) {
			Pixel e = pixel.get(i);
			//e.render(g);
			
		}
		
		
		if(Messages.messageBallon == false) {
			ui.renderUI(g);
			ui.renderInventory(g);
			ui.renderMission(g);
		}
		mensagem.render(g);

		
		g.dispose();// Limpar dados de imagem não usados
		g = bs.getDrawGraphics();
		
		//ENCAIXAR IMAGEM NA TELA CHEIA
		//g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		g.drawImage(image, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		//NOME DO ITEM
		
		if(Messages.messageBallon == false) {
			ui.render2(g);
		}for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render2(g);
			
		}
		// Game Over Configs
		if (gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			//g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.setColor(Color.white);
			g.drawString("Game Over", (WIDTH * SCALE) / 2 - 100, (HEIGHT * SCALE) / 2 - 40);
			if (showMessageGameOver) {
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("> Press Enter to Restart the Game <", (WIDTH * SCALE) / 2 - 170, (HEIGHT * SCALE) / 2);
			}

		} else if (gameState == "MENU") {
			menu.render(g);
		}
		
		
		bs.show();
		
		
	}
	
	

	// Controle de FPS
	public void run() {
		// Variables
		long lastTime = System.nanoTime();// Usa o tempo atual do computador em nano segundos, bem mais preciso
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;// Calculo exato de Ticks
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		// Ruuner Game
		while (isRunning == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				//System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop(); // Garante que todas as Threads relacionadas ao computador foram terminadas,
				// para garantir performance.

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	//PRESSIONAR A TECLA
	public void keyPressed(KeyEvent e) {
		// Esquerda e Direita
		
			if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				
					if(player.changeDir) {
						if(player.down == true) {
							player.sudeste = true;	
							player.right = true;
							
						}if(player.up == true) {
							player.nordeste = true;	
							player.right = true;
						}else {
							
							player.right = true;
							
						}
					}
				
			}else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				
					if(player.changeDir) {
						if(player.down == true) {
							player.sudoeste = true;	
							player.left = true;
						}else if(player.up == true) {
							player.noroeste = true;	
							player.left = true;
						}else {
							player.left = true;
						}
						
					}
				
				
				
				
				
			}if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				
				
					if(player.changeDir) {
						if(player.right == true) {
							player.nordeste = true;
							player.up = true;
						}else if(player.left == true){
							player.noroeste = true;
							player.up = true;
						}else {
							player.up = true;
						}
						
					}
				
				
				
				if (gameState == "MENU") {
					this.menu.up = true;
				}

			}else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				
					if(player.changeDir) {
						if(player.right == true) {
							player.sudeste = true;
							player.down = true;
						}else if(player.left == true) {
							player.sudoeste = true;
							player.down = true;
							
						}else {
							player.down = true;
						}
						
					}
					if (gameState == "MENU") {
						this.menu.down = true;
					}
			}
				
				
				
			
		
		

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "MENU";
			menu.pause = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			 Game.player.getAnimation = true;
		}if (e.getKeyCode() == KeyEvent.VK_I) {
			 if(Game.player.openInventory == true) {
				 Game.player.openInventory = false;
			 }else {
				 Game.player.openInventory = true;
			 }
		}if (e.getKeyCode() == KeyEvent.VK_M) {
			 if(Game.player.openMission == true) {
				 Game.player.openMission = false;
			 }else {
				 Game.player.openMission = true;
			 }
		}if (e.getKeyCode() == KeyEvent.VK_E) {	
						
			
		}
		

	}
	public void back() {
		if(player.dirBack == true) {
			Player.dirBack(player, player.dirSave+1);
			player.dirBack = false;
		}
	}
	//SOLTAR TECLA===========================================================================================================================
	public void keyReleased(KeyEvent e) {
		// Esquerda e Direita
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			
			if(player.sudeste == true) {
				player.sudeste = false;
				player.right = false;
				if(Player.tecla == 0) {
					back();
				}
				
			}else if(player.nordeste == true) {
				player.nordeste = false;
				player.right = false;
				if(Player.tecla == 0) {
					back();
				}
				
			}else if(player.nordeste == false && player.sudeste == false){
				player.right = false;
				if(Player.tecla == 0) {
					back();
				}
				
				
			}
			
			

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if(player.sudoeste == true) {
				player.sudoeste = false;
				player.left = false;
				if(Player.tecla == 1) {
					back();
				}
			}else if(player.noroeste == true) {
				player.noroeste = false;
				player.left = false;
				if(Player.tecla == 1) {
					back();
				}
			}else if(player.noroeste == false && player.sudoeste == false){
				player.left = false;
				if(Player.tecla == 1) {
					back();
				}
			}
			
		}

		// Cima e Baixo
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			
			if(player.nordeste == true) {
				player.nordeste = false;
				player.up = false;
				back();
			}else if(player.noroeste == true) {
				player.noroeste = false;
				player.up = false;
				back();
			}
			else if(player.nordeste == false && player.noroeste == false){
				player.up = false;
				back();
			}


		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			
			if(player.sudeste == true) {
				player.sudeste = false;
				player.down = false;
				back();
			}else if(player.sudoeste) {
				player.sudoeste = false;
				player.down = false;
				back();
			}
			else if(player.sudeste == false && player.sudoeste == false){
				player.down = false;
				back();
				
			}
				
			
			
			
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if (gameState == "MENU") {
				this.menu.enter = true;
			}
		}if (e.getKeyCode() == KeyEvent.VK_E) {	
			
			Player.interact = true;
			

		}

	}
	//BOTÕES E CLICKS DO MOUSE===============================================================================================================
	public void mouseClicked(MouseEvent e) {
		//AO CLICAR QUALQUER BOTÃO DO MOUSE
		//ABRIR INVENTARIO CLICANDO NO ICONE DA TELA
		if(e.getButton() ==  MouseEvent.BUTTON1) {
			//System.out.println("clicando esquerdo");
			
		}else if(e.getButton() ==  MouseEvent.BUTTON3) {
			//System.out.println("clicando direito");
			
			
		}
		
			
		
		
		
	}
	

	public void mousePressed(MouseEvent e) {
		//AO PRESSIONAR UM BOTÃO DO MOUSE
		//AO CLICAR NA ABA DE MOVER----------------------------------------------------------------------------------------------------------
		
		
		//INVENTARIO
		if(e.getButton() ==  MouseEvent.BUTTON1) {
			if(Game.player.openMission == true) {
				if(UI.mouseDragged == false) {
					UI.winClik = false;
				}
				
			}
			
			if(Game.player.openInventory == true || Game.player.openMission == true) {
				if(Game.player.openMission == true &&((Game.player.Winmy > Win.Ymis+5) && (Game.player.Winmy < Win.Ymis + 98)) && ((Game.player.Winmx > Win.Xmis) && (Game.player.Winmx < Win.Xmis + 164))) {
					
				}else if(Game.player.openMission == true && ((Game.player.Winmy > Win.Yinv+5) && (Game.player.Winmy < Win.Yinv + 98)) && ((Game.player.Winmx > Win.Xinv) && (Game.player.Winmx < Win.Xinv + 164))){
					if(Game.player.openMission == true) {
						
					}
				}else {
					Game.player.actionClick = true;
				}
			}else {
				Game.player.actionClick = true;
			}
			
				
			
			
			
			
			//AO CLICAR NO "X"---------------------------------------------------------------------------------------------------------------
			UI.mouseDragged = true;
			if((Game.player.Winmy >= Win.Yinv ) && (Game.player.Winmy <= Win.Yinv + 7)) {
				if((Game.player.Winmx >= Win.Xinv+156) && (Game.player.Winmx <= Win.Xinv + 164))
					Game.player.openInventory = false;
			}
			
			if((Game.player.Winmy >= Win.Ymis ) && (Game.player.Winmy <= Win.Ymis + 7)) {
				if((Game.player.Winmx >= Win.Xmis+156) && (Game.player.Winmx <= Win.Xmis + 164))
					Game.player.openMission = false;
			}
			//NÃO ESTAR NA TELA PRA ATACAR-------------------------------------------------------------------------------------------
			//-----------------------------------------------------------------------------------------------------------------------
			if((Game.player.Winmy >= Win.Yinv + 5) && (Game.player.Winmy <= Win.Yinv + 20)) {
				if((Game.player.Winmx >= Win.Xinv) && (Game.player.Winmx <= Win.Xinv + 156)) {
					if(Win.invLokc == false) {
						
					}else {
						Win.invLokc = false;
						
					}
				}
			}if((Game.player.Winmy >= Win.Ymis + 5) && (Game.player.Winmy <= Win.Ymis + 20)) {
				if((Game.player.Winmx >= Win.Xmis) && (Game.player.Winmx <= Win.Xmis + 156)) {
					if(Win.missionLokc == false) {
						
					}else {
						Win.missionLokc = false;
						
						
					}
				}
			}
			
			
		}else if(e.getButton() ==  MouseEvent.BUTTON3) {
			if(UI.useItem == false) {
				UI.useItem = true;
			}
			
		}
		
		Game.player.mx =  (e.getX()/(Toolkit.getDefaultToolkit().getScreenSize().width/WIDTH))+ Camera.x;
		Game.player.my =  (e.getY()/(Toolkit.getDefaultToolkit().getScreenSize().height/HEIGHT))+ Camera.y;
		
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------
		
	}

	public void mouseReleased(MouseEvent e) {
		//AO SOLTAR ALGUM BOTÃO DO MOUSE
		if(e.getButton() ==  MouseEvent.BUTTON1) {
			//System.out.println("clicando esquerdo");
			if(Game.player.openMission == true) {
				UI.winClik = true;
			}
			
			if(UI.mouseDragged == true && UI.change == true) {
				
				
				Material.changeSlots(UI.slotMetodo);
				
			}if(UI.dropping == true) {
				
				Material.playerDropping(UI.idItemSlot[UI.nSlot], UI.nSlot, UI.slot[UI.nSlot]);
				
				
			}
			
		}else if(e.getButton() ==  MouseEvent.BUTTON3) {
			//System.out.println("clicando direito");
			UI.useItem = false;
		}
		
		UI.mouseDragged = false;
		Win.invLokc = true;	
		Win.missionLokc = true;	
		
		
	}
	//MOVIMENTAÇÃO DO MOUSE==================================================================================================================
	public void mouseEntered(MouseEvent e) {
		//AO ENTRAR O MOUSE NA TELA
	}

	public void mouseExited(MouseEvent e) {
		//AO TIRAR O MOUSE DA TELA
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//AO  PRESSIONAR E ARRASTAR O MOUSE NA TELA
		
		Game.player.Winmx =  (e.getX()/(Toolkit.getDefaultToolkit().getScreenSize().width/WIDTH));
		Game.player.Winmy =  (e.getY()/(Toolkit.getDefaultToolkit().getScreenSize().height/HEIGHT));
		
		
		if(Game.player.openInventory == true && Win.invLokc == false) {
			if(Game.player.Winmy > 4 && Game.player.Winmy < 200) {
				Win.missionLokc = true;
				Win.Xinv = (e.getX()/(Toolkit.getDefaultToolkit().getScreenSize().width/WIDTH)-78);
				Win.Yinv = (e.getY()/(Toolkit.getDefaultToolkit().getScreenSize().height/HEIGHT)-5);
				
			}
			
		}
		if(Game.player.openMission == true && Win.missionLokc == false) {
			if(Game.player.Winmy > 4 && Game.player.Winmy < 200) {
				Win.invLokc = true;
				Win.Xmis = (e.getX()/(Toolkit.getDefaultToolkit().getScreenSize().width/WIDTH)-78);
				Win.Ymis = (e.getY()/(Toolkit.getDefaultToolkit().getScreenSize().height/HEIGHT)-5);
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		//AO MEXER O MOUSE NA TELA
		//System.out.println(Game.player.my);
		
		if(Game.player != null) {
			Game.player.mx =  (e.getX()/(Toolkit.getDefaultToolkit().getScreenSize().width/WIDTH))+ Camera.x;
			Game.player.my =  (e.getY()/(Toolkit.getDefaultToolkit().getScreenSize().height/HEIGHT))+ Camera.y;
		}
		
		if(Game.player != null) {
			Game.player.Winmx =  (e.getX()/(Toolkit.getDefaultToolkit().getScreenSize().width/WIDTH));
			Game.player.Winmy =  (e.getY()/(Toolkit.getDefaultToolkit().getScreenSize().height/HEIGHT));
		}
		

	}

}
