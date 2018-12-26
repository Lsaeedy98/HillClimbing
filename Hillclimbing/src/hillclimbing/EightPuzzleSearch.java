package hillclimbing;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


/**
 *
 * @author Leyla
 */
public class EightPuzzleSearch {
	//returns an int array that contains number of nodes generated, number of steps made, and 1 for solution or 0 for dead end
	public int[] steepestAscent(EightPuzzleNode initial, int[][] goal) {
                EightPuzzleNode current = initial;
		current.setF(ManhattanDistance(initial, goal));
		boolean foundSolution = false, deadEnd = false;
		int steps = 0, nodes = 0;
		while (!foundSolution && !deadEnd) {
			steps++;
			current.makeNeighbors();
			EightPuzzleNode bestNode = current;
			deadEnd = true;
			for (EightPuzzleNode currentChild : current.getNeighbors()) {
				nodes++;
				currentChild.setF(ManhattanDistance(currentChild, goal));
				if (currentChild.getF()<= bestNode.getF()) {
					//found better node,reset deadEnd
                                        bestNode = currentChild;
					deadEnd = false;
					bestNode.setParent(current);
                                        if(currentChild.getF()==0){
                                            foundSolution = true; 
                                            break; 
                                        }
				}
			}
			current = bestNode;
		}
		if(foundSolution){
                    //System.out.println("found solution");
                    //reconstructPath(current);
			return new int[]{steps, nodes, 1};
		}
		else if(deadEnd){
                    //System.out.println("local extermom");
                    //reconstructPath(current);
			return new int[]{steps, nodes, 0};
		}
		else{
		//should never happen
                    return new int[]{0,0,0};
		}
	}

	public int[] firstChoice(EightPuzzleNode initial, int[][] goal){
		EightPuzzleNode current = initial;
		current.setF(ManhattanDistance(initial, goal));
		boolean foundSolution = false, deadEnd = false;
		int steps = 0, nodes = 0;
		while (!foundSolution && !deadEnd) {
			steps++;
			current.makeNeighbors();
			EightPuzzleNode bestNode = current;
			deadEnd = true;
			for (EightPuzzleNode currentChild : current.getNeighbors()) {
				nodes++;
				currentChild.setF(ManhattanDistance(currentChild, goal));
				if (currentChild.getF()<bestNode.getF()){
					bestNode = currentChild;
					deadEnd = false;
					bestNode.setParent(current);
                                        if(currentChild.getF()==0){
                                            foundSolution=true;
                                        }
                                        //dont continue to make other children 
                                        break;
				}
			}
			current = bestNode;
		}
		if(foundSolution){
                    //reconstructPath(current);
                    return new int[]{steps, nodes, 1};
		}
		else if(deadEnd){
                    //reconstructPath(current);
                    return new int[]{steps, nodes, 0};
		}
		else{
			return new int[]{0,0,0};
		}
		
	}
	public void reconstructPath(EightPuzzleNode current) {
		int moves = 0;
                //to determine where to stop printing the parent
                boolean flg=true;
		while (flg) {
			if(current.getParent() == null)
                            flg=false;
                        moves++;
                        System.out.print(moves);
                        current.printNode();
			current = current.getParent();
		}                
	}
	public EightPuzzleNode simulatedAnneal(EightPuzzleNode initial,int[][] goal){
		EightPuzzleNode currentNode = initial;
		double temperature =30;
		double val = 0.5;
		double probability;
		int delta;
		double determine;
                
                int nodes=0;
                EightPuzzleNode nextNode =new EightPuzzleNode(new int[0][0]);
		currentNode.setF(ManhattanDistance(currentNode, goal));
		while(currentNode.getF()!=0 && temperature > 0){
			//select a random neighbour from currentNode
			nextNode = currentNode.getRandomNeighbour();
			nodes++;
                        nextNode.setF(ManhattanDistance(currentNode, goal));
			if(nextNode.getF()==0)
				return nextNode;
			
			delta = currentNode.getF() - nextNode.getF();
			if(delta > 0){
				currentNode = nextNode;
			}else{ 
				probability = Math.exp(delta/temperature);
				determine = Math.random();
				
				if(determine <= probability){ //choose nextNode
					currentNode = nextNode;
				}
			}
			temperature = temperature - val;
		}
		return currentNode;
	}
	public int ManhattanDistance(EightPuzzleNode initial, int[][] goal) {
		int h = 0;
		int[][] initialValues = new int[0][0];
		initialValues = initial.getNums();
		for (int i = 0; i < initialValues.length; ++i) {
			for (int j = 0; j < initialValues[i].length; ++j) {
				if (initialValues[i][j] != goal[i][j] /*&& initialValues[i][j] != 0*/) {
					int[] goalPosition = getGoalPositionForVal(initialValues[i][j], goal);
					if (goalPosition != new int[] { -1, -1 }) {
						h += (Math.abs(i - goalPosition[0]) + Math.abs(j - goalPosition[1]));// distance
								} else {
						System.out.println("error in input values");
						break;
					}
				}
			}

		}
		return h;
	}
	public int[] getGoalPositionForVal(int val, int[][] goal) {
                for (int i = 0; i < goal.length; ++i) {
			for (int j = 0; j < goal[i].length; ++j) {
				if (goal[i][j] == val) {
					return new int[] { i, j };
				}
			}
		}
                return new int[] { -1, -1 };
	}
}
