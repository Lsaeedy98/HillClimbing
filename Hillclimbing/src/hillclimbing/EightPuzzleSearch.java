package hillclimbing;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


/**
 *
 * @author Leyla
 */
public class EightPuzzleSearch {
	//returns an int array that contains number of steps made, and 1 for solution or 0 for dead end
	public int[] steepestAscent(EightPuzzleNode initial, int[][] goal) {
                EightPuzzleNode current = initial;
		current.setF(current.ManhattanDistance());
		boolean foundSolution = false, deadEnd = false;
		int steps = 0;
		while (!foundSolution && !deadEnd) {
			steps++;
                        if(steps>2000)
                            break;
			EightPuzzleNode bestNode = current;
			deadEnd = true;
			EightPuzzleNode bestChild=current.getSteepestBestNeighbor();
                        if(bestChild!=null) {
                            bestNode=bestChild;
                            current = bestNode;
                            deadEnd = false;
                                if(bestNode.getF()==0){
                                    foundSolution = true; 
                                    break; 
                                }
			}
			
		}
		if(foundSolution){
			return new int[]{steps, 1};
		}
		else if(deadEnd){
                    return new int[]{steps,0};
		}
		else{
		//should never happen
                    return new int[]{0,0};
		}
	}

	public int[] firstChoice(EightPuzzleNode initial, int[][] goal){
		EightPuzzleNode current = initial;
		current.setF(current.ManhattanDistance());
		boolean foundSolution = false, deadEnd = false;
		int steps = 0;
		while (!foundSolution && !deadEnd) {
			steps++;
			EightPuzzleNode bestNode = current;
			deadEnd = true;
			for (EightPuzzleNode currentChild : current.getNeighbors()) {
				if (currentChild.getF()<bestNode.getF()){
					bestNode = currentChild;
					deadEnd = false;
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
                    return new int[]{steps, 1};
		}
		else if(deadEnd){
                    //reconstructPath(current);
                    return new int[]{steps, 0};
		}
		else{
			return new int[]{0,0};
		}
		
	}
	public int[] simulatedAnneal(EightPuzzleNode initial,int[][] goal){
		EightPuzzleNode currentNode = initial;
		double temperature =30;
		double val = 0.5;
		double probability;
		int delta;
		double determine;
                
                int steps=0;
                EightPuzzleNode nextNode =new EightPuzzleNode(new int[0][0]);
		currentNode.setF(currentNode.ManhattanDistance());
		while(currentNode.getF()!=0 && temperature > 0){
			//select a random neighbour from currentNode
			nextNode = currentNode.getRandomNeighbour();
			steps++;
			if(nextNode.getF()==0){
                                return new int[]{steps,1};
                        }
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
                return new int[]{steps,0};
	}
	
}
