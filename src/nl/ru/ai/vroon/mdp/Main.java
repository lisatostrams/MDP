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
        for (int x = 0; x < 10; x++) {
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

            ValueIterationAlgorithm via2 = new ValueIterationAlgorithm(mdp2);
            mdp2.setShowProgress(false);
            while (!via2.converged()) {
                via2.update();
                System.out.println(via2.toString());
                System.out.println(via2.policy());
                mdp2.restart();
            }
            via2.finish();

            mdp = new MarkovDecisionProblem();
            mdp.setInitialState(0, 0);
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
            System.out.println(qla.toString());
            qla.finish();

            mdp2 = new MarkovDecisionProblem(10, 10);
            mdp2.setField(5, 5, Field.REWARD);
            mdp2.setShowProgress(false);
            qla = new QLearningAlgorithm(mdp2);
            System.out.println(qla.toString());
            i = 0;
            qla.setGreedyEpsilon(true);
            while (i++ < 10000) {

                while (!mdp2.isTerminated()) {
                    qla.experience(qla.policy_currentState());
                //System.out.println(qla.toString());
                    //System.out.println(qla.policy());
                }
                mdp2.restart();
            }
            System.out.println(qla.toString());
            System.out.println(qla.policy());
            qla.finish();

        }
    }
}
