package com.visp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BossMusic implements Music{
	
	Music bass1, drums1, drums2, drums3, drums4, drums5, 
		  guitar1, guitar2, guitar3, guitar4, guitar5;

	static int bossState;
	
	private boolean kill;

	Enemy boss;

	public BossMusic() {
		bass1 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/bass1.ogg"));
		drums1 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/drums1.ogg"));
		drums2 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/drums2.ogg"));
		drums3 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/drums3.ogg"));
		drums4 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/drums4.ogg"));
		drums5 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/drums5.ogg"));
		guitar1 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/guitar1.ogg"));
		guitar2 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/guitar2.ogg"));
		guitar3 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/guitar3.ogg"));
		guitar4 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/guitar4.ogg"));
		guitar5 = Gdx.audio.newMusic(Gdx.files.internal("music/boss/guitar5.ogg"));		
		
		kill = false;
		
		bossState = 8;
	}
	
	public static void setBossState(int s) {
		bossState = s;
	}
	
	public void update() {
		if(kill) return;
		
		if(!drums1.isPlaying()) {
			drums1.setLooping(true);
			drums1.play();
		}
		
		float pos = drums1.getPosition();
		switch (bossState) {
			case 7: if(bass1.isPlaying()) break;
					bass1.setLooping(true);
					bass1.play();
					bass1.setPosition(pos);
					break;
			case 6: if(bass1.isPlaying()) break;
					bass1.setLooping(true);
					bass1.play();
					bass1.setPosition(pos);
					break;
			case 5: if(drums2.isPlaying()) break;
					drums1.setVolume(0.0f);
					drums2.setLooping(true);
					drums2.play();
					drums2.setPosition(pos);
					guitar1.setLooping(true);
					guitar1.play();
					guitar1.setPosition(pos);
					break;
			case 4: if(drums3.isPlaying()) break;
					drums2.stop();
					drums3.setLooping(true);
					drums3.play();
					drums3.setPosition(pos);
					guitar2.setLooping(true);
					guitar2.play();
					guitar2.setPosition(pos);
					break;
			case 3: if(drums4.isPlaying()) break;
					drums3.stop();
					drums4.setLooping(true);
					drums4.play();
					drums4.setPosition(pos);
					guitar3.setLooping(true);
					guitar3.play();
					guitar3.setPosition(pos);
					break;
			case 2: if(drums5.isPlaying()) break;
					drums4.stop();
					drums5.setLooping(true);
					drums5.play();
					drums5.setPosition(pos);
					guitar4.setLooping(true);
					guitar4.play();
					guitar4.setPosition(pos);
					break;
			case 1: if(guitar5.isPlaying()) break;
					guitar5.setLooping(true);
					guitar5.play();
					guitar5.setPosition(pos);
					break;
			default:
				break;
		}
	}
	
	public void resetBM() {
		drums5.stop();
		drums4.stop();
		drums3.stop();
		drums2.stop();
		guitar5.stop();
		guitar4.stop();
		guitar3.stop();
		guitar2.stop();
		guitar1.stop();
		bass1.stop();
	}
	
	public void setKill(boolean b) { kill = b; }

	public void play() { }

	public void pause() { }

	public void stop() { }

	public boolean isPlaying() { return false; }

	public void setLooping(boolean isLooping) { }

	public boolean isLooping() { return false; }

	public void setVolume(float volume) { }

	public float getVolume() { return 0; }

	public void setPan(float pan, float volume) { }

	public void setPosition(float position) { }

	public float getPosition() { return 0; }

	public void dispose() { }

	public void setOnCompletionListener(OnCompletionListener listener) { }
	

}
