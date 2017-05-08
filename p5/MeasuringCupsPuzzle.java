///////////////////////////////////////////////////////////////////////////////
//
// Main Class File:  MeasuringCupSolver.java
// File:             MeasuringCupPuzzle.java
// Semester:         CS367, Fall 2016
//
// Author:           Yahn-Chung Chen, chen666@wisc.edu
// CS Login:         yahn-chung
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;

/**
 * A class describing the measuring cups puzzle with a startState
 * {@link MeasuringCupsPuzzleState} and a goalState
 * {@link MeasuringCupsPuzzleState}
 */
public class MeasuringCupsPuzzle {

	private MeasuringCupsPuzzleState startState;
	private MeasuringCupsPuzzleState goalState;

	private MeasuringCupsPuzzleADT measuringCupsPuzzleADT;

	private MeasuringCupsPuzzleStateList pathFromStartToGoal;
	private MeasuringCupsPuzzleStateList processedStates;
	private MeasuringCupsPuzzleState foundGoalState;

	/**
	 * Construct a puzzle object by describing the startState and goalState
	 *
	 * @param startState
	 *            a state describing the capacities and initial volumes of
	 *            measuring cups {@link MeasuringCupsPuzzleState}
	 * @param goalState
	 *            a state describing the desired end volumes of measuring cups
	 *            {@link MeasuringCupsPuzzleState}
	 */
	public MeasuringCupsPuzzle(MeasuringCupsPuzzleState startState, MeasuringCupsPuzzleState goalState) {
		this.startState = startState;
		this.goalState = goalState;

		this.pathFromStartToGoal = new MeasuringCupsPuzzleStateList();
		this.processedStates = new MeasuringCupsPuzzleStateList();
		this.foundGoalState = null;
		this.measuringCupsPuzzleADT = null;
	}

	/**
	 * Solve the measuring cups puzzle if it can be solved. Set processedStates
	 * by adding a {@link MeasuringCupsPuzzleState} graph node to the list as
	 * the algorithm visits that node. Set foundGoalState to a
	 * {@link MeasuringCupsPuzzleState} if the graph traversal algorithm labeled
	 * by *algorithm* visits a node with the same values as the desired
	 * goalState
	 *
	 * @param algorithm
	 *            a String describing how the puzzle will be solved; has a value
	 *            equal to the project configuration {@link Config} BFS or DFS;
	 *            e.g. "BFS"
	 * @return true if the puzzle can be solved (and has been solved, see
	 *         {@link retrievePath} to obtain the solution stored in this
	 *         object) and false otherwise
	 */
	public boolean findPathIfExists(String algorithm) {
		resetCupPuzzle();
		// Choose DFS or BFS
		chooseADT(algorithm);	
		measuringCupsPuzzleADT.add(startState);
		while (!measuringCupsPuzzleADT.isEmpty()) {
			MeasuringCupsPuzzleState c = measuringCupsPuzzleADT.remove();
			//Mark c as visited
			processedStates.add(c);
			//Add unvisited successor into Queue/Stack
			for (MeasuringCupsPuzzleState ms : getSuccessors(c)) {
				if (ms.equals(goalState)) {
					foundGoalState = ms;
					return true;
				}
				if (!isProcessed(ms)) {					
					measuringCupsPuzzleADT.add(ms);
				}
			}
		}
		return false;
	}

	/**
	 * Set member measuringCupsPuzzleADT {@link MeasuringCupsPuzzleADT} with a
	 * data type that will be used to solve the puzzle.
	 *
	 * @param algorithm
	 *            a String describing how the puzzle will be solved; has a value
	 *            equal to the project configuration {@link Config} BFS or DFS;
	 *            e.g. "BFS"
	 */
	private void chooseADT(String algorithm) {
		if (algorithm.equals("BFS")) {
			measuringCupsPuzzleADT = new MeasuringCupsPuzzleQueue();
		} else if (algorithm.equals("DFS")) {
			measuringCupsPuzzleADT = new MeasuringCupsPuzzleStack();
		}
	}

	/**
	 * Reset the puzzle by erasing all member variables which store some aspect
	 * of the solution (pathFromStartToGoal, processedStates, and
	 * foundGoalState) and setting them to their initial values
	 */
	private void resetCupPuzzle() {
		pathFromStartToGoal.clear();
		processedStates.clear();
		foundGoalState = null;
	}

	/**
	 * Mark the graph node represented by currentState as visited for the
	 * purpose of the graph traversal algorithm being used to solve the puzzle
	 * (set by {@link chooseADT})
	 *
	 * @param currentState
	 *            {@link MeasuringCupsPuzzleState}
	 * @return true if the currentState has been visited and false otherwise
	 */
	private boolean isProcessed(MeasuringCupsPuzzleState currentState) {
		for (MeasuringCupsPuzzleState ms : processedStates) {
			if (currentState.equals(ms)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Assuming {@link findPathIfExists} returns true, return the solution that
	 * was found. Set pathFromStartToGoal by starting at the foundGoalState and
	 * accessing/setting the current node to the parentState
	 * {@link MeasuringCupsPuzzleState#getParentState} until reaching the
	 * startState
	 *
	 * @return a list of states {@link MeasuringCupsPuzzleStateList}
	 *         representing the changes in volume of cupA and cupB from the
	 *         initial state to the goal state.
	 */
	public MeasuringCupsPuzzleStateList retrievePath() {
		pathFromStartToGoal.add(foundGoalState);
		//Keop adding parents until reaching the starting point
		while (null != foundGoalState.getParentState()) {		
			pathFromStartToGoal.add(foundGoalState.getParentState());
			foundGoalState = foundGoalState.getParentState();
		}
		pathFromStartToGoal.reverse();
		return pathFromStartToGoal;
	}

	/**
	 * Enumerate all possible states that can be reached from the currentState
	 *
	 * @param currentState
	 *            the current volumes of cupA and cupB
	 * @return a list of states {@link MeasuringCupsPuzzleStateList} that can be
	 *         reached by emptying cupA or cupB, pouring from cupA to cupB and
	 *         vice versa, and filling cupA or cupB to its max capacity
	 */
	public MeasuringCupsPuzzleStateList getSuccessors(MeasuringCupsPuzzleState currentState) {
		MeasuringCupsPuzzleStateList successors = new MeasuringCupsPuzzleStateList();
		MeasuringCupsPuzzleStateList cycle = new MeasuringCupsPuzzleStateList();
		if (currentState == null) {
			return successors;
		}
		// Fill CupA
		successors.add(this.fillCupA(currentState));
		// Fill CupB
		successors.add(this.fillCupB(currentState));		
		// Empty CupA
		successors.add(this.emptyCupA(currentState));
		// Empty CupB
		successors.add(this.emptyCupB(currentState));
		// Pour from CupA to CupB
		successors.add(this.pourCupAToCupB(currentState));
		// Pour from CupB to CupA
		successors.add(this.pourCupBToCupA(currentState));
		// remove successors if same as currentState
		Iterator<MeasuringCupsPuzzleState> itr = successors.iterator();
		// Use a list to record duplicate and remove it later
		while (itr.hasNext()) {
			MeasuringCupsPuzzleState ms = itr.next();
			if ( ms.equals(currentState)) {
				cycle.add(ms);
			}
		}
		for (MeasuringCupsPuzzleState ms2 : cycle) {
			if (successors.contains(ms2)) {
				successors.remove(ms2);
			}
		}
		return successors;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by filling cupA to its max
	 *         capacity
	 */
	public MeasuringCupsPuzzleState fillCupA(MeasuringCupsPuzzleState currentState) {
		Cup newCup = new Cup(currentState.getCupA().getCapacity(), currentState.getCupA().getCapacity());
		return new MeasuringCupsPuzzleState(newCup, currentState.getCupB(), currentState);
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by filling cupB to its max
	 *         capacity
	 */
	public MeasuringCupsPuzzleState fillCupB(MeasuringCupsPuzzleState currentState) {
		Cup newCup = new Cup(currentState.getCupB().getCapacity(), currentState.getCupB().getCapacity());
		return new MeasuringCupsPuzzleState(currentState.getCupA(), newCup, currentState);
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by emptying cupA
	 */
	public MeasuringCupsPuzzleState emptyCupA(MeasuringCupsPuzzleState currentState) {
		Cup newCup = new Cup(currentState.getCupA().getCapacity(), 0);
		return new MeasuringCupsPuzzleState(newCup, currentState.getCupB(), currentState);
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by emptying cupB
	 */
	public MeasuringCupsPuzzleState emptyCupB(MeasuringCupsPuzzleState currentState) {
		Cup newCup = new Cup(currentState.getCupB().getCapacity(), 0);
		return new MeasuringCupsPuzzleState(currentState.getCupA(), newCup, currentState);
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState pouring the currentAmount
	 *         of cupA into cupB until either cupA is empty or cupB is full
	 */
	public MeasuringCupsPuzzleState pourCupAToCupB(MeasuringCupsPuzzleState currentState) {
		Cup newCupA;
		Cup newCupB;
		// create two cups to meet two different situations
		int sum = currentState.getCupA().getCurrentAmount() + currentState.getCupB().getCurrentAmount();
		// (1)A still have some, B is full (2) A is empty, B is full(or not)
		if (sum >= currentState.getCupB().getCapacity()) {
			newCupA = new Cup(currentState.getCupA().getCapacity(), sum - currentState.getCupB().getCapacity());
			newCupB = new Cup(currentState.getCupB().getCapacity(), currentState.getCupB().getCapacity());

		} else {
			newCupA = new Cup(currentState.getCupA().getCapacity(), 0);
			newCupB = new Cup(currentState.getCupB().getCapacity(), sum);
		};
		return new MeasuringCupsPuzzleState(newCupA, newCupB, currentState);
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState pouring the currentAmount
	 *         of cupB into cupA until either cupB is empty or cupA is full
	 */
	public MeasuringCupsPuzzleState pourCupBToCupA(MeasuringCupsPuzzleState currentState) {
		Cup newCupA;
		Cup newCupB;
		// create two cups to meet two different situations
		int sum = currentState.getCupA().getCurrentAmount() + currentState.getCupB().getCurrentAmount();
		// (1)A is full, B still have some (2) A is full(or not), B is empty
		if (sum >= currentState.getCupA().getCapacity()) {
			newCupA = new Cup(currentState.getCupA().getCapacity(), currentState.getCupA().getCapacity());
			newCupB = new Cup(currentState.getCupB().getCapacity(), sum - currentState.getCupA().getCapacity());

		} else {
			newCupA = new Cup(currentState.getCupA().getCapacity(), sum);
			newCupB = new Cup(currentState.getCupB().getCapacity(), 0);
		};
		return new MeasuringCupsPuzzleState(newCupA, newCupB, currentState);
	}
}
