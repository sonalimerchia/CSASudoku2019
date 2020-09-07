package HelloWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *	SudokuChecker - Solves an incomplete Sudoku puzzle using recursion and backtracking
 *
 *	@author	Sonali Merchia
 *	@since	January 28th, 2019
 *
 */
public class SudokuChecker {

	private int[][] puzzle;		// the Sudoku puzzle
	private int counter;

	public SudokuChecker() {
		puzzle = new int[9][9];
		// fill puzzle with zeros
		for (int row = 0; row < puzzle.length; row++)
			for (int col = 0; col < puzzle[0].length; col++)
				puzzle[row][col] = 0;

		counter = 0;
	}

	public int getNumSolutions( int[][] puzzle1 )
	{
		for (int row = 0; row < puzzle.length; row++)
			for (int col = 0; col < puzzle[0].length; col++)
				puzzle[row][col] = puzzle1[row][col];

		solvePuzzle( 0, 0 );
		int x = counter;
		counter = 0;

		return x;
	}

	/**	Solve the Sudoku puzzle using brute-force method. */
	public boolean solvePuzzle(int row, int col) {

		if( row == 9 ) {
			counter++;
			return true;
		}
		if( puzzle[row][col] != 0 )
			if( col+1 == 9 ) return solvePuzzle( row + 1, 0 );
			else return solvePuzzle( row, col + 1 );

		for( int x = 1; x <= 9; x++ ){
			puzzle[row][col] = x;

			if( !isInBox( row, col ) && !isInRow( row, col ) &&
				!isInCol( row, col ) ) {

					boolean b = false;
					if( col + 1 == 9 ) b = solvePuzzle( row + 1, 0 );
					else b = solvePuzzle( row, col + 1 );
				}
		}
		puzzle[row][col] = 0;
		return false;
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
		for( int x = 0; x < 9; x ++ )
			if( puzzle[row][col] == puzzle[row][x] && col != x)
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
		for( int x = 0; x < 9; x ++ )
			if( puzzle[row][col] == puzzle[x][col] && row != x)
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
}
