package com.jerhis.statsquiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen, InputProcessor {
	
	final MyGdxGame game;
	OrthographicCamera camera;

	TextureAtlas textures;// textures2, textures3;
	//AtlasRegion
    Texture bg, next, fin, newland1, newland2, newland3, line, slid, checked, unchecked;
	//State state;

    boolean leavingToMainMenu, waitingToStart, results, finished;
    float finTime;
    //static boolean soundOn;
    //public static Sound soundDrop;
    //Music musicGame;

	public GameScreen(final MyGdxGame gam) {
		game = gam;
		Gdx.input.setInputProcessor(this);
 
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		//textures = new TextureAtlas("gameimages.txt");

        /*soundOn = game.sound;
       // soundDrop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        //musicGame = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        //musicGame.setVolume(0.2f);
       // musicGame.setLooping(true);
        //if (game.sound)
         //   musicGame.play();*/
		
		game.g.clear();
        leavingToMainMenu = false;
        waitingToStart = true;
        finished = false;
        results = false;
        finTime = 0;

        next = new Texture(Gdx.files.internal("next.png"));
        fin = new Texture(Gdx.files.internal("finished.png"));

        switch ((int)(4*Math.random())) {
            case 1: bg = new Texture(Gdx.files.internal("cyan.png")); break;
            case 2: bg = new Texture(Gdx.files.internal("green.png")); break;
            case 3: bg = new Texture(Gdx.files.internal("salmon.png")); break;
            case 0: bg = new Texture(Gdx.files.internal("yellow.png"));
        }
        newland1 = new Texture(Gdx.files.internal("newlandhappy2.png"));
        newland2 = new Texture(Gdx.files.internal("newlandhappy1.png"));
        newland3 = new Texture(Gdx.files.internal("newlandsad1.png"));
        line = new Texture(Gdx.files.internal("line.png"));
        slid = new Texture(Gdx.files.internal("slider.png"));
        checked = new Texture(Gdx.files.internal("checked.png"));
        unchecked = new Texture(Gdx.files.internal("unchecked.png"));

        //splash = new Texture(Gdx.files.internal("splash.png"));
		//highScore = game.prefs.getInteger("best", 0);
		//game.prefs.putInteger("best", 100);
		//game.prefs.flush(); <--- IMPORTANT!
	}
 
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0.5f, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
 
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
 
		stateRender(delta);
		stateDraw(delta, false);

        if (leavingToMainMenu) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
	}
 
	public void stateRender(float delta) {
        if (finished) {
            finTime += delta;
            if (finTime > 5.12) finTime = 5.12f;
        }
        else if (waitingToStart) {

        }
        else if (results) {

        }
        else { //running
            int k = game.g.update(delta);
            if (k == -1) {
                results = true;
                game.g.score += game.g.currentQuestion.getScore(game.g.response);
            }
        }
	}
	
	public void stateDraw(float delta, boolean tutorial) {
		game.batch.begin();

        if (finished) {
            game.batch.draw(fin, 0, 0);
            if (game.g.score >= 900)
                game.batch.draw(newland1, 400-256, 100*finTime - 512);
            if (game.g.score >600) game.batch.draw(newland2, 400-256, 100*finTime - 512);
            else game.batch.draw(newland3, 400-256, 100*finTime - 512);
        }
        else if (waitingToStart) {
            game.batch.draw(next,0,0);
        }
        else if (results) {
           // d("RESULTS " + game.g.round,259, 490);
            game.batch.draw(bg,0,0);
            game.g.drawResults(game.font, game.batch);
        }
        else { //running
            //d("RUNNING " + game.g.round, 50, 300);
            game.batch.draw(bg,0,0);
            d("" + ((int)(game.g.time*10))/10.0, game.g.time > 10 ? 700 : 727, 490);
            game.g.drawRunning(game.font, game.batch, line, slid, checked, unchecked);
        }

		d("Score: " + (int)game.g.score, 10, 490);
		//d("Best: " + game.highScore, 10, 460);
		if (game.debug) {
            int fps = (int)(1/game.g.lastDelta) > 57 ? 60 : (int)(1/game.g.lastDelta);
			//d("FPS: " + fps, 10, 430);
		}

		game.batch.end();
	}

    //private void d(Texture t, int x, int y) { game.batch.draw(t, x, y); }
    private void d(AtlasRegion at, float x, float y) { game.batch.draw(at, x, y); }
    private void d(String text, float x, float y) { game.font.draw(game.batch, text, x, y); }
	
	
	@Override
	public void resize(int width, int height) {
	}
 
	@Override
	public void show() {
	}
 
	@Override
	public void hide() {
	}
 
	@Override
	public void pause() {
		//if (state == State.Running)
		//	state = State.Paused;
        //if (game.sound) {
         //   musicGame.pause();
        //}
        //game.platformHandeler.wakeLock(0);
	}
 
	@Override
	public void resume() {
       // if (game.sound) {
            //musicGame.play();
        //}
	}
 
	@Override
	public void dispose() {
		//textures.dispose();
		//textures2.dispose();
        //textures3.dispose();
        //bg1.dispose();
        //musicGame.stop();
        //musicGame.dispose();
        //soundDrop.stop();
        //soundDrop.dispose();
        //game.platformHandeler.wakeLock(0);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 pos = new Vector3(screenX, screenY, 0);
		camera.unproject(pos);
		int x = (int) pos.x, y = (int) pos.y;

        if (finished) {
            leavingToMainMenu = true;
        }
        else if (waitingToStart) {
            waitingToStart = false;
        }
        else if (results) {
            game.g.moveOn();
            if (game.g.finished) {
                finished = true;
                game.highScore(game.g.score);
                game.increaseGamesPlayed();
                game.g.finish();
            }
            else {
                results = false;
                waitingToStart = true;
            }
        }
        else { //running
            game.g.touchDown(x,y,pointer);
        }
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector3 pos = new Vector3(screenX, screenY, 0);
		camera.unproject(pos);
		int x = (int) pos.x, y = (int) pos.y;

        if (finished) {

        }
        else if (waitingToStart) {

        }
        else if (results) {

        }
        else { //running
            game.g.touchUp(x,y,pointer);
        }
				
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 pos = new Vector3(screenX, screenY, 0);
		camera.unproject(pos);
		int x = (int) pos.x, y = (int) pos.y;

        if (finished) {

        }
        else if (waitingToStart) {

        }
        else if (results) {

        }
        else { //running
            game.g.touchDragged(x,y,pointer);
        }
		
		return false;
	}

   /* private void sound() {
        game.toggleSound();
        soundOn = game.sound;
        if (game.sound) {
            musicGame.play();
        }
        else {
            musicGame.stop();
        }
    }

    public static void playSound(Sound sound) {
        if (soundOn) {
            sound.play();
        }
    }*/

	@Override
	public boolean keyDown(int keycode) {return false;}
	@Override
	public boolean keyUp(int keycode) {return false;}
	@Override
	public boolean keyTyped(char character) {return false;}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {return false;}
	@Override
	public boolean scrolled(int amount) {return false;}
	
	public enum State {
		Running, Paused, Finished, Ready
	}
	
}
