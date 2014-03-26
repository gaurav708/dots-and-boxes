import java.util.ArrayList;

public class GreedySolver extends GameSolver {

    @Override
    public Edge getNextMove(final Board board, int color) {
        
        ArrayList<Edge> moves = board.getAvailableMoves();
        int moveCount;
        int value[moveCount];
        
        Board tempBoard = new Board;
        tempBoard = board;

        for(int i=0;i<moveCount;i++){
        	value[i] = heuristic(tempboard.getNewBoard(moves[i],color),color);
        }

        int maxValueIndex=0;
        for(int i=0;i<moveCount;i++){
        	if(value[i]>value[maxValueIndex])
        		maxValueIndex=i;
        }

        return moves[maxValueIndex];
       
    }

}
