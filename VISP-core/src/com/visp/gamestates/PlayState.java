package com.visp.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Pool;
import com.visp.entities.BossMusic;
import com.visp.entities.Enemy;
import com.visp.entities.EnemyBullet;
import com.visp.entities.Explosion;
import com.visp.entities.Hazard;
import com.visp.entities.Player;
import com.visp.entities.PlayerBullet;
import com.visp.entities.PowerUp;
import com.visp.entities.SoundEffect;
import com.visp.entities.Text;
import com.visp.entities.WeaponMusic;
import com.visp.main.Game;
import com.visp.managers.GameKeys;
import com.visp.managers.GameStateManager;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	private SpriteBatch sb;
	private BitmapFont font;
	
	private Touchpad touchpad;
	private TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
	
	public static Player player;
	public static ArrayList<PlayerBullet> pBullets;
	public static ArrayList<EnemyBullet> eBullets;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Hazard> hazards;
	public static ArrayList<PowerUp> powerUps;
	public static ArrayList<Text> texts;
	public static ArrayList<Explosion> explosions;
	
	//public static Music musicDrums;
	public static WeaponMusic wm;
	public static BossMusic bm;
	public static SoundEffect se;
	
	public static final Pool<Enemy> enemyPool = new Pool<Enemy>() {
		
		@Override
		protected Enemy newObject() {
			return new Enemy(0, 0);
		}
	};
	
	private long waveStartTimer;
	private long waveStartTimerDiff;
	private int waveNumber;
	private int waveDelay = 3000;
	private boolean waveStart;
	
	private long slowDownTimer;
	private long slowDownTimerDiff;
	private int slowDownLength = 6000;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		font = new BitmapFont();
		
		// Load on Screen Touchpad
		touchpadSkin = new Skin();
		//touchpadSkin.add("touchBackground", new Texture("data/touchpadBackground.png"));
		//touchpadSkin.add("touchKnob", new Texture("data/touchpadKnob.png"));
		touchpadStyle = new TouchpadStyle();
		
		touchpad = new Touchpad(10, touchpadStyle);
		
		player = new Player();
		pBullets = new ArrayList<PlayerBullet>();
		eBullets = new ArrayList<EnemyBullet>();
		enemies = new ArrayList<Enemy>();
		hazards = new ArrayList<Hazard>();
		powerUps = new ArrayList<PowerUp>();
		texts = new ArrayList<Text>();
		explosions = new ArrayList<Explosion>();
		
		wm = new WeaponMusic();
		bm = new BossMusic();
		se = new SoundEffect();
		
		bm.setKill(true);
		
		waveStartTimer = 0;
		waveStartTimerDiff = 0;
		waveStart = true;
		waveNumber = 0;
		
	}
	
	
	public void update() {
		// new wave
		if(waveStartTimer == 0 && enemies.size() == 0) {
			System.gc();
			waveNumber++;
			waveStart = false;
			waveStartTimer = System.nanoTime();
		} else {
			waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000;
			if(waveStartTimerDiff > waveDelay) {
				waveStart = true;
				waveStartTimer = 0;
				waveStartTimerDiff = 0;
			}
		}
		
		// create enemies
		if(waveStart && enemies.size() == 0) {
			createNewEnemies();
		}

		handleInput();
		
		// player update
		player.update();
		
		// Player BULLET UPDATE
		for(int i = 0; i < pBullets.size(); i++) {
			boolean remove = pBullets.get(i).update();
			if(remove) {
				pBullets.remove(i);
				i--;
			}
		}
		
		// Enemy Bullet Update
		for(int i = 0; i < eBullets.size(); i++) {
			boolean remove = eBullets.get(i).update();
			if(remove) {
				eBullets.remove(i);
				i--;
			}
		}
		
		// Enemy update
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		
		// Hazard update
		for(int i = 0; i < hazards.size(); i++) {
			hazards.get(i).update();
			if(hazards.get(i).isDead()) {
				hazards.remove(i);
				i--;
			}
		}
		
		// update bullet-enemy collision 
		for(int i = 0; i < pBullets.size(); i++) {
			
			PlayerBullet b = pBullets.get(i);
			double bx = b.getX();
			double by = b.getY();
			double br = b.getRadius();
			
			for(int j = 0; j < enemies.size(); j++) {
				Enemy e = enemies.get(j);
				double ex = e.getX();
				double ey = e.getY();
				double er = e.getRadius();
				double dx = bx - ex;
				double dy = by - ey;
				double dist = Math.sqrt(dx * dx + dy * dy);
				if(dist < br + er) {
					e.hit(b.getPayload());
					pBullets.remove(i);
					i--;
					break;
				}
			}
		}
		
		// update bullet-player collision 
				for(int i = 0; i < eBullets.size(); i++) {
					
					EnemyBullet b = eBullets.get(i);
					double bx = b.getX();
					double by = b.getY();
					double br = b.getRadius();
					
					double px = player.getX();
					double py = player.getY();
					double pr = player.getRadius();
					double dx = bx - px;
					double dy = by - py;
					double dist = Math.sqrt(dx * dx + dy * dy);
					if(dist < br + pr) {
						eBullets.remove(i);
						i--;
						player.loseLife();
						player.resetPowerLevel();
						player.setScoreMultiplier(1.0);
						SoundEffect.HURT.play();
						wm.resetMusicLevel();
						break;	
					}
				}
		
		// powerUp update
		for(int i = 0; i < powerUps.size(); i++) {
			boolean remove = powerUps.get(i).update();
			if(remove) {
				powerUps.remove(i);
				i--;
			}
		}
		
		// explosion update
		for(int i = 0; i < explosions.size(); i++) {
			boolean remove = explosions.get(i).update();
			if(remove) {
				explosions.remove(i);
				i--;
			}
		}
		
		// text update
		for(int i = 0; i < texts.size(); i++) {
			boolean remove = texts.get(i).update();
			if(remove) {
				texts.remove(i);
				i--;
			}
		}
		
		// check dead player
		if(player.isDead()) {
			wm.stop();
			wm.setKill(true);
			wm.dispose();
			bm.stop();
			bm.setKill(true);
			bm.dispose();
			gsm.pushState(GameStateManager.MENUSTATE);
			gsm.pushState(GameStateManager.HIGHSCORESTATE);
			//gsm.pushState(GameStateManager.HIGHSCORESTATE);
			//SoundEffect.GAMEOVER.play();
		}
		
		// player-enemy collision
		if(!player.isRecovering()) {
			double px = player.getX();
			double py = player.getY();
			float pr = player.getRadius();
			for(int i = 0; i < enemies.size(); i++) {
				Enemy e = enemies.get(i);
				double ex = e.getX();
				double ey = e.getY();
				double er = e.getRadius();
				double dx = px - ex;
				double dy = py - ey;
				double dist = Math.sqrt(dx * dx + dy * dy);
				if(dist < pr + er) {
					player.loseLife();
					player.resetPowerLevel();
					player.setScoreMultiplier(1.0);
					SoundEffect.HURT.play();
					wm.resetMusicLevel();
				}
			}
		}
		
		// player-hazard collision
		if(!player.isRecovering()) {
			double px = player.getX();
			double py = player.getY();
			float pr = player.getRadius();
			for(int i = 0; i < hazards.size(); i++) {
				Hazard h = hazards.get(i);
				double hx = h.getX();
				double hy = h.getY();
				double hr = h.getR();
				double dx = px - hx;
				double dy = py - hy;
				double dist = Math.sqrt(dx * dx + dy * dy);
				if(dist < pr + hr) {
					player.loseLife();
					player.resetPowerLevel();
					player.setScoreMultiplier(1.0);
					SoundEffect.HURT.play();
					wm.resetMusicLevel();
				}
			}
		}
		
		// player-powerUp collision
		double px = player.getX();
		double py = player.getY();
		float pr = player.getRadius();
		for(int i = 0; i < powerUps.size(); i++) {
			PowerUp p = powerUps.get(i);
			double x = p.getX();
			double y = p.getY();
			double r = p.getRadius();
			double dx = px - x;
			double dy = py - y;
			double dist = Math.sqrt(dx * dx + dy * dy);
			
			// collected powerUp
			if(dist < pr + r) {
				int type = p.getType();
				if(type == 1) {
					player.gainLife();
					player.addScore(50);
					SoundEffect.EXTRALIFE.play();
					texts.add(new Text(player.getX(), player.getY(), 1500, "1UP", 9));
				}
				if(type == 2) {
					player.increasePower(1);
					player.addScore(10);
					SoundEffect.SMALLPOWER.play();
					texts.add(new Text(player.getX(), player.getY(), 1500, "power", 17));
				}
				if(type == 3) {
					player.increasePower(2);
					player.addScore(20);
					SoundEffect.LARGEPOWER.play();
					texts.add(new Text(player.getX(), player.getY(), 1500, "POWER", 17));
				}
				if(type == 4) {
					slowDownTimer = System.nanoTime();
					for(int j = 0; j < enemies.size(); j++) {
						enemies.get(j).setSlow(true);
					}
					SoundEffect.SLOW.play();
					texts.add(new Text(player.getX(), player.getY(), 1500, "Slow Down", 32));
				}
				powerUps.remove(i);
				i--;	
			}
		}
		
		// update check dead enemies
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).isDead()) {
				
				player.addKills();
				Enemy e = enemies.get(i);
				
				// chance for powerUp
				double rand =  Math.random();
				if(rand < 0.005) powerUps.add(new PowerUp(1, e.getX(), e.getY()));
				else if(rand < 0.050) powerUps.add(new PowerUp(3, e.getX(), e.getY()));
				else if(rand < 0.150) powerUps.add(new PowerUp(2, e.getX(), e.getY()));
				else if(rand < 0.170) powerUps.add(new PowerUp(4, e.getX(), e.getY()));
				
				// fix type to reflect proper scoring 
				player.addScore(e.getType() + e.getRank());
				
				if(e.getRank() == 1) {
					explosions.add(new Explosion(e.getX(), e.getY(), e.getRadius(), e.getRadius() + 20));
					SoundEffect.RANK1EXPLOSION.play();
				}
				if(e.getRank() == 2) {
					explosions.add(new Explosion(e.getX(), e.getY(), e.getRadius(), e.getRadius() + 30));
					SoundEffect.RANK2EXPLOSION.play();
				}
				if(e.getRank() == 3) {
					explosions.add(new Explosion(e.getX(), e.getY(), e.getRadius(), e.getRadius() + 40));
					SoundEffect.RANK3EXPLOSION.play();
				}
				if(e.getRank() == 4) {
					explosions.add(new Explosion(e.getX(), e.getY(), e.getRadius(), e.getRadius() + 50));
					SoundEffect.RANK4EXPLOSION.play();
				}
				if(e.getRank() >= 5) {
					explosions.add(new Explosion(e.getX(), e.getY(), e.getRadius(), e.getRadius() + 50));
					SoundEffect.RANK4EXPLOSION.play();
				}
				
				// Hazard spawn
				if(e.getType() == 3) {
					if(e.getRank() == 1) {
					hazards.add(new Hazard(1, 1, e.getX(), e.getY()));
					}
					if(e.getRank() == 2) {
					hazards.add(new Hazard(1, 2, e.getX(), e.getY()));
					}
					if(e.getRank() == 3) {
					hazards.add(new Hazard(1, 3, e.getX(), e.getY()));
					}
					if(e.getRank() == 4) {
					hazards.add(new Hazard(1, 4, e.getX(), e.getY()));
					}
				}
				e.explode();
				enemies.remove(i);
				enemyPool.free(e);
				i--;
			}
		}
		
		// slow down update
		if(slowDownTimer != 0) {
			slowDownTimerDiff = (System.nanoTime() - slowDownTimer) / 1000000;
			if(slowDownTimerDiff > slowDownLength) {
				slowDownTimer = 0;
				for(int j = 0; j < enemies.size(); j++) {
					enemies.get(j).setSlow(false);
				}
			}
		}
		
		// weapon music update
		wm.update();
		bm.update();
		
	}
	public void draw() {
		// draw player
		player.draw(sr);
		
		// draw player bullets
		for(int i = 0; i < pBullets.size(); i++) {
			pBullets.get(i).draw(sr);	
		}
		
		// DRAW ENEMIES
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(sr);
		}
		
		// draw enemy bullets
		for(int i = 0; i < eBullets.size(); i++) {
			eBullets.get(i).draw(sr);	
		}

		// Draw Hazards
		for(int i = 0; i < hazards.size(); i++) {
			hazards.get(i).draw(sr);
		}
		
		// Draw powerUps
		for(int i = 0; i < powerUps.size(); i++) {
			powerUps.get(i).draw(sr);
		}
		
		// draw text
		for(int i = 0; i < texts.size(); i++) {
			texts.get(i).draw(sr, sb);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(sr);
		}
		
		// draw player lives
		for(int i = 0; i < player.getLives(); i++) {
			sr.setColor(.7f, .7f, .7f, 1);
			sr.begin(ShapeType.Filled);
			sr.ellipse(12 + (20 * i), 480, player.getRadius() * 2, player.getRadius() * 2);
			sr.end();
			sr.setColor(1, 1, 1, 1);
			sr.begin(ShapeType.Filled);
			sr.ellipse(13 + (20 * i), 481, (player.getRadius() * 2) - 2, (player.getRadius() * 2) - 2);
			sr.end();
		}
		
		// draw player power 178, 15, 63
		sr.setColor(255 / 255, 237 / 255, 9 / 255, 1);	// yellow
		sb.begin();
		font.draw(sb, "V", 5, 475);
		font.draw(sb, "I", 7, 455);
		font.draw(sb, "S", 5, 435);
		font.draw(sb, "P", 5, 415);
		sb.end();
		for(int i = 0; i < 4; i++) {
			if(player.getWeaponState() == i) {
				sr.setColor(1, 0, 0, 1);
				sr.begin(ShapeType.Filled);
				sr.rect(20, 465 - 20 * i, player.getPower(i) * 8, 8);
				sr.end();
				sr.setColor(1, 0, 0, 1);
				sr.begin(ShapeType.Line);
				for(int j = 0; j < player.getRequiredPower(i); j++) {
					sr.rect(20 + 8 * j, 465 - 20 * i, 8, 8);
				}
				sr.end();
			} else {
				Gdx.gl.glEnable(GL30.GL_BLEND);
			    Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
				sr.setColor(1, 1, 1, 0.5f);
				sr.begin(ShapeType.Filled);
				sr.rect(20, 465 - 20 * i, player.getPower(i) * 8, 8);
				sr.end();
				sr.setColor(1, 1, 1, 0.5f);
				sr.begin(ShapeType.Line);
				for(int j = 0; j < player.getRequiredPower(i); j++) {
					sr.rect(20 + 8 * j, 465 - 20 * i, 8, 8);
				}
				sr.end();
				Gdx.gl.glDisable(GL30.GL_BLEND);
			}
		}
		
		// draw wave number
		sb.begin();
		font.draw(sb, "Wave: " + waveNumber, Game.WIDTH - 235, 490);
		sb.end();
		
		// draw player score
		sr.setColor(1, 1, 1, 1);
		sb.begin();
		font.draw(sb, "Score: " + player.getScore(), Game.WIDTH - 120, 485);
		sb.end();
		
		// draw score multiplier
		sb.begin();
		font.draw(sb, "Multiplier: " + player.getScoreMultiplier() + "x", Game.WIDTH - 110, 470);
		sb.end();
		
		// draw slowdown meter
		if(slowDownTimer != 0) {
			sr.setColor(1, 1, 1, 1);
			sr.begin(ShapeType.Line);
			sr.rect(135, 490, 100, 8);
			sr.end();
			sr.begin(ShapeType.Filled);
			sr.rect(135, 490, (int) (100 - 100.0 * slowDownTimerDiff / slowDownLength), 8);
			sr.end();
		}
		
	}
	
	private void createNewEnemies() {
		
		enemies.clear();

		if(waveNumber == 1) {
			for(int i = 0; i < 4; i++) {
				enemies.add(createNewEnemy(1, 1));
			}
			//enemies.add(createNewEnemy(4, 8));
		}
		if(waveNumber == 2) {
			for(int i = 0; i < 2; i++) {
				enemies.add(createNewEnemy(1, 2));
			}
			for(int i = 0; i < 4; i++) {
				enemies.add(createNewEnemy(2, 1));
			}
		}
		if(waveNumber == 3) {
			for(int i = 0; i < 1; i++) {
				enemies.add(createNewEnemy(1, 3));
			}
			for(int i = 0; i < 2; i++) {
				enemies.add(createNewEnemy(2, 2));
			}
			for(int i = 0; i < 4; i++) {
				enemies.add(createNewEnemy(3, 1));
			}
		}
		if(waveNumber == 4) {
			for(int i = 0; i < 1; i++) {
				enemies.add(createNewEnemy(1, 4));
			}
			for(int i = 0; i < 1; i++) {
				enemies.add(createNewEnemy(2, 3));
			}
			for(int i = 0; i < 2; i++) {
				enemies.add(createNewEnemy(3, 2));
			}
		}
		if(waveNumber == 5) {
			for(int i = 0; i < 2; i++) {
				enemies.add(createNewEnemy(1, 4));
			}
			for(int i = 0; i < 1; i++) {
				enemies.add(createNewEnemy(2, 4));
			}
			for(int i = 0; i < 1; i++) {
				enemies.add(createNewEnemy(3, 3));
			}
		}
		if(waveNumber == 6) {
			for(int i = 0; i < 2; i++) {
				enemies.add(createNewEnemy(1, 4));
			}
			for(int i = 0; i < 2; i++) {
				enemies.add(createNewEnemy(2, 4));
			}
			for(int i = 0; i < 1; i++) {
				enemies.add(createNewEnemy(3, 4));
			}
		}
		if(waveNumber == 7) {
			for(int i = 0; i < 3; i++) {
				enemies.add(createNewEnemy(1, 4));
			}
			for(int i = 0; i < 2; i++) {
				enemies.add(createNewEnemy(2, 4));
			}
			for(int i = 0; i < 2; i++) {
				enemies.add(createNewEnemy(3, 4));
			}
		}
		if(waveNumber == 8) {
			for(int i = 0; i < 3; i++) {
				enemies.add(createNewEnemy(1, 4));
			}
			for(int i = 0; i < 3; i++) {
				enemies.add(createNewEnemy(2, 4));
			}
			for(int i = 0; i < 2; i++) {
				enemies.add(createNewEnemy(3, 4));
			}
		}
		if(waveNumber == 9) {
			for(int i = 0; i < 3; i++) {
				enemies.add(createNewEnemy(1, 4));
			}
			for(int i = 0; i < 3; i++) {
				enemies.add(createNewEnemy(2, 4));
			}
			for(int i = 0; i < 3; i++) {
				enemies.add(createNewEnemy(3, 4));
			}
		}		
		if(waveNumber == 10) { 
			
			wm.stop();
			wm.setKill(true);
			wm.dispose();
			bm.setKill(false);
			enemies.add(createNewEnemy(4, 8));	
		}
		
		if(waveNumber == 11) {
			bm.resetBM();
			bm.stop();
			bm.setKill(true);
			bm.dispose();
			gsm.pushState(GameStateManager.GAMEOVERSTATE);
			gsm.pushState(GameStateManager.HIGHSCORESTATE);
		}
	}
	
	private Enemy createNewEnemy(int type, int rank) {
		Enemy e = enemyPool.obtain();
		e.init(type, rank);
		
		return e;
	}
	
	public void handleInput() {
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		player.setDown(GameKeys.isDown(GameKeys.DOWN));
		player.setFiring(GameKeys.isDown(GameKeys.SPACE));
		if(GameKeys.isDown(GameKeys.A)) { player.setWeaponState(0); }
		if(GameKeys.isDown(GameKeys.S)) { player.setWeaponState(1); }
		if(GameKeys.isDown(GameKeys.D)) { player.setWeaponState(2); }
		if(GameKeys.isDown(GameKeys.F)) { player.setWeaponState(3); }
		if(GameKeys.isPressed(GameKeys.Q)) { player.increasePower(1); }
		if(GameKeys.isPressed(GameKeys.ESCAPE)) { gsm.pushState(GameStateManager.PAUSESTATE); }
	}
	
	public void dispose() {
		wm.dispose();
		sr.dispose();
		sb.dispose();
		font.dispose();
	}



}
