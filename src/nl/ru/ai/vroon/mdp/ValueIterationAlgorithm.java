/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.ai.vroon.mdp;

import java.util.Vector;

/**
 *
 * @author Lisa Tostrams s4386167
 */
public class ValueIterationAlgorithm {

    double[][] V;
    double[][][] Q;
    int width;
    int height;
    int actions = Action.values().length;
    double gamma = 0.8;
    MarkovDecisionProblem mdp;

    public ValueIterationAlgorithm(MarkovDecisionProblem mpd) {
        width = mpd.getWidth();
        height = mpd.getHeight();

        V = new double[width][height];
        Q = new double[width][height][actions];

        this.mdp = mpd;
        initializeVQ();

    }

    private void initializeVQ() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                V[i][j] = 0;
                for (int a = 0; a < actions; a++) {
                    Q[i][j][a] = 0;
                }
            }
        }
    }

    public void experience(Action a) {
        int x = mdp.getStateXPosition();
        int y = mdp.getStateYPostion();
        double r = mdp.performAction(a);
        int x_ = mdp.getStateXPosition();
        int y_ = mdp.getStateYPostion();

        update(new Experience(x, y, a, r, x_, y_));
    }

    public void update(Experience e) {
        double opt = e.r + gamma * maxQ_s_a(e.x_new, e.y_new); 
        Q[e.x][e.y][Action.valueOf(e.a.name()).ordinal()] += (1 / mdp.getActionsCounter()) * (opt - Q[e.x][e.y][Action.valueOf(e.a.name()).ordinal()]);
        V[e.x][e.y] = opt;
    }

    private double maxQ_s_a(int x, int y) {
        if(mdp.isTerminated()) return 0; 
        MarkovDecisionProblem temp = mdp;
        mdp.setShowProgress(false);
        int k = mdp.getActionsCounter();
        Action max = Action.values()[0];
        double mv = -1000000;
        for (int a = 0; a < actions; a++) {
            double r = temp.performAction(Action.values()[a]);

            double v = Q[x][y][a] + (1 / k + 1) * (r + (gamma * V[x][y]) - Q[x][y][a]);

            if (v > mv) {
                mv = v;
                max = Action.values()[a];
            }
            if (temp.getAction() != null) {
                temp.doAction(Action.backAction(temp.getAction()));
            }
            temp.setTerminated();

        }
        mdp.setShowProgress(true);
        mdp.setActionSteps(k);
        return mv;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                str += ("| " + String.format("%1.3f", V[j][i]) + " |");
            }
            str += "\n";
        }
        return str;

    }
}