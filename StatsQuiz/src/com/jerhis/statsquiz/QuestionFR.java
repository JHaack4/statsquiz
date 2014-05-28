package com.jerhis.statsquiz;

import java.util.ArrayList;
import java.util.Scanner;

public class QuestionFR extends Question {

    public QuestionFR(int n) {
        super();
        readQuestion(n);
    }

    private void readQuestion(int n) {
        String s = getFRQuestion(n);

        Scanner sc1 = new Scanner(s);
        sc1.useDelimiter("[#]+");

        startTime = Integer.parseInt(sc1.next());
        String questionString = sc1.next();
        String explainString = sc1.next();
        String values = sc1.next();


        Scanner sc2 = new Scanner(questionString);
        sc2.useDelimiter("[$]+");
        question = new ArrayList<String>();
        while (sc2.hasNext()) {
            question.add(sc2.next());
        }

        Scanner sc3 = new Scanner(explainString);
        sc3.useDelimiter("[$]+");
        explain = new ArrayList<String>();
        while (sc3.hasNext()) {
            explain.add(sc3.next());
        }

        Scanner sc4 = new Scanner(values);
        sc4.useDelimiter("[$]+");
        ArrayList<String> opts = new ArrayList<String>();
        while (sc4.hasNext()) {
            opts.add(sc4.next());
        }
        String chosen = opts.get((int)(Math.random() * opts.size()));

        Scanner sc5 = new Scanner(chosen);
        sc5.useDelimiter("[%]+");
        delimiter = new ArrayList<String>();
        answer = Float.parseFloat(sc5.next());
        while (sc5.hasNext()) {
            delimiter.add(sc5.next());
        }
    }

    private String getFRQuestion(int n1) {
        switch(n1) {
            default:
            case -1: return "1#Question " + n1 + "q: @$n3$n4$n5$n6#ex#0.33%0.23$0.55%0.45";
            case 0: return "25#Calculate $ P(z @ @) #ex#0.11%<%-1.25$0.02%<%-2.1$0.44%<%-0.15$0.58%<%0.2$0.92%<%1.4$0.96%>%-1.7$0.69%>%-0.5$0.27%>%0.6$0.0%>%4.7";
            case 1: return "25#Caluclate $ P(x @ @) $ when mean = @, s = @#ex#0.07%<%48%78%20$0.86%<%-10%-35%23$0.14%>%4%-2%5.5$0.73%>%94%100%10";
            case 2: return "25#Caluclate $ P(@ < z < @) #ex#0.17%-1.7%-0.8$0.81%-2.4%0.9$0.5%0.0%7.5$0.2%0.3%0.9";
            case 3: return "25#Calculate $ P(t @ @) $ when df = @#ex#0.08%<%-1.7%2$0.58%<%0.2%6$0.92%>%-1.5%14$0.35%>%0.4%7";
            case 4: return "25#Calculate $ P(chi^2 > @) $ when df = @#ex#1.0%0.0%187$0.54%7%8$0.21%18%14$0.98%18%32";
            case 5: return "20#Calculate $ P(X = @) $ for a binomial distribution $ where n = @, p = @#ex#0.16%8%20%0.45$0.17%5%15%0.25$0.28%5%7%0.8$0.35%2%4%0.6";
            case 6: return "20#Calculate $ P(Y = @) $ for a geometric distribution $ where p = @#ex#0.08%5%0.25$0.21%2%0.7$0.05%8%0.15$0.25%2%0.55";
            case 7: return "25#Calculate the sample $standard deviation of $ @, @, @, @, @#ex#0.19%0.5%0.2%0.6%0.4%0.7$0.36%0.1%0.9%0.1%0.7%0.3$0.65%1.0%2.0%1.5%2.5%1.0";
            case 8: return "25#Calculate the slope of$the regression line through$the following points:$ @ @ @#ex#0.64%(2,2)%(3,3)%(5,4)$0.85%(5,1)%(4,1)%(9,5)$0.21%(2,4)%(5,5)%(7,5)";
            //tests, sampling distribution
        }
    }

    public int getScore(float response) {
        int k = 100 - Math.round(Math.abs(response - answer)*1000);
        return k > 0 && response != -1 ? k : 0;
    }
}
