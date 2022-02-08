package com.tutubastudio.entities;

public class asdasdasd {

	public asdasdasd() {
		// TODO Auto-generated constructor stub
	}
	public enum MarcasEnum {
		AMAZON, DELL, HP, TOSHIBA, LG, SAMSUNG;
		}
	//public static  
	
	public static void main(String[] args) {
		MarcasEnum hp = MarcasEnum.HP;
		MarcasEnum samsung = MarcasEnum. SAMSUNG;
		System.out.println("Nome da Marca = "+hp.name());
		System.out.println("Nome da Marca = "+samsung.name());
	}
	
	

}
