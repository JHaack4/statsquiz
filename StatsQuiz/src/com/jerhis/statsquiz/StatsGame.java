package com.jerhis.statsquiz;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class StatsGame {
	
	final MyGdxGame game;
	int score;
	//float bgOffSet = 0, bg1 = 0, bg2 = 0;
	float time, lastDelta = 1;
    int round;
    boolean finished = false;
    float response;
    Question currentQuestion;
    final int mcYValues[] = {50,190,140,90,40}, fontCorrection = 43, fontHeight = 32;

    ArrayList<Question> questions;
	
	public StatsGame(MyGdxGame gam) {
		game = gam;
		clear();
	}
	
	public int update(float delta) {
        //delta = Math.min(delta, 0.025f);
        lastDelta = delta;
		time -= delta;

        if (time <= 0) {
            time = 0;
            return -1;
        }

        return 0;
	}

    public void drawResults(BitmapFont f, SpriteBatch b) {

        if (currentQuestion instanceof QuestionMC) {
            int k = 0;
            for (; k < currentQuestion.question.size(); k++) {
                //f.draw(b, "Question: ", 40, 430);
                f.draw(b, currentQuestion.question.get(k), 60, 430-(k)*(fontHeight + 5));
            }
            f.draw(b, "Answer: ", 40, 430-(k)*(fontHeight + 5));
            f.draw(b, ((QuestionMC) currentQuestion).correct, 60, 430-(k+1)*(fontHeight + 5));
        }
        else if (currentQuestion instanceof QuestionFR) {
            int delimIndex = 0;
            int k = 0;
            for (; k < currentQuestion.question.size(); k++) {
                String init = currentQuestion.question.get(k), fin = "";
                for (int k2 = 0; k2 < init.length(); k2++) {
                    char ch = init.charAt(k2);
                    if (ch == '@') {
                        fin = fin + currentQuestion.delimiter.get(delimIndex++);
                    }
                    else {
                        fin += ch;
                    }
                }
                f.draw(b, fin, 60, 430-k*(fontHeight + 5));
            }
            f.draw(b, "Answer: ", 40, 430-(k)*(fontHeight + 5));
            f.draw(b, currentQuestion.answer + "", 60, 430-(k+1)*(fontHeight + 5));
        }

        boolean q = currentQuestion instanceof QuestionMC;

        if (response == -1)
            f.draw(b,"The answer was " + convert(currentQuestion.answer) + ".",q ? 170 : 154,70);
        else if (response == currentQuestion.answer)
            f.draw(b,"You put the correct answer of " + convert(currentQuestion.answer) + ".",q ? 80 : 50,70);
        else
            f.draw(b,"You put " + convert(response) + ", but the answer was " + convert(currentQuestion.answer) + ".",q ? 60 : 10,70);

        f.draw(b,"You scored " + currentQuestion.getScore(response) + " points.",170, 110);
    }

    private String convert(float f) {
        if (currentQuestion instanceof QuestionMC) {
            return (char)('A' - 1 + ((int)(10*f)) ) + "";
        }
        else return ""+f;
    }

    public void drawRunning(BitmapFont f, SpriteBatch b, Texture line, Texture slid, Texture checked, Texture unchecked) {
        //f.draw(b,"res " + response + " ans " + currentQuestion.answer + " scr " + currentQuestion.getScore(response),100,370)

        if (currentQuestion instanceof QuestionMC) {
            for (int k = 0; k < currentQuestion.question.size(); k++) {
                f.draw(b, currentQuestion.question.get(k), 60, 430-k*(fontHeight + 5));
            }

            int x = 130;

            for (int k = 1; k <= 4; k++) {
                if (response * 10 == k)
                    b.draw(checked,x - 45,mcYValues[k] -19);
                else b.draw(unchecked,x - 45,mcYValues[k] -19);
            }

            f.draw(b,"A: " + ((QuestionMC)(currentQuestion)).a,x,mcYValues[1] + fontCorrection - fontHeight/2);
            f.draw(b,"B: " + ((QuestionMC)(currentQuestion)).b,x,mcYValues[2] + fontCorrection - fontHeight/2);
            f.draw(b,"C: " + ((QuestionMC)(currentQuestion)).c,x,mcYValues[3] + fontCorrection - fontHeight/2);
            f.draw(b,"D: " + ((QuestionMC)(currentQuestion)).d,x,mcYValues[4] + fontCorrection - fontHeight/2);
            //f.draw(b,"E: " + ((QuestionMC)(currentQuestion)).e,x,mcYValues[5] + fontCorrection - fontHeight/2);

        }
        else if (currentQuestion instanceof QuestionFR) {
            int delimIndex = 0;
            for (int k = 0; k < currentQuestion.question.size(); k++) {
                String init = currentQuestion.question.get(k), fin = "";
                for (int k2 = 0; k2 < init.length(); k2++) {
                    char ch = init.charAt(k2);
                    if (ch == '@') {
                        fin = fin + currentQuestion.delimiter.get(delimIndex++);
                    }
                    else {
                        fin += ch;
                    }
                }
                f.draw(b, fin, 60, 430-k*(fontHeight + 5));
            }

            if (response != -1) f.draw(b,"" + response,360,145);
            b.draw(line, 75, 50);
            b.draw(slid,response* 600 + 100 - 30 ,22);
            f.draw(b,"-",45,100);
            f.draw(b,"+",755,100);
        }
    }

    public void moveOn() {
        round++;
        response = -1;
        if (round > questions.size()) {
            finished = true;
            return;
        }
        currentQuestion = questions.get(round - 1);
        time = currentQuestion.startTime;
    }

    public void finish() {

    }
	
	public void touchDown(int x, int y, int pointer) {
        if (currentQuestion instanceof QuestionMC) {
            for (int k = 1; k <= 4; k++) {
                if (y < mcYValues[k] + mcYValues[0]/2 && y > mcYValues[k] - mcYValues[0]/2)
                    response = k/10.0f;
            }
        }
        else if (currentQuestion instanceof QuestionFR) {
            if (y < 200 && x > 95 && x < 705) {
                response = Math.round((x - 100.0f)/6.0f) / 100.0f;
                response = Math.round(response * 100) / 100.0f;
                response = response > 0 ? response : 0;
                response = response < 1 ? response : 1;
            }
            else if (y < 200 && x < 95) {
                response -= 0.01f;
                response = Math.round(response * 100) / 100.0f;
                response = response > 0 ? response : 0;
                response = response < 1 ? response : 1;
            }
            else if (y < 200 && x > 705) {
                if (response == -1) response = 1;
                response += 0.01f;
                response = Math.round(response * 100) / 100.0f;
                response = response > 0 ? response : 0;
                response = response < 1 ? response : 1;
            }
        }
	}
	
	public void touchDragged(int x, int y, int pointer) {
        if (currentQuestion instanceof QuestionFR) {
            if (y < 200 && x > 95 && x < 705) {
                response = Math.round((x - 100.0f)/6.0f) / 100.0f;
                response = Math.round(response * 100) / 100.0f;
                response = response > 0 ? response : 0;
                response = response < 1 ? response : 1;
            }
        }
	}

	public void touchUp(int x, int y, int pointer) {

	}

	
	public void clear() {
		score = 0;
        round = 0;
        finished = false;
        questions = Question.generateQuestions();
		moveOn();
	}

}
