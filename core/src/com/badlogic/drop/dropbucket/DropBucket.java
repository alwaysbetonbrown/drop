package com.badlogic.drop.dropbucket;

import com.badlogic.drop.ObstacleGame;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DropBucket extends ObstacleGame {

	@Override
	public void create() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new DropBucketGameScreen(this));
	}

}
