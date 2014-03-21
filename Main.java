import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private final static int size = 8;
    private final static int dist = 40;

    private int n;
    private Board board;
    private int boardWidth;
    private int turn;

    private JLabel[][] hEdge, vEdge, box;
    private boolean[][] isSetHEdge, isSetVEdge;

    private JFrame frame;
    private JPanel grid;
    private JLabel redScoreLabel, blueScoreLabel, resultLabel;
    private MouseListener listener;

    Main(int n) {
        this.n = n;
        board = new Board(n);
        boardWidth = n * size + (n-1) * dist;
        hEdge = new JLabel[n-1][n];
        isSetHEdge = new boolean[n-1][n];
        vEdge = new JLabel[n][n-1];
        isSetVEdge = new boolean[n][n-1];
        box = new JLabel[n-1][n-1];
        turn = board.RED;
        listener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Edge location = getSource(mouseEvent.getSource());
                int x=location.getX(), y=location.getY();
                ArrayList<Point> ret;
                if(location.isHorizontal()) {
                    if(isSetHEdge[x][y]) return;
                    ret = board.setHEdge(x,y,turn);
                    hEdge[x][y].setBackground(Color.BLACK);
                    isSetHEdge[x][y] = true;
                }
                else {
                    if(isSetVEdge[x][y]) return;
                    ret = board.setVEdge(x,y,turn);
                    vEdge[x][y].setBackground(Color.BLACK);
                    isSetVEdge[x][y] = true;
                }
                for(Point p : ret)
                    box[p.x][p.y].setBackground((turn == board.RED) ? Color.RED : Color.BLUE);

                redScoreLabel.setText(String.valueOf(board.getRedScore()));
                blueScoreLabel.setText(String.valueOf(board.getBlueScore()));

                if(board.isComplete()) {
                    int winner = board.getWinner();
                    if(winner == Board.RED) {
                        resultLabel.setText("Red is the winner!");
                        resultLabel.setForeground(Color.RED);
                    }
                    else if(winner == Board.BLUE) {
                        resultLabel.setText("Blue is the winner!");
                        resultLabel.setForeground(Color.BLUE);
                    }
                    else
                        resultLabel.setText("Game Tied!");
                    //System.exit(0);
                }

                if(ret.isEmpty())
                    turn = (turn == board.RED) ? board.BLUE : board.RED;
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                Edge location = getSource(mouseEvent.getSource());
                int x=location.getX(), y=location.getY();
                if(location.isHorizontal()) {
                    if(isSetHEdge[x][y]) return;
                    hEdge[x][y].setBackground((turn == board.RED) ? Color.RED : Color.BLUE);
                }
                else {
                    if(isSetVEdge[x][y]) return;
                    vEdge[x][y].setBackground((turn == board.RED) ? Color.RED : Color.BLUE);
                }
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                Edge location = getSource(mouseEvent.getSource());
                int x=location.getX(), y=location.getY();
                if(location.isHorizontal()) {
                    if(isSetHEdge[x][y]) return;
                    hEdge[x][y].setBackground(Color.WHITE);
                }
                else {
                    if(isSetVEdge[x][y]) return;
                    vEdge[x][y].setBackground(Color.WHITE);
                }
            }
        };
        initGame();
    }

    private Edge getSource(Object object) {
        for(int i=0; i<(n-1); i++)
            for(int j=0; j<n; j++)
                if(hEdge[i][j] == object)
                    return new Edge(i,j,true);
        for(int i=0; i<n; i++)
            for(int j=0; j<(n-1); j++)
                if(vEdge[i][j] == object)
                    return new Edge(i,j,false);
        return new Edge();
    }

    private JLabel getHorizontalEdge() {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(dist, size));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setOpaque(true);
        label.addMouseListener(listener);
        return label;
    }

    private JLabel getVerticalEdge() {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(size, dist));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setOpaque(true);
        label.addMouseListener(listener);
        return label;
    }

    private JLabel getDot() {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(size, size));
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        return label;
    }

    private JLabel getBox() {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(dist, dist));
        label.setOpaque(true);
        return label;
    }

    private JLabel getEmptyLabel(Dimension d) {
        JLabel label = new JLabel();
        label.setPreferredSize(d);
        return label;
    }

    private void initGame() {
        grid = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        grid.add(getEmptyLabel(new Dimension(2 * boardWidth, dist)), constraints);

        JPanel scorePanel = new JPanel(new GridLayout(2,2));
        scorePanel.setPreferredSize(new Dimension(2 * boardWidth, dist));
        scorePanel.add(new JLabel("<html><font color='red'>Red Score", SwingConstants.CENTER));
        scorePanel.add(new JLabel("<html><font color='blue'>Blue Score", SwingConstants.CENTER));
        redScoreLabel = new JLabel(String.valueOf(board.getRedScore()), SwingConstants.CENTER);
        redScoreLabel.setForeground(Color.RED);
        scorePanel.add(redScoreLabel);
        blueScoreLabel = new JLabel(String.valueOf(board.getBlueScore()), SwingConstants.CENTER);
        blueScoreLabel.setForeground(Color.BLUE);
        scorePanel.add(blueScoreLabel);
        ++constraints.gridy;
        grid.add(scorePanel, constraints);

        ++constraints.gridy;
        grid.add(getEmptyLabel(new Dimension(2 * boardWidth, dist)), constraints);

        for(int i=0; i<(2*n-1); i++) {
            JPanel pane = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
            if(i%2==0) {
                pane.add(getDot());
                for(int j=0; j<(n-1); j++) {
                    hEdge[j][i/2] = getHorizontalEdge();
                    pane.add(hEdge[j][i/2]);
                    pane.add(getDot());
                }
            }
            else {
                for(int j=0; j<(n-1); j++) {
                    vEdge[j][i/2] = getVerticalEdge();
                    pane.add(vEdge[j][i/2]);
                    box[j][i/2] = getBox();
                    pane.add(box[j][i/2]);
                }
                vEdge[n-1][i/2] = getVerticalEdge();
                pane.add(vEdge[n-1][i/2]);
            }
            ++constraints.gridy;
            grid.add(pane, constraints);
        }

        ++constraints.gridy;
        grid.add(getEmptyLabel(new Dimension(2 * boardWidth, dist)), constraints);

        resultLabel = new JLabel("Game is on!", SwingConstants.CENTER);
        resultLabel.setPreferredSize(new Dimension(2 * boardWidth, dist));
        ++constraints.gridy;
        grid.add(resultLabel, constraints);

        ++constraints.gridy;
        grid.add(getEmptyLabel(new Dimension(2 * boardWidth, dist)), constraints);

        frame = new JFrame("Dots And Boxes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Enter the size of grid:");
        Scanner kb = new Scanner(System.in);
        new Main(kb.nextInt());
    }
}
