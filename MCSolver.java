import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;


public class MCSolver extends GameSolver{

	@Override
	public Edge getNextMove(final Board board, int color)
	{
		ArrayList<Edge> moves = board.getAvailableMoves();
		int moveCount = moves.size();
		int blueWins[] = new int[moveCount], redWins[] = new int[moveCount], draw[] = new int[moveCount];
		int j;
		int winner,winRed,winBlue,drawResult;
		int value[] = new int[moveCount];
		int currentSimulation;
			
		for(int i=0 ; i<moveCount ; i++){
			
			//currentSimulation = new Random().nextInt(moveCount);
			Board simulatedBoard = board.getNewBoard(moves.get(i), color);
			j=0;winBlue=0;winRed=0;drawResult=0;
			while(j<1000){
				winner = simulateFromState(simulatedBoard, (simulatedBoard.getScore(color) > board.getScore(color) ? color : Board.toggleColor(color)));
				if(winner == Board.RED)
					winRed++;
				else if(winner == Board.BLUE)
					winBlue++;
				else if(winner == Board.BLANK)
					drawResult++;
				else
					System.out.println("Error in Simulation");
				j++;
			}

				blueWins[i] = winBlue;
				redWins[i] = winRed;
				draw[i] = drawResult;
		}
		
		if(color == Board.BLUE)
			for(int i=0;i<moveCount;i++)
				value[i] = blueWins[i];		// /1000 + (int) sqrt( log( (double)(1000*moveCount) )/500.0 );   		//second factor same for both cases, since no of simulations taken is same

		else
			for(int i=0;i<moveCount;i++)
				value[i] = redWins[i];			

		int indexMCSelection=0;
		for(int i=0;i< moveCount;i++)
			if(value[i]>value[indexMCSelection])
				indexMCSelection=i;

		return moves.get(indexMCSelection);
	}

	private int simulateFromState(Board board, int color)
	{
		int winner;
		//int selection=0,boardSize=board.getSize();
		if(board.isComplete())
			return board.getWinner();
		else{
			ArrayList<Edge> moves = board.getAvailableMoves();
			int nextSimulation = new Random(System.nanoTime()).nextInt(moves.size());
			Board nextBoard = board.getNewBoard(moves.get(nextSimulation), color);
			winner = simulateFromState(nextBoard, (nextBoard.getScore(color) > board.getScore(color) ? color : Board.toggleColor(color)));
			return winner;
			}
	}


}





