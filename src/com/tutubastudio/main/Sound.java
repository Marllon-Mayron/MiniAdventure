package com.tutubastudio.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;




public class Sound {
	private static FloatControl volumeGeral;
	public static int volumeAmbiente = 30;
	public static int volume = 20;
	private static SourceDataLine sourceDataLine;
	private static AudioFormat audioFormat;
	
	
	
	public static class Clips{
		public Clip[] clips;
		private int p;
		private int count;
		
		
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if(buffer == null)
				return;
			
			clips = new Clip[count];
			this.count = count;
			
			for(int i = 0; i < count; i++) {
				
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
				
				
				
			}
		}
		//RODAR O SOM UMA VEZ 
		public void play(int vol) {
			
			if(clips == null) return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			FloatControl volume = (FloatControl) clips[p].getControl(FloatControl.Type.MASTER_GAIN);
	        volume.setValue(-1 * vol);
			clips[p].start();
			p++;
			if(p>=count) {
				
				p = 0;
			}  
			
		}
		
		public void stop() {
			clips[p].stop();
			clips[p].setFramePosition(0);
			p = 0;
		}
		//RODAR O SOM EM LOOP
		public void loop(int vol) {
			if(clips == null) return;
			FloatControl volume = (FloatControl) clips[p].getControl(FloatControl.Type.MASTER_GAIN);
	        volume.setValue(-1 * vol);
			clips[p].loop(300);
			
			
			
		}

	}
	//LISTAS DE MUSICAS E SONS
	
	
	
	public static Clips soco = load("/audio/soco.wav",1);
	public static Clips tchikenHit = load("/audio/tchikenHit.wav",1);
	public static Clips eat = load("/audio/eat.wav",1);
	public static Clips brokenCrate = load("/audio/brokenCrate.wav",1);
	public static Clips money = load("/audio/money.wav",1);
	public static Clips passos = load("/audio/passosGrama.wav",1);
	public static Clips baterMadeira = load("/audio/treeCut.wav",1);
	public static Clips openChest = load("/audio/openChest.wav",1);
	public static Clips insertKey = load("/audio/keyChest.wav",1);
	public static Clips bush = load("/audio/bush.wav",1);
	//musicas 
	public static Clips music = load("/audio/sound1.wav",1);
	public static Clips boss = load("/audio/boss1.wav",1);
	public static Clips forest = load("/audio/forest.wav",1);
	
	
	private static Clips load(String name,int count) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while((read = dis.read(buffer)) >= 0) {
				baos.write(buffer,0,read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data,count);
		}catch(Exception e) {
			try {
				return new Clips(null,0);
			}catch(Exception ee) {
				return null;
			}
		}
	}
	//ALTERAR VOLUME
	public static void setVolume(int vol) throws LineUnavailableException{
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat); 
		sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		sourceDataLine.open();
		
		if (sourceDataLine.isOpen()) {
				if (volumeGeral == null) {
						//Controle de Volume
					Port lineOut;
					if (AudioSystem.isLineSupported(Port.Info.LINE_OUT)) {
						lineOut = (Port) AudioSystem.getLine(Port.Info.LINE_OUT);
						lineOut.open();
					} else if (AudioSystem.isLineSupported(Port.Info.HEADPHONE)) {
						lineOut = (Port) AudioSystem.getLine(Port.Info.HEADPHONE);
						lineOut.open();
					} else if (AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
						lineOut = (Port) AudioSystem.getLine(Port.Info.SPEAKER);
						lineOut.open();
					} else {
						throw new LineUnavailableException();
					}
					
					volumeGeral = (FloatControl) lineOut.getControl(FloatControl.Type.VOLUME);
				}
				volumeGeral.setValue((float) (vol / 100.0));
		}
	}
	
}
