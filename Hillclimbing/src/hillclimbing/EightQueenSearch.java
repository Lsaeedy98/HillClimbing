package hillclimbing;


/**
 *
 * @author Leyla
 */
public class EightQueenSearch {
    //returns an int array: number of steps made, and 1=solution or 0 for dead end
    public int[] steepestAscent(EightQueenNode initial, int[][] goal) {
	EightQueenNode current = initial;
	current.setF(heuristic(initial));                
	boolean foundSolution = false, deadEnd = false;
	int steps = 0;
	while (!foundSolution && !deadEnd) {
		steps++;
		current.makeNeighbours();
		EightQueenNode bestNode = current;
		deadEnd = true;
		for (EightQueenNode currentChild : current.getNeighbours()) {
			currentChild.setF(heuristic(currentChild));
			if (currentChild.getF()<= bestNode.getF()) {
				//found better node,reset deadEnd
                                bestNode = currentChild;
				deadEnd = false;
				bestNode.setParent(current);                                        
                                if(currentChild.getF()==0){
                                    foundSolution = true; 
                                    break; 
                                }
                                break;
			}
		}
		current = bestNode;
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
		current.setF(heuristic(initial));
		boolean foundSolution = false, deadEnd = false;
		int steps = 0, nodes = 0;
		while (!foundSolution && !deadEnd) {
			steps++;
			current.makeNeighbours();
			EightQueenNode bestNode = current;
			deadEnd = true;
			for (EightQueenNode currentChild : current.getNeighbours()) {
				nodes++;
				currentChild.setF(heuristic(currentChild));
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
                    return new int[]{steps, nodes, 1};
		}
		else if(deadEnd){
                    return new int[]{steps, nodes, 0};
		}
		else{
                    return new int[]{0,0,0};
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
		currentNode.setF(heuristic(currentNode));
		while(currentNode.getF()!=0 && temperature > 0){
			//select a random neighbour from currentNode
			nextNode = currentNode.getRandomNeighbour();
			steps++;
                        nextNode.setF(heuristic(currentNode));
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
    private void reconstructPath(EightQueenNode current) {
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
	
    
    public boolean canAttack(int row1,int column1,int row2,int column2){
	boolean canAttack=false;
        if(row1==row2 || column1==column2)
            canAttack=true;
	//test diagonal attacking
	else if(Math.abs(column1-column2) == Math.abs(row1-row2))
		canAttack=true;
			
	return canAttack;
    }    
    public int heuristic(EightQueenNode q){
        int h=0;
        for(int i=0; i< (q.getN()-1); i++){
		for(int j=i+1; j<q.getN(); j++){
			//if state i can attack state j which are queens at columns i and j
                        if(canAttack(q.getRowsState()[i],i,q.getRowsState()[j],j)){
                            h++;
                        }
		}
	}	
            return h;
    }
}
