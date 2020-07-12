package com.badlogic.drop.dodgefalcon;

import com.badlogic.drop.ObstacleGame;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DodgeFalcon extends ObstacleGame {
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new DodgeFalconGameScreen(this));
	}
}
