package hillclimbing;


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Leyla
 */

public class EightQueenNode {
    //int row is state[i],int column is index i which doesnt change for each queen
    private int[] rowsState;
    private final int N=8;
    //private ArrayList<EightQueenNode> neighbours;
    private int f;
    //private EightQueenNode parent;
    
    public EightQueenNode(){
	rowsState = new int[N]; //empty state
	//neighbours = new ArrayList<EightQueenNode>();
        f=0;
    }
    public EightQueenNode(EightQueenNode q){
        rowsState = new int[N];
	//neighbours = new ArrayList<EightQueenNode>();
	for(int i=0; i<N; i++)
            rowsState[i] = q.getRowsState()[i];
	f=0;
    }
    
    public void setF(int F) {
        this.f = F;
    }

    public int getF() {
        return f;
    }
/*
    public void setParent(EightQueenNode Parent) {
        this.parent = parent;
    }

    public EightQueenNode getParent() {
        return parent;
    }
*/
    public int getN() {
        return N;
    }
   
    public int[] getRowsState() {
         //make a copy and return it
        int[] rows = new int[N];
	for(int i = 0; i < rows.length; ++i){
		rows[i] = rowsState[i];
	}
	return rows;
    }
    
    public void setRowsState(int[] newRowsState) {
        this.rowsState = newRowsState;
    }

    @Override
	public String toString(){
		String result="";
		String[][] board = new String[N][N];
		
		//show X's to indicate empty spaces
		for(int i=0; i<N; i++)
			for(int j=0; j<N; j++)
				board[i][j]="X ";
		
		//place the queens on the board
		for(int i=0; i<N; i++){
			board[rowsState[i]][i]="Q ";
		}
		
		//feed values into the result string
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				result+=board[i][j];
			}
			result+="\n";
		}
		return result;
	}
    public void printNode(){
        System.out.println(this.toString());
    }
    public ArrayList<EightQueenNode> getNeighbours(){
        ArrayList<EightQueenNode> neighbours=new ArrayList<>();
	for(int i=0; i<N; i++){
            for(int j=1; j<N; j++){
                EightQueenNode neighbour=new EightQueenNode(this);
		neighbour.moveDown(j,i);
                neighbour.printNode();
		neighbour.setF(neighbour.heuristic());
                neighbours.add(neighbour);
            }
	}
        return neighbours;
    }
    public EightQueenNode getSteepestBestNeighbor(){ 
        EightQueenNode best=null;
        int bestf=getF();
        for(EightQueenNode currentChild : getNeighbours()){
            if(currentChild.getF()<=bestf){
                        best=currentChild;
                        bestf=currentChild.getF();
                        currentChild.printNode();
                    }
        }
        return best;
    }
    
    public EightQueenNode getFirstChoiceNeighbor(){ 
        ArrayList<EightQueenNode> bestNeighbors=new ArrayList<>();
        for(EightQueenNode currentChild : getNeighbours()){
            if(currentChild.getF()<getF()){
                        bestNeighbors.add(currentChild);
                        currentChild.printNode();
                    }
        }
        Random random =new Random();
        if(bestNeighbors.isEmpty())
            return null;
        else
            return bestNeighbors.get(random.nextInt(bestNeighbors.size()));
    }
    
	// Returns a randomly generated neighbour of a given state
    public EightQueenNode getRandomNeighbour(){
	Random rand = new Random();
		
	int col = rand.nextInt(N);
	int d = rand.nextInt(N-1)+1;
	EightQueenNode neighbour = new EightQueenNode(this);
	neighbour.moveDown(d,col);
	neighbour.setF(neighbour.heuristic());
	return neighbour;
    }
    public void moveDown(int moves,int column/*column of the queen to move*/){
	rowsState[column]+=moves;
        int row=rowsState[column];
	if(row>7 && row%7!=0){
            //out of bound
            rowsState[column]=(row%7)-1;
	}
	else if(row>7 && row%7==0){
		rowsState[column]=7;
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
    public int heuristic(){
        int h=0;
        for(int i=0; i< (getN()-1); i++){
		for(int j=i+1; j<getN(); j++){
			//if state i can attack state j which are queens at columns i and j
                        if(canAttack(rowsState[i],i,rowsState[j],j)){
                            h++;
                        }
		}
	}	
            return h;
    }
}

