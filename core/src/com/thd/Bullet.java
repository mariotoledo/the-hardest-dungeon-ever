package com.thd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bullet extends Actor {
	public final static int FRAMES_PER_SECOND = 5;
	
	Texture bulletTexture;
	TextureRegion[][] bulletFrames;
	TextureRegion currentBulletFrame;
	
	float attackAnimationTime;
	
	Rectangle hitbox;
	SpriteBatch batch;
	boolean player, direction, animating;
	
	float positionX, positionY, posRandomX, posRandomY;
	int width, height;
	
	int currentBulletFrameIndex = 0;
	
	public void setPosition(float x, float y){
		this.positionX = x;
		this.positionY = y;
	}
	
	public Bullet(String text, float initialX, float initialY, int spriteNumberofRows, int spriteNumberofColumns){

		bulletTexture = new Texture(Gdx.files.internal(text));
		
		setPosition(initialX, initialY);
		batch = new SpriteBatch();
		
		this.width = bulletTexture.getWidth() / spriteNumberofColumns;
		this.height = bulletTexture.getHeight() / spriteNumberofRows;
		
		posRandomX = (float) ((Math.random() * 1280) - 640) + 1;
		posRandomY = (float) (Math.random() * 480) + 1;
		
		
		hitbox = new Rectangle(positionX, positionY, width, height);
		
		bulletFrames = TextureRegion.split(bulletTexture, width, height);
		
		if(Math.random() * 100 > 50){
			direction = true;
		}else{
			direction = false;
		}
		
		animating = true;
		attackAnimationTime = 0;
		//player = true;
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		
		if(attackAnimationTime % 4 == 0){
			if(currentBulletFrameIndex >= 2){
				currentBulletFrameIndex = 0;
				attackAnimationTime = 0;
				//animating = false;
			}
			else{
				
				currentBulletFrameIndex++;
			}
		}
		
		batch.draw(bulletFrames[0][currentBulletFrameIndex], this.positionX,  this.positionY);
		
		attackAnimationTime++;
		/*if(animating){
			
		} else {
			currentBulletFrameIndex = 0;
			attackAnimationTime = 0;
		}*/
		
		hitbox.setPosition(positionX, positionY);
		
	}
	
	@Override
	public void act(float delta){
		if(player == true){
			positionY += 5;
		}else{
			double[] angularELinear = GeometricHelper.getAngLin(positionX, posRandomX, positionY, posRandomY);
			if(direction){
				positionX++;
			}else{
				positionX--;
			}
			positionY = (float)((angularELinear[0] * positionX) + angularELinear[1]);
		}
			
	}
	
}
