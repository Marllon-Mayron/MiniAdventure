package com.tutubastudio.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
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
import com.tutubastudio.graphics.Spritesheet;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.graphics.Win;
import com.tutubastudio.main.Sound.Clips;
import com.tutubastudio.world.Biomes;
import com.tutubastudio.world.Camera;
import com.tutubastudio.world.OutsideObjects;
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
	
	public static int WIDTH = (Toolkit.getDefaultToolkit().getScreenSize().width*redimensionar)/100; 
	public static int HEIGHT = (Toolkit.getDefaultToolkit().getScreenSize().height*redimensionar)/100, SCALE = 10;
	
	// Imagens e Gráficos
	private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	private BufferedImage image;
	
	private Graphics g;
	// Entities
	//LISTA DE COISAS NO JOGO
	public static List<Entity> entities;
	public static List<Entity> animals;
	public static List<Enemy> enemies;
	public static List<Tree> simpleTrees;
	public static List<Stone> simpleStones;
	public static List<OutsideObjects> outObject;
	public static List<Biomes> biomes;
	public static List<Material> itensMaterial;
	public static List<Win> windowns;
	//spritesheets
	public static Spritesheet spritesheet;
	public static Spritesheet spritesheetAnimations;
	public static Spritesheet spritesheetMapa;
	public static Spritesheet spritesheetPlayer;
	public static Spritesheet spritesheetItens;
	public static Spritesheet spritesheetAnimal;
	
	
	public static World world;
	public static Player player;
	public static Random random;
	public UI ui;
	public Sound som;
	public Win win;
	public ImageEquiped imgEquip;
	
	
	// Game State
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	// Menu
	public Menu menu;
	public static int wt = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int ht = Toolkit.getDefaultToolkit().getScreenSize().height;
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
		
		
		
		UI.foundSize();
		
		image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		
		
		//LISTAS
		entities = new ArrayList<Entity>();
		animals = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		simpleTrees = new ArrayList<Tree>();
		simpleStones = new ArrayList<Stone>();
		outObject = new ArrayList<OutsideObjects>();
		biomes = new ArrayList<Biomes>();
		itensMaterial = new ArrayList<Material>();
		windowns = new ArrayList<Win>();
		
		
		//IMAGENS
		spritesheet = new Spritesheet("/spritesheet.png");
		spritesheetAnimations = new Spritesheet("/spritesheetAnimations.png");
		spritesheetPlayer = new Spritesheet("/player.png");
		spritesheetMapa = new Spritesheet("/mapa.png");
		spritesheetItens = new Spritesheet("/spritesheetItens.png");
		spritesheetAnimal = new Spritesheet("/spritesheetAnimals.png");
		
		player = new Player(0, 0, 16, 16, spritesheetPlayer.getSprite(64, 96, 32, 32));
		entities.add(player);
		
		world = new World("/level1.png");
		
		//win.setMask(0, 0, 114, 80, 0,0,0,0);
		
		win = new Win(0, 0, 0, 0, null);;
		
		entities.add(win);
		
		
		menu = new Menu();
		wt = Toolkit.getDefaultToolkit().getScreenSize().width/UI.wt;
		ht = Toolkit.getDefaultToolkit().getScreenSize().height/UI.ht;
		//Material.adicionarItens();
		imgEquip = new ImageEquiped();
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
			imagem = ImageIO.read(getClass().getResource("/icon.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
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
		//System.out.println(WIDTH);
		imgEquip.tick();
		if(UI.foundSize() == true) {
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		}
		//System.out.println("Win"+Game.player.Winmx);
		//System.out.println("Player"+Game.player.Winmy);
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
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

		/* Render do jogo */
		world.render(g);
		Collections.sort(entities, Entity.nodeSorter);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		
		
		/***/
		ui.render(g);
		g.dispose();// Limpar dados de imagem não usados
		g = bs.getDrawGraphics();
		
		//ENCAIXAR IMAGEM NA TELA CHEIA
		//g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		g.drawImage(image, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		
		g.setFont(new Font("arial", Font.BOLD, 17));
		g.setColor(Color.white);
		//g.drawString("Munição: " + player.ammo, 600, 40);
		//NOME DO ITEM
		if(UI.showDesc == true) {
			Material item = new Material(0, 0, 16, 16, null);
			item.id = UI.idItemTemp;
			item.nome = Material.nomeList[item.id];
			item.peso = Material.pesoList[item.id];
			item.tipo = Material.tipoList[item.id];
			g.setColor(Color.black);
			if(item.id != 0) {
				g.drawString("nome: "+item.nome, ((Win.Xinv+4)*wt), (Win.Yinv+13)*ht);
				//g.drawString("preço: "+item.nome, ((Win.Xinv+4)*wt), (Win.Yinv+13)*ht);
			}
			
			
		}
		if(Game.player.openInventory == true) {
			g.setColor(Color.yellow);
			g.setFont(new Font("arial", Font.BOLD, 18));
			g.drawString(": "+Player.key, ((Win.Xinv+85)*wt), (Win.Yinv+24)*ht);
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString((int)Game.player.mana+"/"+(int)Game.player.maxMana, ((Game.WIDTH*13/100))*wt, ((Game.HEIGHT*11/100)+1)*ht);
		
		
		g.setFont(new Font("arial", Font.BOLD, 17));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife, ((Game.WIDTH*15/100))*wt, ((Game.HEIGHT*8/100)+1)*ht);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.YELLOW);
		g.drawString(": "+Player.gold, ((Game.WIDTH*21/100))*wt, ((Game.HEIGHT*13/100))*ht);
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
			player.right = true;
			

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}

		// Cima e Baixo
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			if (gameState == "MENU") {
				this.menu.up = true;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
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
		}if (e.getKeyCode() == KeyEvent.VK_E) {
			 Player.interact = true;
		}
		

	}
	//SOLTAR TECLA===========================================================================================================================
	public void keyReleased(KeyEvent e) {
		// Esquerda e Direita
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
			

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
			
		}

		// Cima e Baixo
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
			

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
			
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if (gameState == "MENU") {
				this.menu.enter = true;
			}
		}if (e.getKeyCode() == KeyEvent.VK_E) {
			 Player.interact = false;
			
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
			//System.out.println("clicando esquerdo");
			Game.player.actionClick = true;
			//AO CLICAR NO "X"---------------------------------------------------------------------------------------------------------------
			UI.mouseDragged = true;
			if((Game.player.Winmy >= Win.Yinv ) && (Game.player.Winmy <= Win.Yinv + 7)) {
				if((Game.player.Winmx >= Win.Xinv+156) && (Game.player.Winmx <= Win.Xinv + 164))
					Game.player.openInventory = false;
			}
			//NÃO ESTAR NA TELA PRA ATACAR-------------------------------------------------------------------------------------------
			if(Game.player.openInventory == true) {
				if(((Game.player.Winmy > Win.Yinv+5) && (Game.player.Winmy < Win.Yinv + 125)) && ((Game.player.Winmx > Win.Xinv) && (Game.player.Winmx < Win.Xinv + 164))) {
					
				}else {
					
				}
			}
			//-----------------------------------------------------------------------------------------------------------------------
			if((Game.player.Winmy >= Win.Yinv + 5) && (Game.player.Winmy <= Win.Yinv + 20)) {
				if((Game.player.Winmx >= Win.Xinv) && (Game.player.Winmx <= Win.Xinv + 156)) {
					if(Win.invLokc == false) {
						
					}else {
						Win.invLokc = false;
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
			if(Game.player.Winmy > 4 && Game.player.Winmy < 163) {
				
				Win.Xinv = (e.getX()/(Toolkit.getDefaultToolkit().getScreenSize().width/WIDTH)-78);
				Win.Yinv = (e.getY()/(Toolkit.getDefaultToolkit().getScreenSize().height/HEIGHT)-5);
				
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
