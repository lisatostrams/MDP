/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.ai.vroon.mdp;

import static java.lang.Math.abs;
import java.util.Vector;

/**
 *
 * @author Lisa Tostrams s4386167
 */
public class ValueIterationAlgorithm {

    private double[][] V;
    private double[][][] Q;
    private int width;
    private int height;
    private int actions = Action.values().length;
    private double gamma = 1;
    private MarkovDecisionProblem mdp;
    private double delta = 0.1;
    private boolean convergence = false;
    private double totalr = 0;
    Log log;
    int count = 0;

    public ValueIterationAlgorithm(MarkovDecisionProblem mpd) {
        width = mpd.getWidth();
        height = mpd.getHeight();

        V = new double[width][height];
        Q = new double[width][height][actions];

        this.mdp = mpd;
        initializeVQ();
        String mdpname = "Value_Iteration_" + width + "x" + height + "_gamma_" + gamma;
        log = new Log(mdpname);

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

    public void update() {
        convergence = true;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                mdp.setState(x, y);
                for (int a = 0; a < actions; a++) {
                    Q[x][y][a] = sum_T_R_V(Action.values()[a]);
                    count++;
                }

                double opt = maxQ_s_a(x, y);
                if (abs(V[x][y] - opt) > delta) {
                    convergence = false;
                }
                V[x][y] = opt;

            }
        }

    }

    public boolean converged() {
        return convergence;
    }

    private double sum_T_R_V(Action action) {
        double sum = 0;
        for (int a = 0; a < actions; a++) {
            double prob = 0;

            if (Action.values()[a] == action) {
                prob = mdp.getP_action();
            }
            if (Action.values()[a] == Action.nextAction(action) || Action.values()[a] == Action.previousAction(action)) {
                prob = mdp.getP_sidestep();
            }
            if (Action.values()[a] == Action.backAction(action)) {
                prob = mdp.getP_backstep();
            }

            sum += (prob * (mdp.getR_a_s(Action.values()[a]) + V[mdp.getX_a(Action.values()[a])][mdp.getY_a(Action.values()[a])]));
        }
        return sum;

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
        if (mv == 0) {
            return null;
        }
        return max;
    }

    @Override
    public String toString() {
        String str = "V(S) = \n";
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                str += ("| " + String.format("%+1.3f", V[j][i]) + " |");
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

    public int walkPolicyPath() {
        int pathlength = 0;
        totalr = 0;
        mdp.restart();
        mdp.setDeterministic();
        //mdp.setShowProgress(true);
        while (!mdp.isTerminated()) {
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
            while (!mdp.isTerminated()) {
                Action action = max_argQ_s_a(mdp.getStateXPosition(), mdp.getStateYPostion());
                r += mdp.performAction(action);
                pathlength++;
            }
            av_path += pathlength; 
            av_reward += r; 
        }
        return "100 simulated runs of probabilistic agent results in: \n Average pathlength: " + av_path/100+ " \n Average reward: " + av_reward/100; 

    }

    public void finish() {
        log.write(this.toString());
        log.write(this.policy());
        log.write("Minimum length of path to goal: " + walkPolicyPath());
        log.write("Max total reward of path: " + totalr);
        log.write(this.simulate100ProbabilisticRuns());
        log.close();

    }

}
