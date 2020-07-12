package com.badlogic.drop;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class ObstacleGameScreen implements Screen 
{
	protected final ObstacleGame game; 
	protected Texture obstacleImage;
	protected Texture playerImage;
	protected Sound impactSound;
	protected Music motionMusic;
	protected OrthographicCamera camera;
	protected Rectangle player;
	protected Array<Rectangle> obstacles;
	long lastDropTime;
	int dropsGathered;
	
	public ObstacleGameScreen(final ObstacleGame game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// create a Rectangle to logically represent the player
		player = new Rectangle();
		player.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		player.y = 20; // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		player.width = 64;
		player.height = 64;

		// create the raindrops array and spawn the first raindrop
		obstacles = new Array<Rectangle>();
		spawnObstacle();
	}
	
	
	private void spawnObstacle() 
	{
		Rectangle obstacle = new Rectangle();
		obstacle.x = MathUtils.random(0, 800 - 64);
		obstacle.y = 480;
		obstacle.width = 64;
		obstacle.height = 64;
		obstacles.add(obstacle);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	@Override
	public void show() {
		motionMusic.play();
	}

	@Override
	public void render(float delta)
	{
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
		game.batch.draw(playerImage, player.x, player.y, player.width, player.height);
		for (Rectangle obstacle : obstacles) {
			game.batch.draw(obstacleImage, obstacle.x, obstacle.y);
		}
		game.batch.end();

		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			player.x = touchPos.x - 64 / 2;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			player.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			player.x += 200 * Gdx.graphics.getDeltaTime();

		// make sure the player stays within the screen bounds
		if (player.x < 0)
			player.x = 0;
		if (player.x > 800 - 64)
			player.x = 800 - 64;

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnObstacle();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the player. In the later case we increase the 
		// value our drops counter and add a sound effect.
		Iterator<Rectangle> iter = obstacles.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0)
				iter.remove();
			if (raindrop.overlaps(player)) {
				dropsGathered++;
				impactSound.play();
				iter.remove();
			}
		}
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
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
