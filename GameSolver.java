public abstract class GameSolver {

    protected int referenceColor;

    protected int heuristic(final Board board, int color) {
        int value;
        if(referenceColor == Board.RED)
            value = 8 * board.getRedScore() - 8 * board.getBlueScore();
        else
            value = 8 * board.getBlueScore() - 8 * board.getRedScore();
        if(referenceColor == color)
            value += 3 * board.getBoxCount(3); //- 2 * board.getBoxCount(2);
        else
            value -= 3 * board.getBoxCount(3); //- 2 * board.getBoxCount(2);
        return value;
    }

    public abstract Edge getNextMove(final Board board, int color );
}
