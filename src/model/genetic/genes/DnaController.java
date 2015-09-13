package model.genetic.genes;

public interface DnaController {
	
	public int[] initialize(int size);
	
	public void mutate(int[] dna);

}
