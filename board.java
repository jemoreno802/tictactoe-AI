
import java.util.Scanner;

public class board{
	public char[] board = {'1','2','3','4','5','6','7','8','9'};
	private player currPlayer;
	private boolean finished =  false;
	private player human;
	private player ai;
	private minimax mini = new minimax();
	
	public void playGame() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to basic Tic Tac Toe");
		System.out.println("Would you like to be 'X' or 'O'?");
		char entry = scan.next().charAt(0);
		if(entry == 'O'|| entry == 'o') {
			human = new player('O');
			ai = new player('X');
			currPlayer = ai;
			
		}else {
			human = new player('X');
			ai = new player('O');
			currPlayer = human;
		}
		printBoard(this.board);
		
		while(finished == false) {
			
			if(currPlayer == human) {
				emptySpots(); //tell player which spots are available to play 
				System.out.println("Enter a position to play:");
				int pos = scan.nextInt();
				makeMove(human, pos);
				currPlayer = ai; //toggle player
				printBoard(this.board);
			}else if(currPlayer == ai) {
				System.out.println("AI move: ");
				char[] temp = board;
				makeMove(ai, mini.minimax(ai.marker, temp, true));
				currPlayer = human;
				printBoard(this.board);
			}
			finishedGame(board);
	
		}
		if(wonGame(ai.marker, board)==true) {
			System.out.println("The winner is: " +ai.marker);
		}else if(wonGame(human.marker, board)== true) {
			System.out.println("The winner is: " +human.marker);

		}else {
			System.out.println("Tie");
		}
		
		
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
	
	/*
	 * given a state and a mark, return the utility of that state for that mark
	 */
	public int getUtility(char[] state, char mark) {
		if(wonGame(mark, state)==true){
			return 1;
		}else if(wonGame(invertMark(mark), state)== true) {		
			return -1;
		}else {
			return 0;
		}	
	}
	
	/*
	 * given a mark, state and position, returns a new board with the mark at the given position
	 */
	public char[] result(char mark, char[] test, int position ) {
		char[] retArray = test;
		if(retArray[position-1]!= 'X'&& retArray[position-1]!= 'O') {
			retArray[position-1] =mark;
		}
		printBoard(retArray);
		return retArray;
	}
	
	/*
	 * makes a move in the given position
	 */
	public void makeMove(player P, int position) {
		if(board[position-1]!= 'X'&& board[position-1]!= 'O') {
			board[position-1] = P.marker;
		}
		else {
			System.out.println("You chose an unavailable spot, your available spots are:");
		}
	}
	
	/*
	 * if the game is completed, update boolean finished = true
	 */
	public void finishedGame(char[] state) {
		
		if(wonGame('X', state)==true|| wonGame('O', state)==true || fullBoard(state)==true) {
			finished = true;
		}
	}
	
	/*
	 * given a state, return true if either player has won the game, or if the board is full
	 */
	public boolean isTerminal(char[] state) {
		if(wonGame('X', state)== true || wonGame('O', state)== true || fullBoard(state)== true) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * returns an array of all possible moves
	 */
	public int[] findMoves(char[] state) {
		int numMoves=0;
		//loop through the state to find the length of the array of moves
		for(int i = 0; i<state.length; i++) {
			if(state[i] != 'X' && state[i] != 'O') {
				numMoves++;
			}
		}
		int[] possibleMoves = new int[numMoves];
		int index = 0;
		for(int i = 0; i<state.length; i++) {
			if(state[i] != 'X' && state[i] != 'O') {
				possibleMoves[index] = (i+1);
				index++;
			}
		}
		return possibleMoves;
	}
	
	/*
	 * prints the available positions on the board
	 */
	public void emptySpots() {
		System.out.println("Your available positions are: ");
		for(int i = 0; i<board.length; i++) {
			if(board[i] != 'X' && board[i] != 'O') {
				System.out.print(" "+ board[i] + " ");
			}
		}
		System.out.println();
	}
	
	/*
	 * returns true if there are three of the same marks in a row 
	 */
	public boolean wonGame(char test, char[] state) {
		if((state[0]== test && state[1] == test && state[2] == test)||
			(state[3]== test && state[4] == test && state[5] == test)||
			(state[6]== test && state[7] == test && state[8] == test)||
			(state[0]== test && state[3] == test && state[6] == test)||
			(state[1]== test && state[4] == test && state[7] == test)||
			(state[2]== test && state[5] == test && state[8] == test)||
			(state[0]== test && state[4] == test && state[8] == test)||
			(state[2]== test && state[4] == test && state[6] == test)
		) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * returns false if there are any available moves in the board
	 */
	public boolean fullBoard(char[] state) {
		//checks if each position is the same as its initial position
		if(state[0]=='1' ||
			state[1]== '2'|| 
			state[2]=='3'||
			state[3]== '4'||
			state[4]== '5'|| 
			state[5]=='6'||
			state[6]== '7'||
			state[7]=='8'||
			state[8]== '9') 
		{
			return false;
		}else {
			return true;
		}
	}
	
	/*
	 * takes in a state of the board and prints it
	 */
	public void printBoard(char[] state) {
		System.out.println("+---+---+---+");
		for(int i = 1;i<=board.length;i++) {
			System.out.print("| " + board[i-1]+ " ");
			if(i%3==0) {
				System.out.println("|");
				System.out.println("+---+---+---+");
			}
		}
	}
	
	
}