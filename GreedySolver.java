import java.util.ArrayList;

public class GreedySolver extends GameSolver {

    @Override
    public Edge getNextMove(final Board board, int color) {
        referenceColor = color;
        ArrayList<Edge> moves = board.getAvailableMoves();
        int moveCount = moves.size();
        int value[] = new int[moveCount];
        
        for(int i=0;i<moveCount;i++) {
            Board nextBoard = board.getNewBoard(moves.get(i), color);
        	value[i] = heuristic(nextBoard, (nextBoard.getScore(color) > board.getScore(color) ? color : Board.toggleColor(color)));
            System.out.println(moves.get(i) + " : " + value[i]);
        }

        int maxValueIndex=0;
        for(int i=1;i<moveCount;i++){
        	if(value[i]>value[maxValueIndex])
        		maxValueIndex=i;
        }
        return moves.get(maxValueIndex);
    }

}
