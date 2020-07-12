package com.badlogic.drop.dropbucket;

import com.badlogic.drop.ObstacleGameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class DropBucketGameScreen extends ObstacleGameScreen {

	public DropBucketGameScreen(final DropBucket game) {
		super(game);
		
		// load the images for the droplet and the bucket, 64x64 pixels each
		obstacleImage = new Texture(Gdx.files.internal("drop.png"));
		playerImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		impactSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		motionMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		motionMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

}
