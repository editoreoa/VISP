package com.visp.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.visp.gamestates.PlayState;
import com.visp.main.Game;

public class Player extends GameObject {
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;
	
	private int[] powerLevel;
	private int[] power;
	private int[] requiredPower = {1, 2, 3, 4, 5};
	
	private boolean[] overdrive;
	private long[] overdriveTimer;
	private long[] overdriveTimerDiff;
	private long overdriveLength = 6000;
	
	private int weaponState;
	
	private boolean recovering;
	private long recoveryTimer;
	
	private int lives;
	private int kills;
	private int totalKills;
	private int score;
	private double scoreMultiplier;
	
	public Player() {
		super(Game.WIDTH / 2, Game.HEIGHT / 2, 6, 3);
		
		firing = false;
		firingTimer = System.nanoTime();
		firingDelay = 1000;
		
		lives = 3;
		score = 0;
		scoreMultiplier = 1.0;
		kills = 0;
		totalKills = 0;
		power = new int[] {0, 0, 0, 0};
		powerLevel = new int[] {1, 1, 1, 1};
		overdrive = new boolean[] {false, false, false, false};
		overdriveTimer = new long[] {0, 0, 0, 0};
		overdriveTimerDiff = new long[] {0, 0, 0, 0};
		
		recovering = false;
		recoveryTimer = 0;
		
		weaponState = 2;
		
	}

	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	
	public void setWeaponState(int i) { if(i >= 0 && i < 4) weaponState = i; }
	
	public void setFiring(boolean b) { firing = b; }
	
	public int getScore() { return score; }
	public double getScoreMultiplier() { return scoreMultiplier; }
	public int getLives() { return lives; }
	public int getKills() { return kills; }
	public int getTotalKills() { return totalKills; }
	
	public boolean isDead() { return lives <= 0; }
	
	public boolean isRecovering() { return recovering; }
	
	public int getWeaponState() { return weaponState; }
	public int getPowerLevel(int i) { return powerLevel[i]; }
	public int getPower(int i) { return power[i]; }
	public int getRequiredPower(int i) { return powerLevel[i] + 1; }
	
	public void addScore(int i) { score += i * (int) scoreMultiplier; }
	public void addKills() { kills++; }
	public void gainLife() { lives++; }
	
	public void setScoreMultiplier(double m) {
		scoreMultiplier = m;
	}
	
	public void resetPowerLevel() { 
		for(int i = 0; i < powerLevel.length; i++) {
			powerLevel[i] = 1;
		}
		for(int i = 0; i < power.length; i++) {
			power[i] = 0;
		}
	}
	
	public void loseLife() {
		lives--;
		kills = 0;
		recovering = true;
		recoveryTimer = System.nanoTime();
	}
	
	public void increasePower(int i) {
		power[weaponState] += i;
		if(powerLevel[weaponState] == 4) {
			if(power[weaponState] > requiredPower[powerLevel[weaponState]]) {
				power[weaponState] = requiredPower[powerLevel[weaponState]];
				overdrive[weaponState] = true;				// trigger overdrive
				overdriveTimer[weaponState] = System.nanoTime();
			}
			return;
		}
		/* Luke's code block
		if(power > powerLevel) {
			powerLevel++;
			power = 0;
		}
		*/
		if(power[weaponState] >= requiredPower[powerLevel[weaponState]]) {
			power[weaponState] -= requiredPower[powerLevel[weaponState]];
			powerLevel[weaponState]++;
			WeaponMusic.setMusicLevel(weaponState, powerLevel[weaponState]);
		}
		
	}
	
	public void update() {
		
		if(left) { dx = -speed; }
		if(right) { dx = speed; }
		if(up) { dy = speed; }
		if(down) { dy = -speed; }
		
		// deceleration
		if(!left && !right && !up && !down) { dx = 0; dy = 0; }
		
		x += dx;
		y += dy;
		
		// update score multiplier
		if(kills >= 10) {
			kills = 0;
			scoreMultiplier++;
		}
		
		if(x < radius) x = radius;
		if(y < radius) y = radius;
		if(x > Game.WIDTH - radius) x = Game.WIDTH - radius;
		if(y > Game.HEIGHT - radius) y = Game.HEIGHT - radius;
		
		// update firing
		if(firing) {
			long elapsed = (System.nanoTime() - firingTimer) / 1000000;
			int elapsedMin = 0;
			do {
				elapsedMin += 20;
			} while(elapsedMin < firingDelay / (overdrive[1] ? 12 : (powerLevel[1] + 2)));
			elapsedMin -= 20;
			if(elapsed > elapsedMin) {
				firingTimer = System.nanoTime();
				SoundEffect.SHOT.play();
				if(overdrive[2]) {
					Random r = new Random();
					int low = 60; 		// min angle during spread overdrive
					int high = 120;		// max angle
					for(int i = 0; i < 6; i++) {
						PlayState.pBullets.add(new PlayerBullet(r.nextInt(high - low) + low, x + 2, y, 6 + r.nextInt(2) + powerLevel[0] * 2 , 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
					}
				}
				if(powerLevel[2] < 2 && !overdrive[2]) {
					PlayState.pBullets.add(new PlayerBullet(90, x + 2, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
				}
				else if(powerLevel[2] < 3 && !overdrive[2]) {
					PlayState.pBullets.add(new PlayerBullet(90, x + 7, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
					PlayState.pBullets.add(new PlayerBullet(90, x - 3, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
				}
				else if(powerLevel[2] < 4 && !overdrive[2]) {
					PlayState.pBullets.add(new PlayerBullet(90, x + 2, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
					PlayState.pBullets.add(new PlayerBullet(80, x + 2, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
					PlayState.pBullets.add(new PlayerBullet(100, x + 2, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
				} 
				else if(powerLevel[2] < 5 && !overdrive[2]) {
					PlayState.pBullets.add(new PlayerBullet(77, x + 2, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
					PlayState.pBullets.add(new PlayerBullet(90, x + 9, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
					PlayState.pBullets.add(new PlayerBullet(90, x - 5, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
					PlayState.pBullets.add(new PlayerBullet(103, x + 2, y, 7 + powerLevel[0] * 2, 2 * (overdrive[3] ? 2 : 1), powerLevel[3] + 1));
				}	
			}
		}
		
		// update overdrives
		for(int i = 0; i < overdrive.length; i++) {
			if(overdrive[i]) {
				overdriveTimerDiff[i] = (System.nanoTime() - overdriveTimer[i]) / 1000000;
				if(overdriveTimerDiff[i] > overdriveLength) {
					overdriveTimer[i] = 0;
					overdrive[i] = false;
				}
			}
		}
		
		// update recovery
		long elapsed = (System.nanoTime() - recoveryTimer) / 1000000;
		if(elapsed > 2000) {
			recovering = false;
			recoveryTimer = 0;
		}
		
	}
	
	public void draw(ShapeRenderer sr) {
		sr.setColor(.7f, .7f, .7f, 1);
		sr.begin(ShapeType.Filled);
		sr.circle(x, y, radius);
		sr.end();
		if(recovering) {
			sr.setColor(.7f, 0, 0, 1);			
		} else {
		sr.setColor(1, 1, 1, 1);
		}
		sr.begin(ShapeType.Filled);
		sr.circle(x, y, radius - 2);
		sr.end();
		
		// draw overdrive meters
		for(int i = 0; i < overdrive.length; i++) {
			if(overdriveTimer[i] != 0) {
				sr.begin(ShapeType.Line);
				sr.setColor(1, 1, 0, 1);
				sr.rect(62, 465 - 20 * i, 100, 9);
				sr.end();
				sr.begin(ShapeType.Filled);
				sr.rect(62, 465 - 20 * i,
					(int) (100 - 100.0 * overdriveTimerDiff[i] / overdriveLength), 9);
				sr.end();
			}
		}
	}
	
}








