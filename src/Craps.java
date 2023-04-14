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
        JButton playerNameNextButton = new JButton("Next");
        JTextArea playerNamesField = new JTextArea();
        playerNamesField.setPreferredSize(new Dimension(200, 125));
        playerNamesField.setFont(new Font("Verdana", Font.PLAIN, 15));

        playerNameNextButton.addActionListener((ActionEvent event) ->
        {
            ArrayList<Player> players = currentGame.getPlayerList();
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
        thirdScene.add(playerNameNextButton, gbcTwo);

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
        JPanel fourthSceneButtonArea = new JPanel();
        JPanel fourthSceneBot = new JPanel();

        ArrayList<Player> players = currentGame.getPlayerList();
        fourthScene.setLayout(new GridBagLayout());
        fourthSceneTop.setLayout(new GridLayout(1, players.size(), 15, 5));
        fourthSceneInfo.setLayout(new GridLayout(2, 1));
        fourthSceneMid.setLayout(new GridLayout(1, players.size()));
        fourthSceneButtonArea.setLayout(new GridLayout(1, players.size()));
        fourthSceneBot.setLayout(new GridLayout(1, players.size()));

        JLabel[] playerLabels = new JLabel[players.size()];
        JLabel[] betLabels = new JLabel[players.size()];
        JTextField[] betTexts = new JTextField[players.size()];
        JButton[] betButtons = new JButton[players.size()];

        for (int i = 0; i < players.size(); i++)
        {
            playerLabels[i] = new JLabel("<html>Player #" + (i + 1) + "<br>" + "Name: " + players.get(i).getName()
                    + "<br>" + "Bank Balance: $" + players.get(i).getBankBalance() + "</html>", SwingConstants.CENTER);

            betLabels[i] = new JLabel("bet:  $", SwingConstants.CENTER);
            betButtons[i] = new JButton("Confirm");
            betButtons[i].addActionListener(new BetButtonListener());

            if (i == currentGame.getShooterIndex())
            {
                playerLabels[i].setForeground(Color.RED);
                betTexts[i] = new JTextField("10");
                BetButtonListener.currentPlayerIndex = i;
            } else
            {
                betTexts[i] = new JTextField("0");
                betTexts[i].setEnabled(false);
                betButtons[i].setEnabled(false);
            }

            betTexts[i].addActionListener(new BetFieldListener(betButtons[i]));
            JPanel betInfo = new JPanel();
            betInfo.setLayout(new GridBagLayout());
            GridBagConstraints betConstraints = new GridBagConstraints();
            betInfo.add(betLabels[i], betConstraints);
            betConstraints.gridx = 1;
            betConstraints.fill = GridBagConstraints.HORIZONTAL;
            betInfo.add(betTexts[i], betConstraints);
            betConstraints.gridy = 1;
            betInfo.add(betButtons[i], betConstraints);

            fourthSceneTop.add(playerLabels[i]);
            fourthSceneMid.add(betInfo);
        }

        JLabel instructionsLabel = new JLabel("Shooter (" + players.get(currentGame.getShooterIndex()).getName()
                + "), Please Enter a bet (Minimum $10)", SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        fourthSceneInfo.add(instructionsLabel);

        BetButtonListener.buttons = betButtons;
        BetButtonListener.fields = betTexts;
        BetButtonListener.instructions = instructionsLabel;
        BetButtonListener.players = players;

        fourthScene.add(fourthSceneInfo, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        fourthScene.add(fourthSceneTop, gbc);
        fourthScene.add(fourthSceneMid, gbc);
        fourthScene.add(fourthSceneButtonArea, gbc);
        gbc.anchor = GridBagConstraints.SOUTH;
        fourthScene.add(fourthSceneBot, gbc);

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
                    rollDice.setEnabled(false);
                }

                if (rollValue == 2 || rollValue == 3 || rollValue == 12)
                {
                    endGame(false, currentGame.getPlayerList(), round, rollStatus);
                    rollDice.setEnabled(false);
                }

                round.setFirstRoll(false);
                round.setShooterPoint(rollValue);

            } else
            {
                if (rollValue == round.getShooterPoint())
                {
                    endGame(true, currentGame.getPlayerList(), round, rollStatus);
                    rollDice.setEnabled(false);
                }

                if (rollValue == 7)
                {
                    endGame(false, currentGame.getPlayerList(), round, rollStatus);
                    rollDice.setEnabled(false);
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

    private class BetButtonListener implements ActionListener
    {
        private static JButton[] buttons;
        private static JTextField[] fields;
        private static int actionAmountCovered = 0;
        private static int currentPlayerIndex;
        private static JLabel instructions;
        private static ArrayList<Player> players;

        private static int nextPlayerIndex()
        {
            return (currentPlayerIndex + 1) % players.size();
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Clicked Button");

            try
            {
                int bet = Integer.parseInt(fields[currentPlayerIndex].getText());

                boolean isShooter = players.get(currentPlayerIndex).getIsShooter();

                if (bet % 10 != 0)
                {
                    JOptionPane.showMessageDialog(null, "Please Enter a natural number that is a multiple of 10");
                    return;
                }
                if (bet < 10)
                {
                    JOptionPane.showMessageDialog(null, "You must bet at minimum of $10");
                    return;
                }

                if (!isShooter && bet > actionAmountCovered)
                {
                    JOptionPane.showMessageDialog(null, "You cannot place a bet that is larger then the pool");
                    return;
                }

                if (bet > players.get(currentPlayerIndex).getBankBalance())
                {
                    JOptionPane.showMessageDialog(null,
                            "You cannot place a bet that is greater then your bank account");
                    return;
                }

                players.get(currentPlayerIndex).setAmountBet(bet);
                boolean lastBet = players.get(nextPlayerIndex()).getIsShooter();

                if (isShooter)
                {
                    actionAmountCovered = bet;
                } else
                {
                    actionAmountCovered -= bet;

                }

                if (!lastBet)
                {

                    buttons[currentPlayerIndex].setEnabled(false);
                    fields[currentPlayerIndex].setEnabled(false);

                    if (actionAmountCovered > 0)
                    {
                        instructions.setText(players.get(nextPlayerIndex()).getName() + " please make a bet (Maximum $"
                                + actionAmountCovered + ")");

                        buttons[nextPlayerIndex()].setEnabled(true);
                        fields[nextPlayerIndex()].setEnabled(true);
                    } else
                    {
                        instructions.setText("No More Bets");

                        new Timer().schedule(new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                bulidFifthScene(new Pass(currentGame.getShooterIndex(),
                                        currentGame.getPlayerList().get(currentGame.getShooterIndex()).getAmountBet(),
                                        actionAmountCovered));
                                nextScene();
                            }
                        }, 1200l);

                    }

                } else
                {
                    bulidFifthScene(new Pass(currentGame.getShooterIndex(),
                            currentGame.getPlayerList().get(currentGame.getShooterIndex()).getAmountBet()-actionAmountCovered,
                            actionAmountCovered));
                    nextScene();
                }

                currentPlayerIndex = nextPlayerIndex();
            } catch (NumberFormatException exc)
            {
                JOptionPane.showMessageDialog(null,
                        players.get(currentPlayerIndex).getName() + "'s bet is not a natural number");
                return;
            }

        }

    }

    private class BetFieldListener implements ActionListener
    {
        public JButton button;

        public BetFieldListener(JButton button)
        {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {

            button.doClick();
        }

    }

}