import java.util.ArrayList;

public class State {
	/* Static Variables */
	final static int player = -1;
	final static int free = 0;
	final static int ai = 1;
	// final static int MAX_SHEEP_TOKENS = 16;

	/* Essential Variables */
	State parentState;
	int currentTurn; // the current turn
	GuiCell[][] board; // the board itself
	int score;

	// int emptySpace; // empty space on the board
	// int blackSheepLeft; // black sheep's tokens left
	// int whiteSheepLeft; // white sheep tokens left


	int childrenLeft;

	State(int[][] board) {
	}

	public State(GuiCell[][] board, State parent, int nextTurn) {
		this.board = board;
		this.parentState = parent;
		this.currentTurn = nextTurn;

	}

	@Override
	public boolean equals(Object o) {
		State s = (State) o;

		return true;
	}

	// function to get board
	public GuiCell[][] getBoard() {
		return board;
	}

	public void computeScore() {

		int sum = 0;
		for (int i = 0; i < Gui.BOARDROW; i++) {
			for (int j = 0; j < Gui.BOARDCOLUMN; j++) {
				if (board[i][j].getOwner() == ai) {
					sum++;
				}
			}
		}

		score = sum;

		propagateScore();
	}

	public void propagateScore() {
		if (parentState != null && childrenLeft == 0)
			parentState.submit(this);
	}

	public void submit(State s) {
		if (currentTurn == player) {
			score = Math.min(score, s.getScore());
		} else {
			score = Math.max(score, s.getScore());
		}
		childrenLeft--;
		propagateScore();
	}
	
	public int getScore(){
		return score;
	}

	public ArrayList<State> generateStates() {
		ArrayList<State> states = new ArrayList<>();

		State tempState;

		for (int i = 0; i < Gui.BOARDROW; i++) {
			for (int j = 0; j < Gui.BOARDCOLUMN; j++) {

				if (board[i][j].getOwner() == ai) {
					//
					for (int k = 0; k < Gui.BOARDROW; k++) {
						for (int l = 0; l < Gui.BOARDCOLUMN; l++) {

							// 1. left diagonal up 2. left diag down 3. right
							// diag
							// up 4. rright diag down 5. horitzontal
							// basically kung abot eto nung ai cell
							if (i - k == j - l || k - i == j - l || i - k == l - j || k - i == l - j || i == k) {
								// check if sagad sa board
								if (k == 0 || k == Gui.BOARDROW || l == 0 || l == Gui.BOARDCOLUMN) {
									if (board[k][l].getOwner() == free) {

										for (int m = 1; m < board[i][j].getValue(); m++) {
											if (k != i && j != l) {
												tempState = new State(this.board, this, player);
												tempState.getBoard()[k][l].setOwner(ai);
												tempState.getBoard()[k][l].setValue(m);
												tempState.getBoard()[i][j].setValue(board[i][j].getValue() - 1 - m);

												states.add(tempState);
											}

										}

									}
								}
								// check obstacles
								else {
									// upper left diag
									for (int n = i - 1; n > 0; n--) {
										if (board[n - 1][n - 1].getOwner() != free) {
											for (int m = 1; m < board[i][j].getValue(); m++) {
												tempState = new State(this.board, this, player);
												tempState.getBoard()[n][n].setOwner(ai);
												tempState.getBoard()[n][n].setValue(m);
												tempState.getBoard()[i][j].setValue(board[i][j].getValue() - 1 - m);
												states.add(tempState);
											}
										}

									}
									// lower right diag
									for (int n = i + 1; n < Gui.BOARDROW - 1; n++) {
										if (board[n + 1][n + 1].getOwner() != free) {
											for (int m = 1; m < board[i][j].getValue(); m++) {
												tempState = new State(this.board, this, player);
												tempState.getBoard()[n][n].setOwner(ai);
												tempState.getBoard()[n][n].setValue(m);
												tempState.getBoard()[i][j].setValue(board[i][j].getValue() - 1 - m);
												states.add(tempState);

											}
										}

									}

									// for left horizontal
									for (int n = i - 1; n > 0; n--) {
										if (board[k][n - 1].getOwner() != free) {
											for (int m = 1; m < board[i][j].getValue(); m++) {
												tempState = new State(this.board, this, player);
												tempState.getBoard()[k][n].setOwner(ai);
												tempState.getBoard()[k][n].setValue(m);
												tempState.getBoard()[i][j].setValue(board[i][j].getValue() - 1 - m);

												states.add(tempState);

											}
										}

									}

									// for right horizontal
									for (int n = i + 1; n < Gui.BOARDCOLUMN - 1; n++) {
										if (board[k][n + 1].getOwner() != free) {
											for (int m = 1; m < board[i][j].getValue(); m++) {
												tempState = new State(this.board, this, player);
												tempState.getBoard()[k][n].setOwner(ai);
												tempState.getBoard()[k][n].setValue(m);
												tempState.getBoard()[i][j].setValue(board[i][j].getValue() - 1 - m);

												states.add(tempState);
											}

										}
									}
								}

								// for left diagonal down
								int o = j - 1;
								for (int n = i + 1; n < Gui.BOARDCOLUMN; n++) {

									if (board[n + 1][o - 1].getOwner() != free) {
										for (int m = 1; m < board[i][j].getValue(); m++) {
											tempState = new State(this.board, this, player);
											tempState.getBoard()[n][o].setOwner(ai);
											tempState.getBoard()[n][o].setValue(m);
											tempState.getBoard()[i][j].setValue(board[i][j].getValue() - 1 - m);

											states.add(tempState);

										}
									}
									o--;
								}

								// for right diagonal up
								int p = j + 1;
								for (int n = i - 1; n > 0; n--) {
									if (board[n - 1][p + 1].getOwner() != free) {
										for (int m = 1; m < board[i][j].getValue(); m++) {
											tempState = new State(this.board, this, player);
											tempState.getBoard()[n][p].setOwner(ai);
											tempState.getBoard()[n][p].setValue(m);
											tempState.getBoard()[i][j].setValue(board[i][j].getValue() - 1 - m);
											states.add(tempState);

										}
									}
									p++;
								}

							}
						}

					}
				}

			}
		}

		return states;

	}

	public int calculateScore() {
		int score = 0;

		return score;
	}
}