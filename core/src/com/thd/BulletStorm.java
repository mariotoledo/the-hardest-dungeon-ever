package com.thd;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BulletStorm implements Screen {

	MainCharacter hero;
	ArrayList<Enemy> enemies;
	
	Bullet bullet;
	
	float delay;
	
	ShapeRenderer debug;
	
	ArrayList<Bullet> balas;
	
	Stage stage;
	
	double randomNumber;
	
	float elapsedTime;
	
	SpriteBatch spriteBatch;
	
	Texture background;
	
	public BulletStorm(MainCharacter hero, ArrayList<Enemy> enemies){
		randomNumber = (Math.random() * 1000) + 5;
		
		stage = new Stage();
		
		this.hero = hero;
		
		this.enemies = enemies;
		
		balas = new ArrayList<Bullet>();
		
		hero.changeImage("Fly.png");
		hero.bulletHitbox(true);
		
		debug = new ShapeRenderer();
		
		stage.addActor(hero);
		for(Enemy e: enemies){
			stage.addActor(e);
		}
		
		spriteBatch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("StarrySky.png"));
	}
	
	@Override
	public void render(float delta) {
		elapsedTime += 1;
		
		if((int)elapsedTime % (int)randomNumber == 0)
			Main.getInstance().goToDungeon(hero, enemies);
		
		//##############
		if(!hero.dying && !hero.dead){
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).setEnemies(false);
			}
			//##############
			debug.begin(ShapeType.Line);
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			delay += delta;
			
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
				hero.moveRight();
			else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
				hero.moveDown();
			else if(Gdx.input.isKeyPressed(Input.Keys.UP))
				hero.moveUp();
			else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
				hero.moveLeft();
			else
				hero.stop();
			if(Gdx.input.isKeyPressed(Input.Keys.Q)){
				if(delay > 0.15){
				Bullet bullet = new Bullet("Bullet1.png", hero.positionX + hero.width/2, hero.positionY + hero.height, 1, 3);
				balas.add(bullet);
				stage.addActor(bullet);
				delay = 0;
				}
			}		
			
			if(delay > 0.1){
				for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).setHeroPosition(hero.positionX, hero.positionY);
				bullet = new Bullet("Bullet1.png", enemies.get(i).positionX + enemies.get(i).width/2, enemies.get(i).positionY + enemies.get(i).height/2, 1, 3);
				balas.add(bullet);
				stage.addActor(bullet);
				delay = 0;
				}
			}
			
			for(int i = 0; i < balas.size(); i++){
				if(balas.get(i).hitbox.overlaps(hero.boxBullet)){ // Colisão entre tiro e herói
					hero.die();
				}
				if(Settings.debugMode){
					debug.rect(balas.get(i).hitbox.x, balas.get(i).hitbox.y, balas.get(i).hitbox.width, balas.get(i).hitbox.height);
				}
				if(balas.get(i).positionY > Gdx.graphics.getHeight() || balas.get(i).positionY < 0 || balas.get(i).positionX > Gdx.graphics.getWidth() || balas.get(i).positionX < 0){
					balas.get(i).remove();
					balas.remove(i);
				}	
			}
			stage.act(delta);
			
			if(Settings.debugMode){
				debug.rect(hero.boxBullet.x, hero.boxBullet.y, hero.boxBullet.width, hero.boxBullet.height);
			}
		} else if(hero.dead){
			Main.getInstance().goToGameOver();
		}
		
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0, background.getWidth(), background.getHeight());
		spriteBatch.end();
		debug.end();
		
		stage.draw();
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
