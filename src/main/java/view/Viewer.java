package view;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import model.SudokuModel;
import model.genetic.GeneticAlgorithmSolver;

public class Viewer extends JFrame {
	
	private SudokuPanel panel;
	
	public Viewer(){
		super("Sudoku Solver");
		initGUI();
	}
	
	private void initGUI() {
		panel = new SudokuPanel(new SudokuModel());
		setContentPane(panel);
		
		makeMenu();
	}
	
	private void makeMenu() {
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
			Action load = new AbstractAction("Load..."){
				public void actionPerformed(ActionEvent e){
					JFileChooser chooser = new JFileChooser();
					int response = chooser.showOpenDialog(Viewer.this);
					if(response == JFileChooser.APPROVE_OPTION){
						File file = chooser.getSelectedFile();
						try{
							FileInputStream fin = new FileInputStream(file);
							ObjectInputStream in = new ObjectInputStream(fin);
							Object obj = in.readObject();
							panel.setModel((SudokuModel)obj);
						} catch (Exception eh){
							eh.printStackTrace();
						}
					}
				}
			};
			fileMenu.add(load);
			Action save = new AbstractAction("Save..."){
				public void actionPerformed(ActionEvent e){
					JFileChooser chooser = new JFileChooser();
					int response = chooser.showSaveDialog(Viewer.this);
					if(response == JFileChooser.APPROVE_OPTION){
						File file = chooser.getSelectedFile();
						try{
							FileOutputStream fout = new FileOutputStream(file);
							ObjectOutputStream out = new ObjectOutputStream(fout);
							out.writeObject(panel.getModel());
						} catch (IOException eh){
							
						}
					}
				}
			};
			fileMenu.add(save);
			Action exit = new AbstractAction("Exit"){
				public void actionPerformed(ActionEvent e){
					System.exit(0);
				}
			};
			fileMenu.add(exit);
		menu.add(fileMenu);
		JMenu sudokuMenu = new JMenu("Sudoku");
			Action solve = new AbstractAction("Solve"){
				public void actionPerformed(ActionEvent event){
					panel.solve();
				}
			};
			sudokuMenu.add(solve);
			Action geneticAlgoSolve = new AbstractAction("Genetic Algorithms Solve"){
				public void actionPerformed(ActionEvent event){
					try {
						panel.setModel(new GeneticAlgorithmSolver());
						panel.solve();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			};
			sudokuMenu.add(geneticAlgoSolve);
		menu.add(sudokuMenu);
		setJMenuBar(menu);
			
	}
	
	public static void main(String[] args) {
		Viewer view = new Viewer();
		view.setSize(700, 700);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
	}

}
