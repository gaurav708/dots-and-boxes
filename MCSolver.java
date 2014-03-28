import.java.util.ArrayList;
import java.util.Random;
import java.lang.Math;


public class MCSolver extends GameSolver{

	@Override
	public Edge getNextMove(final Board board, int color)
	{
		ArrayList<Edge> moves = board.getAvailableMoves();
		int moveCount = moves.size();
		int blueWins[] = new int[moveCount], redWins[] = new int[moveCount], draw[] = new int[moveCount];
		int i=0;
		int winner,winRed,winBlue,draw,value[moveCount];
		int currentSimulation;
			
		for(int i=0 ; i<moveCount ; i++){
			
			//currentSimulation = new Random().nextInt(moveCount);
			Board simulatedBoard = getNewBoard(moves.get(i), color);
			
			while(i<1000){
				winner = simulateFromState(simulatedBoard, (simulatedBoard.getScore(color) > board.getScore(color) ? color : Board.toggleColor(color)));
				if(winner == Board.RED)
					winRed++;
				else if(winner == Board.BLUE)
					winBlue++;
				else if(winner == Board.BLANK)
					draw++;
				else
					System.out.println("Error in Simulation");
				i++;
			}

				blueWins[i] = winBlue;
				redWins[i] = redWins;
				draw[i] = draw;
		}
		
		if(color == Board.blue)
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
			nextSimulation = new Random(System.nanoTime()).nextInt(moves.size());
			Board nextBoard = getNewBoard(moves.get(nextSimulation), color);
			winner = simulateFromState(nextBoard, (nextBoard.getScore(color) > board.getScore(color) ? color : Board.toggleColor(color)));
			return winner;
			}
			return -1;
	}


}





