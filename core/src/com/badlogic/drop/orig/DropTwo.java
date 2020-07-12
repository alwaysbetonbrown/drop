package com.badlogic.drop.orig;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class DropTwo extends Game {

	public SpriteBatch batch;
	public BitmapFont font;

	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

}
