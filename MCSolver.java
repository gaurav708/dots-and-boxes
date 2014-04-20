import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MCSolver extends GameSolver{
    final Random r = new Random(System.nanoTime());
    //private int size ;

    @Override
    public Edge getNextMove(Board board, int color) {
        MCTreeNode.nodeCount = 0;
        MCTreeNode.simCount = 0;
        referenceColor = color;
        //size = board.getSize() ;
        MCTreeNode root = new MCTreeNode(board, color, null, 0) ;
        long oldTime = System.nanoTime() ;
        while(System.nanoTime()-oldTime<1900000000){
            selectAction(root);
        }
        System.out.println(MCTreeNode.nodeCount +" "+ MCTreeNode.simCount) ;
        return root.getMove();
    }

    public void selectAction(MCTreeNode root) {
        List<MCTreeNode> visited = new LinkedList<MCTreeNode>();
        MCTreeNode cur = root;
        visited.add(root);
        while (true) {
            MCTreeNode child = select(cur);
            if(child==null) break;
            cur=child;
            visited.add(cur);
        }
        cur.expand();
        MCTreeNode newNode = select(cur);
        if(newNode==null)
            newNode = cur ;
        visited.add(newNode);
        int value = simulateFromState(newNode.getBoard(), newNode.getTurn());
        /*int value;
        if(winner == referenceColor) value = 1;
        else value = 0;*/
        for (MCTreeNode node : visited) {
            node.updateStats(value);
        }
    }

    private int simulateFromState(Board board, int turn)
    {
        int winner;
        if(board.isComplete()) {
            MCTreeNode.simCount++;
            return (board.getScore(referenceColor)-board.getScore(Board.toggleColor(referenceColor)));
        }
        else{
            Edge move = (new GreedySolver().getNextMove(board, turn));
            Board nextBoard = board.getNewBoard(move, turn) ;
            winner = simulateFromState(nextBoard, (nextBoard.getScore(turn) > board.getScore(turn) ? turn : Board.toggleColor(turn)));
            return winner;
        }
    }

    private MCTreeNode select(MCTreeNode cur) {
        ArrayList<MCTreeNode> children = cur.getChildren();
        if(children==null)
            return null ;
        MCTreeNode selected = null;
        double bestValue = Double.NEGATIVE_INFINITY;

        if(cur.getVisits()>5)
            for (MCTreeNode c : children) {
                double uctValue = /* size*c.getVisits()* */   c.getValue(cur.getVisits());   //+ heuristic(c.getBoard(), c.getTurn())    ;
                if (uctValue > bestValue) {
                    selected = c;
                    bestValue = uctValue;
                }
            }
        else
            for(MCTreeNode c:children) {
                double currentValue = heuristic(c.getBoard(),c.getTurn()) ;
                if(currentValue>bestValue) {
                    bestValue = currentValue;
                    selected = c;
                }
            }

        return selected;
    }
}





