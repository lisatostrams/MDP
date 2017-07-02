package nl.ru.ai.vroon.mdp;

import java.util.Random;

/**
 * This main is for testing purposes (and to show you how to use the MDP class).
 *
 * @author Jered Vroon
 *
 */
public class Main {

    /**
     * @param args, not used
     */
    public static void main(String[] args) {
        for (int x = 0; x < 1; x++) {
            //  via_stochastic(); 
            //  via_stochastic_with_obstacles(); 
            //  via_stochastic_with_penalties();

            //qla_stochastic();
            //qla_stochastic_with_obstacles(); 
            //qla_stochastic_with_penalties();
        }
    }

    public static void via_stochastic() {
        MarkovDecisionProblem mdp = new MarkovDecisionProblem();
        mdp.setInitialState(0, 0);
        mdp.setShowProgress(false);
        ValueIterationAlgorithm via = new ValueIterationAlgorithm(mdp);
        System.out.println(via.toString());
        while (!via.converged()) {
            via.update();
            System.out.println(via.toString());
            System.out.println(via.policy());
            mdp.restart();
        }
        System.out.println(via.toString());
        via.finish();

        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        mdp2.setPosReward(10);
        ValueIterationAlgorithm via2 = new ValueIterationAlgorithm(mdp2);
        mdp2.setShowProgress(false);
        while (!via2.converged()) {
            via2.update();
            System.out.println(via2.toString());
            System.out.println(via2.policy());
            mdp2.restart();
        }
        via2.finish();

        mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(9, 9, Field.REWARD);
        mdp2.setPosReward(10);

        via2 = new ValueIterationAlgorithm(mdp2);
        mdp2.setShowProgress(false);
        while (!via2.converged()) {
            via2.update();
            System.out.println(via2.toString());
            System.out.println(via2.policy());
            mdp2.restart();
        }
        via2.finish();

    }

    public static void via_stochastic_with_obstacles() {

        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        mdp2.setField(3, 2, Field.OBSTACLE);
        mdp2.setField(3, 3, Field.OBSTACLE);
        mdp2.setField(3, 4, Field.OBSTACLE);
        mdp2.setField(3, 5, Field.OBSTACLE);
        mdp2.setField(3, 6, Field.OBSTACLE);

        mdp2.setField(7, 9, Field.OBSTACLE);
        mdp2.setField(7, 8, Field.OBSTACLE);
        mdp2.setField(7, 7, Field.OBSTACLE);
        mdp2.setField(7, 6, Field.OBSTACLE);
        mdp2.setField(7, 5, Field.OBSTACLE);

        mdp2.setField(4, 3, Field.OBSTACLE);
        mdp2.setField(5, 3, Field.OBSTACLE);
        mdp2.setField(6, 3, Field.OBSTACLE);
        mdp2.setField(7, 3, Field.OBSTACLE);
        mdp2.setField(8, 3, Field.OBSTACLE);
        mdp2.setField(9, 3, Field.OBSTACLE);

        mdp2.setPosReward(10);
        ValueIterationAlgorithm via2 = new ValueIterationAlgorithm(mdp2);
        mdp2.setShowProgress(false);
        while (!via2.converged()) {
            via2.update();
            System.out.println(via2.toString());
            System.out.println(via2.policy());
            mdp2.restart();
        }
        via2.finish();

        mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(9, 9, Field.REWARD);
        mdp2.setPosReward(10);

        mdp2.setField(3, 2, Field.OBSTACLE);
        mdp2.setField(3, 3, Field.OBSTACLE);
        mdp2.setField(3, 4, Field.OBSTACLE);
        mdp2.setField(3, 5, Field.OBSTACLE);
        mdp2.setField(3, 6, Field.OBSTACLE);

        mdp2.setField(7, 9, Field.OBSTACLE);
        mdp2.setField(7, 8, Field.OBSTACLE);
        mdp2.setField(7, 7, Field.OBSTACLE);
        mdp2.setField(7, 6, Field.OBSTACLE);
        mdp2.setField(7, 5, Field.OBSTACLE);

        mdp2.setField(4, 3, Field.OBSTACLE);
        mdp2.setField(5, 3, Field.OBSTACLE);
        mdp2.setField(6, 3, Field.OBSTACLE);
        mdp2.setField(7, 3, Field.OBSTACLE);
        mdp2.setField(8, 3, Field.OBSTACLE);
        mdp2.setField(9, 3, Field.OBSTACLE);
        via2 = new ValueIterationAlgorithm(mdp2);
        mdp2.setShowProgress(false);
        while (!via2.converged()) {
            via2.update();
            System.out.println(via2.toString());
            System.out.println(via2.policy());
            mdp2.restart();
        }
        via2.finish();

    }

    public static void via_stochastic_with_penalties() {

        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        mdp2.setField(3, 3, Field.NEGREWARD);
        mdp2.setPosReward(10);
        ValueIterationAlgorithm via2 = new ValueIterationAlgorithm(mdp2);
        mdp2.setShowProgress(false);
        while (!via2.converged()) {
            via2.update();
            System.out.println(via2.toString());
            System.out.println(via2.policy());
            mdp2.restart();
        }
        via2.finish();

        mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(9, 9, Field.REWARD);
        mdp2.setField(3, 3, Field.NEGREWARD);
        mdp2.setPosReward(10);

        via2 = new ValueIterationAlgorithm(mdp2);
        mdp2.setShowProgress(false);
        while (!via2.converged()) {
            via2.update();
            System.out.println(via2.toString());
            System.out.println(via2.policy());
            mdp2.restart();
        }
        via2.finish();

    }

    public static void qla_stochastic() {
        MarkovDecisionProblem mdp = new MarkovDecisionProblem();
        mdp.setInitialState(0, 0);
        mdp.setShowProgress(false);
        QLearningAlgorithm qla = new QLearningAlgorithm(mdp);
        System.out.println(qla.toString());
        int i = 0;
        qla.setGreedyEpsilon(true);
        mdp.setShowProgress(false);
        while (i++ < 1000) {

            while (!mdp.isTerminated()) {
                qla.experience(qla.policy_currentState());
                //System.out.println(qla.toString());
                //System.out.println(qla.policy());
            }
            mdp.restart();
        }
        qla.finish();

        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        mdp2.setPosReward(10);
        qla = new QLearningAlgorithm(mdp2);
        System.out.println(qla.toString());
        i = 0;
        qla.setGreedyEpsilon(true);
        mdp2.setShowProgress(false);
        while (i++ < 5000) {

            while (!mdp2.isTerminated()) {
                qla.experience(qla.policy_currentState());
                //System.out.println(qla.toString());
                //System.out.println(qla.policy());
            }
            mdp2.restart();
        }
        qla.finish();
        mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(9, 9, Field.REWARD);
        mdp2.setPosReward(10);

        qla = new QLearningAlgorithm(mdp2);
        System.out.println(qla.toString());
        i = 0;
        qla.setGreedyEpsilon(true);
        mdp2.setShowProgress(false);
        while (i++ < 5000) {

            while (!mdp2.isTerminated()) {
                qla.experience(qla.policy_currentState());
                //System.out.println(qla.toString());
                //System.out.println(qla.policy());
            }
            mdp2.restart();
        }
        qla.finish();

    }

    public static void qla_stochastic_with_obstacles() {

        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        mdp2.setField(3, 2, Field.OBSTACLE);
        mdp2.setField(3, 3, Field.OBSTACLE);
        mdp2.setField(3, 4, Field.OBSTACLE);
        mdp2.setField(3, 5, Field.OBSTACLE);
        mdp2.setField(3, 6, Field.OBSTACLE);

        mdp2.setField(7, 9, Field.OBSTACLE);
        mdp2.setField(7, 8, Field.OBSTACLE);
        mdp2.setField(7, 7, Field.OBSTACLE);
        mdp2.setField(7, 6, Field.OBSTACLE);
        mdp2.setField(7, 5, Field.OBSTACLE);

        mdp2.setField(4, 3, Field.OBSTACLE);
        mdp2.setField(5, 3, Field.OBSTACLE);
        mdp2.setField(6, 3, Field.OBSTACLE);
        mdp2.setField(7, 3, Field.OBSTACLE);
        mdp2.setField(8, 3, Field.OBSTACLE);
        mdp2.setField(9, 3, Field.OBSTACLE);

        mdp2.setPosReward(10);
        QLearningAlgorithm qla = new QLearningAlgorithm(mdp2);
        System.out.println(qla.toString());
        int i = 0;
        qla.setGreedyEpsilon(true);
        mdp2.setShowProgress(false);
        while (i++ < 1000) {

            while (!mdp2.isTerminated()) {
                qla.experience(qla.policy_currentState());
                //System.out.println(qla.toString());
                //System.out.println(qla.policy());
            }
            mdp2.restart();
        }
        qla.finish();

        mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(9, 9, Field.REWARD);
        mdp2.setPosReward(10);

        mdp2.setField(3, 2, Field.OBSTACLE);
        mdp2.setField(3, 3, Field.OBSTACLE);
        mdp2.setField(3, 4, Field.OBSTACLE);
        mdp2.setField(3, 5, Field.OBSTACLE);
        mdp2.setField(3, 6, Field.OBSTACLE);

        mdp2.setField(7, 9, Field.OBSTACLE);
        mdp2.setField(7, 8, Field.OBSTACLE);
        mdp2.setField(7, 7, Field.OBSTACLE);
        mdp2.setField(7, 6, Field.OBSTACLE);
        mdp2.setField(7, 5, Field.OBSTACLE);

        mdp2.setField(4, 3, Field.OBSTACLE);
        mdp2.setField(5, 3, Field.OBSTACLE);
        mdp2.setField(6, 3, Field.OBSTACLE);
        mdp2.setField(7, 3, Field.OBSTACLE);
        mdp2.setField(8, 3, Field.OBSTACLE);
        mdp2.setField(9, 3, Field.OBSTACLE);
        qla = new QLearningAlgorithm(mdp2);
        System.out.println(qla.toString());
        i = 0;
        qla.setGreedyEpsilon(true);
        mdp2.setShowProgress(false);
        while (i++ < 1000) {

            while (!mdp2.isTerminated() && mdp2.getActionsCounter() < 10000) {
                qla.experience(qla.policy_currentState());
                //System.out.println(qla.toString());
                //System.out.println(qla.policy());
            }
            mdp2.restart();
        }
        qla.finish();

    }

    public static void qla_stochastic_with_penalties() {

        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        mdp2.setField(3, 3, Field.NEGREWARD);
        mdp2.setPosReward(10);
        QLearningAlgorithm qla = new QLearningAlgorithm(mdp2);
        System.out.println(qla.toString());
        int i = 0;
        qla.setGreedyEpsilon(true);
        mdp2.setShowProgress(false);
        while (i++ < 1000) {

            while (!mdp2.isTerminated()) {
                qla.experience(qla.policy_currentState());
                //System.out.println(qla.toString());
                //System.out.println(qla.policy());
            }
            mdp2.restart();
        }
        qla.finish();
        mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(9, 9, Field.REWARD);
        mdp2.setField(3, 3, Field.NEGREWARD);
        mdp2.setPosReward(10);

        qla = new QLearningAlgorithm(mdp2);
        System.out.println(qla.toString());
        i = 0;
        qla.setGreedyEpsilon(true);
        mdp2.setShowProgress(false);
        while (i++ < 1000) {

            while (!mdp2.isTerminated()) {
                qla.experience(qla.policy_currentState());
                //System.out.println(qla.toString());
                //System.out.println(qla.policy());
            }
            mdp2.restart();
        }
        qla.finish();

    }

}
