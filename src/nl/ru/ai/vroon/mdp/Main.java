package nl.ru.ai.vroon.mdp;

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
		MarkovDecisionProblem mdp = new MarkovDecisionProblem();
		mdp.setInitialState(0, 0);
                ValueIterationAlgorithm via = new ValueIterationAlgorithm(mdp);
                System.out.println(via.toString());
		for (int i = 0; i < 50; i++){
                        via.experience(Action.UP); 
			via.experience(Action.UP);
			via.experience(Action.RIGHT);
			via.experience(Action.RIGHT);
			via.experience(Action.RIGHT);
                        via.experience(Action.UP); 
                        System.out.println(via.toString());
			mdp.restart();
		}
                System.out.println(via.toString());
//		
//		MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
//		mdp2.setField(5, 5, Field.REWARD);
//		for (int i = 0; i < 100; i++){
//			mdp2.performAction(Action.UP);
//			mdp2.performAction(Action.RIGHT);
//			mdp2.performAction(Action.DOWN);
//			mdp2.performAction(Action.LEFT);
//		}
	}
}
