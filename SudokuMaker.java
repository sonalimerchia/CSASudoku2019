package HelloWorld;

/**
 *	SudokuMaker - Creates a Sudoku puzzle using recursion and backtracking
 *
 *	@author Sonali Merchia
 *	@since January 22nd, 2019
 *
 */
public class SudokuMaker {
	
	private int[][] puzzle; //The Sudoku Puzzle that the program makes
	
	/**
	 * A constructor to initialize the sudoku puzzle object and set all
	 * the values to 0.
	 */
	public SudokuMaker() {
		
		//Initialize puzzle
		puzzle = new int[ 9 ][ 9 ];
		
		//Set Values to 0
		for(int row = 0; row < puzzle.length; row++)
			for(int col = 0; col < puzzle[row].length; col++)
			puzzle[ row ][ col ] = 0;
	}
	/**
	 * A method which creates the puzzle and prints it out
	 */
	public void run(){
		//Create the Puzzle
		createPuzzle(0,0);
		
		//Print the Final Puzzle
		printPuzzle();
	}
	/**
	 * The recursive method that creates the Sudoku Puzzle via backtracking.
	 * Every call determines the value of a single number.
	 * 
	 * @param row	The x coordinate of the number to be decided with the call
	 * @param col	The y coordinate of the number to be decided with the call
	 * @return		true if there is a number between 1 and 9 which will correctly
	 * 				go in the place specified by the row and col variables.
	 * 				otherwise false
	 */
	public boolean createPuzzle(int row, int col) {
	
		//If there are no more numbers to initialize, the puzzle has been solved
		if( row == 9) return true;
		
		//Get numbers 1-9 inclusive in a randomly ordered list
		int[] randomList = makeRandomList();
		//Default to not being solvable
		boolean works = false;
		
		//Go through the numbers in the list until one works
		for( int x = 0; x < randomList.length; x++ ){
			//Assign a random number 1-9 inclusive to the element index
			puzzle[row][col]=randomList[x];
			
			/*If the number is not in the row, column, or box, then it
			 * adheres to the rules of sudoku and the program can proceed
			 * to the next element*/
			if( !isInRow( row, col) && !isInCol( row, col ) && 
				!isInBox( row, col ))
			{
				if( col + 1 == 9 ) works = createPuzzle( row + 1, 0 );
				else works = createPuzzle( row, col + 1);
				if( works ) return works;
			}
		}
		//If no number works, reset the index and go back
		if( !works ) puzzle[ row ][ col ] = 0;
		return works;
	}
	/**
	 * Checks to see if a prospective number at index (row, column) is 
	 * repeated anywhere else in the row
	 * 
	 * @param row 	The x coordinate of the prospective number
	 * @param col	The y coordinate of the prospective number
	 * @return 	true if the number is a repeat, otherwise false
	 */
	private boolean isInRow( int row, int col){
		
		/*Go through all the elements up until the prospective number and
		 *return true if they equal the prospective number */
		for( int x = 0; x < col; x ++ )
			if( puzzle[row][col] == puzzle[row][x])
				return true;
		
		//If not found, return false
		return false;
	}
	/**
	 * Checks to see if a prospective number at index (row, column) is 
	 * repeated anywhere else in the column
	 * 
	 * @param row 	The x coordinate of the prospective number
	 * @param col	The y coordinate of the prospective number
	 * @return 	true if the number is a repeat, otherwise false
	 */
	private boolean isInCol( int row, int col){
		
		/*Go through all the elements up until the prospective number and
		 *return true if they equal the prospective number */
		for( int x = 0; x < row; x ++ )
			if( puzzle[row][col] == puzzle[x][col])
				return true;
		
		//If not found, return false
		return false;
	}
	/**
	 * Checks to see if a prospective number at index (row, column) is 
	 * repeated anywhere else in the box
	 * 
	 * @param row 	The x coordinate of the prospective number
	 * @param col	The y coordinate of the prospective number
	 * @return 	true if the number is a repeat, otherwise false
	 */
	private boolean isInBox( int row, int col ){
		
		/*Go through all the elements in the box including the prosepective
		 * number and return true if any element except the prospective
		 * number is equivalent*/
		for( int x = col/3*3; x < (col/3+1)*3; x++)
			for( int y = row/3*3; y < (row/3+1)*3; y++)
				{
					if( puzzle[row][col] == puzzle[y][x] &&
						(row!=y || col!=x)) 
					{
						return true;
					}
				}
				
		//If not found, return false
		return false;
	}
	/**
	 * Make a random list of numbers 1-9 inclusive without repeats
	 * 
	 * @return a 9 element array holding numbers 1-9 inclusive in random order
	 */
	private int[] makeRandomList(){
		
		//Create array and variables to track the index and whether it repeats
		int[] list = new int[ 9 ];
		int x = 0;
		boolean rep = false;
		
		//Until the last element is initialized and not a repeat...
		while( list[ 8 ] == 0 || rep)
		{
			//Generate a random number between 1 and 9
			list[ x ]= (int)(Math.random()*9) + 1;
			rep = false;
			
			//Check prior values to check for repetition
			for(int y = 0; y < x; y++)
			if( list[x] == list[y] ) rep = true;
			
			//Move on to the next element if there is no repeat
			if( !rep ) x++;
		}
		
		//return the array
		return list;
	}
	/**
	 *	printPuzzle - prints the Sudoku puzzle with borders
	 *	If the value is 0, then print an empty space; otherwise, print the number.
	 */
	public void printPuzzle() {
		System.out.print("  +-----------+-----------+-----------+\n");
		String value = "";
		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[0].length; col++) {
				// if number is 0, print a blank
				if (puzzle[row][col] == 0) value = " ";
				else value = "" + puzzle[row][col];
				if (col % 3 == 0)
					System.out.print("  |  " + value);
				else
					System.out.print("  " + value);
			}
			if ((row + 1) % 3 == 0)
				System.out.print("  |\n  +-----------+-----------+-----------+\n");
			else
				System.out.print("  |\n");
		}
	}
	/**
	 * A method to get the puzzle from a client class
	 * @return	The object of the puzzle made by this class
	 */
	public int[][] getPuzzle()
	{
		return puzzle;
	}
	/**
	 * Create an instance of the class and call the method which runs all
	 * operations
	 * 
	 * @param args 	The strings entered after the name of the program on 
	 * 				the command line
	 */
	public static void main(String[] args)
	{
		SudokuMaker obj = new SudokuMaker();
		obj.run();
	}
}
