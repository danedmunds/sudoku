package model.genetic.genes;

import java.util.Arrays;

public class SudokuLineGene {
	
	private int[] dna;
	private DnaController controller = new ValidLineDnaController();
	
	public SudokuLineGene() {
		dna = controller.initialize(9);
	}

	public SudokuLineGene(int[] dna) {
		this.dna = dna;
	}
	
	public SudokuLineGene copy() {
		return new SudokuLineGene(Arrays.copyOf(dna, dna.length));
	}
	
	public void mutate() {
		controller.mutate(dna);
	}
	
	public int getValueAt(int i) {
		return dna[i];
	}

	public int getLength() {
		return dna.length;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SudokuLineGene))
			return false;
		
		SudokuLineGene other = (SudokuLineGene) obj;
		for (int i = 0; i < getLength(); ++i)
			if (getValueAt(i) != other.getValueAt(i))
				return false;
		
		return true;
	}
}
