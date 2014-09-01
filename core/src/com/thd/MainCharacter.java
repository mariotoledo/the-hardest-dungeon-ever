package com.thd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

enum Direction{
	Down(0), Left(1), Right(2), Up(3);
	
	private final int value;
	private Direction(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}

public class MainCharacter extends Actor {
	private static final int MOVEMENT_SPEED = 5;
	
	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 4;
	
	private static final int FRAMES_PER_SECOND = 10;
	
	Rectangle hitbox, box, boxBullet;
	Texture walkTexture;
	TextureRegion[][] walkFrames;
	TextureRegion currentFrame;
	
	Direction direction;
	
	float positionX;
	float positionY;
	int width;
	int height;
	
	float stateTime;
	
	boolean animating;
	
	boolean attacking;
	
	boolean dying;
	
	boolean dead;
	
	boolean change;
	
	int currentFrameIndex = 0;
	
	public void changeImage(String imageName){
		this.walkTexture = new Texture(Gdx.files.internal(imageName));
		this.width = walkTexture.getWidth() / FRAME_ROWS;
		this.height = walkTexture.getHeight() / FRAME_COLS;
		
		walkFrames = TextureRegion.split(this.walkTexture, width, height);
	}
	
	public void bulletHitbox(boolean change){
		this.change = change;
	}
	
	public MainCharacter(float initialPositionX, float initialPositionY){
		changeImage("Sheet2.png");
		direction = Direction.Down;
		
		stateTime = 0f;
		
		this.positionX = initialPositionX;
		this.positionY = initialPositionY;

		hitbox = new Rectangle(this.positionX, this.positionY, width, height);
		box = new Rectangle(this.positionX + 20, this.positionY + 10, width - 40, height - 20);
		boxBullet = new Rectangle(this.positionX + 20, this.positionY, width - 45, height - 40);
		
		setBounds(this.positionX, this.positionY, this.width, this.height);
		
	}
	
	public boolean isOppositeDirection(Direction direction){
		if(direction == Direction.Down)
		{
			return this.direction == Direction.Up;
		}
		if(direction == Direction.Up)
		{
			return this.direction == Direction.Down;
		}
		if(direction == Direction.Left)
		{
			return this.direction == Direction.Right;
		}
		if(direction == Direction.Right)
		{
			return this.direction == Direction.Left;
		}
		
		return false;
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
					currentFrameIndex = 0;
					stateTime = 0;
					dead = true;
				}
				else{
					currentFrameIndex++;
				}
			}
		} else if(attacking){
			if(stateTime % FRAMES_PER_SECOND == 0){
				if(stateTime == FRAMES_PER_SECOND * FRAME_COLS)
				{
					currentFrameIndex = 0;
					stateTime = 0;
					changeImage("Sheet2.png");
					attacking = false;
				}
				else{
					currentFrameIndex++;
				}
			}
		} else if(animating){
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
		hitbox.setPosition(positionX, positionY);
		box.setPosition(this.positionX + 20, this.positionY + 10);
		if(change == false){
			box.setPosition(this.positionX + 20, this.positionY + 10);
		}else if(change == true){
			boxBullet.setPosition(this.positionX + 25, this.positionY + 30);
		}
		
	}
	
	public void punch(){
		attacking = true;
		changeImage("AttackSheet.png");
		currentFrameIndex = 0;
	}
	
	public void die(){
		dying = true;
		changeImage("HeroDying.png");
		currentFrameIndex = 0;
	}
	
	public void moveRight(){
		if(positionX + width < Gdx.graphics.getWidth()){
			this.positionX += MOVEMENT_SPEED;
			this.animating = true;
		}
		
		this.direction = Direction.Right;		
	}
	
	public void moveLeft(){
		if(positionX > 0){
			this.positionX -= MOVEMENT_SPEED;
			this.animating = true;
		}
		
		this.direction = Direction.Left;		
	}
	
	public void moveUp(){
		if(positionY + height < Gdx.graphics.getHeight()){
			this.positionY += MOVEMENT_SPEED;
			this.animating = true;
		}
		
		this.direction = Direction.Up;
	}
	
	public void moveDown(){
		if(positionY > 0){
			this.positionY -= MOVEMENT_SPEED;
			this.animating = true;
		}
		
		this.direction = Direction.Down;
	}
	
	public void stop(){
		this.animating = false;
	}
	
	@Override
	public void act(float delta){
		
	}
}
