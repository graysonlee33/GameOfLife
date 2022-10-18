import java.awt.*;
import javax.swing.*;
import java.util.*;

/** The view (graphical) component */
public class LifeView extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static int SIZE = 60;
    private boolean isColor;

    /** store a reference to the current state of the grid */
    private LifeCell[][] grid;

    public LifeView()
    {
        isColor = false;
        grid = new LifeCell[SIZE][SIZE];
    }

    /** entry point from the model, requests grid be redisplayed */
    public void updateView( LifeCell[][] mg )
    {
        grid = mg;
        repaint();
    }

    public void setColor(boolean c)
    {
        isColor = c;
    }

    public boolean getColor()
    {
        return isColor;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int testWidth = getWidth() / (SIZE+1);
        int testHeight = getHeight() / (SIZE+1);
        // keep each life cell square
        int boxSize = Math.min(testHeight, testWidth);

        for ( int r = 0; r < SIZE; r++ )
        {
            for (int c = 0; c < SIZE; c++ )
            {
                if (grid[r][c] != null)
                {
                    if ( grid[r][c].isAliveNow() )
                        if (getColor())
                        {
                            Random rand = new Random();
                            int colorOne = rand.nextInt(255);
                            int colorTwo = rand.nextInt(255);
                            int colorThree = rand.nextInt(255);
                            g.setColor(new Color(colorOne, colorTwo, colorThree));
                        }
                        else
                        {
                            g.setColor( Color.BLUE );
                        }

                    else
                        g.setColor( new Color(235,235,255) );

                    g.fillRect( (c+1)*boxSize, (r+1)* boxSize, boxSize-2, boxSize-2);
                }
            }
        }
    }
}
