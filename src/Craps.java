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
    private JMenuBar navBar;
    private JOptionPane optionPane;
    private int numOfPlayers;

    public Craps()
    {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // slider creation inside constructor
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 6, 2);
        slider.setMajorTickSpacing(1);

        optionPane = new JOptionPane();
        optionPane.setVisible(false);

        navBar = new JMenuBar();

        JMenu instructionsMenu = new JMenu("Instructions");
        JMenu aboutMenu = new JMenu("About");

        aboutMenu.addItemListener((ItemEvent event) ->
        {
            if (event.getStateChange() == ItemEvent.SELECTED)
            {
                optionPane.setVisible(true);
                optionPane.showMessageDialog(null, "About");
            }

        });

        navBar.add(instructionsMenu);
        navBar.add(aboutMenu);

        this.add(navBar, BorderLayout.NORTH);
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Craps();
    }

}