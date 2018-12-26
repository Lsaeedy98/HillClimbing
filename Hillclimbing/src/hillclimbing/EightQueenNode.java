package hillclimbing;


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Leyla
 */

public class EightQueenNode {
    //int row is state[i]
    //int column is index i which doesnt change for each queen
    private int[] rowsState;
    private final int N=8;
    private ArrayList<EightQueenNode> neighbours;
    private int f;
    private EightQueenNode parent;
    
    public EightQueenNode(){
	rowsState = new int[N]; //empty state
	neighbours = new ArrayList<EightQueenNode>(); //empty neighbour list
        f=0;
    }
    public EightQueenNode(EightQueenNode q){
        rowsState = new int[N];
	neighbours = new ArrayList<EightQueenNode>();
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

    public void setParent(EightQueenNode Parent) {
        this.parent = parent;
    }

    public EightQueenNode getParent() {
        return parent;
    }

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
    public void makeNeighbours(){
            int count=0;
	for(int i=0; i<N; i++){
            for(int j=1; j<N; j++){
		neighbours.add(count, new EightQueenNode(this));        
		neighbours.get(count).moveDown(j,i);
                neighbours.get(count).setParent(this);
                neighbours.get(count).printNode();
		//neighbours.get(count).setF(heuristic(neighbours.get(count)));
		count++;
            }
	}
    }

    public ArrayList<EightQueenNode> getNeighbours() {
        return neighbours;
    }
    
	// Returns a randomly generated neighbour of a given state
    public EightQueenNode getRandomNeighbour(){
	Random rand = new Random();
		
	int col = rand.nextInt(N);
	int d = rand.nextInt(N-1)+1;
	EightQueenNode neighbour = new EightQueenNode(this);
	neighbour.moveDown(d,col);
	//neighbour.setF(heuristic(neighbour));
	return neighbour;
    }
    
    public EightQueenNode makeRandomNode(int N/*number of queens*/){
        EightQueenNode temp=new EightQueenNode();
        int[] rows =new int[N];
        Random rand=new Random();
        for(int i=0; i<N; i++){
            rows[i]=rand.nextInt(N);
        }
        temp.setRowsState(rows);
        return temp;
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
}

