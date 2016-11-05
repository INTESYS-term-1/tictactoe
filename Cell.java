package tic;
public class Cell {
	int y = 0;
	int x = 0;
	char value = ' ';

	public Cell(int x, int y, char value){
		this.x = x;
		this.y = y;
		this.value = value;
	}
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

}
