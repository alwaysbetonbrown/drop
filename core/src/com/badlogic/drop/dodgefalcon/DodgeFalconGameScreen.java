package com.badlogic.drop.dodgefalcon;

import com.badlogic.drop.ObstacleGameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class DodgeFalconGameScreen extends ObstacleGameScreen {	
	
	public DodgeFalconGameScreen(final DodgeFalcon game) {
		super(game);
				
		// load the images for the droplet and the bucket, 64x64 pixels each
		obstacleImage = new Texture(Gdx.files.internal("enemy_jet.png"));
		playerImage = new Texture(Gdx.files.internal("falcon.png"));

		// load the drop sound effect and the rain background "music"
		impactSound = Gdx.audio.newSound(Gdx.files.internal("jet_explodes.wav"));
		motionMusic = Gdx.audio.newMusic(Gdx.files.internal("jet_flying.mp3"));
		motionMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}	
}
