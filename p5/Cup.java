///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  MeasuringCupSolver.java
// File:             Cup.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen, chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////

/**
 * A representation of a measuring cup.
 */
public class Cup {

	private int capacity;
	private int currentAmount;

	/**
	 * Construct a measuring cup
	 * 
	 * @param capacity
	 *            the maximum volume of the measuring cup
	 * @param currentAmount
	 *            the current volume of fluid in the measuring cup
	 * @throws IllegalArgumentException
	 *             when any of these conditions are true: capacity < 0,
	 *             currentAmount < 0, currentAmount > capacity
	 * 
	 */
	public Cup(int capacity, int currentAmount) {
		if (capacity < 0 || currentAmount < 0 || capacity < currentAmount) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		this.currentAmount = currentAmount;
	}

	/**
	 * @return capacity
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * @return currentAmount
	 */
	public int getCurrentAmount() {
		return this.currentAmount;
	}

	/**
	 * Compare this cup against another cup
	 * 
	 * @param cup
	 *            an other cup to compare against this cup
	 * @return true if the other cup has the same capacity and currentAmount as
	 *         this cup and false otherwise
	 */
	public boolean equals(Cup cup) {
		if (this.getCapacity() == cup.getCapacity() &&
		 this.getCurrentAmount() == cup.getCurrentAmount()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return a string containing the currentAmount
	 */
	public String toString() {
		return String.valueOf(this.currentAmount);
	}
}
