package tic;

import java.util.ArrayList;
import java.util.Stack;

public class main {

	public static void main(String[] args) {
		int lowest;
		ArrayList<TicTacState> explore = new ArrayList<TicTacState>(), visited = new ArrayList<TicTacState>();
		ArrayList<TicTacState> nextStates = new ArrayList<TicTacState>();

		ArrayList<TicTacState> propagate = new ArrayList<TicTacState>();

		char[][] init = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };

		TicTacState initialState = new TicTacState(init), currState = initialState;// care
		initialState.printBoard();
		// int count = 0;
		explore.add(initialState);

		propagate.add(initialState);

		final long startTime = System.currentTimeMillis();
		while (!explore.isEmpty()) {
			currState = explore.get(0);
			explore.remove(0);
			visited.add(currState);

//			if (currState.isLeaf()) {
//				currState.computeScore();
//			}

			// else{

			nextStates = currState.getNextStates();
			// lowest = findLowestScore(nextStates);
			for (TicTacState s : nextStates) {
				if (!visited.contains(s)
						&& !explore.contains(s) /* && s.isValid() */) {
					// if(lowest <= s.getScore())
					explore.add(s);// uncomment for BFS

					propagate.add(initialState);

//					if (s.getScore() == 1)
						s.printBoard();
					// s.printBoard();
					// explore.add(0, s);//uncomment for DFS
				}
				// count++;
			}
			// }
		}
		
//		
//		for(int i = 0; i < propagate.size(); i++){
//			currState = propagate.get(i);
//			if (currState.isLeaf()) {
//				currState.computeScore();
//			}
//		}
//		
//		
//		for(int i = 0; i < propagate.size(); i++){
//			System.out.println(propagate.get(i).getScore());
//		
//		}
		
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Visited: " + visited.size());
		System.out.println("Total execution time: " + (endTime - startTime) + " ms");

	}

	private static int findLowestScore(ArrayList<TicTacState> nextStates) {
		int lowest = 100;
		for (TicTacState s : nextStates)
			if (lowest > s.getScore())
				lowest = s.getScore();
		return lowest;
	}
}
