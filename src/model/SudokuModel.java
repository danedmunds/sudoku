package model;

import java.io.Serializable;

import view.RefreshListener;

public class SudokuModel implements Serializable, ISudokuSolver {
	
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7118645241269014943L;
	private Square[][] squares = new Square[9][9];
	
	public SudokuModel(){
		for(int i=0; i<9; ++i){
			for(int j=0; j<9; ++j){
				squares[i][j] = new Square();
			}
		}
	}	
	public int getValueAt(int x, int y){
		int val = squares[x][y].value;
		if(val == -1){
			val = squares[x][y].tempValue;
		}
		return val;
	}
	public void setValueAt(int x, int y, int val){
		squares[x][y].value = val;
	}
	
	public boolean validate(){
		for(int i=0; i<9; ++i){
			if(!checkRow(0, i)){
				return false;
			}
			if(!checkCol(i, 0)){
				return false;
			}
			if(i%3 == 0){
				if(!(checkBox(i, 0) && checkBox(i, 3) && checkBox(i, 6))){
					return false;
				}
			}
		}
		return true;
	}
	
	public void output(){
		for(int i=0; i<9; ++i){
			for(int j=0; j<9; ++j){
				if(squares[i][j].value != -1){
					System.out.print(squares[i][j].value+" ");
				} else {
					System.out.print(squares[i][j].tempValue+" ");
				}
			}
			System.out.println();
		}
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	}
	
	public boolean solve(RefreshListener listener) {
		return solve(0, 0);
	}
	
	public boolean solve(int x, int y) {
		if (squares[x][y].value != -1) {
			if (solve(x, y, squares[x][y].value)) {
				return true;
			}
		} else {
			for (int i = 1; i < 10; ++i) {
				if (solve(x, y, i)) {
					return true;
				}
			}
		}
		if (squares[x][y].value == -1) {
			squares[x][y].tempValue = -1;
		}
		return false;
	}
	
	public boolean solve(int x, int y, int val){
		
		squares[x][y].tempValue = val;
//		output();
		
//		check for rule breaks
		if(!(checkRow(x,y) && checkCol(x,y) && checkBox(x,y))){
			squares[x][y].tempValue = -1;
			return false;
		}
//		success case
		if(x == 8 && y == 8){
			return true;
		}
		
//		recursive step
		if(x!=8){
			return solve(x+1, y);
		} else {
			return solve(0, y+1);
		}
	}
	
	private boolean checkRow(int x, int y){
		boolean[] seen = new boolean[10];
		for(int i=0; i<10; i++){
			seen[i] = false;
		}
		for(int i=0; i<9; ++i){
			Square current = squares[i][y];
			int val = current.value;
			if(val == -1){
				val = current.tempValue;
			}
			if(val != -1){
				if(seen[val] == true){
					return false;
				} 
				seen[val] = true;
			}
		}
		return true;
	}
	
	private boolean checkCol(int x, int y){
		boolean[] seen = new boolean[10];
		for(int i=0; i<10; i++){
			seen[i] = false;
		}
		for(int i=0; i<9; ++i){
			Square current = squares[x][i];
			int val = current.value;
			if(val == -1){
				val = current.tempValue;
			}
			if(val != -1){
				if(seen[val] == true){
					return false;
				} 
				seen[val] = true;
			}
		}
		return true;
	}
	
	private boolean checkBox(int x, int y){
		if(x<3 && y<3){ 			//square 00
			return checkSquare(0,0);
		} else if (x<6 && y<3){ 	//square 01
			return checkSquare(3, 0);
		} else if (y<3){ 			//square 02
			return checkSquare(6, 0);
		} else if (x<3 && y<6){ 	//square 10
			return checkSquare(0, 3);
		} else if (x<6 && y<6){ 	//square 11
			return checkSquare(3, 3);
		} else if (y<6){ 			//square 12
			return checkSquare(6, 3);
		} else if (x<3){ 			//square 20
			return checkSquare(0, 6);
		} else if (x<6){ 			//square 21
			return checkSquare(3, 6);
		} else { 					//square 23
			return checkSquare(6, 6);
		}
	}
	
	private boolean checkSquare(int x, int y){
		int xstart = (x / 3) * 3;
		int ystart = (y / 3) * 3;
		
		boolean[] seen = new boolean[10];
		for(int i=0; i<10; i++){
			seen[i] = false;
		}
		for(int i=xstart; i<(xstart+3); ++i){
			for(int j=ystart; j<(ystart+3); ++j){
				Square current = squares[i][j];
				int val = current.value;
				if(val == -1){
					val = current.tempValue;
				}
				if(val != -1){
					if(seen[val] == true){
						return false;
					} 
					seen[val] = true;
				}
			}
		}
		return true;
	}
	
	public class Square implements Serializable{
		int value = -1;
		int tempValue = -1;
	}

	@Override
	public boolean isCellValid(int x, int y) {
		return checkSquare(x, y) && checkRow(x, y) && checkCol(x, y);
	}
}
