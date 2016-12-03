package model.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.SwingUtilities;

import model.ISudokuSolver;
import view.RefreshListener;

public class GeneticAlgorithmSolver implements ISudokuSolver {
	
	public static int populationSize = 100;
	
	private SudokuSolution fittest;
	private List<SudokuSolution> population;
	private Set<SudokuSolution> set;
	private BreedingConditions conditions;
	private Random random;
	
	public GeneticAlgorithmSolver() {
		random = new Random();
		population = new ArrayList<SudokuSolution>();
		set = new HashSet<SudokuSolution>();
		
		fittest = new SudokuSolution();
		conditions = new BreedingConditions();
		conditions.setChildrenCount(3);
		conditions.setCrossoverProbability(1);
		conditions.setMutationProbability(0.4);
		
//		SortedSet<SudokuSolution> sorted = new TreeSet<SudokuSolution>(new Comparator<SudokuSolution>() {
//			@Override
//			public int compare(SudokuSolution solution1, SudokuSolution solution2) {
//				return solution1.getFitness() - solution2.getFitness();
//			}
//		});
	}
	
	//this is garbage and will be thrown out
	public GeneticAlgorithmSolver(SudokuSolution solution) {
		fittest = solution;
	}

	public void sortPopulation() {
		Collections.sort(population, new Comparator<SudokuSolution>() {
			@Override
			public int compare(SudokuSolution solution1, SudokuSolution solution2) {
				return solution1.getFitness() - solution2.getFitness();
			}
		});
	}
	
	public void initialPopulation() {
		for (int i = 0; i < populationSize; ++i)
			add(new SudokuSolution());
			
	}

	@Override
	public boolean solve(final RefreshListener listener) {
		initialPopulation();
		sortPopulation();
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (!step()) {
					System.out.println(fittest.getFitness());
					System.out.println((char)random.nextInt(256));
					SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run() {
							listener.refresh(fittest);
						}
					});
				}
				
				System.out.println("SOLVED!!");
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						listener.refresh(fittest);
					}
				});
			}
		});
		thread.start();
		
		return false;
	}
	
	public boolean step() {
		List<SudokuSolution> breeders = new ArrayList<SudokuSolution>();
		breeders.addAll(population);
		
		while(!breeders.isEmpty()) {
			SudokuSolution solution1 = breeders.remove(random.nextInt(breeders.size()));
			if(breeders.isEmpty())
				break;
			
			SudokuSolution solution2 = breeders.remove(random.nextInt(breeders.size()));
			
			List<SudokuSolution> children = solution1.breed(conditions, solution2);
			for (SudokuSolution individual : children)
				add(individual);
		}
		
		sortPopulation();
		
		while (population.size() > populationSize)
			remove(population.size() - 1);
		
		fittest = population.get(0);
		
		return fittest.getFitness() == 0;
	}
	
	private void add(SudokuSolution individual) {
		if (set.add(individual))
			population.add(individual);
	}
	
	private void remove(int index) {
		SudokuSolution individual = population.remove(index);
		if (individual != null)
			set.remove(individual);
	}

	@Override
	public boolean validate() {
		return false;
	}

	@Override
	public int getValueAt(int x, int y) {
		return fittest.getValueAt(x, y);
	}

	@Override
	public void setValueAt(int x, int y, int val) {
		fittest.setValueAt(x, y, val);
	}

	@Override
	public boolean isCellValid(int x, int y) {
		return fittest.isCellValid(x, y);
	}
	
}
