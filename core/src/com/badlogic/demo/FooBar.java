package com.badlogic.demo;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class FooBar extends ApplicationAdapter {
	private OrthographicCamera cm;
	private SpriteBatch bh;
	
	private Texture di;
	private Texture bi;
	private Sound ds;
	private Music rm;
	
	private Rectangle b;
	
	private Array<Rectangle> rds;
	
	private long ldt;
	
	@Override
	public void create () {
		di = new Texture(Gdx.files.internal("dp.png"));
		bi = new Texture(Gdx.files.internal("bt.png"));		
		ds = Gdx.audio.newSound(Gdx.files.internal("dp.wav"));
		rm = Gdx.audio.newMusic(Gdx.files.internal("rn.mp3"));		
		rm.setLooping(true);
		rm.play();		
		cm = new OrthographicCamera();
		cm.setToOrtho(false, 800, 480);		
		bh = new SpriteBatch();
		b = new Rectangle();
		b.x = 800 / 2 - 64 / 2;
		b.y = 20;
		b.width = 64;
		b.height = 64;		
		rds = new Array<Rectangle>();
		Rectangle rd = new Rectangle();
		rd.x = MathUtils.random(0, 800-64);
		rd.y = 480;
		rd.width = 64;
		rd.height = 64;
		rds.add(rd);
		ldt = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cm.update();
		
		bh.setProjectionMatrix(cm.combined);
		bh.begin();
		bh.draw(bi, b.x, b.y);
		for(Rectangle raindrop: rds) {
			bh.draw(di, raindrop.x, raindrop.y);
		}
		bh.end();
		
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			cm.unproject(touchPos);
			b.x = touchPos.x - 64 / 2;
		}
		
	   if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) b.x -= 200 * Gdx.graphics.getDeltaTime();
	   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) b.x += 200 * Gdx.graphics.getDeltaTime();
	   
	   if(b.x < 0) b.x = 0;
	   if(b.x > 800 - 64) b.x = 800 - 64;
	   
	   if(TimeUtils.nanoTime() - ldt > 1000000000) {
		   Rectangle rd = new Rectangle();
		   rd.x = MathUtils.random(0, 800-64);
		   rd.y = 480;
		   rd.width = 64;
		   rd.height = 64;
		   rds.add(rd);
		   ldt = TimeUtils.nanoTime();
	   }
	   
	   for (Iterator<Rectangle> iter = rds.iterator(); iter.hasNext(); ) {
		   Rectangle rd = iter.next();
		   rd.y -= 200 * Gdx.graphics.getDeltaTime();
		   if(rd.y + 64 < 0) iter.remove();
		   
	      if(rd.overlaps(b)) {
	          ds.play();
	          iter.remove();
	       }
	   }	   
	}
	
	@Override
	public void dispose () {
		di.dispose();
		bi.dispose();
		ds.dispose();
		rm.dispose();
		bh.dispose();
	}
	}
