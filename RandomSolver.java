import java.util.ArrayList;
import java.util.Random;

public class RandomSolver extends GameSolver {

    @Override
    public Edge getNextMove(final Board board) {
        ArrayList<Edge> moves = board.getAvailableMoves();
        return moves.get(new Random().nextInt(moves.size()));
    }

}
