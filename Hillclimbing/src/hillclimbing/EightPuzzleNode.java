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
    private EightPuzzleNode parent;
    private LinkedList<EightPuzzleNode> neighbors;

    public EightPuzzleNode(int[][] nums) {
        numbers = nums;
	neighbors = new LinkedList<EightPuzzleNode>();
        setBlank();
	parent = null;
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
    
    public EightPuzzleNode getParent() {
        return parent;
    }

    public void setParent(EightPuzzleNode Parent) {
        this.parent = Parent;
    }

    public LinkedList<EightPuzzleNode> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(LinkedList<EightPuzzleNode> Neighbors) {
        this.neighbors = Neighbors;
    }
    
    public void makeNeighbors(){
	//make sure the moves in each direction are validand if it is add a new child node  
            //check move up
		if(blankRow-1 >= 0 ){
			int[][] tempVals =  getNums();
			tempVals[blankRow][blankColumn] = getNums()[blankRow - 1][blankColumn];
			tempVals[blankRow - 1][blankColumn] = 0;
                        EightPuzzleNode child=new EightPuzzleNode(tempVals);
                        child.setParent(this);
			neighbors.add(child);
		}
		//check move down
		if(blankRow+1 < numbers.length){
			int[][] tempVals = getNums();
			tempVals[blankRow][blankColumn] = getNums()[blankRow+1][blankColumn]; 
			tempVals[blankRow + 1][blankColumn] = 0;
			EightPuzzleNode child=new EightPuzzleNode(tempVals);
                        child.setParent(this);
			neighbors.add(child);

		}
		//check move to the right
		if(blankColumn+1 < numbers[blankRow].length ){
			int[][] tempVals = getNums();
			tempVals[blankRow][blankColumn] = getNums()[blankRow][blankColumn+1];
			tempVals[blankRow][blankColumn + 1] = 0;
			EightPuzzleNode child=new EightPuzzleNode(tempVals);
                        child.setParent(this);
			neighbors.add(child);

		}
		//check move to the left
		if(blankColumn-1 >= 0){
			int[][] tempVals =  getNums();
			tempVals[blankRow][blankColumn] = getNums()[blankRow][blankColumn - 1];
			tempVals[blankRow][blankColumn - 1] = 0;
			EightPuzzleNode child=new EightPuzzleNode(tempVals);
                        child.setParent(this);
			neighbors.add(child);

		}
    }
    public EightPuzzleNode getRandomNeighbour(){
			makeNeighbors();
                        Random random=new Random();
                        LinkedList<EightPuzzleNode> collection =getNeighbors();
                        EightPuzzleNode current = collection.remove(random.nextInt(collection.size()));
                        return current;
    }
    public static EightPuzzleNode makeRandomNode(int[][]goal){
        EightPuzzleNode temp=new EightPuzzleNode(goal);
        
        for(int i=0;i<100;i++){
            temp=temp.getRandomNeighbour();
        }
        return temp;
    }

}
