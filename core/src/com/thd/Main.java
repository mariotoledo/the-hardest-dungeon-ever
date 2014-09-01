package com.thd;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {

	private static Main _instance = null;
	
	TitleScreen titleScreen;
	Dungeon dungeon;
	GameOver gameOver;
	Dialogue dialogue;
	BulletStorm bulletStorm;
	GameStarting gameStarting;
	
	public static Main getInstance(){
		if(_instance == null)
			_instance = new Main();
		
		return _instance;
	}

	@Override
	public void create() {
		goToTitleScreen();
	}
	
	@Override
	public void dispose(){
		titleScreen.dispose();
		dungeon.dispose();
		bulletStorm.dispose();
	}
	
	public void goToTitleScreen(){
		titleScreen = new TitleScreen();
		this.setScreen(titleScreen);
	}
	
	public void goToDungeon(MainCharacter hero, ArrayList<Enemy> enemies){
		dungeon = new Dungeon(hero, enemies);
		this.setScreen(dungeon);
	}
	
	public void goToGameOver(){
		gameOver = new GameOver();
		this.setScreen(gameOver);		
	}
	
	public void goToDialogue(){
		dialogue = new Dialogue();
		this.setScreen(dialogue);
	}
	
	public void goToGameStarting(){ 
		gameStarting = new GameStarting();
		this.setScreen(gameStarting);
	}
	
	public void goToBulletStorm(MainCharacter hero, ArrayList<Enemy> enemies){
		bulletStorm = new BulletStorm(hero, enemies);
		this.setScreen(bulletStorm);
	}
}
