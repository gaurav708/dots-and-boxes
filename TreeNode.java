public class TreeNode {
    private Board board ;
    private TreeNode parent ;
    private int color, utility ;
    private Edge edge ;
    final static int MIN = -1000000000 ;

    public TreeNode(Board board, int color, TreeNode parent, Edge edge) {
        this.board = board;
        this.color = color;
        this.parent = parent ;
        this.utility = MIN ;
        this.edge = edge ;
    }
    public Board getBoard() {
        return board;
    }
    public TreeNode getParent() {
        return parent;
    }

    public int getColor() {
        return color;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility ;
    }

    public Edge getEdge() {
        return this.edge ;
    }

    public void setEdge(Edge edge) {
        this.edge = edge ;
    }

}
