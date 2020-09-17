package com.wolfyy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter
{
	SpriteBatch batch;

	// Texture -> Image
	Texture background;
	Texture[] man;
	int manState;
	int pause = 0;
	float gravity = 0.25f;
	float velocity = 0;
	int manY = 0;
	Rectangle manRectangle;

	// Coin objects
	ArrayList<Integer> coinXs = new ArrayList<>();
	ArrayList<Integer> coinYs = new ArrayList<>();
	ArrayList<Rectangle> coinRectangles = new ArrayList<>();
	Texture coin;
	int coinCount;

	// Bomb objects
	ArrayList<Integer> bombXs = new ArrayList<>();
	ArrayList<Integer> bombYs = new ArrayList<>();
	ArrayList<Rectangle> bombRectangles = new ArrayList<>();
	Texture bomb;
	int bombCount;

	Random random;

	int score;
	// for score text
	BitmapFont font;
	int gameState = 0;
	Texture dizzy;

	Music mario;
	Music coinSound;
	Music bombSound;

	int highestScore;
	BitmapFont highFont;

	
	@Override
	public void create ()// For First Time
	{
		batch = new SpriteBatch(); // STARTING POINT FOR PUTTING SOMETHING ON THE SCREEN
		background = new Texture("bg.png");
		man = new Texture[4];
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame-2.png");
		man[2] = new Texture("frame-3.png");
		man[3] = new Texture("frame-4.png");

		manY = Gdx.graphics.getHeight()/2;  // CENTER OF SCREEN

		coin = new Texture("coin.png");
		bomb = new Texture("bomb.png");

		random = new Random();

		font= new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		dizzy = new Texture("dizzy-1.png");

		mario = Gdx.audio.newMusic(Gdx.files.internal("mario.mp3"));
		coinSound = Gdx.audio.newMusic(Gdx.files.internal("coin.mp3"));
		bombSound = Gdx.audio.newMusic(Gdx.files.internal("bomb.mp3"));

		highFont= new BitmapFont();
		highFont.setColor(Color.RED);
		highFont.getData().setScale(7);



	}

	public void makeCoin()
	{
		float height  = random.nextFloat() * Gdx.graphics.getHeight();
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());
	}
	public void makeBomb()
	{
		float height  = random.nextFloat() * Gdx.graphics.getHeight();
		bombYs.add((int)height);
		bombXs.add(Gdx.graphics.getWidth());
	}

	@Override
	public void render ()// RUNS UNTIL YOU FINISH THE GAME
	{
		batch.begin();
		//         name       starting pos            width                      height
		batch.draw(background, 0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState == 1)
		{			// CHECK IF GAME IS LIVE

			// Bomb after 100 unit time
			mario.play();
			if(bombCount < 250)
			{
				bombCount++;
			}
			else
			{
				bombCount = 0;
				makeBomb();
			}
			bombRectangles.clear();
			for (int i = 0; i < bombXs.size(); i++)
			{
				// for drawing
				batch.draw(bomb, bombXs.get(i), bombYs.get(i));
				bombXs.set(i, bombXs.get(i) - 8);
				// IF TOUCHED
				bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(), bomb.getHeight()));
			}
			// Coin after 100 unit time
			if(coinCount < 100)
			{
				coinCount++;
			}
			else
			{
				coinCount = 0;
				makeCoin();
			}
			coinRectangles.clear();
			for (int i = 0; i < coinXs.size(); i++)
			{
				// for drawing
				batch.draw(coin, coinXs.get(i), coinYs.get(i));
				coinXs.set(i, coinXs.get(i) - 13);
				// if touched
				coinRectangles.add(new Rectangle(coinXs.get(i), coinYs.get(i), coin.getWidth(), coin.getHeight()));
			}

			// TO JUMP ON SINGLE TOUCH
			if(Gdx.input.justTouched() == true)
			{
				velocity = -10;
			}

			//  TO BRING A LITTLE INTERVAL BETWEEN IMAGES (SLOW THE )
			if(pause < 4)
			{
				pause++;
			}
			else
			{
				pause = 0;
				// LOOP THROUGH DIFFERENT IMAGES IF THE MAN
				if (manState < 3)
				{
					manState++;
				}
				else
				{
					manState = 0;
				}
			}

			// FALLING DOWN
			velocity = velocity + gravity;
			manY -= velocity;
			// TO PREVENT HIM FROM GETTING OF THE SCREEN
			if(manY <= 0)
			{
				manY = 0;
			}

		}
		else if (gameState == 0)
		{// WAITING TO START

			if(Gdx.input.justTouched() )
			{
				gameState=1;
			}
		}
		else if (gameState == 2)
		{ // GAME OVER
			mario.stop();
			if(Gdx.input.justTouched() )
			{
				gameState=1;
				manY = Gdx.graphics.getHeight()/2;  // CENTER OF SCREEN
				score =0;
				velocity=0;
				coinXs.clear();
				coinYs.clear();
				coinRectangles.clear();
				coinCount = 0;
				bombRectangles.clear();
				bombXs.clear();
				bombXs.clear();
				bombCount =0;
			}

		}


		if(gameState == 2)
		{
			batch.draw(dizzy,Gdx.graphics.getWidth()/2 -100 - man[manState].getWidth(),manY);
		}
		else
		{
			batch.draw(man[manState],Gdx.graphics.getWidth()/2-100 - man[manState].getWidth(),manY);
		}
		// drawing man at the center of the screen
		// 	FOR COIN CILISION
		manRectangle = new Rectangle(Gdx.graphics.getWidth()/2 -100- man[manState].getWidth() , manY ,man[manState].getWidth(),man[manState].getHeight());
		for (int i = 0; i < coinRectangles.size(); i++)
		{
			if(Intersector.overlaps(manRectangle,coinRectangles.get(i)))
			{
				coinSound.play();
				Gdx.app.log("coin!","Collision!");

				score++;
				if (highestScore<score)
				{
					highestScore = score;
				}
				// GET RID OF COIN
				coinRectangles.remove(i);
				coinXs.remove(i);
				coinYs.remove(i);
				break;
			}
		}

		// 	FOR BOMB CILISION
		for (int i = 0; i < bombRectangles.size(); i++)
		{
			if(Intersector.overlaps(manRectangle,bombRectangles.get(i)))
			{
				bombSound.play();
				Gdx.app.log("Bomb!","Collision!");
				// game over
				gameState =2;

			}
		}
		// SHOWING SCORE
		font.draw(batch, String.valueOf(score),Gdx.graphics.getWidth() - 1000,Gdx.graphics.getHeight()-100);
		highFont.draw(batch, String.valueOf(highestScore),Gdx.graphics.getWidth() - 150,Gdx.graphics.getHeight()-100);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
