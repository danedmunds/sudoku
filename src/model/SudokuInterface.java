package model;

public interface SudokuInterface {
	
	public int getValueAt(int x, int y);
	
	public void setValueAt(int x, int y, int val);

	public boolean isCellValid(int x, int y);
}
