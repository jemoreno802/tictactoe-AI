
public class minimax {
	
	
	/*
	 * for every possible move, compute the utility and return the move with the maximum utility
	 */
	public int minimax(char mark, char[] state , boolean isAI) {
		
		board boardie = new board();
		boardie.board = state;
		char[] test = makeCopy(state);
		int[] moves = boardie.findMoves(test); //finds all possible moves
		
		
		int bestUtil = -10;					 
		int optimalMove = 0;
		for(int i = 0; i<moves.length;i++) {
			int utility = minValue(result(mark, test, moves[i]), invertMark(mark)); //tests i in moves, returns the min value of all possible moves resulting from i 
			if(utility>bestUtil) { //if move[i] has higher utility, store the optimal move
				bestUtil = utility;
				optimalMove = moves[i];
			}
		}
		
		//System.out.println("AI making move: " + optimalMove);
		return optimalMove;
	}
	
	/*
	 * given a mark, state and position, returns a new board with the mark at the given position
	 */
	private char[] result(char mark, char[] state, int position ) {
		board boardie = new board();
		boardie.board = state;
		
		char[] test = makeCopy(state);
		if(test[position-1]!= 'X'&& test[position-1]!= 'O') {
			test[position-1] =mark;
		}
		return test;
	}
	
	
	/*
	 * if the given state is terminal, return the utility of the state
	 * else, for each possible move compute the utility of the resulting minimizing state and return the maximum utility. 
	 *
	 * */
	public int maxValue(char[] state, char mark) {
		board boardie = new board();
		boardie.board = state;
		char[] test = makeCopy(state);
		int[] moves = boardie.findMoves(test);
		
		if(boardie.isTerminal(test)) {
			//System.out.println("utility: " + boardie.getUtility(test, mark));
			return boardie.getUtility(test, mark);
		}
		int maxUtility = -10;
		for(int i = 0; i< moves.length;i++) {
			//System.out.println("MAX STATE, char: " + mark + " TESTING move : " + moves[i]);

			maxUtility = Math.max(maxUtility, minValue(result(mark, test, moves[i]), invertMark(mark)));
		}
		return maxUtility;
	}
	
	/*
	 * if the given state is terminal, return the utility of the state
	 * else, for each possible move compute the utility of the resulting maximizing state and return the minimum utility. 
	 *
	 * */
	public int minValue(char[] state, char mark) {
		board boardie = new board();
		boardie.board = state;
		char[] test = makeCopy(state);
		int[] moves = boardie.findMoves(test);
		
		if(boardie.isTerminal(test)) {
			//System.out.println("utility: " + boardie.getUtility(test, invertMark(mark))); //check utility for X ALWAYS
			return boardie.getUtility(test, invertMark(mark));
		}
		int maxUtility = 20;
		for(int i = 0; i< moves.length;i++) {
			//System.out.println("MIN STATE, char: " + mark + " TESTING move : \" " + moves[i]);
			maxUtility = Math.min(maxUtility, maxValue(result(mark, test, moves[i]), invertMark(mark)));
		}
		return maxUtility;
	}
	
	/*
	 * takes in a board and returns a copy of it
	 */
	public char[] makeCopy(char[] state) {
		char[] test = new char[state.length];
		for(int i = 0;i<state.length;i++) {
			test[i]= state[i];
		}
		return test;
	}
	
	/*
	 * takes in a mark and returns the opposite mark
	 */
	public char invertMark(char mark) {
		char retChar;
		if(mark == 'X') {
			retChar = 'O';
		}else {
			retChar = 'X';
		}
		return retChar;
	}
	
}