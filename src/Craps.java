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
import java.util.ArrayList;

public class Craps extends JFrame
{
    // testcommit
    private JLabel textNumPlayers;
    private JMenuBar navBar;
    private int numOfPlayers;
    private JMenu aboutMenu, instructionsMenu;
    private Game currentGame;

    private ArrayList<JPanel> scenes = new ArrayList<JPanel>();
    private int sceneIndex = 0;

    public Craps()
    {
        this.setTitle("Craps");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // slider creation inside constructor
        currentGame = new Game();
        createScenes();

        aboutMenu = new JMenu("About");
        instructionsMenu = new JMenu("Instructions");

        navBar = new JMenuBar();

        aboutMenu.addItemListener(new MenuListener());
        instructionsMenu.addItemListener(new MenuListener());

        navBar.add(instructionsMenu);
        navBar.add(aboutMenu);

        this.add(navBar, BorderLayout.NORTH);
        this.add(scenes.get(sceneIndex), BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void createScenes()
    {
        // these constraints are reused;
        GridBagConstraints gbcOne = new GridBagConstraints();
        gbcOne.gridy = 1;
        GridBagConstraints gbcTwo = new GridBagConstraints();
        gbcTwo.gridy = 2;

        JPanel firstScene = new JPanel();
        firstScene.setLayout(new GridBagLayout());
        firstScene.setPreferredSize(new Dimension(50, 50));

        JLabel gameTitle = new JLabel("Craps", SwingConstants.CENTER);
        gameTitle.setFont(new Font("Verdana", Font.BOLD, 40));
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(100, 30));
        playButton.addActionListener((ActionEvent e) ->
        {
            nextScene();
        });

        firstScene.add(gameTitle);
        firstScene.add(playButton, gbcOne);

        JPanel secondScene = new JPanel();
        secondScene.setLayout(new GridBagLayout());
        JLabel playerText = new JLabel("How many players will be playing (2 - 6) inclusive", SwingConstants.CENTER);
        playerText.setFont(new Font("Verdana", Font.BOLD, 20));
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 6, 2);
        slider.addChangeListener(new SliderHandler());
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener((ActionEvent e) ->
        {
            nextScene();
            currentGame.populatePlayerList(numOfPlayers);
            for (int i = 0; i < currentGame.getPlayerList().size(); ++i)
            {
                System.out.println("Player " + (i + 1) + " " + currentGame.getPlayerList().get(i).getName());
            }
        });

        secondScene.add(playerText);
        secondScene.add(slider, gbcOne);
        secondScene.add(submitButton, gbcTwo);

        JPanel thirdScene = new JPanel();
        thirdScene.setLayout(new GridBagLayout());

        scenes.add(firstScene);
        scenes.add(secondScene);
        scenes.add(thirdScene);
    }

    private void nextScene()
    {
        this.remove(scenes.get(sceneIndex));
        ++sceneIndex;
        this.add(scenes.get(sceneIndex));
        SwingUtilities.updateComponentTreeUI(this);
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

    private class MenuListener implements ItemListener
    {

        @Override
        public void itemStateChanged(ItemEvent e)
        {

            System.out.println(e.getStateChange());
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                JMenu source = (JMenu) e.getSource();
                if (source.equals(aboutMenu))
                {
                    JOptionPane.showMessageDialog(null, "About", "About", JOptionPane.PLAIN_MESSAGE);
                } else
                {
                    JOptionPane.showMessageDialog(null, "Instructions", "Instructions", JOptionPane.PLAIN_MESSAGE);
                }

            }

        }

    }

}