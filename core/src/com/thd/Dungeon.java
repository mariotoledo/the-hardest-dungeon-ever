package com.thd;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Dungeon extends ApplicationAdapter implements Screen {

	Stage stage;
	
	MainCharacter hero;
	
	ShapeRenderer debug;
	
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	
	Rectangle hitboxattack;
	
	ArrayList<Enemy> enemies;
	
	Rectangle vectorTop;
	Rectangle vectorLeft;
	Rectangle vectorBottom;
	Rectangle vectorRight;
	
	Rectangle deathRectangle;
	
	float elapsedTimeSincePlayerLost;
	
	ShapeRenderer shapeRenderer;
	
	float elapsedTime;
	
	double randomNumber;
	
	public Dungeon(MainCharacter hero, ArrayList<Enemy> enemies){
		randomNumber = (Math.random() * 1000) + 5;
		
		stage = new Stage();
		this.hero = hero;//new MainCharacter(50, 50);
		
		debug = new ShapeRenderer();
		
		this.enemies = enemies;//new ArrayList<Enemy>();
		
		for(int i = 0; i < 2; i++){
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
			e.setEnemies(true);
		}
		
		stage.addActor(hero);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		tiledMap = new TmxMapLoader().load("tilemap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
		vectorTop = new Rectangle(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1);
		vectorBottom = new Rectangle(0, 0, Gdx.graphics.getWidth(), 1);
		vectorLeft = new Rectangle(0, 0, 1, Gdx.graphics.getHeight());
		vectorRight = new Rectangle(Gdx.graphics.getWidth(), 0, 1, Gdx.graphics.getHeight());
		
		deathRectangle = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		hero.changeImage("Sheet2.png");
		hero.bulletHitbox(true);
		
		shapeRenderer = new ShapeRenderer();
	}
	
	private void goToBulletStorm(){
		Main.getInstance().goToBulletStorm(this.hero, this.enemies);
	}
	
	@Override
	public void render(float delta) {
		elapsedTime += 1;
		
		if((int)elapsedTime % (int)randomNumber == 0)
			Main.getInstance().goToBulletStorm(hero, enemies);
		
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debug.begin(ShapeType.Line);
		
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		
		if(Settings.debugMode){
			debug.rect(hero.hitbox.x, hero.hitbox.y, hero.hitbox.width, hero.hitbox.height);
			debug.rect(hero.box.x, hero.box.y, hero.box.width, hero.box.height);
		}
		
		stage.draw();
		
		//CHECK FOR ENEMY COLISION
		for(int i = 0; i<enemies.size(); i++){
			enemies.get(i).setEnemies(true);
			enemies.get(i).setHeroBox(hero.hitbox);
			enemies.get(i).setHeroPosition(hero.positionX, hero.positionY);
			
			if(enemies.get(i).hitbox != null)
			{
				if(enemies.get(i).hitbox.overlaps(vectorLeft)){
					enemies.get(i).moveRight();
				}
				
				if(enemies.get(i).hitbox.overlaps(vectorRight)){
					enemies.get(i).moveLeft();
				}
				
				if(enemies.get(i).hitbox.overlaps(vectorTop)){
					enemies.get(i).moveDown();
				}
				
				if(enemies.get(i).hitbox.overlaps(vectorBottom)){
					enemies.get(i).moveUp();
				}
				
				if (enemies.get(i).hitbox.overlaps(hero.box)){
					hero.die();
				}
				
				if(enemies.get(i).hitbox.overlaps(hero.hitbox)){
					if(hero.isOppositeDirection(enemies.get(i).direction) && hero.attacking){
						enemies.get(i).die();
					} 
				}
				
				if(Settings.debugMode){
					if(enemies.get(i).hitbox != null)
						debug.rect(enemies.get(i).hitbox.x, enemies.get(i).hitbox.y, enemies.get(i).hitbox.width, enemies.get(i).hitbox.height);
				}
			}			
			
			if(enemies.get(i).dead){
				enemies.get(i).remove();
				enemies.remove(i);
			}
		}		
		
		if(!hero.dying && !hero.dead){
			//UPDATES HEROS MOVEMENT
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
				hero.moveRight();
			else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
				hero.moveDown();
			else if(Gdx.input.isKeyPressed(Input.Keys.UP))
				hero.moveUp();
			else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
				hero.moveLeft();
			else if(Gdx.input.isKeyPressed(Input.Keys.A)){
				hero.stop();
				hero.punch();
			}
			else
				hero.stop();
			stage.act(delta);
		}
		else if(hero.dead){
			Main.getInstance().goToGameOver();
		}		
		debug.end();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}