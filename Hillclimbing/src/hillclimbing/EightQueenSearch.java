package hillclimbing;


/**
 *
 * @author Leyla
 */
public class EightQueenSearch {
    //returns an int array: number of steps made, and 1=solution or 0 for dead end
    public int[] steepestAscent(EightQueenNode initial) {
	EightQueenNode current = initial;
	current.setF(current.heuristic());                
	boolean foundSolution = false, deadEnd = false;
	int steps = 0;
	while (!foundSolution && !deadEnd) {
		steps++;
                if(steps>2000)
                    break;
                System.out.println(steps);
		EightQueenNode bestNode = current;
		deadEnd = true;
		EightQueenNode bestChild=current.getSteepestBestNeighbor();
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
            return new int[]{steps, 0};
	}
            else{                
            //should never happen
            return new int[]{0,0};
	}
    }
    
    public int[] firstChoice(EightQueenNode initial){
		EightQueenNode current = initial;
		current.setF(current.heuristic());
		boolean foundSolution = false, deadEnd = false;
		int steps = 0;
		while (!foundSolution && !deadEnd) {
                    steps++;
                    if(steps>2000)
                    break;
                    System.out.println(steps);
                    EightQueenNode bestNode = current;
                    deadEnd = true;
                    EightQueenNode firstChild=current.getFirstChoiceNeighbor();
                    if(firstChild!=null) {
                        bestNode=firstChild;
                        current = bestNode;
                        deadEnd = false;
                        if(bestNode.getF()==0){
                            foundSolution = true; 
                            break;
                        }
                    }
		}
		if(foundSolution){
                    return new int[]{steps,1};
		}
		else if(deadEnd){
                    return new int[]{steps,0};
		}
		else{
                    return new int[]{0,0};
		}
    }
    public int[] simulatedAnneal(EightQueenNode initial){
		EightQueenNode currentNode = initial;
		double temperature =30;
		double val = 0.5;
		double probability;
		int delta;
		double determine;
                int steps=0;
                EightQueenNode nextNode =new EightQueenNode();
		currentNode.setF(currentNode.heuristic());
		while(currentNode.getF()!=0 && temperature > 0){
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
