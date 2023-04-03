/*
 * Class Name: Craps Purpose: This class Represents
 * Purpose: The application class for the Craps serving as a window
 * Coder: Bayethe, Keenan
 * Date: April 3, 2023;
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

public class Craps extends JFrame
{
    private JMenuBar navBar;
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
        slider.setPaintTicks(true);

        JButton aboutMenu = new JButton("About");
        JButton instructionsMenu = new JButton("Instructions");

        slider.addChangeListener(new SliderHandler());

        navBar = new JMenuBar();

        aboutMenu.addActionListener(new MenuButtonListener());
        instructionsMenu.addActionListener(new MenuButtonListener());

        navBar.add(instructionsMenu);
        navBar.add(aboutMenu);

        this.add(slider, BorderLayout.CENTER);
        this.add(navBar, BorderLayout.NORTH);
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Craps();
    }

    private class SliderHandler implements ChangeListener
    {

        @Override
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider) e.getSource();

            if (!source.getValueIsAdjusting())
            {
                numOfPlayers = (int) source.getValue();
            }
        }

    }

    private class MenuButtonListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("About"))
            {
                JOptionPane.showMessageDialog(null, "About", "About", JOptionPane.PLAIN_MESSAGE);
            } else
            {
                JOptionPane.showMessageDialog(null, "Instructions", "Instructions", JOptionPane.PLAIN_MESSAGE);
            }
        }

    }

}