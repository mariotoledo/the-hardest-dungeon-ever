package com.thd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Attack extends Actor {
	public final static int FRAMES_PER_SECOND = 15;
	
	Texture attackTexture;
	TextureRegion[] attackFrames;
	TextureRegion currentAttackFrame;
	Rectangle hitbox;
	
	float attackAnimationTime;
	
	float attackPositionX;
	float attackPositionY;
	int width;
	int height;
	
	boolean animating;
	
	int currentAttackFrameIndex = 0;
	
	public void setPosition(float x, float y){
		this.attackPositionX = x;
		this.attackPositionY = y;
	}
	
	public Attack(String spriteFileName, int spriteNumberOfColumns, int spriteNumberofRows){
		this.attackTexture = new Texture(Gdx.files.internal(spriteFileName));
		this.width = attackTexture.getWidth() / spriteNumberofRows;
		this.height = attackTexture.getHeight() / spriteNumberOfColumns;
		
		hitbox = new Rectangle(attackPositionX, attackPositionY, width, height);
		
		attackFrames = TextureRegion.split(this.attackTexture, attackTexture.getWidth() / spriteNumberofRows, attackTexture.getHeight())[0];
		
		setBounds(this.attackPositionX, this.attackPositionY, this.width, this.height);
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		currentAttackFrame = attackFrames[currentAttackFrameIndex];
		
		if(animating){
			batch.draw(currentAttackFrame, this.attackPositionX,  this.attackPositionY);
			if(attackAnimationTime % FRAMES_PER_SECOND == 0){
				if(currentAttackFrameIndex == 3)
				{
					currentAttackFrameIndex = 0;
					attackAnimationTime = 0;
					animating = false;
				}
				else{
					currentAttackFrameIndex++;
				}
			}
		} else {
			currentAttackFrameIndex = 0;
			attackAnimationTime = 0;
		}
		hitbox.setPosition(attackPositionX, attackPositionY);
	}
	
	public void animate(boolean start){
		animating = start;
	}
	public Rectangle getHitbox(Rectangle r){
		return hitbox;
	}
}
