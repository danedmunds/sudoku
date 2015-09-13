package model;

import view.RefreshListener;

public interface ISudokuSolver extends SudokuInterface {
	
	public boolean solve(RefreshListener listener);
	
	public boolean validate();

}
