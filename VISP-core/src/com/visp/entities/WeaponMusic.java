package com.visp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class WeaponMusic implements Music{
	
	Music bass1, drums1, guitar1, keyboard1, 
			bass2, drums2, guitar2, keyboard2, 
			bass3, drums3, guitar3, keyboard3, 
			bass4, drums4, guitar4, keyboard4, musicDrums;
	
	static int musicLevel[] = {1, 1, 1, 1};
	
	private boolean kill;
	
	public WeaponMusic() {
		musicDrums = Gdx.audio.newMusic(Gdx.files.internal("music/Drums_Main_8000.ogg"));
		bass1 = Gdx.audio.newMusic(Gdx.files.internal("music/1/BASS_1_8000.ogg"));
		drums1 = Gdx.audio.newMusic(Gdx.files.internal("music/1/DRUMS_1_8000.ogg"));
		guitar1 = Gdx.audio.newMusic(Gdx.files.internal("music/1/GUITAR_1_8000.ogg"));
		keyboard1 = Gdx.audio.newMusic(Gdx.files.internal("music/1/KEYBOARD_1_8000.ogg"));
		bass2 = Gdx.audio.newMusic(Gdx.files.internal("music/2/BASS_2_8000.ogg"));
		drums2 = Gdx.audio.newMusic(Gdx.files.internal("music/2/DRUMS_2_8000.ogg"));
		guitar2 = Gdx.audio.newMusic(Gdx.files.internal("music/2/GUITAR_2_8000.ogg"));
		keyboard2 = Gdx.audio.newMusic(Gdx.files.internal("music/2/KEYBOARD_2_8000.ogg"));
		bass3 = Gdx.audio.newMusic(Gdx.files.internal("music/3/BASS_3_8000.ogg"));
		drums3 = Gdx.audio.newMusic(Gdx.files.internal("music/3/DRUMS_3_8000.ogg"));
		guitar3 = Gdx.audio.newMusic(Gdx.files.internal("music/3/GUITAR_3_8000.ogg"));
		keyboard3 = Gdx.audio.newMusic(Gdx.files.internal("music/3/KEYBOARD_3_8000.ogg"));
		bass4 = Gdx.audio.newMusic(Gdx.files.internal("music/4/BASS_4_8000.ogg"));
		drums4 = Gdx.audio.newMusic(Gdx.files.internal("music/4/DRUMS_4_8000.ogg"));
		guitar4 = Gdx.audio.newMusic(Gdx.files.internal("music/4/GUITAR_4_8000.ogg"));
		keyboard4 = Gdx.audio.newMusic(Gdx.files.internal("music/4/KEYBOARD_4_8000.ogg"));
		
		kill = false;
	}
	
	public static void setMusicLevel(int i, int j) { musicLevel[i] = j; }

	public void play() {}
	
	public void update() {
		if(kill) return;
		
		if(!musicDrums.isPlaying()) {
			musicDrums.setLooping(true);
			musicDrums.play();
		}
		float pos = musicDrums.getPosition();
		for(int i = 0; i < musicLevel.length; i++) {
			if(musicLevel[i] != 1) {
				switch (i) {
					case 0: switch (musicLevel[i]) {
								case 2: if(bass1.isPlaying()) break;
										bass1.setLooping(true);
										bass1.play();
										bass1.setPosition(pos);
										break;
								case 3: if(bass2.isPlaying()) break;
										bass2.setLooping(true);
										bass2.setVolume(0.7f);
										bass2.play();
										bass2.setPosition(pos);
										break;
								case 4: if(bass3.isPlaying()) break;
										bass3.setLooping(true);
										bass3.play();
										bass3.setPosition(pos);
										break;
								case 5: if(bass4.isPlaying()) break;
										bass4.setLooping(true);
										bass4.play();
										bass4.setPosition(pos);
										break;
								default: break;
					}	
					break;
					case 1: switch (musicLevel[i]) {
					 			case 2: if(drums1.isPlaying()) break;
					 					drums1.setLooping(true);
					 					drums1.play();
					 					drums1.setPosition(pos);
					 					break;
					 			case 3: if(drums2.isPlaying()) break;
					 					drums2.setLooping(true);
					 					drums2.play();
					 					drums2.setPosition(pos);
					 					break;
					 			case 4: if(drums3.isPlaying()) break;
					 					drums3.setLooping(true);
					 					drums3.play();
					 					drums3.setPosition(pos);
					 					break;
					 			case 5: if(drums4.isPlaying()) break;
					 					drums4.setLooping(true);
					 					drums4.play();
					 					drums4.setPosition(pos);
					 					break;
					 			default: break;
					}	
					break;
					case 2: switch (musicLevel[i]) {
								case 2: if(guitar1.isPlaying()) break;
										guitar1.setLooping(true);
										guitar1.setVolume(0.35f);
										guitar1.play();
										guitar1.setPosition(pos);
										break;
								case 3: if(guitar2.isPlaying()) break;
										guitar2.setLooping(true);
										guitar2.setVolume(0.35f);
										guitar2.play();
										guitar2.setPosition(pos);
										break;
								case 4: if(guitar3.isPlaying()) break;
										guitar3.setLooping(true);
										guitar3.setVolume(0.35f);
										guitar3.play();
										guitar3.setPosition(pos);
										break;
								case 5: if(guitar4.isPlaying()) break;
										guitar4.setLooping(true);
										guitar4.setVolume(0.35f);
										guitar4.play();
										guitar4.setPosition(pos);
										break;
					}	
					break;
					case 3: switch (musicLevel[i]) {
								case 2: if(keyboard1.isPlaying()) break;
										keyboard1.setLooping(true);
										keyboard1.setVolume(0.3f);
										keyboard1.play();
										keyboard1.setPosition(pos);
										break;
								case 3: if(keyboard2.isPlaying()) break;
										keyboard2.setLooping(true);
										keyboard2.setVolume(0.5f);
										keyboard2.play();
										keyboard2.setPosition(pos);
										break;
								case 4: if(keyboard3.isPlaying()) break;
										keyboard3.setLooping(true);
										keyboard3.setVolume(0.5f);
										keyboard3.play();
										keyboard3.setPosition(pos);
										break;
								case 5: if(keyboard4.isPlaying()) break;
										keyboard4.setLooping(true);
										keyboard4.setVolume(0.5f);
										keyboard4.play();
										keyboard4.setPosition(pos);
										break;
					}	
					break;
					default: break;
				}
			} 
		}	
	}
	
	public void setKill(boolean b) { kill = b; }
	
	public void restart() {
		musicDrums.stop();
	}
	
	public void resetMusicLevel() {
		bass1.stop();
		bass2.stop();
		bass3.stop();
		bass4.stop();
		drums1.stop();
		drums2.stop();
		drums3.stop();
		drums4.stop();
		guitar1.stop();
		guitar2.stop();
		guitar3.stop();
		guitar4.stop();
		keyboard1.stop();
		keyboard2.stop();
		keyboard3.stop();
		keyboard4.stop();
		for(int i = 0; i < musicLevel.length; i++) {
			musicLevel[i] = 1;
		}
	}

	public void pause() { }

	public void stop() { }

	public boolean isPlaying() { return false; }

	public void setLooping(boolean isLooping) {	}

	public boolean isLooping() { return false; }

	public void setVolume(float volume) { }

	public float getVolume() { return 0; }

	public void setPan(float pan, float volume) { }

	public void setPosition(float position) { }

	public float getPosition() { return 0; }

	public void dispose() {
		bass1.dispose();
		bass2.dispose();
		bass3.dispose();
		bass4.dispose();
		drums1.dispose();
		drums2.dispose();
		drums3.dispose();
		drums4.dispose();
		guitar1.dispose();
		guitar2.dispose();
		guitar3.dispose();
		guitar4.dispose();
		keyboard1.dispose();
		keyboard2.dispose();
		keyboard3.dispose();
		keyboard4.dispose();
		musicDrums.dispose();
	}

	public void setOnCompletionListener(OnCompletionListener listener) {
		
		
	}

}
