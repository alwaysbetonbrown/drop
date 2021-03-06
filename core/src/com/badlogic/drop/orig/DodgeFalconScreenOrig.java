package com.badlogic.drop.orig;

import java.util.Iterator;

import com.badlogic.drop.dodgefalcon.DodgeFalcon;
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

public class DodgeFalconScreenOrig implements Screen {
	private final DodgeFalcon game;
	
	Texture enemyImage;
	Texture falconImage;
	Sound crashSound;
	Music jetMusic;
	OrthographicCamera camera;
	Rectangle bucket;
	Array<Rectangle> enemies;
	long lastDropTime;
	int dropsGathered;

	
	public DodgeFalconScreenOrig(final DodgeFalcon game) {
		this.game = game;

		// load the images for the droplet and the bucket, 64x64 pixels each
		enemyImage = new Texture(Gdx.files.internal("enemy_jet.jpg"));
		falconImage = new Texture(Gdx.files.internal("falcon.png"));

		// load the drop sound effect and the rain background "music"
		crashSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		jetMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		jetMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// create a Rectangle to logically represent the bucket
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		bucket.width = 64;
		bucket.height = 64;

		// create the raindrops array and spawn the first raindrop
		enemies = new Array<Rectangle>();
		spawnRaindrop();
	}

	private void spawnRaindrop() {
		Rectangle enemy = new Rectangle();
		enemy.x = MathUtils.random(0, 800 - 64);
		enemy.y = 480;
		enemy.width = 64;
		enemy.height = 64;
		enemies.add(enemy);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
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
		game.batch.draw(falconImage, bucket.x, bucket.y, bucket.width, bucket.height);
		for (Rectangle enemy : enemies) {
			game.batch.draw(enemyImage, enemy.x, enemy.y);
		}
		game.batch.end();

		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			bucket.x += 200 * Gdx.graphics.getDeltaTime();

		// make sure the bucket stays within the screen bounds
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 800 - 64)
			bucket.x = 800 - 64;

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we increase the 
		// value our drops counter and add a sound effect.
		Iterator<Rectangle> iter = enemies.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0)
				iter.remove();
			if (raindrop.overlaps(bucket)) {
				dropsGathered++;
				crashSound.play();
				iter.remove();
			}
		}
	}


	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		jetMusic.play();
	}

	@Override
	public void dispose() {
		enemyImage.dispose();
		falconImage.dispose();
		crashSound.dispose();
		jetMusic.dispose();
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

}
