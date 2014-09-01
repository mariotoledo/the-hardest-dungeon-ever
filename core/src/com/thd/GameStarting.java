package com.thd;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStarting implements Screen {
	Stage stage;
	
	MainCharacter hero;
	
	ShapeRenderer debug;
	
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	
	Rectangle hitboxattack;
	
	ArrayList<Enemy> enemies;
	
	Texture gameStarting;
	
	float screenTime;
	float prepareseTimer;
	
	SpriteBatch batch;
	
	public GameStarting(){
		stage = new Stage();
		hero = new MainCharacter(50, 50);
		
		debug = new ShapeRenderer();
		
		enemies = new ArrayList<Enemy>();
		
		for(int i = 0; i < 5; i++){
			double initialX = (Math.random() * Gdx.graphics.getWidth()) - hero.width;
			double initialY = (Math.random() * Gdx.graphics.getHeight()) - hero.height;
		
			while((initialX >= hero.positionX && initialX <= hero.positionX + hero.width) || (initialY >= hero.positionY && initialY <= hero.positionY + hero.height)){
				initialX = Math.random() * Gdx.graphics.getWidth();
				initialY = Math.random() * Gdx.graphics.getHeight();
			}
			enemies.add(new Enemy((float)initialX, (float)initialY, stage, true));
		}
		
		for(Enemy e : enemies){
			e.setHeroBox(hero.hitbox);
			stage.addActor(e);
		}
		stage.addActor(hero);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		tiledMap = new TmxMapLoader().load("tilemap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
		gameStarting = new Texture(Gdx.files.internal("preparese.png"));
		
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		prepareseTimer += delta;
		screenTime += delta;
		
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		

		
		if(screenTime > 5){
			Main.getInstance().goToDungeon(hero, enemies);
		}
		
		stage.draw();
		batch.begin();
		batch.draw(gameStarting, 80, 300, gameStarting.getWidth(), gameStarting.getHeight());
		batch.end();
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
	public void resize(int width, int height) {
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
