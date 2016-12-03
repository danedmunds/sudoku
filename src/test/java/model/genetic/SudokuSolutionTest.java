package model.genetic;

import static org.junit.Assert.*;

import model.genetic.genes.SudokuLineGene;

import org.junit.Before;
import org.junit.Test;

public class SudokuSolutionTest {
	
	private SudokuLineGene[] genes;
	private SudokuSolution solution;
	
	@Before
	public void before() {
		genes = new SudokuLineGene[9];
		solution = new SudokuSolution(genes);
	}
	
	@Test
	public void verifyRowFitness() throws Exception {
		int[] geneDna = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9};
		genes[0] = new SudokuLineGene(geneDna);
		assertEquals(0, solution.getRowFitness(0));
		
		geneDna = new int[] { 1, 2, 3, 4, 5, 6, 7, 9, 9};
		genes[0] = new SudokuLineGene(geneDna);
		assertEquals(1, solution.getRowFitness(0));
		
		geneDna = new int[] { 1, 1, 3, 4, 5, 6, 7, 9, 9};
		genes[0] = new SudokuLineGene(geneDna);
		assertEquals(2, solution.getRowFitness(0));
		
		geneDna = new int[] { 1, 1, 1, 1, 5, 6, 7, 9, 9};
		genes[0] = new SudokuLineGene(geneDna);
		assertEquals(4, solution.getRowFitness(0));
	}

}
