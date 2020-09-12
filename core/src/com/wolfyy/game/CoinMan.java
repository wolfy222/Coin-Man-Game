package com.wolfyy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CoinMan extends ApplicationAdapter
{
	SpriteBatch batch;
	
	@Override
	public void create ()// For First Time
	{
		batch = new SpriteBatch(); // STARTING POINT FOR PUTTING SOMETHING ON THE SCREEN

	}

	@Override
	public void render ()// RUNS UNTIL YOU FINISH THE GAME
	{

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
