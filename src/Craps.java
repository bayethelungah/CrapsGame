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
import java.util.Timer;
import java.util.TimerTask;

public class Craps extends JFrame
{
    private JMenuBar navBar;
    private int numOfPlayers;
    private JMenu aboutMenu, instructionsMenu;
    private Game currentGame;
    private ArrayList<Player> players;

    private ArrayList<JPanel> scenes = new ArrayList<JPanel>();
    private int sceneIndex = 0;

    private static final int GAME_BEGINNING_SCENE = 3;

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
        JLabel creatorsLabel = new JLabel("By Bayethe and Keenan", SwingConstants.CENTER);
        gameTitle.setFont(new Font("Verdana", Font.BOLD, 60));
        creatorsLabel.setFont(new Font("Verdana", Font.ITALIC, 20));
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(100, 30));
        playButton.addActionListener((ActionEvent e) ->
        {
            nextScene();
        });

        firstScene.add(gameTitle);
        firstScene.add(creatorsLabel, gbcOne);
        firstScene.add(playButton, gbcTwo);

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
        currentGame.setTotalPotAmount(currentGame.getPlayerList().size());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        JPanel fourthScene = new JPanel();
        JPanel fourthSceneTop = new JPanel();
        JPanel fourthSceneInfo = new JPanel();
        JPanel fourthSceneMid = new JPanel();
        JPanel fourthSceneBot = new JPanel();
        fourthScene.setLayout(new GridBagLayout());
        fourthSceneTop.setLayout(new GridLayout(1, currentGame.getPlayerList().size(), 15, 5));
        fourthSceneInfo.setLayout(new GridLayout(2, 1));
        fourthSceneMid.setLayout(new GridLayout(1, currentGame.getPlayerList().size()));
        fourthSceneBot.setLayout(new FlowLayout());
        JLabel[] playerLabels = new JLabel[currentGame.getPlayerList().size()];
        JLabel[] betLabels = new JLabel[currentGame.getPlayerList().size()];
        JTextField[] betTexts = new JTextField[currentGame.getPlayerList().size()];

        for (int i = 0; i < currentGame.getPlayerList().size(); i++)
        {
            playerLabels[i] = new JLabel("<html>Player #" + (i + 1) + "<br>" + "Name: " + players.get(i).getName()
                    + "<br>" + "Bank Balance: $" + players.get(i).getBankBalance() + "</html>", SwingConstants.CENTER);

            if (i == currentGame.getShooterIndex())
            {
                playerLabels[i].setForeground(Color.RED);
                betTexts[i] = new JTextField("10");
            } else
            {
                betTexts[i] = new JTextField("0");
            }
            betLabels[i] = new JLabel("bet:", SwingConstants.CENTER);

            fourthSceneTop.add(playerLabels[i]);
            fourthSceneMid.add(betLabels[i]);
            fourthSceneMid.add(betTexts[i]);
        }

        JLabel instructionsLabel = new JLabel(
                "Shooter (" + currentGame.getPlayerList().get(currentGame.getShooterIndex()).getName()
                        + "), Please Enter a bet (Minimum $10)",
                SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        fourthSceneInfo.add(instructionsLabel);

        JButton placeShooterBets = new JButton("Confirm Shooter Bet");
        JButton placeOpponentBets = new JButton("Confirm Opposing Bets");
        placeOpponentBets.setEnabled(false);
        fourthSceneBot.add(placeShooterBets);
        fourthSceneBot.add(placeOpponentBets);

        fourthScene.add(fourthSceneInfo, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        fourthScene.add(fourthSceneTop, gbc);
        fourthScene.add(fourthSceneMid, gbc);
        gbc.anchor = GridBagConstraints.SOUTH;
        fourthScene.add(fourthSceneBot, gbc);

        placeShooterBets.addActionListener((ActionEvent event) ->
        {
            try
            {
                int bet = Integer.parseInt(betTexts[currentGame.getShooterIndex()].getText());

                if (bet > currentGame.getPlayerList().get(currentGame.getShooterIndex()).getBankBalance())
                {
                    JOptionPane.showMessageDialog(null, "The Amount Betted Exceeds The shooters Bank balance", "Error",
                            JOptionPane.PLAIN_MESSAGE);
                    return;
                }

                if (bet % 10 != 0) // checking if bet is multiple of 10
                {
                    JOptionPane.showMessageDialog(null, "Please Enter a Natural number that is a Multiple of 10",
                            "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }

                System.out.println("Shooter Bet: " + bet);
                currentGame.getPlayerList().get(currentGame.getShooterIndex()).setAmountBet(bet);
                placeOpponentBets.setEnabled(true);

                instructionsLabel.setText("Opponents can place their bets on the pool of: $"
                        + players.get(currentGame.getShooterIndex()).getAmountBet());

            } catch (NumberFormatException exc)
            {
                JOptionPane.showMessageDialog(null, "Please Enter a Natural number that is a Multiple of 10", "Error",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        placeOpponentBets.addActionListener((ActionEvent event) ->
        {
            int actionAmountCovered = 0;
            for (int i = 0; i < betTexts.length; ++i)
            {
                if (currentGame.getShooterIndex() == i)
                    continue;
                try
                {
                    int bet = Integer.parseInt(betTexts[i].getText());

                    if (bet > players.get(currentGame.getShooterIndex()).getAmountBet())
                    {
                        JOptionPane.showMessageDialog(null, players.get(i).getName() + "'s bet is too large");
                        return;
                    }

                    currentGame.getPlayerList().get(i).setAmountBet(bet);
                    actionAmountCovered += bet;
                    System.out.println("Opponent Bet: " + bet);

                } catch (NumberFormatException exc)
                {
                    JOptionPane.showMessageDialog(null, players.get(i).getName() + "'s bet is not a natural number");
                    return;
                }

            }

            bulidFifthScene(new Pass(currentGame.getShooterIndex(),
                    currentGame.getPlayerList().get(currentGame.getShooterIndex()).getAmountBet(),
                    actionAmountCovered));
            nextScene();
            // Collections.swap(scenes, 3, 4);

        });

        scenes.add(3, fourthScene);
    }

    private void deleteGameScenes()
    {
        JPanel scene = scenes.get(3);
        scenes.remove(scene);
    }

    private void bulidFifthScene(Pass round)
    {
        JPanel fifthScene = new JPanel();
        fifthScene.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;

        JLabel rollResultText = new JLabel("Roll Value: ", SwingConstants.CENTER);
        rollResultText.setFont(new Font("Verdana", Font.BOLD, 40));
        JLabel rollStatus = new JLabel(" ");
        rollStatus.setFont(new Font("Verdana", Font.BOLD, 30));

        Die die = new Die();

        JButton rollDice = new JButton("Roll Dice");
        fifthScene.add(rollResultText, gbc);
        gbc.gridy = 2;
        fifthScene.add(rollStatus, gbc);
        gbc.gridy = 3;
        fifthScene.add(rollDice, gbc);

        rollDice.addActionListener((ActionEvent event) ->
        {
            int rollValue = die.rollDie() + die.rollDie();
            rollResultText.setText("Roll Value: " + rollValue);

            if (round.getFirstRoll())
            {

                if (rollValue == 7 || rollValue == 11)
                {
                    endGame(true, currentGame.getPlayerList(), round, rollStatus);
                }

                if (rollValue == 2 || rollValue == 3 || rollValue == 12)
                {
                    endGame(false, currentGame.getPlayerList(), round, rollStatus);
                }

                round.setFirstRoll(false);
                round.setShooterPoint(rollValue);

            } else
            {
                if (rollValue == round.getShooterPoint())
                {
                    endGame(true, currentGame.getPlayerList(), round, rollStatus);
                }

                if (rollValue == 7)
                {
                    endGame(false, currentGame.getPlayerList(), round, rollStatus);
                }
            }
        });

        try
        {
            JPanel previousFifthScene = scenes.get(4);
            scenes.remove(previousFifthScene);
        } catch (IndexOutOfBoundsException exc)
        {

        }

        scenes.add(4, fifthScene);
    }

    private void endGame(boolean shooterWin, ArrayList<Player> players, Pass round, JLabel diceText)
    {
        String finishingText = shooterWin ? " has Won The roll" : " has lost the roll";
        diceText.setText(players.get(currentGame.getShooterIndex()).getName() + finishingText);

        // delays asking the user if they want to shoot again.
        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                round.settleBets(shooterWin, currentGame.getPlayerList());
                currentGame.updatePlayerList();

                if (!round.shootOrPass())
                {
                    currentGame.updateShooterIndex();
                }

                deleteGameScenes();
                buildFourthScene();
                nextScene();
            }
        }, 1200l);
        ;
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
        if (sceneIndex >= scenes.size())
        {
            sceneIndex = GAME_BEGINNING_SCENE;
        }
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