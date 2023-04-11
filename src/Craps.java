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
    private JMenuBar navBar;
    private int numOfPlayers;
    private JMenu aboutMenu, instructionsMenu;
    private Game currentGame;
    private ArrayList<Player> players;

    private ArrayList<JPanel> scenes = new ArrayList<JPanel>();
    private int sceneIndex = 0;

    public Craps()
    {
        this.setTitle("Craps");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        currentGame = new Game();
        createScenes();
        createNavBar();

        this.add(navBar, BorderLayout.NORTH);
        this.add(scenes.get(sceneIndex), BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void createScenes()
    {
        // these constraints are reused;
        GridBagConstraints gbcOne = new GridBagConstraints();
        gbcOne.gridy = 1;
        gbcOne.insets = new Insets(10, 0, 10, 0); // adding margin
        GridBagConstraints gbcTwo = new GridBagConstraints();
        gbcTwo.gridy = 2;
        gbcTwo.insets = new Insets(10, 0, 10, 0);

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
        JLabel playerText = new JLabel("How many players will be playing?", SwingConstants.CENTER);
        playerText.setFont(new Font("Verdana", Font.BOLD, 20));
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 6, 2);
        slider.setPreferredSize(new Dimension(500, 60));
        slider.addChangeListener(new SliderHandler());
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        JButton nextButton = new JButton("Next");

        nextButton.addActionListener((ActionEvent e) ->
        {
            if (numOfPlayers < 2)
            {
                numOfPlayers = 2;
            }
            nextScene();
            currentGame.populatePlayerList(numOfPlayers);
        });

        secondScene.add(playerText);
        secondScene.add(slider, gbcOne);
        secondScene.add(nextButton, gbcTwo);

        JPanel thirdScene = new JPanel();
        thirdScene.setLayout(new GridBagLayout());
        JLabel playerNameLabel = new JLabel("Enter the name of all the players on new lines", SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        JButton playerNameNexButton = new JButton("Next");
        JTextArea playerNamesField = new JTextArea();
        playerNamesField.setPreferredSize(new Dimension(200, 125));
        playerNamesField.setFont(new Font("Verdana", Font.PLAIN, 15));
        playerNameNexButton.addActionListener((ActionEvent event) ->
        {
            players = currentGame.getPlayerList();
            String[] names = playerNamesField.getText().split("\n");

            if (names.length < players.size())
            {
                JOptionPane.showMessageDialog(null, "Too few names", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            } else if (names.length > players.size())
            {
                JOptionPane.showMessageDialog(null, "Too many names", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            for (int i = 0; i < players.size(); ++i)
            {
                players.get(i).setName(names[i]);
            }

            buildFourthScene();
            nextScene();

        });

        thirdScene.add(playerNameLabel);
        thirdScene.add(playerNamesField, gbcOne);
        thirdScene.add(playerNameNexButton, gbcTwo);

        scenes.add(firstScene);
        scenes.add(secondScene);
        scenes.add(thirdScene);

    }

    private void buildFourthScene()
    {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        JPanel fourthScene = new JPanel();
        JPanel fourthSceneTop = new JPanel();
        JPanel fourthSceneMid = new JPanel();
        JPanel fourthSceneBot = new JPanel();
        fourthScene.setLayout(new GridBagLayout());
        fourthSceneTop.setLayout(new GridLayout(1, currentGame.getPlayerList().size()));
        fourthSceneMid.setLayout(new GridLayout(1, currentGame.getPlayerList().size()));
        fourthSceneBot.setLayout(new FlowLayout());
        JLabel[] playerLabels = new JLabel[currentGame.getPlayerList().size()];
        JLabel[] betLabels = new JLabel[currentGame.getPlayerList().size()];
        JTextField[] betTexts = new JTextField[currentGame.getPlayerList().size()];

        int shooterIndex = (int) Math.random() * players.size();
        players.get(shooterIndex).setIsShooter(true);

        for (int i = 0; i < currentGame.getPlayerList().size(); i++)
        {
            playerLabels[i] = new JLabel("<html>Player #" + (i + 1) + "<br>" + "Name: " + players.get(i).getName()
                    + "<br>" + "Bank Balance: " + players.get(i).getBankBalance() + "</html>", SwingConstants.CENTER);
            if (i == shooterIndex)
            {
                playerLabels[i].setForeground(Color.RED);
            }
            betLabels[i] = new JLabel("Player " + (i + 1) + " bet:", SwingConstants.CENTER);

            betTexts[i] = new JTextField();
            fourthSceneTop.add(playerLabels[i]);
            fourthSceneMid.add(betLabels[i]);
            fourthSceneMid.add(betTexts[i]);
        }

        JButton placeBets = new JButton("Confirm Bet");
        fourthSceneBot.add(placeBets);

        fourthScene.add(fourthSceneTop, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        fourthScene.add(fourthSceneMid, gbc);
        gbc.anchor = GridBagConstraints.SOUTH;
        fourthScene.add(fourthSceneBot, gbc);
        scenes.add(fourthScene);

    }

    private void createNavBar()
    {
        aboutMenu = new JMenu("About");
        instructionsMenu = new JMenu("Instructions");

        navBar = new JMenuBar();

        aboutMenu.addItemListener(new MenuListener());
        instructionsMenu.addItemListener(new MenuListener());

        navBar.add(instructionsMenu);
        navBar.add(aboutMenu);
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

            System.out.println("About is " + (aboutMenu.isSelected() ? "Selected" : "Diselected"));
            System.out.println("instructions is " + (instructionsMenu.isSelected() ? "Selected" : "Diselected"));
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