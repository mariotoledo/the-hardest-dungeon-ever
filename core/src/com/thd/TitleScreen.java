package com.thd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TitleScreen implements Screen{

	SpriteBatch batch;
	Texture title;
	Texture pressEnter;
	Texture credits;
	Texture SpJam_Logo;
	
	float pressEnterTimer;
	float backgroundTimer;
	
	Texture background;
	TextureRegion[][] backgroundFrames;
	TextureRegion backgroundFrame;
	
	int backgroundFrameIndex;
	
	//TODO: botar o press start para piscar
	
	public TitleScreen(){
		background = new Texture(Gdx.files.internal("TitleBackground.png"));
		title = new Texture(Gdx.files.internal("title.png"));
		pressEnter = new Texture(Gdx.files.internal("PressStart.png"));
		credits = new Texture(Gdx.files.internal("helloSword.png"));
		SpJam_Logo = new Texture(Gdx.files.internal("spjam.png"));
		
		backgroundFrames = TextureRegion.split(this.background, 640, 480);
		
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		pressEnterTimer += delta;
		backgroundTimer += 1;
		
		batch.begin();
		
		backgroundFrame = backgroundFrames[0][backgroundFrameIndex];	
		batch.draw(backgroundFrame, 0,  0);
		
		if(backgroundTimer % 5 == 0){
			if(backgroundFrameIndex >= 3)
			{
				backgroundFrameIndex = 0;
				backgroundTimer = 0;
			}
			else{
				backgroundFrameIndex++;
			}
		}
		
		batch.draw(title, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(credits, 10, 10);
		batch.draw(SpJam_Logo, Gdx.graphics.getWidth() - SpJam_Logo.getWidth() - 10, 10);
		
		if(pressEnterTimer > 0.5 && pressEnterTimer < 1)
			batch.draw(pressEnter, 235, 108, 171, 29);

		if(pressEnterTimer > 1){
			pressEnterTimer = 0;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			Main.getInstance().goToDialogue();
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		title.dispose();
		pressEnter.dispose();		
	}

}
