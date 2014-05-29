package com.jerhis.statsquiz;

import java.util.ArrayList;
import java.util.Scanner;

public class QuestionMC extends Question {

    String a = "a", b = "b", c = "c", d = "d", correct = "correct";

    public QuestionMC(int n) {
        super();
        readQuestion(n);
    }

    public int getScore(float response) {
        if (response == answer)
            return 100;
        else return 0;
    }

    private void readQuestion(int n) {
        String s = getMCQuestion(n);

        Scanner sc1 = new Scanner(s);
        sc1.useDelimiter("[#]+");

        startTime = Integer.parseInt(sc1.next());
        String questionString = sc1.next();
        correct = sc1.next();
        String[] answers = new String[] {"", correct, sc1.next(), sc1.next(), sc1.next()};
        String explainString = sc1.next();

        question = new ArrayList<String>();
        /*Scanner sc2 = new Scanner(questionString);
        sc2.useDelimiter("[$]+");
        question = new ArrayList<String>();
        while (sc2.hasNext()) {
            question.add(sc2.next());
        }*/
        int lastSpace = 0, space = 0;

        for (int k = 0; k < questionString.length(); k++) {
            char c = questionString.charAt(k);
            if (c == ' ') {
                space = k;
            }
            if (k - lastSpace > 31) {
                question.add(questionString.substring(lastSpace, space).trim());
                lastSpace = space;
            }
        }
        question.add(questionString.substring(lastSpace).trim());

        Scanner sc3 = new Scanner(explainString);
        sc3.useDelimiter("[$]+");
        explain = new ArrayList<String>();
        while (sc3.hasNext()) {
            explain.add(sc3.next());
        }

        int[] answerArray = generateCorrectAnswer();
        answer = 0.1f * answerArray[0];
        a = answers[answerArray[1]];
        b = answers[answerArray[2]];
        c = answers[answerArray[3]];
        d = answers[answerArray[4]];
    }

    private String getMCQuestion(int n) {
        switch (n) {
            default:
            case -1: return "8#Question" + n + "#right#w1#w2#w3#ex";
            case 0: return "8#This breaks the range of values of a variable into classes and displays only the count or percent of the observations that fall into each class.#histogram#box plot#stem-and-leaf plot#least squares regression line#explanation new line";
            case 1: return "8#Systematically recorded information, whether numbers or labels, together with its context #data#variable#units#observation#explanation new line";
            case 2: return "8#An arrangement of data in which each row represents a case and each column represents a variable#Data table#2 way table#conditional distribution#matrix#explanation new line";
            case 3: return "8#An individual about whom or which we have data#subject#person#unit#target#explanation new line";
            case 4: return "8#A variable that names categories (whether with words or numerals)#categorical variable#response variable#quantitative variable#independent variable#explanation new line";
            case 5: return "8#A variable in which the numbers act as numerical values; always has units#quantitative variable#categorical variable#response variable#dependant variable#explanation new line";
            case 6: return "8#Lists the categories in a categorical variable and gives the count or percentage of observations for each category #frequency table#2 way table#bar graph#variable#explanation new line";
            case 7: return "8#Gives the possible values of the variable and the relative frequency of each value#distribution#variable#table#constant#explanation new line";
            case 8: return "8#Shows a bar representing the count of each category in a categorical variable#bar graph#box plot#pie chart#histogram#explanation new line";
            case 9: return "8#Shows how a whole divides into categories by showing a wedge of a circle whose area corresponds to the proportion in each category#pie chart#stem plot#box plot#distribution#explanation new line";
            case 10: return "8#The distribution of either variable alone in a contingency table; the counts or percentages are the totals found in the margins (last row or column) of the table#marginal distribution#conditional distribution#bar graph#frequency table#explanation new line";
            case 11: return "8#The distribution of a variable restricting the who to consider only a smaller group of individuals#conditional distribution#marginal distribution#pie chart#variable#explanation new line";
            case 12: return "8#Variables are said to be this if the conditional distribution of one variable is the same for each category of the other#independance#dependance#experimental#mutually exclusive#explanation new line";
            case 13: return "8#When averages are taken across different groups, they can appear to contradict the overall averages#simpson's paradox#mean paradox#newland's rule#euler’s rule#explanation new line";
            case 14: return "8#Gives the possible values of the variable and the frequency or relative frequency of each value#distribution#independance#line graph#histogram#explanation new line";
            case 15: return "8#Shows quantitative data values in a way that sketches the distribution of the data split based on digits#stem-and-leaf plot#box plot#line plot#histogram#explanation new line";
            case 16: return "8#Graphs a dot for each case against a single axis#dot plot#stem-and-leaf plot#line plot#bar graph#explanation new line";
            case 17: return "8#To describe this aspect of a distribution, look for single vs. multiple modes, and symmetry vs. skewness#shape#center#standard deviation#spread#explanation new line";
            case 18: return "8#A numerical summary of how tightly the values are clustered around the center#spread#shape#mean#mode#explanation new line";
            case 19: return "8#A hump or local high point in the shape of the distribution of a variable; the apparent locations of these can change as the scale of a histogram is changed#mode#center#mean#shape#explanation new line";
            case 20: return "8#Having one mode; this is a useful term for describing the shape of a histogram when it's generally mound-shaped#unimodal#bimodal#high point#symmetric#explanation new line";
            case 21: return "8#Distributions with two modes#bimodal#unimodal#symmetric#center#explanation new line";
            case 22: return "8#Distributions with more than two modes#multimodal#bimodal#trimodal#supermodal#explanation new line";
            case 23: return "8#A distribution that's roughly flat#uniform#bimodal#constant#asymmetric#explanation new line";
            case 24: return "8#A distribution is this if the two halves on either side of the center look approximately like mirror images of each other#symmetric#unimodal#skewed#normal#explanation new line";
            case 25: return "8#A distribution is this if it's not symmetric and one tail stretches out farther than the other#skewed#symmetric#unimodal#normal#explanation new line";
            case 26: return "8#Extreme values that don't appear to belong with the rest of the data#outliers#quartiles#extremes#IQR#explanation new line";
            case 27: return "8#Summarized with the mean or the median#center#shape#outliers#spread#explanation new line";
            case 28: return "8#The middle value with half of the data above and half below it#median#mean#standard deviation#mode#explanation new line";
            case 29: return "8#Summarized with the standard deviation, interquartile range, and range#spread#center#outliers#shape#explanation new line";
            case 30: return "8#The difference between the lowest and highest values in a data set#range#IQR#spread#standard deviation#explanation new line";
            case 31: return "8#The lower of this is the value with a quarter of the data below it; the upper of this has a quarter of the data above it#quartile#IQR#median#range#explanation new line";
            case 32: return "8#The difference between the first and third quartiles#IQR#spread#outliers#range#explanation new line";
            case 33: return "8#The nth _______ is the number that falls above n% of the data#percentile#quartile#spread#center#explanation new line";
            case 34: return "8#Consists of the minimum and maximum, the quartiles Q1 and Q3, and the median#5-number summary#dot plot#mean summary#matrix#explanation new line";
            case 35: return "8#The sum of squared deviations from the mean, divided by the count minus one#variance#standard deviation#median#statistic#explanation new line";
            case 36: return "8#The square root of the variance#standard deviation#nothing#median#z-score#explanation new line";
            case 37: return "8#Tells how many standard deviations a value is from the mean; have a mean of zero and a standard deviation of one#z-score#t*#normal#t-test#explanation new line";
        }
    }

    private int[] generateCorrectAnswer() {
        boolean[] used = new boolean[] {false, false, false, false, false};
        int[] a = new int[5];
        int c = (int)(Math.random()*4) + 1;
        used[c] = true;
        a[0] = c;
        a[c] = 1;
        int k = 2;
        while (!used[4] || !used[1] || !used[2] || !used[3]) {
            c = (int)(Math.random()*4) + 1;
            if (!used[c]) {
                used[c] = true;
                a[c] = k++;
            }
        }
        return a;
    }
}
