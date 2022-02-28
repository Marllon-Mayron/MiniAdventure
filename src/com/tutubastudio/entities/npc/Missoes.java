package com.tutubastudio.entities.npc;

import com.tutubastudio.entities.Player;
import com.tutubastudio.graphics.UI;
import com.tutubastudio.main.Game;
import com.tutubastudios.itens.Material;

public class Missoes {
	public static boolean missionAlert = false;
	public static int qntMission = 5;
	public static String[] missionDescription = new String[qntMission];
	public static String[] missionTitle = new String[qntMission];
	public static int showDescription = 0;
	public static boolean[] completedMission = new boolean[3];
	
	
	
	
	public Missoes(int id, String title, String desc) {
		
		// TODO Auto-generated constructor stub
		
		
	}
	
	
	public static int guardarMissao(int idMission) {
		for(int i = 0; i < 4; i++) {
			if(UI.missionSlot[i] == false) {
				
				UI.slotTitle[i] = Missoes.missionTitle[idMission];
				UI.slotDesc[i] = Missoes.missionDescription[idMission];
				UI.missionSlot[i] = true;
				return i;
			}
		}
		return 0;
	}
	public static void removeMission(int idMission) {
		UI.slotTitle[idMission] = "";
		UI.slotDesc[idMission] = "";
		UI.missionSlot[idMission] = false;
	}
	public static void textoMissoes() {
		missionTitle[1] = "Galinha no prato";
		missionDescription[1] = "O pobre velhinho está com fome e \ngostaria de uma galinha no ponto.";
		missionTitle[2] = "Arvores Arvores";
		missionDescription[2] = "O pobre velhinho está precisando \nde lenha.";
		missionTitle[3] = "Abra o bau";
		missionDescription[3] = "Abra o bau";
		
	}
	public static boolean objetivesMissions(int idMission) {
		if(idMission == 0) {
			if(Player.chiken >= 1) {
				Player.chiken--;
				Material.deleteItem(8, 1);
				return true;
			}
		}if(idMission == 1) {
			if(Player.wood >= 2) {
				Player.wood-=2;
				System.out.println("Chamei");
				Material.deleteItem(1, 2);
				return true;
			}
		}
		return false;
	}

}
