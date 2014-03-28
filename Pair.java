
public class Pair implements Comparable<Pair>{
    private int utility ;
    private Edge edge;

    Pair(Edge edge, int utility) {
        this.edge = edge ;
        this.utility = utility ;
    }

    int getUtility() {
        return utility ;
    }

    Edge getEdge() {
        return this.edge ;
    }

    void setEdge(Edge edge) {
        this.edge = edge ;
    }
    void setUtility(int utility) {
        this.utility = utility ;
    }

    @Override
    public int compareTo(Pair pair) {
        return this.utility - pair.utility;
    }
}
