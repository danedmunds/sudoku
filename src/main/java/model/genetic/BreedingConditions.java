package model.genetic;

import java.util.Random;


public class BreedingConditions {

	private Random random;
	private int childrenCount;
	private double crossoverProbability;
	private double mutationProbability;

	public BreedingConditions() {
		random = new Random();
	}
	
	public int getChildrenCount() {
		return childrenCount;
	}
	
	public boolean shouldCrossover() {
		return random.nextDouble() <= getCrossoverProbability();
	}
	
	public boolean shouldMutate() {
		return random.nextDouble() <= getMutationProbability();
	}

	public double getCrossoverProbability() {
		return crossoverProbability;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}
	
	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
	

}
