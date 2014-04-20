import java.util.ArrayList;
import java.util.Random;

public class RandomSolver extends GameSolver {

    @Override
    public Edge getNextMove(final Board board, int color) {
        ArrayList<Edge> moves = board.getAvailableMoves();
        ArrayList<Edge> bMoves = new ArrayList<Edge>();
        for(Edge move : moves) {
        	Board newBoard = board.getNewBoard(move, color);
        	if(newBoard.getScore(color) > board.getScore(color))
        		bMoves.add(move);
        }
        if(!bMoves.isEmpty()) moves = bMoves;
        return moves.get(new Random().nextInt(moves.size()));
    }

}
