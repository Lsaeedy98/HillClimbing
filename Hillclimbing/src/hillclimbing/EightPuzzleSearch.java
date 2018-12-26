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
		current.setF(ManhattanDistance(initial, goal));
		boolean foundSolution = false, deadEnd = false;
		int steps = 0;
		while (!foundSolution && !deadEnd) {
			steps++;
			current.makeNeighbors();
			EightPuzzleNode bestNode = current;
			deadEnd = true;
			for (EightPuzzleNode currentChild : current.getNeighbors()) {
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
                    //reconstructPath(current);
			return new int[]{steps, 1};
		}
		else if(deadEnd){
                    //reconstructPath(current);
			return new int[]{steps,0};
		}
		else{
		//should never happen
                    return new int[]{0,0};
		}
	}

	public int[] firstChoice(EightPuzzleNode initial, int[][] goal){
		EightPuzzleNode current = initial;
		current.setF(ManhattanDistance(initial, goal));
		boolean foundSolution = false, deadEnd = false;
		int steps = 0;
		while (!foundSolution && !deadEnd) {
			steps++;
			current.makeNeighbors();
			EightPuzzleNode bestNode = current;
			deadEnd = true;
			for (EightPuzzleNode currentChild : current.getNeighbors()) {
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
	
	/*public int[] randomRestart(EightPuzzleNode initial, int[][] goal){
            EightPuzzleNode current = initial;
            int nodes = 0, steps = 0;
            HashSet<String> added = new HashSet<String>(); 
            ArrayList<EightPuzzleNode> collection = new ArrayList<EightPuzzleNode>();
            //added.add(current.getKey());
            collection.add(current);
            
            Random random = new Random();
            int[] results = steepestAscent(current, goal);
            while(results[2] != 1){
			current.makeNeighbors();
			for(EightPuzzleNode currentChild: current.getNeighbors()){
                            if(!added.contains(currentChild.getKey())){
				added.add(currentChild.getKey());
				collection.add(currentChild);
                            }
			}
			current = collection.remove(random.nextInt(collection.size()));
                          while (!current.getNeighbors().isEmpty()) {
                               current.getNeighbors().removeFirst();
                            }
			results = steepestAscent(current, goal);
			steps += results[0];
			nodes += results[1];
            }
            reconstructPath(current);
            return new int[]{steps, nodes, 1};
	}*/
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
	public int[] simulatedAnneal(EightPuzzleNode initial,int[][] goal){
		EightPuzzleNode currentNode = initial;
		double temperature =30;
		double val = 0.5;
		double probability;
		int delta;
		double determine;
                
                int steps=0;
                EightPuzzleNode nextNode =new EightPuzzleNode(new int[0][0]);
		currentNode.setF(ManhattanDistance(currentNode, goal));
		while(currentNode.getF()!=0 && temperature > 0){
			//select a random neighbour from currentNode
			nextNode = currentNode.getRandomNeighbour();
			steps++;
                        nextNode.setF(ManhattanDistance(currentNode, goal));
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
