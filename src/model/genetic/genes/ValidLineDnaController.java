package model.genetic.genes;

import java.util.Random;

public class ValidLineDnaController implements DnaController {
	
	private Random random;

	public ValidLineDnaController() {
		random = new Random();
	}

	@Override
	public int[] initialize(int size) {
		int[] dna = new int[size];
		
		for (int i = 0; i < size; ++i)
			dna[i] = i + 1;
		
		for (int i = 0; i < size; ++i)
			swap(dna, random.nextInt(size), random.nextInt(size));
		
		return dna;
	}

	@Override
	public void mutate(int[] dna) {
		swap(dna, random.nextInt(dna.length), random.nextInt(dna.length));
		
	}
	
	private void swap(int[] dna, int x, int y) {
		int temp = dna[x];
		dna[x] = dna[y];
		dna[y] = temp;
	}

}
