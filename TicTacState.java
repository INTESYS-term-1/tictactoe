package tic;

import java.util.ArrayList;

public class TicTacState {

	Cell[][] board = new Cell[3][3];
	TicTacState parentState = null;
	Cell zeroCell;
	int score;
	boolean xoTurn; // true - X // false - O
	boolean type; // true - max // false - min
	int gameScore;
	int childrenLeft;
	int level;

	public Cell[][] getBoard() {
		return board;
	}

	public TicTacState(Cell[][] initBoard) { // use this constructor for initial
												// and final board state
		board = initBoard;
		xoTurn = true;
		level = 0;
	}

	public TicTacState(Cell[][] initBoard, TicTacState parent, boolean xoTurn, int level, int x, int y) { // use
																											// this
																											// constructor
																											// for
																											// generate
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.board[i][j] = new Cell(i, j, initBoard[i][j].getValue());
				if (x == i && y == j)
					if (xoTurn == false)
						board[i][j].setValue('X');
					else
						board[i][j].setValue('O');
			}
		}
		this.parentState = parent;
		this.xoTurn = xoTurn;
		if (xoTurn == true)
			type = true;
		else
			type = false;
		this.level = level;
	}

	public void computeScore() {
		if (xWin())
			gameScore = 1;
		else if (oWin())
			gameScore = -1;
		else
			gameScore = 0;

		propagateScore();
	}

	public boolean xWin() {
		if (board[0][0].getValue() == 'X' && board[0][1].getValue() == 'X' && board[0][2].getValue() == 'X') {// row
																												// 1
			return true;
		} else if (board[1][0].getValue() == 'X' && board[1][1].getValue() == 'X' && board[1][2].getValue() == 'X') {// row
																														// 2
			return true;
		} else if (board[2][0].getValue() == 'X' && board[2][1].getValue() == 'X' && board[2][2].getValue() == 'X') {// row
																														// 3
			return true;
		} else if (board[0][0].getValue() == 'X' && board[1][0].getValue() == 'X' && board[2][0].getValue() == 'X') {// col
																														// 1
			return true;
		} else if (board[0][1].getValue() == 'X' && board[1][1].getValue() == 'X' && board[2][1].getValue() == 'X') {// col
																														// 2
			return true;
		} else if (board[0][2].getValue() == 'X' && board[1][2].getValue() == 'X' && board[2][2].getValue() == 'X') {// col
																														// 3
			return true;
		} else if (board[0][0].getValue() == 'X' && board[1][1].getValue() == 'X' && board[2][2].getValue() == 'X') {// diagonal
																														// 1
			return true;
		} else if (board[0][2].getValue() == 'X' && board[1][1].getValue() == 'X' && board[2][0].getValue() == 'X') {// diagonal
																														// 2
			return true;
		}
		return false;
	}

	public boolean oWin() {
		if (board[0][0].getValue() == 'O' && board[0][1].getValue() == 'O' && board[0][2].getValue() == 'O') {// row
																												// 1
			return true;
		} else if (board[1][0].getValue() == 'O' && board[1][1].getValue() == 'O' && board[1][2].getValue() == 'O') {// row
																														// 2
			return true;
		} else if (board[2][0].getValue() == 'O' && board[2][1].getValue() == 'O' && board[2][2].getValue() == 'O') {// row
																														// 3
			return true;
		} else if (board[0][0].getValue() == 'O' && board[1][0].getValue() == 'O' && board[2][0].getValue() == 'O') {// col
																														// 1
			return true;
		} else if (board[0][1].getValue() == 'O' && board[1][1].getValue() == 'O' && board[2][1].getValue() == 'O') {// col
																														// 2
			return true;
		} else if (board[0][2].getValue() == 'O' && board[1][2].getValue() == 'O' && board[2][2].getValue() == 'O') {// col
																														// 3
			return true;
		} else if (board[0][0].getValue() == 'O' && board[1][1].getValue() == 'O' && board[2][2].getValue() == 'O') {// diagonal
																														// 1
			return true;
		} else if (board[0][2].getValue() == 'O' && board[1][1].getValue() == 'O' && board[2][0].getValue() == 'O') {// diagonal
																														// 2
			return true;
		}
		return false;
	}

	public boolean isLeaf() {
		if (xWin() || oWin())
			return true;


		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j].getValue() == ' ') {
					return false;
				}
			}
		}
		return true;
	}

	public void propagateScore() {
		if (parentState != null && childrenLeft == 0)
			parentState.submit(this);
	}

	public void submit(TicTacState s) {
		if (type == false) {
			score = Math.min(score, s.getScore());
		} else {
			score = Math.max(score, s.getScore());
		}
		childrenLeft--;
		propagateScore();
	}

	@Override
	public boolean equals(Object o) {
		TicTacState s = (TicTacState) o;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.board[i][j].getValue() != s.board[i][j].getValue())
					return false;
			}
		}
		return true;
	}

	public void printBoard() {
		System.out.println("Level: " + level);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j].getValue() + " | ");
			}
			System.out.println("");
		}
		System.out.println("-------------");

	}

	public ArrayList<TicTacState> getNextStates() {
		ArrayList<TicTacState> states = new ArrayList<TicTacState>();
		Cell[][] tempBoard;
		if (xoTurn == true) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j].getValue() == ' ') {
						tempBoard = this.board;
						states.add(new TicTacState(tempBoard, this, false, level + 1, i, j));
					}
				}
			}
		}
		if (xoTurn == false) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j].getValue() == ' ') {
						tempBoard = this.board;
						states.add(new TicTacState(tempBoard, this, true, level + 1, i, j));
					}
				}
			}
		}
		return states;
	}

	public TicTacState getParentState() {
		return parentState;
	}

	public int getScore() {
		return gameScore;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}
}
