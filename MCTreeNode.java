import java.util.ArrayList;
import java.util.Random;

public class MCTreeNode {
    private final static Random r = new Random(System.nanoTime());
    private final static int maxLevel = 3;
    private final static double epsilon = 1e-6;
    static int nodeCount = 0, simCount = 0 ;
    
    private  ArrayList<MCTreeNode> children;
    private double nVisits, totValue;
    private Board board ;
    private Edge edge ;
    private int turn, level ;

    public void expand() {
        if(level==maxLevel) {
            children = null;
            return;
        }
        ArrayList<Edge> moves = board.getAvailableMoves() ;
        if(moves.size()==0) {
            children = null;
            return;
        }

        children = new ArrayList<MCTreeNode>() ;
        for(Edge edge:moves) {
            Board nextBoard = board.getNewBoard(edge, turn) ;
            children.add(new MCTreeNode(nextBoard, (board.getScore(turn)<nextBoard.getScore(turn)) ? turn : Board.toggleColor(turn), edge, (level+1) )) ;
            nodeCount++ ;
        }
    }

    public double getValue(double pVisits) {
        double mid = Math.sqrt(Math.log(pVisits+1) / (nVisits + epsilon)) ;/// MCTreeNode.ROOT2 ;
        return  totValue / (nVisits + epsilon) +
                mid +
                r.nextDouble() * epsilon;
    }

    public Edge getMove() {
        double bestValue = Double.NEGATIVE_INFINITY ;
        Edge bestEdge = null ;
        for(MCTreeNode child:children) {
            double currentValue = child.getAverageValue() ;
            if(currentValue>bestValue) {
                bestValue = currentValue ;
                bestEdge = child.getEdge() ;
            }
        }
        return bestEdge ;
    }

    public void updateStats(double value) {
        nVisits++;
        totValue += value;
    }

    public double getAverageValue() {
        return totValue/nVisits ;
    }

    public Edge getEdge() {
        return edge ;
    }

    public Board getBoard() {
        return board ;
    }

    public int getTurn() {
        return turn ;
    }

    public double getVisits() {
        return nVisits ;
    }

    public ArrayList<MCTreeNode> getChildren() {
        return children ;
    }

    MCTreeNode(Board board, int turn, Edge edge, int level) {
        this.board = board ;
        this.turn = turn ;
        this.level = level ;
        this.edge = edge ;
    }
}