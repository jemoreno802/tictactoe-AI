import java.util.Scanner;

public class nineBoard {
	public char[][] nineBoard = new char[9][9];
	private player human;
	private player ai;
	private player currPlayer;
	private boolean finished = false;
	private alphaBeta ab = new alphaBeta();
	
	public void playNineBoard() {
		nineBoard = initializeBoard();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Welcome to 9-Board Tic Tac Toe");
		System.out.println("You may enter moves as two numbers 1-9 separated by a space");
		System.out.println("For example, the move 1 2 would place your mark in board 1, position 2");
		System.out.println("Would you like to be 'X' or 'O'?");
		char entry = scan.next().charAt(0);
		printSuperBoard(nineBoard);
		if(entry == 'O'|| entry == 'o') {
			human = new player('O');
			ai = new player('X');
			currPlayer = ai;
			
		}else {
			human = new player('X');
			ai = new player('O');
			currPlayer = human;
			
		}
		
		int firstMove = 0;
		int p = 0;
		while(finished==false) {
			System.out.println();
			
			if(currPlayer == human) {
				if(firstMove == 0) {
					emptyPositions();
				}else {
					legalMoves(p);
				}
				System.out.println("Enter a position to play: ");
				int b = scan.nextInt();
				 p = scan.nextInt();
				makeMove(nineBoard, b,p,human.marker);
				finished = false;
				currPlayer = ai;
				printSuperBoard(nineBoard);
			}else if(currPlayer == ai) {
				if(firstMove ==0) {
					makeMove(nineBoard, 1,1,ai.marker);
					p=1;
					System.out.println("AI made move: (1 1)");
				}else {
					int move = ab.alphaBeta(ai.marker, nineBoard, p);
					makeMove(nineBoard,p,move, ai.marker);
					System.out.println("AI made move: ("+ p + " " + move + ")");

					p = move;
				}
				
				currPlayer = human;
				printSuperBoard(nineBoard);
				
			}
			firstMove++;
			finishedGame(nineBoard);
		}
		System.out.println("The winner is: " + invertMark(currPlayer.marker));

	}
	
	/*
	 * prints out ALL available moves for the human if they are the first player
	 */
	public void emptyPositions() {
		System.out.println("Your available positions are: ");
		for(int i = 0; i<nineBoard.length; i++) {
			System.out.println();
			for(int j = 0; j<nineBoard[i].length;j++) {
				
				if(nineBoard[i][j] != 'X' && nineBoard[i][j] != 'O') {
					System.out.print("("+  (i+1) + " " +nineBoard[i][j] + ") ");
				}
			}
		}	
		System.out.println();
	}
	
	
	/*
	 * prints out the available moves for a given board
	 */
	public void legalMoves(int board) {
		System.out.println("Your available positions are: ");
		for(int j = 0; j<nineBoard[board-1].length;j++) {
			
			if(nineBoard[board-1][j] != 'X' && nineBoard[board-1][j] != 'O') {
				System.out.print("("+  (board) + " " + nineBoard[board-1][j] + ") ");
			}
		}
		System.out.println();
	}
	
	/*
	 * sets all initial variables in the board
	 */
	public char[][] initializeBoard(){
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				char entry = Integer.toString((j+1)).charAt(0);
				nineBoard[i][j] = entry;
			}
			
		}
		return nineBoard;
	}

	/*
	 * changed global variable finished -> true if the given state is terminal
	 */
	public void finishedGame(char[][] state) {
		if((wonGame('X',state)==true)||(wonGame('O', state)==true)||fullBoard(state)== true) {
			finished = true;
		}
	}
	
	/*
	 * returns true if the game is won, lost or tied
	 */
	public boolean isTerminal(char[][] state) {
		if(wonGame('X', state)== true || wonGame('O', state)== true || fullBoard(state)== true) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * returns the utility of a given state
	 */
	public int getUtility(char[][] state, char mark) {
		if(wonGame(mark, state)==true){
			return 10;
		}else if(wonGame(invertMark(mark), state)== true) {		
			return -10;
		}else {
			return 0;
		}	
	}
	
	/*
	 * checks all boards in the 9-board, if the given character has won
	 * any of them, return true
	 */
	public boolean wonGame(char mark, char[][] state) {
		boolean won = false;
		for(int i=0;i<state.length;i++) {
			if(wonBoard(mark, state[i])== true) {
				won = true;
			}
		}
		return won;
	}
	
	/*
	 * returns true if the given mark has won the given board
	 */
	public boolean wonBoard(char mark, char[] innerBoard) {
		if((innerBoard[0]== mark && innerBoard[1] == mark && innerBoard[2] == mark)||
				(innerBoard[3]== mark && innerBoard[4] == mark && innerBoard[5] == mark)||
				(innerBoard[6]== mark && innerBoard[7] == mark && innerBoard[8] == mark)||
				(innerBoard[0]== mark && innerBoard[3] == mark && innerBoard[6] == mark)||
				(innerBoard[1]== mark && innerBoard[4] == mark && innerBoard[7] == mark)||
				(innerBoard[2]== mark && innerBoard[5] == mark && innerBoard[8] == mark)||
				(innerBoard[0]== mark && innerBoard[4] == mark && innerBoard[8] == mark)||
				(innerBoard[2]== mark && innerBoard[4] == mark && innerBoard[6] == mark)
			) {
				return true;
			}else {
				return false;
			}
	}
	
	/*
	 * returns true if the 9-board is full
	 */
	public boolean fullBoard(char[][] state) {
		boolean fullBoard = true;
		for(int i = 0;i<state.length;i++) {
			if(
				state[i][0]== '1'||
				state[i][1]== '2'|| 
				state[i][2]== '3'||
				state[i][3]== '4'||
				state[i][4]== '5'|| 
				state[i][5]== '6'||
				state[i][6]== '7'||
				state[i][7]== '8'||
				state[i][8]== '9') 
			{
				fullBoard = false;
			}
			
		}
		return fullBoard;
	}
	
	/*
	 * makes a mark in the given location if there is not already one there
	 */
	public char[][] makeMove(char[][] state, int board, int pos, char mark) {
		if((state[board-1][pos-1] != 'X')&&(state[board-1][pos-1] != 'O')) {
			state[board-1][pos-1] = mark;
		}
		else {
			System.out.println("cannot move in this position");
		}
		return state;
	}
	
	/*
	 * invert X->O, O->X
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
	
	/*
	 * given a 9-board, print it out
	 */
	public void printSuperBoard(char[][] superBoard) {

		//row 1
		System.out.println("|---|---|---||---|---|---||---|---|---||");
		for(int i = 0;i<3;i++) {
		System.out.print("| "+ superBoard[0][i]+" ");
		}
		System.out.print("|");
		for(int i = 0;i<3;i++) {
		System.out.print( "| "+superBoard[1][i]+" ");
		}
		System.out.print("|");
		for(int i = 0;i<3;i++) {
		System.out.print("| "+ superBoard[2][i]+" ");
		}
		System.out.println("||");
		System.out.println("|---|---|---||---|---|---||---|---|---||");


		//row 2
		for(int i = 3;i<6;i++) {
		System.out.print("| "+ superBoard[0][i]+" ");
		}
		System.out.print("|");
		for(int i = 3;i<6;i++) {
		System.out.print("| "+ superBoard[1][i]+" ");
		}
		System.out.print("|");
		for(int i = 3;i<6;i++) {
		System.out.print("| "+ superBoard[2][i]+" ");
		}
		System.out.println("||");
		System.out.println("|---|---|---||---|---|---||---|---|---||");


		//row 3
		for(int i = 6;i<9;i++) {
		System.out.print("| "+ superBoard[0][i]+" ");
		}
		System.out.print("|");
		for(int i = 6;i<9;i++) {
		System.out.print("| "+ superBoard[1][i]+" ");
		}
		System.out.print("|");
		for(int i = 6;i<9;i++) {
		System.out.print("| "+ superBoard[2][i]+" ");
		}
		System.out.println("||");
		System.out.println("|=====================================||");

		//row 4

		for(int i = 0;i<3;i++) {
		System.out.print("| "+ superBoard[3][i]+" ");
		}
		System.out.print("|");
		for(int i = 0;i<3;i++) {
		System.out.print("| "+ superBoard[4][i]+" ");
		}
		System.out.print("|");
		for(int i = 0;i<3;i++) {
		System.out.print("| "+ superBoard[5][i]+" ");
		}
		System.out.println("||");
		System.out.println("|---|---|---||---|---|---||---|---|---||");


		//row 5

		for(int i = 3;i<6;i++) {
		System.out.print("| "+ superBoard[3][i]+" ");
		}
		System.out.print("|");
		for(int i = 3;i<6;i++) {
		System.out.print("| "+ superBoard[4][i]+" ");
		}
		System.out.print("|");
		for(int i = 3;i<6;i++) {
		System.out.print("| "+ superBoard[5][i]+" ");
		}
		System.out.println("||");
		System.out.println("|---|---|---||---|---|---||---|---|---||");

		//row 6

		for(int i = 6;i<9;i++) {
		System.out.print("| "+ superBoard[3][i]+" ");
		}
		System.out.print("|");
		for(int i = 6;i<9;i++) {
		System.out.print("| "+ superBoard[4][i]+" ");
		}
		System.out.print("|");
		for(int i = 6;i<9;i++) {
		System.out.print("| "+ superBoard[5][i]+" ");
		}
		System.out.println("||");
		System.out.println("|=====================================||");

		//row 7

		for(int i = 0;i<3;i++) {
		System.out.print("| "+ superBoard[6][i]+" ");
		}
		System.out.print("|");
		for(int i = 0;i<3;i++) {
		System.out.print("| "+ superBoard[7][i]+" ");
		}
		System.out.print("|");
		for(int i = 0;i<3;i++) {
		System.out.print("| "+ superBoard[8][i]+" ");
		}
		System.out.println("||");
		System.out.println("|---|---|---||---|---|---||---|---|---||");

		//row8

		for(int i = 3;i<6;i++) {
		System.out.print("| "+ superBoard[6][i]+" ");
		}
		System.out.print("|");
		for(int i = 3;i<6;i++) {
		System.out.print("| "+ superBoard[7][i]+" ");
		}
		System.out.print("|");
		for(int i = 3;i<6;i++) {
		System.out.print("| "+ superBoard[8][i]+" ");
		}
		System.out.println("||");
		System.out.println("|---|---|---||---|---|---||---|---|---||");

		//row 9

		for(int i = 6;i<9;i++) {
		System.out.print("| "+ superBoard[6][i]+" ");
		}
		System.out.print("|");
		for(int i = 6;i<9;i++) {
		System.out.print("| "+ superBoard[7][i]+" ");
		}
		System.out.print("|");
		for(int i = 6;i<9;i++) {
		System.out.print("| "+ superBoard[8][i]+" ");
		}
		System.out.println("||");
		System.out.println("|=====================================||");

		}
	
}