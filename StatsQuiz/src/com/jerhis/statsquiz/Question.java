package com.jerhis.statsquiz;

import java.util.ArrayList;

public abstract class Question {

    float answer;
    float startTime;
    ArrayList<String> question, explain, delimiter;

    public Question() {

    }

    public Question(float answer, float startTime) {
        this.answer = answer;
        this.startTime = startTime;
    }

    public static final ArrayList<Question> generateQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();

        int[] qMC = generateNums(38, 5);
        int[] qFR = generateNums(9, 5);
        for (int k = 0; k < 5; k++) {
            questions.add(new QuestionMC(qMC[k]));
            questions.add(new QuestionFR(qFR[k]));
        }

        return questions;
    }

    private static int[] generateNums(int max, int l) {
        boolean[] d = new boolean[max];
        for (int q = 0; q < max; q++)
            d[q] = false;
        int[] ret = new int[l];
        int k = 0;

        while (k < l) {
            int j = (int)(Math.random()*max);
            if (!d[j]) {
                ret[k++] = j;
                d[j] = true;
            }
        }
        return ret;
    }

    public abstract int getScore(float response);



}
