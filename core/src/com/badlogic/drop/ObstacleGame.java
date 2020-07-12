package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class ObstacleGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	public abstract void create();
}
