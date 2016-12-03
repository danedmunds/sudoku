package model.genetic;

import java.util.ArrayList;
import java.util.List;

import model.SudokuInterface;
import model.genetic.genes.SudokuLineGene;

public class SudokuSolution implements SudokuInterface {
	
	private SudokuLineGene[] genes;
	
	public SudokuSolution() {
		genes = new SudokuLineGene[9];
		for (int i = 0; i < genes.length; ++i)
			genes[i] = new SudokuLineGene();
	}
	
	public SudokuSolution(SudokuLineGene[] genes) {
		assert(genes.length == 9);
		this.genes = genes;
	}
	
	public SudokuSolution copy() {
		SudokuLineGene[] copyGenes = new SudokuLineGene[genes.length];
		for (int i = 0; i < copyGenes.length; ++i)
			copyGenes[i] = genes[i].copy();
		
		return new SudokuSolution(copyGenes);
	}
	
	public List<SudokuSolution> breed(BreedingConditions conditions, SudokuSolution mate) {
		assert(genes.length == mate.genes.length);
		
		List<SudokuSolution> offspring = new ArrayList<SudokuSolution>(conditions.getChildrenCount());
		
		for (int i = 0; i < conditions.getChildrenCount(); ++i) {
			SudokuSolution child = copy();
			for (int x = 0; x < genes.length; ++x) {
				if (conditions.shouldCrossover())
					child.setGeneAt(mate.getGene(x).copy(), x);
				
				if (conditions.shouldMutate())
					child.getGene(x).mutate();
			}
			
			offspring.add(child);
		}
		
		return offspring;
	}

	private SudokuLineGene getGene(int i) {
		assert(i < genes.length);
		return genes[i];
	}
	
	private void setGeneAt(SudokuLineGene gene, int index) {
		assert(index < genes.length);
		genes[index] = gene;
	}

	@Override
	public int getValueAt(int x, int y) {
		return genes[y].getValueAt(x);
	}

	@Override
	public void setValueAt(int x, int y, int val) {
		System.out.println("Not implemented yet");
	}

	@Override
	public boolean isCellValid(int x, int y) {
		return (getRowFitness(y) + getColumnFitness(x) + getSquareFitness(x, y)) == 0;
	}
	
	public int getFitness() {
		int fitness = 0;
		
		for(int i = 0; i < genes.length; ++i) {
			fitness += getRowFitness(i);
			fitness += getColumnFitness(i);
		}
		
		int boxSize = (int) Math.sqrt(genes.length);
		for (int x = 0; x < genes.length; x += boxSize)
			for (int y = 0; y < genes.length; y += boxSize)
				fitness += getSquareFitness(x, y);
		
		return fitness;
	}
	
	protected int getRowFitness(int rowIndex) {
		assert(rowIndex < genes.length);
		
		SudokuLineGene row = genes[rowIndex];
		FitnessCounter counter = new FitnessCounter(row.getLength());
		
		for (int i = 0; i < row.getLength(); ++i)
			counter.occurred(row.getValueAt(i));
		
		return counter.getFitness();
	}
	
	protected int getColumnFitness(int column) {
		assert(column < genes.length);
		
		FitnessCounter counter = new FitnessCounter(genes.length);
		for(SudokuLineGene gene : genes)
			counter.occurred(gene.getValueAt(column));
		
		return counter.getFitness();
	}
	
	protected int getSquareFitness(int x, int y) {
		int boxSize = (int) Math.sqrt(genes.length);
		int xstart = (x / boxSize) * boxSize;
		int ystart = (y / boxSize) * boxSize;
		
		FitnessCounter counter = new FitnessCounter(genes.length);
		for(int i=xstart; i<(xstart+3); ++i)
			for(int j=ystart; j<(ystart+3); ++j)
				counter.occurred(getValueAt(i, j));
		
		return counter.getFitness();
	}
	
	public int getGeneCount() {
		return genes.length;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SudokuSolution))
			return false;
		
		SudokuSolution other = (SudokuSolution) obj;
		for (int i = 0; i < getGeneCount(); ++i)
			if (!getGene(i).equals(other.getGene(i)))
				return false;
		
		return true;
	}
}
