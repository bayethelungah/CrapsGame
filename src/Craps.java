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

<<<<<<< HEAD
=======
        // slider creation inside constructor
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 6, 2);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);

        slider.addChangeListener(new SliderHandler());

>>>>>>> 4c02fe58aaaa3c66b6e9437cc7a95243545d9375
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