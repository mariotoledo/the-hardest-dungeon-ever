package com.thd;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Enemy extends Actor{
	private static final int MOVEMENT_SPEED = 2;
	
	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 4;
	
	private static final int FRAMES_PER_SECOND = 10;
	
	Texture walkTexture;
	TextureRegion[][] walkFrames;
	TextureRegion currentFrame;
	Rectangle hitbox, herobox;
	
	Direction direction;
	
	float positionX;
	float positionY;
	int width;
	int height;
	
	float stateTime;
	
	double elapsedTime;
	
	boolean animating;
	
	int currentFrameIndex = 0;
	
	double randomMovement;
	
	boolean dying;
	
	boolean dead;
	
	boolean enemiesDoMove;
	
	private ArrayList<Bullet> balas;
	private int delay;
	
	public void changeImage(String imageName){
		this.walkTexture = new Texture(Gdx.files.internal(imageName));
		this.width = walkTexture.getWidth() / FRAME_ROWS;
		this.height = walkTexture.getHeight() / FRAME_COLS;
		
		walkFrames = TextureRegion.split(this.walkTexture, width, height);
	}
	
	public Enemy(float initialPositionX, float initialPositionY, Stage stage, boolean doMove){
		changeImage("Enemy.png");
		
		direction = Direction.Down;
		
		stateTime = 0f;
		
		this.positionX = initialPositionX;
		this.positionY = initialPositionY;
		this.enemiesDoMove = doMove;
		
		hitbox = new Rectangle(this.positionX + 15, this.positionY + 20, this.width - 40, this.height - 40);
		
		setBounds(this.positionX, this.positionY, this.width, this.height);
		
		randomMovement = (Math.random() * 10) + 1;
		balas = new ArrayList<Bullet>();
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		stateTime += alpha;		
		
		currentFrame = walkFrames[direction.getValue()][currentFrameIndex];		
		batch.draw(currentFrame, this.positionX,  this.positionY);
		
		if(dying){
			if(stateTime % FRAMES_PER_SECOND == 0){
				if(stateTime == FRAMES_PER_SECOND * FRAME_COLS)
				{
					dead = true;
					dying = false;
					stateTime = 0;
					currentFrameIndex = 0;
				}
				else{
					currentFrameIndex++;
				}
			}
		}
		else if(animating){
			if(stateTime % FRAMES_PER_SECOND == 0){
				if(stateTime == FRAMES_PER_SECOND * FRAME_COLS)
				{
					currentFrameIndex = 0;
					stateTime = 0;
				}
				else{
					currentFrameIndex++;
				}
			}
		} else {
			currentFrameIndex = 0;
			stateTime = 0;
		}
		
		if(hitbox != null)
			hitbox.setPosition(this.positionX + 30, this.positionY + 20);
		
	}
	
	public void moveRight(){
		this.positionX += MOVEMENT_SPEED;
		this.direction = Direction.Right;
		this.animating = true;
	}
	
	public void moveLeft(){
		this.positionX -= MOVEMENT_SPEED;
		this.direction = Direction.Left;
		this.animating = true;
	}
	
	public void moveUp(){
		this.positionY += MOVEMENT_SPEED;
		this.direction = Direction.Up;
		this.animating = true;
	}
	
	public void moveDown(){
		this.positionY -= MOVEMENT_SPEED;
		this.direction = Direction.Down;
		this.animating = true;
	}
	
	public void stop(){
		this.animating = false;
	}
	
	public void die(){
		dying = true;
		changeImage("EnemyDying.png");
		currentFrameIndex = 0;
		direction = Direction.Down;
		hitbox = null;
	}
	
	public void setHeroBox(Rectangle r){
		this.herobox = r;
	}
	
	float heroPosX, heroPosY;
	
	public void setHeroPosition(float x, float y){
		this.heroPosX = x;
		this.heroPosY = y;
	}
	
	
	@Override
	public void act(float delta){
		elapsedTime += delta;		
		
		if(elapsedTime > 1){
			randomMovement = (Math.random() * 100) + 1;
			elapsedTime = 0;
		}
		
		if(dying == false)
		{
			if(this.hitbox.overlaps(herobox)){	
				if(heroPosX < positionX){
					this.direction = Direction.Left;
				}
				if(heroPosX > positionX){
					this.direction = Direction.Right;
				}
			}else if(enemiesDoMove == true){
				if((int)randomMovement > 0 && (int)randomMovement < 25)
					moveRight();
				else if((int)randomMovement > 25 && (int)randomMovement < 50)
					moveUp();
				else if((int)randomMovement > 50 && (int)randomMovement < 75)
					moveDown();
				else if((int)randomMovement > 75 && (int)randomMovement < 100)
					moveLeft();
				System.out.println("Enemies do move: " + enemiesDoMove);
			}
		} else {
			direction = Direction.Down;
		}
	}
	
	public void setEnemies(boolean doEnemiesMove){
		this.enemiesDoMove = doEnemiesMove;
	}
}
