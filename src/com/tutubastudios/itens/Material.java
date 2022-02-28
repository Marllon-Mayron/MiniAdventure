package com.tutubastudios.itens;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.tutubastudio.entities.Entity;
import com.tutubastudio.entities.Player;
import com.tutubastudio.graphics.Spritesheet;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.graphics.Win;
import com.tutubastudio.main.Game;
import com.tutubastudio.main.Sound;
import com.tutubastudio.world.Bau;
import com.tutubastudio.world.Camera;
import com.tutubastudio.world.Tree;

public class Material extends Entity{
	public int id;
	public String nome;
	public double peso;
	public String tipo;
	public boolean gived = false;
	public static int itensTotais = 11;
	public static String nomeList[] = new String[itensTotais];
	public static double pesoList[] = new double[itensTotais];
	public static String tipoList[] = new String[itensTotais];
	//variavel para saber se o item equipado ja está contando com os buffs
	public static boolean[] buff = new boolean[6];
	//variaveis guardadas para incrementar nos status do player
	public static double maxLife[] = new double[6];
	public static double maxMana[] = new double[6];
	public static double maxDamageAttack[] = new double[6];
	public static double maxSpeed[] = new double[6];
	
	
	
	
	public Material(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);	
		
		nomeList[0] = "";
		nomeList[1] = "Madeira";
		nomeList[2] = "Maça";
		nomeList[3] = "Galho";
		nomeList[4] = "Bandana";
		nomeList[5] = "Espada";
		//[6]GOLD
		nomeList[7] = "Penas";
		nomeList[8] = "Frango";
		//[9]GOLDKEY
		nomeList[10] = "Bolinho de Café";
		pesoList[0] =  0;
		pesoList[1] =  2;
		pesoList[2] =  0;
		pesoList[3] =  1;
		pesoList[4] =  0.5;
		pesoList[5] =  2;
		pesoList[7] =  0.1;
		pesoList[8] =  2;
		pesoList[10] =  0.2;
		
		tipoList[0] = "";
		tipoList[1] = "Material";
		tipoList[2] = "Consumivel";
		tipoList[3] = "Material";
		tipoList[4] = "Capacete";
		tipoList[5] = "Arma";
		tipoList[7] = "Material";
		tipoList[8] = "Consumivel";
		tipoList[10] = "Consumivel";
	}
	public void tick() {
		
		//CHAMAR METODOS DE COLIÃO COM ITENS
		CheckInterationsTreeDrop();
		
		CheckInterationsChestDrop();
		
		
	}
	//DROP DE CADA OBJETO======================================================================================================================
	public void CheckInterationsChestDrop() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player){
				if(Bau.isCollidding(this, e)) {
					System.out.println("no bau");
					if(this.id == 4) {
						
					}
				}
			}
		}
	}
	public void CheckInterationsTreeDrop() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player){
				if(Tree.isCollidding(this, e)) {
					if(this.id == 6) {
						Sound.money.play(Sound.volume);
						Player.gold++;
						Game.entities.remove(this);
					}if(this.id == 9) {
						
						Player.key++;
						Game.entities.remove(this);
					}
					if(Player.get == true) {
						//AÇÕES DE ITENS-----------------------------------------------------------------------------------------------------
						if(this.id == 1) {
							
							if(this.gived == false ){
								if(insertSlots(this) == true) {
									Player.wood ++; 
									Game.entities.remove(this);
								}
							}
							
						}else if(this.id == 2){
							
							if(this.gived == false){
								if(insertSlots(this) == true) {
									Player.apple ++;
									Game.entities.remove(this);
								}
							}
							
						}else if(this.id == 3){
							if(this.gived == false){								
								if(insertSlots(this) == true) {
									Player.apple ++;
									Game.entities.remove(this);
								}
							}
							
						}if(this.id == 4) {
							
							if(this.gived == false ){
								if(insertSlots(this) == true) {
									
									Game.entities.remove(this);
								}
							}
							
						}if(this.id == 5) {
							
							if(this.gived == false ){
								if(insertSlots(this) == true) {
									
									Game.entities.remove(this);
								}
							}
							
						}if(this.id == 7) {
							if(insertSlots(this) == true) {
								
								Game.entities.remove(this);
							}
						}if(this.id == 8) {
							if(insertSlots(this) == true) {
								Player.chiken++;
								Game.entities.remove(this);
							}
							
						}if(this.id == 10) {
							if(insertSlots(this) == true) {
								Player.cakeCoffe++;
								Game.entities.remove(this);
							}
							
						}
						//--------------------------------------------------------------------------------------------------------------------
						return;
					}
				}
			}
		}
	}
	//METODO PARA INSERIR IMAGENS NO SLOT VAZIO
	public boolean insertSlots(Entity e1) {
		
						
		for(int j = 0; j < 16; j++) {
			
			if(UI.slotFree[j] == true && this.gived == false ) {
				
				//DIZER QUE OBJETO FOI PEGO
				this.gived = true;
				//DIZER QUE O SLOT DO OBJETO AGORA É TRAVADO
				UI.slotFree[j] = false;	
				//PEGAR QUAL POSIÇÃO DESENHAR				
				UI.slot[j] = this.sprite;
				//MANDAR O ID DO ITEM PARA O SLOT
				UI.idItemSlot[j] = this.id;
				System.out.println("Pegou o objeto de id: "+this.id+this.nome+" no slot "+j);
				return true;	
			}
		}
		return false;
	}
	public boolean receberItem(int id, int qnt) {
		
		
		for(int j = 0; j < 16; j++) {
			
			if(UI.slotFree[j] == true && this.gived == false ) {
				
				//DIZER QUE OBJETO FOI PEGO
				this.gived = true;
				//DIZER QUE O SLOT DO OBJETO AGORA É TRAVADO
				UI.slotFree[j] = false;	
				//PEGAR QUAL POSIÇÃO DESENHAR				
				UI.slot[j] = this.sprite;
				//MANDAR O ID DO ITEM PARA O SLOT
				UI.idItemSlot[j] = this.id;
				return true;	
			}
		}
		return false;
	}
	//METODO PRA TROCAR SLOTS DE LUGAR
	public static void changeSlots(int slot) {
		//PARA TODOS SLOTS NORMAIS
		
		for(int i = 0; i < UI.slot.length; i++) {
			
			
			//slot = SLOT QUE EU PRETENDO JOGAR MEU ITEM
			
			if(slot <= 15 ) {
				if(i == 16) {
					
					if(UI.idItemSlot[slot] == 0) {
						if(UI.mouseDragged == true && UI.moveSlot[i] == true ) {
							
							UI.slot[slot] = UI.slot[i];
							UI.slot[i] = UI.slotTemp;
							
							UI.slotFree[slot] = UI.slotFree[i];
							UI.slotFree[i] = UI.slotFreeTemp;
							
							UI.idItemSlot[slot] = UI.idItemSlot[i];
							UI.idItemSlot[i] = UI.idItemTemp;
						}
						
					}
					
				}else if(UI.mouseDragged == true && UI.moveSlot[i] == true ) {
						
						UI.slot[slot] = UI.slot[i];
						UI.slot[i] = UI.slotTemp;
						
						UI.slotFree[slot] = UI.slotFree[i];
						UI.slotFree[i] = UI.slotFreeTemp;
						
						UI.idItemSlot[slot] = UI.idItemSlot[i];
						UI.idItemSlot[i] = UI.idItemTemp;
						
						
				}
				//PARA SLOTS EQUIPAVEIS
			}else if(tipoList[UI.idItemSlot[i]].equals("Capacete") ) {
				if(slot == 16) {
					
					if(UI.mouseDragged == true && UI.moveSlot[i] == true ) {	
						
						if(UI.idItemSlot[i] != 0) {
							Player.equipedId[0] = UI.idItemSlot[i];
							buff[0] = true;
							buffItens(Player.equipedId[0], 0);
							
						}
							UI.slot[slot] = UI.slot[i];
							UI.slot[i] = UI.slotTemp;
							
							UI.slotFree[slot] = UI.slotFree[i];
							UI.slotFree[i] = UI.slotFreeTemp;
							
							UI.idItemSlot[slot] = UI.idItemSlot[i];
							UI.idItemSlot[i] = UI.idItemTemp;
					}
					
				}
				
			}else if(tipoList[UI.idItemSlot[i]].equals("Arma") ) {
				if(slot == 19) {
					
						if(UI.mouseDragged == true && UI.moveSlot[i] == true ) {	
						
							if(UI.idItemSlot[i] != 0) {
								Player.equipedId[3] = UI.idItemSlot[i];
								buff[3] = true;
								buffItens(Player.equipedId[3], 3);
								
							}
								UI.slot[slot] = UI.slot[i];
								UI.slot[i] = UI.slotTemp;
							
								UI.slotFree[slot] = UI.slotFree[i];
								UI.slotFree[i] = UI.slotFreeTemp;
							
								UI.idItemSlot[slot] = UI.idItemSlot[i];
								UI.idItemSlot[i] = UI.idItemTemp;
					}
					
				}
				
			}
			//DESEQUIPAR O ITEM 
			if(UI.slot[16] == null && buff[0] == true) {
				//INFORMAR QUE O PLAYER NÃO ESTÁ EQUIPANDO MAIS NADA
				Player.equipedId[0] = 0;
				//INFORMAR QUE O BUFF DO SLOT NÃO EXISTE
				buff[0] = false;
				//ZERAR 
				zerarAtributos(0);
				//ATUALIZA OS BIFFS
				buffItens(Player.equipedId[0], 0);
				//ORGANIZAR OS ATRIBUTOS DO PLAYER
				organizarAtributos();
				
			}if(UI.slot[19] == null && buff[3] == true) {
				Player.equipedId[3] = 0;
				buff[3] = false;
				zerarAtributos(3);
				
				buffItens(Player.equipedId[3], 3);
				
				organizarAtributos();
			}
			
		}
		
		//Player.Buffs();
	}
	//METODO PRA RETIRAR ITEM DO IVENTARIO, PEGO O ID E A QUANTIDADE DE ITENS A SER RETIRADO
	public static void deleteItem(int id, int qnt) {
		int num = 0;
		do {
			for(int i = 0; i < 16; i++) {
				if(UI.slotFree[i] == false) {
					if(UI.idItemSlot[i] == id) {
						UI.slotFree[i] = true;	
						UI.slot[i] = null;
						UI.idItemSlot[i] = 0;
						num++;
						if(num == qnt) {
							break;
						}
						
					}	
				}
			}	
		}while(!(num <= qnt));
		
	}
	public static void useItem(int id, int slot) {
		boolean delete = false;
		if(tipoList[id].equals("Consumivel")) {
			if(id == 2) {
				if(Game.player.life < Game.player.maxLife) {
					Game.player.life += 3;
					Game.player.mana += 2;
					Player.apple --;
					delete = true;
					Sound.eat.play(Sound.volume);
					organizarAtributos();
					
				}
			}else if(id == 8) {
				if(Game.player.life < Game.player.maxLife) {
					Game.player.life += 5;
					delete = true;
					Game.player.mana += 3;
					Sound.eat.play(Sound.volume);
					organizarAtributos();
					
				}
			}
			if(delete == true) {
				//APAGAR O ITEM DO SLOT
				UI.slot[slot] = null;
				UI.slotFree[slot] = true;
				UI.idItemSlot[slot] = 0;
				delete = false;
			}
		}
		
		
	}
	//METODO PARA DROPAR ITEM DE ALGUM LUGAR
	public static void droppingItens(int id, int x, int y, int xpos, int ypos) {
		
		
		
		//LISTA DE ITENS DO GAME
		if(id == 6) {
			Material item = new Material(x, y, 16, 16, Game.spritesheetItens.getSprite(xpos*16, ypos*16, 16, 16));
			item.id = id;
			item.setMask(5, 7, 5, 6,0,0,0,0);
			Game.entities.add(item);
			Game.itensMaterial.add(item);
		}else {
			Material item = new Material(x, y, 16, 16, Game.spritesheetItens.getSprite(xpos*16, ypos*16, 16, 16));
			item.id = id;
			item.nome = nomeList[id];
			item.peso = pesoList[id];
			item.tipo = tipoList[id];
			item.setMask(0+3, 0+3, 10, 10,0,0,0,0);
			
			Game.entities.add(item);
			Game.itensMaterial.add(item);
		}
		return;
	}public static void playerDropping(int id,int slotTemp, BufferedImage img) {
		
		UI.slot[slotTemp] = null;
		UI.slotFree[slotTemp] = true;
		UI.idItemSlot[slotTemp] = 0;
		
		//LISTA DE ITENS DO GAME
		
		int xtemp = Game.random.nextInt(32) + Game.player.getX();
		int ytemp = Game.random.nextInt(32) + Game.player.getY();
		Material item = new Material(xtemp-8, ytemp-8, 16, 16, img);
		item.id = id;
		item.nome = nomeList[id];
		item.peso = pesoList[id];
		item.tipo = tipoList[id];
		item.setMask(0+3, 0+3, 10, 10,0,0,0,0);
		//System.out.println("Dropou: "+item.id+item.nome+" Do slot "+slotTemp);
		Game.entities.add(item);
		Game.itensMaterial.add(item);
		UI.dropping = false;
		return;
	}
	public static void buffItens(int id, int equipSlot) {
		//CHAMO AS VARIAVEIS PADRÕES DO PLAYER
		
		Game.player.padrao();
		
		
		
		if(equipSlot == 0) {
			
			if(buff[0] == true) {
					
				if(id == 4) {
					maxLife[0] = 4;
					maxMana[0] = 2;
				}
			}
		}else if(equipSlot == 3) {
			if(buff[3] == true) {	
				if(id == 5) {
					Game.player.setActionRange(6,10,20,20);
					maxLife[3] = 8;
					maxDamageAttack[3] = 2;
				}
			}
		}
		
		for(int i = 0; i < 6; i++) {
			Game.player.maxLife = Game.player.maxLife + maxLife[i];
			Game.player.maxMana = Game.player.maxMana + maxMana[i];
			Player.attackDamage = Player.attackDamage + maxDamageAttack[i];
			Game.player.speed = Game.player.speed + maxSpeed[i];
		}
		
	}
	public static boolean check(Entity e) {
		for(int i = 0; i < Game.itensMaterial.size(); i++) {
			if (e == Game.itensMaterial.get(i)) {
				 return true;
			}
		}
		
		return false;
	}public static void zerarAtributos(int slot) {
		maxLife[slot] = 0;
		maxMana[slot] = 0;
		maxDamageAttack[slot] = 0;
	}public static void organizarAtributos() {
		if(Game.player.life > Game.player.maxLife) {
			Game.player.life = Game.player.maxLife;
		}if(Game.player.mana > Game.player.maxMana) {
			Game.player.mana = Game.player.maxMana;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.fillRect(this.getX() + maskx/2 - Camera.x, this.getY()+ masky/2 - Camera.y, mwidth, mheight);
		
		
	}
	

}
