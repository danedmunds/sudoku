package model;

import java.util.HashSet;
import java.util.Set;

public class Block{
	
	private int value = -1;
	private Set<Integer> possibilities = new HashSet<Integer>();
	
	public Block(){
		for (int i = 1; i < 10; ++i)
			possibilities.add(i);
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Return whether removing this value causes this Block to be assigned a value
	 * @param value
	 * @return
	 */
	public boolean removePossibility(int possibility) {
		if (value != -1)
			return false;
		
		possibilities.remove(possibility);
		assert(!possibilities.isEmpty());
		
		if (possibilities.size() == 1)
			value = possibilities.iterator().next();
		
		return true;
	}
}
