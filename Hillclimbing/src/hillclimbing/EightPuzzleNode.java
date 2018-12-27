package hillclimbing;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import sun.font.EAttribute;

/**
 *
 * @author Leyla
 */
public class EightPuzzleNode {
    private int[][] numbers;
    private int f;
    //we keep i and j of the blank tile or zero
    private int  blankRow;
    private int  blankColumn;

    public EightPuzzleNode(int[][] nums) {
        numbers = nums;
        setBlank();
    }
        public void printNode() {
	for (int i = 0; i < numbers.length; ++i) {
		System.out.print("{ ");
                for (int j = 0; j < numbers[i].length; ++j) {
			System.out.print(numbers[i][j] + " ");
		}
		System.out.print("}\n");
	}
	System.out.println();
    }
        
    public int[][] getNums(){
       //make a copy and return it
        int[][] nums = new int[numbers.length][];
	for(int i = 0; i < nums.length; ++i){
		nums[i] = Arrays.copyOf(numbers[i], numbers[i].length);
	}
	return nums;
      //return numbers;
    }
    public void setBlank(){
        boolean notFound=true;
        for(int i=0; i<numbers.length && notFound; ++i){
            for(int j=0; j<numbers[i].length && notFound ; ++j){
                if(numbers[i][j] == 0){
                    blankRow=i;
                    blankColumn=j;
                    notFound =false;
                }
            }
        }
    }
    
    public String getKey() {
        return Arrays.deepToString(getNums());
    }
    public void setF(int F) {
        this.f = F;
    }

    public int getF() {
        return f;
    }
    public ArrayList<EightPuzzleNode> getNeighbors(){
	//make sure the moves in each direction are validand if it is add a new child node  
            ArrayList<EightPuzzleNode> children=new ArrayList<>();
            //check move up
		if(blankRow-1 >= 0 ){
			int[][] tempVals =  getNums();
			tempVals[blankRow][blankColumn] = tempVals[blankRow - 1][blankColumn];
			tempVals[blankRow - 1][blankColumn] = 0;
                        EightPuzzleNode child=new EightPuzzleNode(tempVals);
                        child.setF(child.ManhattanDistance());
			children.add(child);
		}
		//check move down
		if(blankRow+1 < numbers.length){
			int[][] tempVals = getNums();
			tempVals[blankRow][blankColumn] = tempVals[blankRow+1][blankColumn]; 
			tempVals[blankRow + 1][blankColumn] = 0;
			EightPuzzleNode child=new EightPuzzleNode(tempVals);
                        child.setF(child.ManhattanDistance());
			children.add(child);
		}
		//check move to the right
		if(blankColumn+1 < numbers[blankRow].length ){
			int[][] tempVals = getNums();
			tempVals[blankRow][blankColumn] = tempVals[blankRow][blankColumn+1];
			tempVals[blankRow][blankColumn + 1] = 0;
			EightPuzzleNode child=new EightPuzzleNode(tempVals);
                        child.setF(child.ManhattanDistance());
			children.add(child);

		}
		//check move to the left
		if(blankColumn-1 >= 0){
			int[][] tempVals =  getNums();
			tempVals[blankRow][blankColumn] = tempVals[blankRow][blankColumn - 1];
			tempVals[blankRow][blankColumn - 1] = 0;
			EightPuzzleNode child=new EightPuzzleNode(tempVals);
                        child.setF(child.ManhattanDistance());
			children.add(child);
		}
                return children;
    }
    public EightPuzzleNode getSteepestBestNeighbor(){ 
        EightPuzzleNode best=null;
        int bestf=getF();
        for(EightPuzzleNode currentChild : getNeighbors()){
            if(currentChild.getF()<=bestf){
                        best=currentChild;
                        bestf=currentChild.getF();
                        currentChild.printNode();
                    }
        }
        return best;
    }
    public int ManhattanDistance(/*, int[][] goal*/) {
        int[][] goal = new int[][] { 
            { 1, 2, 3 },
            { 8, 0, 4 },
            { 7, 6, 5 } }; 
        int h = 0;
		int[][] initialValues = new int[0][0];
		initialValues = numbers;
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
    public EightPuzzleNode getRandomNeighbour(){
                        Random random=new Random();
                        EightPuzzleNode current = getNeighbors().get(random.nextInt(getNeighbors().size()));
                        return current;
    }
    

}
