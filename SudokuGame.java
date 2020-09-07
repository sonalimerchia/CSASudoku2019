package HelloWorld;

import java.awt.Color;
import java.awt.Font;
/**
 *  SudokuGame - uses the class SudokuMaker to create a puzzle and then
 *  uses StdDraw in order to create a workable sudoku game interface
 *
 *  @author Sonali Merchia
 *  @since January 22nd, 2019
 *
 */
public class SudokuGame
{
    private int[][] solution;
    private SingleBox[][] game;
    private int selectedNumber;
    private boolean inPlay;
    private long startTime;

    public SudokuGame()
    {
        SudokuMaker obj = new SudokuMaker();
        obj.createPuzzle( 0,0 );
        solution = obj.getPuzzle();

        setGivenNumbers();
        selectedNumber = 0;

        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale( 0, 10 );
        StdDraw.setYscale( 0, 10 );
        StdDraw.enableDoubleBuffering();

        startTime = System.currentTimeMillis();
        inPlay = true;
    }
    public static void main(String[]args)
    {
        SudokuGame obj = new SudokuGame();
        obj.play();
    }
    private void setGivenNumbers()
    {
        int[][] arr = new int[9][9];
        for (int row = 0; row < arr.length; row++)
            for (int col = 0; col < arr[0].length; col++)
                arr[row][col] = solution[row][col];

        SudokuChecker check = new SudokuChecker();
        int numRemovedNumbers = (int)(Math.random()*15 + 40 );
        int x = 0;
        while( x < numRemovedNumbers )
        {
            int row = (int)(Math.random()*9);
            int col = (int)(Math.random()*9);
            int save = arr[row][col];
            if( save != 0 )
            {
                arr[row][col] = 0;
                if( check.getNumSolutions( arr ) != 1 )
                {
                    arr[row][col] = save;
                }
                else x++;
            }
        }

        game = new SingleBox[9][9];
        for( int row = 0; row < game.length; row++)
            for( int col = 0; col < game[0].length; col++)
                if( arr[row][col] == 0)
                    game[row][col] = new SingleBox( row, col );
                            else game[row][col] = new SingleBox( row, col, arr[row][col]);
    }
    public void play()
    {
        while( inPlay ) {
            drawBoard();
            StdDraw.show();
            if( StdDraw.isMousePressed() )
            clicked( StdDraw.mouseX(), StdDraw.mouseY() );
        }
        drawBoard();

        StdDraw.setFont( new Font("Sans Serif", Font.BOLD, 50 ) );
        StdDraw.setPenColor( Color.GREEN );
        StdDraw.text( 5, 5, "SUCCESS!!!");
        StdDraw.show();
    }
    public void drawBoard()
    {
        StdDraw.setPenColor( Color.BLACK );
        StdDraw.filledRectangle(0, 0, 10, 10);

        StdDraw.setPenColor( Color.WHITE );
        for( int x = 0; x <= 10; x ++)
        {
            StdDraw.line( x, 0, x, 10 );
            StdDraw.line( 0, x, 10, x );
        }

        drawNumberBox();
        drawPuzzle();
        drawOptionsBox();

        for( int row = 0; row < game.length; row++ )
            for( int col = 0; col < game[row].length; col ++ )
                game[row][col].paintNumber();
    }
    private void drawPuzzle()
    {
        StdDraw.setPenColor( Color.YELLOW );
        StdDraw.filledSquare( 4, 6, 3.5 );

        StdDraw.setPenColor( Color.WHITE );
        StdDraw.filledSquare( 4, 6, 3.375  );

        StdDraw.setPenColor( Color.BLACK );
        for( double x = 0; x < 7.5; x += 0.75 )
        {
            if( (x/0.75) % 3 == 0 ) StdDraw.setPenRadius( 0.004 );
            else StdDraw.setPenRadius( 0.002 );
            StdDraw.line( x+0.625, 10-0.625, x+0.625, 10-(0.625+0.75*9));
            StdDraw.line( 0.625, x+2.625, 0.625+0.75*9, x+2.625);
        }
        StdDraw.setPenRadius( 0.002 );
    }
    private void drawNumberBox()
    {
        StdDraw.setPenColor( Color.YELLOW );
        StdDraw.filledRectangle( 9, 5, 0.6, 4.6 );

        StdDraw.setPenColor( Color.WHITE );
        StdDraw.filledRectangle( 9, 5, 0.5, 4.5 );

        StdDraw.setPenColor( Color.BLACK );
        for( int x = 0; x <= 8; x++ )
        {
            StdDraw.line( 8.5, 0.5 + x, 9.5, 0.5 + x );
            StdDraw.text( 9, x + 1, "" +(int)(9 - x) );
        }
        StdDraw.rectangle( 9, 5, 0.5, 4.5 );

    }
    private void drawOptionsBox()
    {
        StdDraw.setPenColor( Color.YELLOW );
        StdDraw.filledRectangle( 4, 1, 2.6, 0.6 );

        StdDraw.setPenColor( Color.WHITE );
        StdDraw.filledRectangle( 4, 1, 2.5, 0.5 );

        StdDraw.setPenColor( Color.BLACK );
        StdDraw.rectangle( 4, 1, 2.5, 0.5 );
        for( int x = 3; x < 6; x ++ )
        StdDraw.line( x + 0.5, 1.5, x + 0.5, 0.5 );

        Font bold = new Font("Sans Serif", Font.BOLD, 16);
        Font reg = new Font("Sans Serif", Font.PLAIN, 16);

        StdDraw.setFont( bold );
        StdDraw.text( 2.5, 1, getTime() );

        if( selectedNumber > 9 ) StdDraw.setFont( reg );
        StdDraw.text( 4, 1, "Play", 45);
        if( selectedNumber != 10 ) StdDraw.setFont( reg );
        else StdDraw.setFont( bold );
        StdDraw.text( 5, 1, "Erase", 45);

        StdDraw.setFont( reg );
        StdDraw.text( 6, 1, "Check", 45);
    }
    private String getTime()
    {
        long t = System.currentTimeMillis() - startTime;
        t = t/1000;
        String min = "" + (t / 60);
        String sec = "" + (t % 60);
        if( sec.length() == 1 ) sec = "0"+sec;
        return "" + min + " : " + sec;
    }
    private void clicked( double x, double y )
    {
        if( 8.5 <= x && x <= 9.5 )
            selectedNumber( y );
        else if( 0.5 <= y && y <= 1.5 && 3.5 <= x && x <= 6.5 )
            mode( (int)(x - 3.5) );
        else if( 0.625 <= x && x <= 7.375 ) {
            int a = (int)((x - 0.625)/0.75);
            int b = (int)(9 - (y - 2.625)/0.75);
            if( 0 <= a && a < 9 && 0 <= b && b < 9)
            attempt( a, b );
        }
    }
    private void selectedNumber( double y )
    {
        for( int x = 1; x <= 9; x ++ )
        {
            if( 9 - (int)(y-0.5) == x ) selectedNumber = x;
        }
    }
    private void mode( int code )
    {
        switch( code ){
            case 0: selectedNumber = 0;
            break;
            case 1: selectedNumber = 10;
            break;
            case 2: checkProgress();
        }
    }
    private void attempt( int x, int y)
    {
        if( selectedNumber == 10 )
            game[ y ][ x ].setNumber( 0 );
        else if( selectedNumber != 0 )
            game[ y ][ x ].setNumber( selectedNumber );
        checkGame();
    }
    private void checkProgress()
    {
        for( int row = 0; row < 9; row ++ )
            for( int col = 0; col < 9; col ++ )
            if( solution[ row ][ col ] != game[ row ][ col ].getNumber() )
            game[ row ][ col ].setNumber( 0 );
    }
    private void checkGame()
    {
        boolean win = true;
        for( int row = 0; row < 9; row ++ )
            for( int col = 0; col < 9; col ++ )
            if( solution[ row ][ col ] != game[ row ][ col ].getNumber() ) win = false;
        inPlay = !win;
    }
}
