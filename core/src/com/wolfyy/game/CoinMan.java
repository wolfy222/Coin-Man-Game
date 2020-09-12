package com.wolfyy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CoinMan extends ApplicationAdapter
{
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create ()// For First Time
	{
		batch = new SpriteBatch(); // STARTING POINT FOR PUTTING SOMETHING ON THE SCREEN
		img = new Texture("badlogic.jpg"); // IMAGE
	}

	@Override
	public void render ()// RUNS UNTIL YOU FINISH THE GAME
	{
		Gdx.gl.glClearColor(1, 0, 0, 1); // BACKGROUNG COLOR
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // DISPLAY IMAGE
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
