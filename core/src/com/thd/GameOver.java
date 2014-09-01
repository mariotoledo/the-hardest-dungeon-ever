package com.thd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver implements Screen{

	SpriteBatch batch;
	
	Texture velhote;
	Texture gameOver;
	
	float elapsedTime;
	
	public GameOver(){
		velhote = new Texture(Gdx.files.internal("gameover1.png"));
		gameOver = new Texture(Gdx.files.internal("gameover2.png"));
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		elapsedTime += delta;
		batch.begin();
		if(elapsedTime < 5){
			batch.draw(velhote, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		else if(elapsedTime > 5 && elapsedTime < 10){
			batch.draw(gameOver, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		} else{
			Main.getInstance().goToTitleScreen();
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
