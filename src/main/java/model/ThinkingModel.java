package model;


/*
00 01 02 | 03 04 05 | 06 07 08
10 11 12 | 13 14 15 | 16 17 18
20 21 22 | 23 24 25 | 26 27 28
------------------------------
30 31 32 | 33 34 35 | 36 37 38
40 41 42 | 43 44 45 | 46 47 48
50 51 52 | 53 54 55 | 56 57 58
------------------------------
60 61 62 | 63 64 65 | 66 67 68
70 71 72 | 73 74 75 | 76 77 78
80 81 82 | 83 84 85 | 86 87 88
*/

public class ThinkingModel implements SudokuInterface{

	private Block[][] blocks = new Block[9][9];
	
	public boolean solve() {
		for (int x=0; x<9; ++x) {
			for (int y=0; y<9; ++y) {
				
			}
		}
		return false;
	}

	private void cascadeFrom(int x, int y, int val) {
		cascadeRowFrom(x, y, val);
		cascadeColFrom(x, y, val);
		cascadeBoxFrom(x, y, val);
	}
	
	private void cascadeRowFrom(int x, int y, int val) {
		for (int ax = 0; ax < 9; ++ax) {
			if (blocks[ax][y].removePossibility(val))
				cascadeFrom(ax, y, blocks[ax][y].getValue());
		}
	}
	private void cascadeColFrom(int x, int y, int val) {
		for (int ay = 0; ay < 9; ++ay){
			if (blocks[x][ay].removePossibility(val))
				cascadeFrom(x, ay, blocks[x][ay].getValue());
		}
	}
	private void cascadeBoxFrom(int x, int y, int val) {
		x = (x / 3) * 3;
		y = (y / 3) * 3;
		
		for (int xoffset = 0; xoffset < 3; ++xoffset)
			for (int yoffset = 0; yoffset < 3; ++yoffset) {
				int xcoord = x + xoffset;
				int ycoord = y + yoffset;
				Block block = blocks[xcoord][ycoord];
				
				if (blocks[x + xoffset][y + yoffset].removePossibility(val))
					cascadeFrom(xcoord, ycoord, block.getValue());
			}
	}
	
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getValueAt(int x, int y) {
		return blocks[x][y].getValue();
	}

	public void setValueAt(int x, int y, int val) {
		blocks[x][y].setValue(val);
	}
	
	@Override
	public boolean isCellValid(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}