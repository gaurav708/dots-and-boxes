public abstract class GameSolver {
    protected int heuristic(final Board board) {
        return 0;
    }
    public abstract Edge getNextMove(final Board board);
}
