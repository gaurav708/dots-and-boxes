import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class MinimaxSolver extends GameSolver {

    @Override
    public Edge getNextMove(Board board, int color) {

        LinkedList<TreeNode> queue =  new LinkedList<TreeNode>();
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>() ;
        ArrayList<Edge> moves ;
        Board currentBoard;
        int currentColor, currentScore ;
        long oldTime = System.nanoTime();
        long timeOut = 980000000;
        /*long r=board.getAvailableMoves().size();
        long size=1 + r, lastSize=r ;
        r--;*/

        TreeNode rootNode = new TreeNode(board,color,null,null), levelNode = null ;
        queue.add(rootNode) ;
        queue.add(levelNode);
        referenceColor = color ;

        do {
            if((System.nanoTime() - oldTime) > timeOut) break;
            TreeNode currentNode = queue.remove() ;
            if(currentNode!=levelNode) {
                stack.add(currentNode) ;
                currentBoard = currentNode.getBoard();
                currentColor = currentNode.getColor();
                currentScore = currentBoard.getScore(currentColor);

                moves = currentBoard.getAvailableMoves();
                Collections.shuffle(moves);

                for (Edge i : moves) {
                    Board child = currentBoard.getNewBoard(i, currentColor);
                    int newScore = child.getScore(currentColor);
                    if (newScore == currentScore)
                        queue.add(new TreeNode(child, Board.toggleColor(currentColor), currentNode, i));
                    else
                        queue.add(new TreeNode(child, currentColor, currentNode,i));
                }

            }
            else {
                /*size += lastSize*r ;

                if( size>=100000L || r==0 )
                    break;

                lastSize *= r ;
                r--;*/
                queue.add(levelNode) ;
            }
        } while(queue.size()!=0) ;

        while(queue.size()!=0) {
            TreeNode currentNode = queue.remove() ;
            if(currentNode!=levelNode)
                stack.add(currentNode) ;
        }

        do {
            TreeNode currentNode = stack.removeLast() ;
            TreeNode parentNode = currentNode.getParent() ;
            int currentUtility = currentNode.getUtility() ;

            if(TreeNode.MIN == currentUtility)
                currentNode.setUtility(heuristic(currentNode.getBoard(), currentNode.getColor())) ;
            currentUtility = currentNode.getUtility() ;

            if(parentNode.getColor()==referenceColor) {
                if(parentNode.getUtility()<currentUtility) {
                    parentNode.setUtility(currentUtility);
                    if (parentNode == rootNode)
                        rootNode.setEdge(currentNode.getEdge());
                }
            }
            else {
                if(parentNode.getUtility()>currentUtility)
                    parentNode.setUtility(currentUtility) ;
            }

        } while(stack.size()!=1) ;
        return rootNode.getEdge() ;
    }


}
