package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import model.ISudokuSolver;
import model.SudokuInterface;
import model.genetic.GeneticAlgorithmSolver;
import model.genetic.SudokuSolution;

public class SudokuPanel extends JPanel implements MouseListener, KeyListener, RefreshListener {
	
	public static int X_ORIGIN = 20;
	public static int Y_ORIGIN = 20;
	
	public static int BOX_WIDTH = 50;
	public static int BOARD_WIDTH = 9 * BOX_WIDTH;
	public static int THICK_LINE_WITH = 3;
	
	
	private ISudokuSolver model;
	private Point selection;
	
	public SudokuPanel(ISudokuSolver model) {
		this.model = model;
		setFocusable(true);
		
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public void setModel(ISudokuSolver model) {
		this.model = model;
		repaint();
	}
	
	public void clear(Graphics pen){
		pen.setColor(Color.WHITE);
		pen.fillRect(0, 0, 900, 800);
	}
	
	@Override
	public void paint(Graphics pen) {
		clear(pen);
		
		//draw the squares
		pen.setColor(Color.BLACK);
		for(int x = 0; x < 9; ++x) {
			for(int y = 0; y < 9; ++y) {
				pen.setColor(Color.WHITE);
				
				if(selection != null && selection.x == x && selection.y == y)
					pen.setColor(Color.PINK);
				else if (!model.isCellValid(x, y))
					pen.setColor(Color.RED);
					
				pen.fillRect(X_ORIGIN + x * BOX_WIDTH, Y_ORIGIN + y * BOX_WIDTH, BOX_WIDTH, BOX_WIDTH);
				
				pen.setColor(Color.BLACK);
				pen.drawRect(X_ORIGIN + x * BOX_WIDTH, Y_ORIGIN + y * BOX_WIDTH, BOX_WIDTH, BOX_WIDTH);
				
				int value = model.getValueAt(x, y);
				if(value != -1) {
					int xcoord = x * BOX_WIDTH + X_ORIGIN + 20;
					int ycoord = y * BOX_WIDTH + Y_ORIGIN + 35;
					pen.setFont(new Font(pen.getFont().getName(), Font.PLAIN, 30));
					pen.drawString(Integer.toString(value), xcoord, ycoord);
				}
			}
		}
		
		//do the thick lines on the outside of the board
		pen.fillRect(X_ORIGIN, Y_ORIGIN, BOARD_WIDTH, THICK_LINE_WITH);
		pen.fillRect(X_ORIGIN, Y_ORIGIN, THICK_LINE_WITH, BOARD_WIDTH);
		pen.fillRect(X_ORIGIN + BOARD_WIDTH, Y_ORIGIN, THICK_LINE_WITH, BOARD_WIDTH + THICK_LINE_WITH);
		pen.fillRect(X_ORIGIN, Y_ORIGIN + BOARD_WIDTH, 9 * BOX_WIDTH + THICK_LINE_WITH, THICK_LINE_WITH);
		
		//do the vertical thick lines
		pen.fillRect(X_ORIGIN + 3 * BOX_WIDTH, Y_ORIGIN, THICK_LINE_WITH, BOARD_WIDTH);
		pen.fillRect(X_ORIGIN + 6 * BOX_WIDTH, Y_ORIGIN, THICK_LINE_WITH, BOARD_WIDTH);
		
		//do the horizontal thick lines
		pen.fillRect(X_ORIGIN, Y_ORIGIN + 3 * BOX_WIDTH, BOARD_WIDTH, THICK_LINE_WITH);
		pen.fillRect(X_ORIGIN, Y_ORIGIN + 6 * BOX_WIDTH, BOARD_WIDTH, THICK_LINE_WITH);
	}
	
	public void mouseClicked(MouseEvent click) {
		selection = getClickedCoordinates(click);
		repaint();
	}
	
	private Point getClickedCoordinates(MouseEvent click) {
		int x = click.getPoint().x;
		int y = click.getPoint().y;
		
		//check for out of bounds
		if(x < X_ORIGIN || x > X_ORIGIN + BOARD_WIDTH || y < X_ORIGIN || y > Y_ORIGIN + BOARD_WIDTH){
			return null;
		}
		
		int posX = (x - X_ORIGIN) / BOX_WIDTH;
		int posY = (y - Y_ORIGIN) / BOX_WIDTH;
		
		Point result = new Point(posX, posY);
		
		return result;
	}
	
	public void mouseEntered(MouseEvent arg0) {
		//no-op
	}

	public void mouseExited(MouseEvent arg0) {
		//no-op
	}

	public void mousePressed(MouseEvent arg0) {
		//no-op
	}

	public void mouseReleased(MouseEvent arg0) {
		//no-op
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(selection != null && is1to9(e.getKeyCode())) {
			int value = getValue(e.getKeyCode());
			model.setValueAt(selection.x, selection.y, value);
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	private int getValue(int keyCode) {
		return keyCode - KeyEvent.VK_0;
	}

	private boolean is1to9(int keyCode) {
		return keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9;
	}

	public SudokuInterface getModel() {
		return model;
	}

	public void solve() {
		model.solve(this);
		repaint();
	}

	@Override
	public void refresh(SudokuSolution solution) {
		model = new GeneticAlgorithmSolver(solution);
		repaint();
	}

}
