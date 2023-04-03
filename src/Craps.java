/*
 * Class Name: Craps Purpose: This class Represents
 * Purpose: The application class for the Craps serving as a window
 * Coder: Bayethe, Keenan
 * Date: April 3, 2023;
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Craps extends JFrame
{
    JMenuBar navBar;

    public Craps()
    {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        navBar = new JMenuBar();

        navBar.setBackground(Color.BLACK);

        this.add(navBar, BorderLayout.NORTH);
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Craps();
    }
}