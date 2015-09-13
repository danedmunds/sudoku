package model.genetic;

public class FitnessCounter {
	
	private int[] counters;
	
	public FitnessCounter(int maxValue) {
		counters = new int[maxValue];
	}
	
	public void occurred(int value) {
		assert(value < counters.length);
		counters[value - 1]++;
	}
	
	public int getFitness() {
		int fitness = 0;
		for (int i = 0; i < counters.length; ++i)
			if (counters[i] > 1)
				fitness += counters[i] - 1;
		
		return fitness;
	}

}
