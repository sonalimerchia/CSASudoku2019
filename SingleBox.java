package HelloWorld;

import java.awt.Color;
/**
 * A simple class which holds a value and its coordinates as well as a
 * boolean and the ability to print itself when called
 *
 * @author Sonali Merchia
 * @since January 28th, 2019
 */
public class SingleBox
{
	private int number;
	private int row;
	private int column;
	private boolean given;

	public SingleBox( int rw, int col )
	{
		row = rw;
		column = col;
		number = 0;
		given = false;
	}
	public SingleBox( int rw, int col, int val )
	{
		row = rw;
		column = col;
		number = val;
		given = true;
	}
	public int getRow()
	{	return row;		}

	public int getCol()
	{	return column;	}

	public int getNumber()
	{	return number;	}

	public void setNumber( int value )
	{
	    if( !given )
	    number = value;
	}

	public void paintNumber()
	{
		if( given ) StdDraw.setPenColor( Color.BLACK );
		else StdDraw.setPenColor( Color.RED );

		if( number != 0  )
		StdDraw.text(1 + column*0.75 ,9 - 0.75*row, ""+number );
	}
}
