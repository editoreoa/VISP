package com.visp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
   

public class SoundEffect implements Sound {
	
	public static Sound SHOT, RANK1EXPLOSION, RANK2EXPLOSION, RANK3EXPLOSION, RANK4EXPLOSION, 
			SMALLPOWER, LARGEPOWER, SLOW, EXTRALIFE, HURT;
	
	public SoundEffect() {
  
	   SHOT = Gdx.audio.newSound(Gdx.files.internal("shot.ogg"));
	   RANK1EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("rank1Explosion.wav"));
	   RANK2EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("rank2Explosion.wav"));
	   RANK3EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("rank3Explosion.wav"));
	   RANK4EXPLOSION = Gdx.audio.newSound(Gdx.files.internal("rank4Explosion.wav"));
	   SMALLPOWER = Gdx.audio.newSound(Gdx.files.internal("smallPower.wav"));
	   LARGEPOWER = Gdx.audio.newSound(Gdx.files.internal("largePower.wav"));
	   SLOW = Gdx.audio.newSound(Gdx.files.internal("slow.wav"));
	   EXTRALIFE = Gdx.audio.newSound(Gdx.files.internal("1up.wav"));
	   HURT = Gdx.audio.newSound(Gdx.files.internal("hurt.wav"));
	   
	}
   
   
public long play() { return 0; }
public long play(float volume) { return 0; }
public long play(float volume, float pitch, float pan) { return 0; }
public long loop() { return 0; }
public long loop(float volume) { return 0; }
public long loop(float volume, float pitch, float pan) { return 0; }
public void stop() {}
public void pause() {}
public void resume() {}
public void dispose() {}
public void stop(long soundId) {}
public void pause(long soundId) {}
public void resume(long soundId) {}
public void setLooping(long soundId, boolean looping) {}
public void setPitch(long soundId, float pitch) {}
public void setVolume(long soundId, float volume) {}
public void setPan(long soundId, float pan, float volume) {}
public void setPriority(long soundId, int priority) {}

   
}
