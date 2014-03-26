public abstract class GameSolver {

    protected int heuristic(final Board board, int color) {
        if(color == Board.RED)
        	return (board.getRedScore() - board.getBlueScore());
        else
        	return (board.getBlueScore() - board.getRedScore());
    }

    public abstract Edge getNextMove(final Board board, int color ,int n);  //n is the size for board
}
