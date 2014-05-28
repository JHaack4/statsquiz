package com.jerhis.statsquiz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {

    //private PlatformHandeler platformHandeler;
    SpriteBatch batch;
	BitmapFont font;
	Preferences prefs;
	StatsGame g;
	public boolean debug = true;
    public int highScore = 0;
    public int gamesPlayed = 0;
    boolean sound = true;
    public float slider = 1;
	String fileNameHighScore = "bestscore";
	String fileNameTiltControls = "tiltcontrols";
    String fileNameGamesPlayed = "gamesplayed";
    String fileNameSound = "sound";
	float accelX = 0, accelY = 0, accelZ = 0;

    public MyGdxGame() {
        //platformHandeler = new PlatformHandeler(platformInterface);
    }

	public void create() {
		
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont(Gdx.files.internal("myfont.fnt"));
		font.setColor(new Color(0,0,0,1));
		//font.setScale(0.5f);
        //Constantes.font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        /*float densityIndependentSize = origFontSize * Gdx.graphics.getDensity();
        int fontSize = Math.round(densityIndependentSize );
        BitmapFont font = generator.generateFont(fontSize );*/
		
		this.prefs = Gdx.app.getPreferences(".erhsstats");
        highScore = decrypt(prefs.getString(fileNameHighScore, "0"));
        //highScore = prefs.getInteger("highscore0", 0);
        gamesPlayed = prefs.getInteger(fileNameGamesPlayed, 0);
        sound = prefs.getBoolean(fileNameSound, true);
		
		g = new StatsGame(this);
		this.setScreen(new SplashScreen(this));
	}

    public void highScore(int score) {
        //sets high score to current highscore
        if (score > highScore) {
            prefs.putString(fileNameHighScore, encrypt(score));
            prefs.flush();
            highScore = score;
        }
    }

    public void increaseGamesPlayed() {
        gamesPlayed++;
        prefs.putInteger(fileNameGamesPlayed, gamesPlayed);
        prefs.flush();
    }

    public void toggleSound() {
        sound = !sound;
        prefs.putBoolean(fileNameSound, sound);
        prefs.flush();
    }

    public String encrypt(int score) {
        String i = Integer.toBinaryString(score);
        String e = "";
        int amod2 = 0, amod10 = 0, amod23 = 0;
        for (int k = 0; k < i.length(); k++) {
            int c = i.charAt(k) - '0' + 'a' + 2*((int)(Math.random()*10));
            amod2 += c;
            amod10 += c;
            amod23 += c;
            e = e + (char)c;
        }
        e = e + (char)(amod2%2 + 'j') + (char)(amod10%10 + 'd') + (char)(amod23%23 + 'b');
        return e;
    }

    public int decrypt(String e) {
        if (e.length() < 4) return 0;
        int amod2 = 0, amod10 = 0, amod23 = 0;
        String i = "";
        for (int k = 0; k < e.length()-3; k++) {
            int c = (e.charAt(k) - 'a')%2 + '0';
            amod2 += e.charAt(k);
            amod10 += e.charAt(k);
            amod23 += e.charAt(k);
            i = i + (char)c;
        }
        boolean b = (char)(amod2%2 + 'j') == e.charAt(e.length()-3) && (char)(amod10%10 + 'd') == e.charAt(e.length()-2) && (char)(amod23%23 + 'b') == e.charAt(e.length()-1);
        if (b) return Integer.parseInt(i, 2);
        else return 0;
    }
 
	public void render() {
		super.render(); // important!
	}
 
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
 
}