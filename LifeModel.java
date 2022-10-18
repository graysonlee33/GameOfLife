import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Timer;

public class LifeModel implements ActionListener
{

    /*
     *  This is the Model component.
     */

    private static int SIZE = 60;
    private LifeCell[][] grid;

    LifeView myView;
    Timer timer;

    /** Construct a new model using a particular file */
    public LifeModel(LifeView view, String fileName) throws IOException
    {
        int r, c;
        grid = new LifeCell[SIZE][SIZE];
        for ( r = 0; r < SIZE; r++ )
            for ( c = 0; c < SIZE; c++ )
                grid[r][c] = new LifeCell();

        if ( fileName == null ) //use random population
        {
            for ( r = 0; r < SIZE; r++ )
            {
                for ( c = 0; c < SIZE; c++ )
                {
                    if ( Math.random() > 0.85) //15% chance of a cell starting alive
                        grid[r][c].setAliveNow(true);
                }
            }
        }
        else
        {
            Scanner input = new Scanner(new File(fileName));
            int numInitialCells = input.nextInt();
            for (int count=0; count<numInitialCells; count++)
            {
                r = input.nextInt();
                c = input.nextInt();
                grid[r][c].setAliveNow(true);
            }
            input.close();
        }

        myView = view;
        myView.updateView(grid);

    }

    /** Constructor a randomized model */
    public LifeModel(LifeView view) throws IOException
    {
        this(view, null);
    }

    /** pause the simulation (the pause button in the GUI */
    public void pause()
    {
        timer.stop();
    }

    /** resume the simulation (the pause button in the GUI */
    public void resume()
    {
        timer.restart();
    }

    /** run the simulation (the pause button in the GUI */
    public void run()
    {
        timer = new Timer(50, this);
        timer.setCoalesce(true);
        timer.start();
    }

    public void step()
    {
        oneGeneration();
        myView.updateView(grid);
    }

    public void change()
    {

    }


    /** called each time timer fires */
    public void actionPerformed(ActionEvent e)
    {
        oneGeneration();
        myView.updateView(grid);
    }

    /** main logic method for updating the state of the grid / simulation */
    private void oneGeneration()
    {
        int num = 0;
        for (int r = 0; r < SIZE; r++)
        {
            for (int c = 0; c < SIZE; c++)
            {
                if (grid[r][c].isAliveNow()) //checking if it is occupied
                {
                    if (r == 0) //checking the top row
                    {
                        if (r == 0 && c == 0) //if it is a top left corner
                        {
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1)
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else if (r == 0 && c == SIZE - 1) //if it is a top right corner
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else //if it is regular
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            if (num >= 4) //if too many
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                    }
                    else if (r == SIZE - 1) //checking the bottom row
                    {
                        if (r == SIZE - 1 && c == 0) //if bottom left corner
                        {
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else if (r == SIZE - 1 && c == SIZE - 1) //if bottom right corner
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else //if it is a regular
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            if (num >= 4) //if too many
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                    }
                    else if (c == 0) //if it is on the left side of the array
                    {
                        if (r == 0 && c == 0) //if it is a top left corner
                        {
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1)
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else if (r == SIZE - 1 && c == 0) //if bottom left corner
                        {
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else //if it is a regular
                        {
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            if (num >= 4) //if too many
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                    }
                    else if (c == SIZE - 1) //on the right side of the array
                    {
                        if (r == 0 && c == SIZE - 1) //if it is a top right corner
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else if (r == SIZE - 1 && c == SIZE - 1) //if bottom right corner
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else //if it is a regular
                        {
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num <= 1) //if lonely
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            if (num >= 4) //if too many
                            {
                                grid[r][c].setAliveNext(false);
                            }
                            else if (num == 2 || num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                    }
                    else //anywhere inside the array
                    {
                        if (grid[r][c + 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r][c - 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r + 1][c].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r + 1][c - 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r + 1][c + 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r - 1][c].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r - 1][c - 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r - 1][c + 1].isAliveNow())
                        {
                            num++;
                        }
                        if (num <= 1) //if lonely
                        {
                            grid[r][c].setAliveNext(false);
                        }
                        if (num >= 4) //if too many
                        {
                            grid[r][c].setAliveNext(false);
                        }
                        else if (num == 2 || num == 3)
                        {
                            grid[r][c].setAliveNext(true);
                        }
                        num = 0;
                    }
                }
                else //if it is unoccupied
                {
                    if (r == 0) //checking the top row
                    {
                        if (r == 0 && c == 0) //if it is a top left corner
                        {
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3)
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else if (r == 0 && c == SIZE - 1) //if it is a top right corner
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else //if it is regular
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                    }
                    else if (r == SIZE - 1) //checking the bottom row
                    {
                        if (r == SIZE - 1 && c == 0) //if bottom left corner
                        {
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else if (r == SIZE - 1 && c == SIZE - 1) //if bottom right corner
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else //if it is a regular
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                    }
                    else if (c == 0) //if it is on the left side of the array
                    {
                        if (r == 0 && c == 0) //if it is a top left corner
                        {
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else if (r == SIZE - 1 && c == 0) //if bottom left corner
                        {
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else //if it is a regular
                        {
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c + 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r-1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                    }
                    else if (c == SIZE - 1) //on the right side of the array
                    {
                        if (r == 0 && c == SIZE - 1) //if it is a top right corner
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else if (r == SIZE - 1 && c == SIZE - 1) //if bottom right corner
                        {
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                        else //if it is a regular
                        {
                            if (grid[r - 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r - 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c - 1].isAliveNow())
                            {
                                num++;
                            }
                            if (grid[r + 1][c].isAliveNow())
                            {
                                num++;
                            }
                            if (num == 3) //if lonely
                            {
                                grid[r][c].setAliveNext(true);
                            }
                            num = 0;
                        }
                    }
                    else //anywhere inside the array
                    {
                        if (grid[r][c + 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r][c - 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r + 1][c].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r + 1][c - 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r + 1][c + 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r - 1][c].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r - 1][c - 1].isAliveNow())
                        {
                            num++;
                        }
                        if (grid[r - 1][c + 1].isAliveNow())
                        {
                            num++;
                        }
                        if (num == 3) //if lonely
                        {
                            grid[r][c].setAliveNext(true);
                        }
                        num = 0;
                    }
                }
            }
        }
        for(int rowe = 0; rowe < SIZE; rowe++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                if (grid[rowe][col].isAliveNext())
                {
                    grid[rowe][col].setAliveNow(true);
                }
                else
                {
                    grid[rowe][col].setAliveNow(false);
                }
            }
        }
    }


}

