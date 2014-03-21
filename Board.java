import java.awt.Point;
import java.util.ArrayList;

public class Board {

    final static int RED = 0;
    final static int BLUE = 1;
    final static int BLACK = 2;
    final static int BLANK = 3;

    private int[][] hEdge;
    private int[][] vEdge;
    private int[][] box;
    private int n, redScore, blueScore;

    public Board(int n) {
        hEdge = new int[n-1][n];
        vEdge = new int[n][n-1];
        box = new int[n-1][n-1];
        fill(hEdge,BLANK);
        fill(vEdge,BLANK);
        fill(box,BLANK);
        this.n = n;
        redScore = blueScore = 0;
    }

    private void fill(int[][] array, int val) {
        for(int i=0; i<array.length; i++)
            for(int j=0; j<array[i].length; j++)
                array[i][j]=val;
    }

    public int getRedScore() {
        return redScore;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public int getHEdge(int x, int y) {
        return hEdge[x][y];
    }

    public int getVEdge(int x, int y) {
        return vEdge[x][y];
    }

    public int getBox(int x, int y) {
        return box[x][y];
    }

    public ArrayList<Point> setHEdge(int x, int y, int color) {
        hEdge[x][y]=BLACK;
        ArrayList<Point> ret = new ArrayList<Point>();
        if(y<(n-1) && vEdge[x][y]==BLACK && vEdge[x+1][y]==BLACK && hEdge[x][y+1]==BLACK) {
            box[x][y]=color;
            ret.add(new Point(x,y));
            if(color == RED) redScore++;
            else blueScore++;
        }
        if(y>0 && vEdge[x][y-1]==BLACK && vEdge[x+1][y-1]==BLACK && hEdge[x][y-1]==BLACK) {
            box[x][y-1]=color;
            ret.add(new Point(x,y-1));
            if(color == RED) redScore++;
            else blueScore++;
        }
        return ret;
    }

    public ArrayList<Point> setVEdge(int x, int y, int color) {
        vEdge[x][y]=BLACK;
        ArrayList<Point> ret = new ArrayList<Point>();
        if(x<(n-1) && hEdge[x][y]==BLACK && hEdge[x][y+1]==BLACK && vEdge[x+1][y]==BLACK) {
            box[x][y]=color;
            ret.add(new Point(x,y));
            if(color == RED) redScore++;
            else blueScore++;
        }
        if(x>0 && hEdge[x-1][y]==BLACK && hEdge[x-1][y+1]==BLACK && vEdge[x-1][y]==BLACK) {
            box[x-1][y]=color;
            ret.add(new Point(x-1,y));
            if(color == RED) redScore++;
            else blueScore++;
        }
        return ret;
    }

    public boolean isComplete() {
        if((redScore + blueScore) == (n-1)*(n-1))
            return true;
        else
            return false;
    }

    public int getWinner() {
        if(redScore > blueScore) return Board.RED;
        else if(redScore < blueScore) return Board.BLUE;
        else return Board.BLANK;
    }
}
