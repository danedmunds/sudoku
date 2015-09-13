package model.genetic.genes;

import java.util.Random;

public class RandomDnaController implements DnaController {

	private Random random;
	
	public RandomDnaController() {
		random = new Random();
	}
	
	@Override
	public int[] initialize(int size) {
		int[] dna =  new int[size];
		for (int i = 0; i < size; ++i)
			dna[i] = random.nextInt(size) + 1;
		
		return dna;
	}

	@Override
	public void mutate(int[] dna) {
		dna[random.nextInt(dna.length)] = random.nextInt(dna.length) + 1;
	}

}
