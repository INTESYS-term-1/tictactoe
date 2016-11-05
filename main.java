package tic;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int lowest;
		ArrayList<TicTacState> explore = new ArrayList<TicTacState>();
		ArrayList<TicTacState> visited = new ArrayList<TicTacState>();
		ArrayList<TicTacState> nextStates = new ArrayList<TicTacState>();

		char[][] init = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };

		TicTacState initialState = new TicTacState(init);

		// initialize empty board
		TicTacState currState = initialState;

		System.out.println("Initial Board");
		initialState.printBoard();

		// int count = 0;
		explore.add(initialState);

		final long startTime = System.currentTimeMillis();

		int inputX = 0;
		int inputY = 0;

		do {
			System.out.println("Input x coordinate: ");
			inputX = sc.nextInt();

			System.out.println("Input y coordinate: ");
			inputY = sc.nextInt();
			init[inputX][inputY] = 'X';
			initialState = new TicTacState(init);

			explore.add(initialState);

			while (!explore.isEmpty()) {
				currState = explore.get(0);
				explore.remove(0);
				visited.add(currState);

				// if (currState.isLeaf()) {
				// currState.computeScore();
				// }

				// else{

				nextStates = currState.getNextStates();
				// lowest = findLowestScore(nextStates);
				for (TicTacState s : nextStates) {
					if (!visited.contains(s)
							&& !explore.contains(s) /* && s.isValid() */) {
						// if(lowest <= s.getScore())
						explore.add(s);// uncomment for BFS

						s.printBoard();
						// s.printBoard();
						// explore.add(0, s);//uncomment for DFS
					}
					// count++;
				}
				// }
			}
		} while (inputX < 3 && inputY < 3);

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
