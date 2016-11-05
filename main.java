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

		Cell[][] board = new Cell[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j].setValue(' ');
			}
		}

		TicTacState initialState = new TicTacState(board);

		// initialize empty board
		TicTacState currState = initialState;

		System.out.println("Initial Board");
		initialState.printBoard();

		// int count = 0;
		// explore.add(initialState);

		final long startTime = System.currentTimeMillis();

		int inputX = 0;
		int inputY = 0;

		int round = 0;

		do {
			System.out.println("Input x coordinate: ");
			inputX = sc.nextInt();

			System.out.println("Input y coordinate: ");
			inputY = sc.nextInt();

			board[inputX][inputY].setValue('X');
			initialState = new TicTacState(board);

			explore.add(initialState);
			//
			// while (!explore.isEmpty()) {
			// currState = explore.get(0);
			// explore.remove(0);
			// visited.add(currState);
			//
			// // if (currState.isLeaf()) {
			// // currState.computeScore();
			// // }
			//
			// nextStates = currState.getNextStates();
			// // lowest = findLowestScore(nextStates);
			// for (TicTacState s : nextStates) {
			// if (!visited.contains(s) && !explore.contains(s)) {
			// // if(lowest <= s.getScore())
			// //explore.add(s);// uncomment for BFS
			//
			// s.printBoard();
			// // s.printBoard();
			// explore.add(0, s);//uncomment for DFS
			// }
			// // count++;
			// }
			// }
			//
			//
			int i = 0;
			while (i < explore.size()) {
				currState = explore.get(i);
				visited.add(currState);

				if (currState.isLeaf()) {
					currState.computeScore();
				}

				nextStates = currState.getNextStates();
				// lowest = findLowestScore(nextStates);
				for (TicTacState s : nextStates) {
					if (!visited.contains(s) && !explore.contains(s)) {
						// if(lowest <= s.getScore())
						explore.add(s);// uncomment for BFS

						// s.printBoard();

						// s.printBoard();
						// explore.add(0, s);//uncomment for DFS
					}
					// count++;
				}
				i++;
			}

			for (int j = 0; j < explore.size(); j++) {
				// explore.get(j).printBoard();
				// System.out.println(explore.get(j).getScore());

				if (explore.get(j).getLevel() == round + 1
						&& (explore.get(j).getScore() == 1 || explore.get(j).getScore() == 0)) {
					explore.get(j).printBoard();
					board = explore.get(j).getBoard();
				}

			}

			round++;
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
