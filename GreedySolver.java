import java.util.ArrayList;

public class GreedySolver extends GameSolver {

    @Override
    public Edge getNextMove(final Board board, int color) {
        
        ArrayList<Edge> moves = board.getAvailableMoves();
        int moveCount;
        int value[] = new int[moveCount];
        
        Board tempBoard;// = new Board(n);
        tempBoard = board;

        for(int i=0;i<moveCount;i++){
        	value[i] = heuristic(tempBoard.getNewBoard(moves[i],color),color);
        }

        int maxValueIndex=0;
        for(int i=0;i<moveCount;i++){
        	if(value[i]>value[maxValueIndex])
        		maxValueIndex=i;
        }

        return moves[maxValueIndex];
       
    }

}
