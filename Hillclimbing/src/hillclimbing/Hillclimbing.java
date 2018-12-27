
package hillclimbing;

import java.time.Clock;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Leyla
 */
public class Hillclimbing {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System. out. println("Enter two numbers for game and algorithm:");
        System. out. println("1) 8puzzle 2)8queen");
        System. out. println("1)steepest ascent 2)first choice 3)simulated annealing");
        int game,algorithm;            
        game=  input. nextInt();
        algorithm=  input. nextInt();
        
        int wins=0,steps=0;
        long elapsedTime=0,startTime;
        if(game==1){
            //8puzzle
            int[][] goalValues = new int[][] { 
            { 1, 2, 3 },
            { 8, 0, 4 },
            { 7, 6, 5 } }; 
            EightPuzzleSearch puzzleSearch = new EightPuzzleSearch();
            int[] results=null;
            startTime = System.currentTimeMillis();
            for(int i=0; i<12; i++){
                EightPuzzleNode test =makeRandPNode(goalValues);
                switch(algorithm){
                    case 1:
                        results=puzzleSearch.steepestAscent(test, goalValues);
                        break;
                    case 2:
                        results=puzzleSearch.firstChoice(test, goalValues);
                        break;
                    case 3:
                        results=puzzleSearch.simulatedAnneal(test, goalValues);
                        break;
                }
                steps+=results[0];
                    if(results[1]==1){
                        wins++;
                    }    
            }
            elapsedTime = System.currentTimeMillis() - startTime;
        }    
            

        if(game==2){
            //8queen
            EightQueenSearch puzzleSearch = new EightQueenSearch();
            int[] results=null;
            startTime = System.currentTimeMillis();
            for(int i=0; i<12; i++){
                EightQueenNode test =makeRandQNode(8);
                switch(algorithm){
                    case 1:
                        results=puzzleSearch.steepestAscent(test);
                        break;
                    case 2:
                        results=puzzleSearch.firstChoice(test);
                        break;
                    case 3:
                        results=puzzleSearch.simulatedAnneal(test);
                        break;
                }
                steps+=results[0];
                    if(results[1]==1){
                        wins++;
                    }    
            }
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        System.out.printf("%s,%d","total time of 12 nodes:",elapsedTime);
        System.out.printf("%s,%d","total steps of 12 nodes:",steps);
        System.out.printf("%s,%d","total wins of 12 nodes:",wins);        
         

        
    }
    public static EightPuzzleNode makeRandPNode(int[][]goal){
        EightPuzzleNode temp=new EightPuzzleNode(goal);
        Random random=new Random();
        int n=random.nextInt(50);
        for(int i=0;i<n;i++){
            temp=temp.getRandomNeighbour();
        }
        //temp.printNode();
        return temp;
    }
    
    
    public static EightQueenNode makeRandQNode(int N/*number of queens*/){
        EightQueenNode temp=new EightQueenNode();
        int[] rows =new int[N];
        Random rand=new Random();
        for(int i=0; i<N; i++){
            rows[i]=rand.nextInt(N);
        }
        temp.setRowsState(rows);
        return temp;
    }
}
