/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.ai.vroon.mdp;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Lisa Tostrams s4386167
 */
public class QLearningAlgorithm {

    private double[][][] Q;
    private int width;
    private int height;
    private int actions = Action.values().length;
    private double gamma = 0.9;
    private MarkovDecisionProblem mdp;
    private double epsilon = 0.1;
    private boolean greedy_epsilon = false;
    private double totalr;
    private Log log;

    Random rand = new Random();

    public QLearningAlgorithm(MarkovDecisionProblem mpd) {
        width = mpd.getWidth();
        height = mpd.getHeight();

        Q = new double[width][height][actions];

        this.mdp = mpd;
        initializeQ();
        String mdpname = "Q_learning_" + width + "x" + height + "_gamma_" + gamma + "_epsilon_" + epsilon;
        log = new Log(mdpname);

    }

    private void initializeQ() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int a = 0; a < actions; a++) {
                    Q[i][j][a] = -rand.nextDouble() * 0.01;
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
        int a = Action.valueOf(e.a.name()).ordinal();
        double Q_star_s_a = e.r + gamma * maxQ_s_a(e.x_new, e.y_new);
        Q[e.x][e.y][a] += (1 / mdp.getActionsCounter() + 1) * (Q_star_s_a - Q[e.x][e.y][a]);

    }

    private double maxQ_s_a(int x, int y) {
        double mv = -1000000;
        for (int a = 0; a < actions; a++) {
            double v = Q[x][y][a];
            if (v > mv) {
                mv = v;
            }
        }
        return mv;
    }

    private Action max_argQ_s_a(int x, int y) {
        double mv = -1000000;
        Action max = null;
        for (int a = 0; a < actions; a++) {
            double v = Q[x][y][a];
            if (v > mv) {
                mv = v;
                max = Action.values()[a];
            }
        }

        return max;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                str += ("| " + String.format("%+1.3f", maxQ_s_a(j, i)) + " |");
            }
            str += "\n";
        }
        return str;

    }

    public String policy() {
        String str = "Policy p(S) = \n";
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                if (max_argQ_s_a(j, i) == null) {
                    str += ("|       |");
                } //String.format("%1$"+5+"s", max_argQ_s_a(j,i).toString());
                else {
                    str += ("| " + String.format("%1$" + 6 + "s", max_argQ_s_a(j, i).toString()) + " |");
                }
            }
            str += "\n";
        }
        return str;
    }

    public void setGreedyEpsilon(boolean set) {
        greedy_epsilon = set;
    }

    public Action policy_currentState() {
        Action action = max_argQ_s_a(mdp.getStateXPosition(), mdp.getStateYPostion());
        if (action == null) {

            return Action.values()[rand.nextInt(actions)];
        }
        if (greedy_epsilon) {
            double p = rand.nextDouble();
            if (p < epsilon) {
                return Action.values()[rand.nextInt(actions)];
            }
        }
        return max_argQ_s_a(mdp.getStateXPosition(), mdp.getStateYPostion());
    }

    public int walkPolicyPath() {
        int pathlength = 0;
        totalr = 0;
        mdp.restart();
        mdp.setDeterministic();
        mdp.setShowProgress(false);
        while (!mdp.isTerminated() && pathlength < 100) {
            Action action = max_argQ_s_a(mdp.getStateXPosition(), mdp.getStateYPostion());
            totalr += mdp.performAction(action);
            pathlength++;
        }
        return pathlength;

    }

    public String simulate100ProbabilisticRuns() {
        mdp.setStochastic();
        double av_path = 0;
        double av_reward = 0;
        for (int i = 0; i < 100; i++) {
            int pathlength = 0;
            double r = 0;
            mdp.restart();

            mdp.setShowProgress(false);
            while (!mdp.isTerminated() && pathlength <100) {
                Action action = max_argQ_s_a(mdp.getStateXPosition(), mdp.getStateYPostion());
                r += mdp.performAction(action);
                pathlength++;
            }
            av_path += pathlength;
            av_reward += r;
        }
        return "100 simulated runs of probabilistic agent results in: \n Average pathlength: " + av_path / 100 + " \n Average reward: " + av_reward / 100;

    }

    public void finish() {
        log.write(this.toString());
        log.write(this.policy());
        log.write("Minimum length of path to goal: " + walkPolicyPath());
        log.write("Maximum total reward of path: " + totalr);
        log.write(this.simulate100ProbabilisticRuns());
        log.close();

    }
}
