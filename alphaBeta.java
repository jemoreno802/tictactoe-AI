
public class alphaBeta {
	
	public int alphaBeta(char mark, char[][] state, int board) {
		nineBoard n = new nineBoard();
		n.nineBoard = state;
		char[][] test = makeCopy(state);
		int[] moves = findMoves(state, board);
		
		int bestUtil = -10;
		int optimalMove = 0;
		for(int i = 0; i<moves.length;i++) {
			char[][] result = n.makeMove(test, board, moves[i], mark);
			int utility = minValue(n.invertMark(mark), result, moves[i], -1000,1000,0); 
			if(utility>bestUtil) { //if move[i] has higher utility, store as optimal move
				if(hasTwo(result, moves[i], n.invertMark(mark))==true) {
					//do nothing
				}else {
					bestUtil = utility;
					optimalMove = moves[i];
				}
			}
		}
		return optimalMove;
		
	}
	
	/*
	 * compute minimax value for a max state
	 */
	public int maxValue(char mark, char[][] state, int board, int alpha, int beta, int depth) {
		 nineBoard n = new nineBoard();
		 n.nineBoard = state;
		 char[][] test = makeCopy(state);
		 int[] moves = findMoves(test, board);
		 if(n.isTerminal(test)== true) { //if a terminal state, return utility
			 return n.getUtility(test, mark);
		 }
		 
		 if(depth>15) {					//depth cutoff = 15, return heuristic estimate of utility of current state
			 return evaluate(state, board, mark);
		 }
		 
		 int maxUtil = -100;
		 for(int i=0;i<moves.length;i++) { //for each move, call maxValue on the result of making the move, store the largest one
			 char[][] result = n.makeMove(test, board, moves[i], mark);
			 maxUtil = Math.max(maxUtil, minValue(n.invertMark(mark), result, moves[i], alpha, beta, (depth+1)));
			 if(maxUtil >= beta) {//if max>beta, prune the rest of the moves
				 return maxUtil;
			 }
			 alpha = Math.max(maxUtil, alpha); //update alpha
		 }
		return maxUtil;
	}
	
	/*
	 * compute minimax value for a min state
	 */
	public int minValue(char mark, char[][] state, int board, int alpha, int beta, int depth) {
		nineBoard n = new nineBoard();
		n.nineBoard = state;
		char[][] test = makeCopy(state);
		int[] moves = findMoves(test, board);

		 if(n.isTerminal(test)== true) { //if a terminal state, return utility
			 return n.getUtility(test, n.invertMark(mark));
		 }
		 
		 if(depth>15) {					//depth cutoff = 15, return heuristic estimate of utility of current state
			 return evaluate(state, board, mark);
		 }
		 
		 int minUtil = 100;
		 for(int i=0;i<moves.length;i++) { //for each move, call minValue on the result of making the move, store the smallest one 
			 char[][] result = n.makeMove(test, board, moves[i], mark);
			 minUtil = Math.min(minUtil, maxValue(n.invertMark(mark), result, moves[i], alpha, beta, (depth+1)));
			 if(minUtil<= alpha) { //if min<alpha, prune the rest of the moves
				 return minUtil;
			 }
			 beta = Math.min(minUtil, beta); //update beta
		 }
		 
		 
		return minUtil;
	}
	
	/*
	 * given a board, heuristically evaluate the utility of that board for the given mark
	 */
	public int evaluate(char[][] state, int board, char mark) {
		nineBoard n = new nineBoard();
		n.nineBoard = state;
		if(hasTwo(state, board, mark) == true) {
			return 1;
		}else if(hasTwo(state, board,n.invertMark(mark))== true) {
			return -1;
		}else {
			return 0;
		}
		
	}
	
	/*
	 * given a board, determine if it has two in a row for a given mark
	 */
	public boolean hasTwo(char[][] state, int board, char mark) {
		
		board = board-1;
		nineBoard n = new nineBoard();
		n.nineBoard = state;
		if((state[board][0]== mark && state[board][1] == mark && state[board][2] != n.invertMark(mark))||
				(state[board][3]== mark && state[board][4] == mark && state[board][5] != n.invertMark(mark))||
				(state[board][6]== mark && state[board][7] == mark && state[board][8] != n.invertMark(mark))||
				(state[board][0]== mark && state[board][3] == mark && state[board][6] != n.invertMark(mark))||
				(state[board][1]== mark && state[board][4] == mark && state[board][7] != n.invertMark(mark))||
				(state[board][2]== mark && state[board][5] == mark && state[board][8] != n.invertMark(mark))||
				(state[board][0]== mark && state[board][4] == mark && state[board][8] != n.invertMark(mark))||
				(state[board][2]== mark && state[board][4] == mark && state[board][6] != n.invertMark(mark))||
				
				(state[board][0]== mark && state[board][1] != n.invertMark(mark) && state[board][2] == mark)||
				(state[board][3]== mark && state[board][4] != n.invertMark(mark) && state[board][5] == mark)||
				(state[board][6]== mark && state[board][7] != n.invertMark(mark) && state[board][8] == mark)||
				(state[board][0]== mark && state[board][3] != n.invertMark(mark) && state[board][6] == mark)||
				(state[board][1]== mark && state[board][4] != n.invertMark(mark) && state[board][7] == mark)||
				(state[board][2]== mark && state[board][5] != n.invertMark(mark) && state[board][8] == mark)||
				(state[board][0]== mark && state[board][4] != n.invertMark(mark) && state[board][8] == mark)||
				(state[board][2]== mark && state[board][4] != n.invertMark(mark) && state[board][6] == mark)||
				
				(state[board][0]!= n.invertMark(mark) && state[board][1] == mark && state[board][2] == mark)||
				(state[board][3]!= n.invertMark(mark) && state[board][4] == mark && state[board][5] == mark)||
				(state[board][6]!= n.invertMark(mark) && state[board][7] == mark && state[board][8] == mark)||
				(state[board][0]!= n.invertMark(mark) && state[board][3] == mark && state[board][6] == mark)||
				(state[board][1]!= n.invertMark(mark) && state[board][4] == mark && state[board][7] == mark)||
				(state[board][2]!= n.invertMark(mark) && state[board][5] == mark && state[board][8] == mark)||
				(state[board][0]!= n.invertMark(mark) && state[board][4] == mark && state[board][8] == mark)||
				(state[board][2]!= n.invertMark(mark) && state[board][4] == mark && state[board][6] == mark)
			) {
				return true;
			}else {
				return false;
			}
	}
	
	/*
	 * given a board, find all possible moves
	 */
	public int[] findMoves(char[][] state, int board) {
		int numMoves = 0;
		for(int i = 0;i<state[board-1].length;i++) {
			if((state[board-1][i] != 'X') && state[board-1][i]!= 'O') {
				numMoves++;
			}
		}
		int index = 0;
		int[] moves = new int[numMoves];
		for(int i = 0;i<state[board-1].length;i++) {
			if((state[board-1][i] != 'X') && state[board-1][i]!= 'O') {
				moves[index] = (i+1);
				index++;
			}
		}
		return moves;
	}
	
	/*
	 * make and return a copy of a given 9 board
	 */
	public char[][] makeCopy(char[][] state){
		char[][] copy = new char[9][9];
		for(int i=0;i<state.length;i++) {
			for(int j=0;j<state[i].length;j++) {
				copy[i][j]= state[i][j];
			}
		}
		return copy;
	}
	
}