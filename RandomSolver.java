import java.util.ArrayList;
import java.util.Random;

public class RandomSolver implements GameSolver {

    @Override
    public Edge getNextMove(Board board) {
        ArrayList<Edge> moves = board.getAvailableMoves();
        return moves.get(new Random().nextInt(moves.size()));
    }

}
