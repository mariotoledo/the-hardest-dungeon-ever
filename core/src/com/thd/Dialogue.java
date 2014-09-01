package com.thd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Dialogue implements Screen {
	
	int currentStage;
	
	SpriteBatch batch;
	Texture dialogue1;
	Texture dialogue2;
	Texture dialogue3;
	Texture dialogue4;
	Texture dialogue5;
	Texture dialogue6;
	Texture dialogue7;
	
	float inputDelay;
	
	BitmapFont font;
	public Dialogue(){
		dialogue1 = new Texture(Gdx.files.internal("dialogue1.png"));
		dialogue2 = new Texture(Gdx.files.internal("dialogue2.png"));
		dialogue3 = new Texture(Gdx.files.internal("dialogue3.png"));
		dialogue4 = new Texture(Gdx.files.internal("dialogue4.png"));
		dialogue5 = new Texture(Gdx.files.internal("dialogue5.png"));
		dialogue6 = new Texture(Gdx.files.internal("dialogue6.png"));
		dialogue7 = new Texture(Gdx.files.internal("dialogue7.png"));
		
		currentStage = 0;
		
		batch = new SpriteBatch();
		
		font = new BitmapFont();
	}
	
	@Override
	public void render(float delta) {
		inputDelay += delta;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && inputDelay > 1){
			currentStage++;
			inputDelay = 0;
		}
		
		switch (currentStage){
			case 0:
				batch.draw(dialogue1, 0, 0, dialogue1.getWidth(), dialogue1.getHeight());
				break;
			case 1:
				batch.draw(dialogue2, 0, 0, dialogue2.getWidth(), dialogue2.getHeight());
				break;
			case 2:
				batch.draw(dialogue3, 0, 0, dialogue3.getWidth(), dialogue3.getHeight());
				break;
			case 3:
				batch.draw(dialogue4, 0, 0, dialogue4.getWidth(), dialogue4.getHeight());
				break;
			case 4:
				batch.draw(dialogue5, 0, 0, dialogue5.getWidth(), dialogue5.getHeight());
				break;
			case 5:
				batch.draw(dialogue6, 0, 0, dialogue6.getWidth(), dialogue6.getHeight());
				break;
			case 6:
				batch.draw(dialogue7, 0, 0, dialogue7.getWidth(), dialogue7.getHeight());
				break;
			case 7:
				Main.getInstance().goToGameStarting();
				break;
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
		// TODO Auto-generated method stub
		
	}

}
