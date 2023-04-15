/*
 * Class Name: Craps
 * Purpose: The application class for the Craps serving as a window
 * Coder: Bayethe, Keenan
 * Date: April 3, 2023;
 */

 //importing import
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
    //useful variables
    private JMenuBar navBar;
    private int numOfPlayers;
    private JMenu aboutMenu, instructionsMenu;
    private Game currentGame;

    private ArrayList<JPanel> scenes = new ArrayList<JPanel>();
    private int sceneIndex = 0;

    private static final int GAME_BEGINNING_SCENE = 3;

    //constructor
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

    /**
    Method Name:    createScenes() <br>
    Purpose:        a private instance method that creates the first 3 scenes <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private void createScenes()
    {
        // these constraints are reused;
        GridBagConstraints gbcOne = new GridBagConstraints();
        gbcOne.gridy = 1;
        gbcOne.insets = new Insets(10, 0, 10, 0); // adding margin
        GridBagConstraints gbcTwo = new GridBagConstraints();
        gbcTwo.gridy = 2;
        gbcTwo.insets = new Insets(10, 0, 10, 0);

        //creating first scene 
        JPanel firstScene = new JPanel();
        firstScene.setLayout(new GridBagLayout());
        firstScene.setPreferredSize(new Dimension(50, 50));

        //components of the first scene
        JLabel gameTitle = new JLabel("Craps", SwingConstants.CENTER);
        JLabel creatorsLabel = new JLabel("By Bayethe and Keenan", SwingConstants.CENTER);
        gameTitle.setFont(new Font("Verdana", Font.BOLD, 60));
        creatorsLabel.setFont(new Font("Verdana", Font.ITALIC, 20));
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(100, 30));
        //action listener which goes to the next scene when clicked
        playButton.addActionListener((ActionEvent e) ->
        {
            nextScene();
        });

        //adding components to the first scene
        firstScene.add(gameTitle);
        firstScene.add(creatorsLabel, gbcOne);
        firstScene.add(playButton, gbcTwo);

        //creating the components second scene
        JPanel secondScene = new JPanel();
        secondScene.setLayout(new GridBagLayout());
        JLabel playerText = new JLabel("How many players will be playing?", SwingConstants.CENTER);
        playerText.setFont(new Font("Verdana", Font.BOLD, 20));
        //slider which holds the player count
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 6, 2);
        //slider settings
        slider.setPreferredSize(new Dimension(500, 60));
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new SliderHandler());
        JButton nextButton = new JButton("Next");
        //labda action listener for when player is done selecting amount of players
        nextButton.addActionListener((ActionEvent e) ->
        {
            //checks count of players to see if slider was never changed
            if (numOfPlayers < 2)
            {
                numOfPlayers = 2;
            }
            //goes to next scene
            nextScene();
            //populates player list with number of players gotten from slider
            currentGame.populatePlayerList(numOfPlayers);
        });
        //adding components to second scene
        secondScene.add(playerText);
        secondScene.add(slider, gbcOne);
        secondScene.add(nextButton, gbcTwo);

        //creating 3rd scene and components
        JPanel thirdScene = new JPanel();
        thirdScene.setLayout(new GridBagLayout());
        JLabel playerNameLabel = new JLabel("Enter the name of all the players on new lines", SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        JButton playerNameNextButton = new JButton("Next");
        JTextArea playerNamesField = new JTextArea();
        playerNamesField.setPreferredSize(new Dimension(200, 125));
        playerNamesField.setFont(new Font("Verdana", Font.PLAIN, 15));

        //labda action listenner for when user has entered all the names
        playerNameNextButton.addActionListener((ActionEvent event) ->
        {
            //gets the list of player names
            ArrayList<Player> players = currentGame.getPlayerList();
            //gets each player names from the text field using newline as delimiter
            String[] names = playerNamesField.getText().split("\n");

            //checking if the amount of names entered is correct
            if (names.length < players.size())
            {
                //message pop up for too few names
                JOptionPane.showMessageDialog(null, "Too few names", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            } else if (names.length > players.size())
            {
                //message pop up for too many names
                JOptionPane.showMessageDialog(null, "Too many names", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            //setting each player name to that from the text field
            for (int i = 0; i < players.size(); ++i)
            {
                players.get(i).setName(names[i]);
            }

            //building the fourth scene
            buildFourthScene();
            //going to next scene
            nextScene();

        });

        //adding 3rd scene components
        thirdScene.add(playerNameLabel);
        thirdScene.add(playerNamesField, gbcOne);
        thirdScene.add(playerNameNextButton, gbcTwo);

        //adding all created scenes to jframe.
        scenes.add(firstScene);
        scenes.add(secondScene);
        scenes.add(thirdScene);

    }

    /**
    Method Name:    buildFourthScene() <br>
    Purpose:        a private instance method that creates the fourth scene <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private void buildFourthScene()
    {
        //setting the total pot based on total players playing
        currentGame.setTotalPotAmount(currentGame.getPlayerList().size());

        //grid bag settings
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        //fourth scene panels
        JPanel fourthScene = new JPanel();
        JPanel fourthSceneTop = new JPanel();
        JPanel fourthSceneInfo = new JPanel();
        JPanel fourthSceneMid = new JPanel();
        JPanel fourthSceneButtonArea = new JPanel();
        JPanel fourthSceneBot = new JPanel();

        //getting player list for future use
        ArrayList<Player> players = currentGame.getPlayerList();
        //changing panel layouts and settings
        fourthScene.setLayout(new GridBagLayout());
        fourthSceneTop.setLayout(new GridLayout(1, players.size(), 15, 5));
        fourthSceneInfo.setLayout(new GridLayout(2, 1));
        fourthSceneMid.setLayout(new GridLayout(1, players.size()));
        fourthSceneButtonArea.setLayout(new GridLayout(1, players.size()));
        fourthSceneBot.setLayout(new GridLayout(1, players.size()));

        //arrays which hold components relavent to each player display
        JLabel[] playerLabels = new JLabel[players.size()];
        JLabel[] betLabels = new JLabel[players.size()];
        JTextField[] betTexts = new JTextField[players.size()];
        JButton[] betButtons = new JButton[players.size()];

        //loop to display each player with their name and a place to enter a bet and confirm it
        for (int i = 0; i < players.size(); i++)
        {
            playerLabels[i] = new JLabel("<html>Player #" + (i + 1) + "<br>" + "Name: " + players.get(i).getName()
                    + "<br>" + "Bank Balance: $" + players.get(i).getBankBalance() + "</html>", SwingConstants.CENTER);

            betLabels[i] = new JLabel("bet:  $", SwingConstants.CENTER);
            betButtons[i] = new JButton("Confirm");
            betButtons[i].addActionListener(new BetButtonListener());

            //sets their text color to red if they are the shooter
            if (i == currentGame.getShooterIndex())
            {
                playerLabels[i].setForeground(Color.RED);
                betTexts[i] = new JTextField("10");
                BetButtonListener.currentPlayerIndex = i;
            } else
            {
                betTexts[i] = new JTextField("10");
                betTexts[i].setEnabled(false);
                betButtons[i].setEnabled(false);
            }

            
            //adding each button, label, and text to the panel;
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

        //creating a label to give the player instructions as the game progresses
        JLabel instructionsLabel = new JLabel("Shooter (" + players.get(currentGame.getShooterIndex()).getName()
                + "), Please Enter a bet (Minimum $10)", SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        fourthSceneInfo.add(instructionsLabel);

        //assigning the buttonlistener with the arrays from this scene so they can be updated
        BetButtonListener.buttons = betButtons;
        BetButtonListener.fields = betTexts;
        BetButtonListener.instructions = instructionsLabel;
        BetButtonListener.players = players;

        //adding all panels in specific locations
        fourthScene.add(fourthSceneInfo, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        fourthScene.add(fourthSceneTop, gbc);
        fourthScene.add(fourthSceneMid, gbc);
        fourthScene.add(fourthSceneButtonArea, gbc);
        gbc.anchor = GridBagConstraints.SOUTH;
        fourthScene.add(fourthSceneBot, gbc);

        //adding this scene to a specific index so it never is out of place
        scenes.add(3, fourthScene);
    }

    /**
    Method Name:    deleteGameScenes() <br>
    Purpose:        a private instance method that removes a specific scene <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private void deleteGameScenes()
    {
        //gets the fourth scene at index 3 to delete
        JPanel scene = scenes.get(3);
        scenes.remove(scene);
    }

    /**
    Method Name:    bulidFifthScene() <br>
    Purpose:        a private instance method that builds the fifth scene <br>
    Accepts:        an instance of a Pass <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private void bulidFifthScene(Pass round)
    {
        //creating the fifth scene
        JPanel fifthScene = new JPanel();
        fifthScene.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;

        //changing label settings
        JLabel rollResultText = new JLabel("Roll Value: ", SwingConstants.CENTER);
        rollResultText.setFont(new Font("Verdana", Font.BOLD, 40));
        JLabel rollStatus = new JLabel(" ");
        rollStatus.setFont(new Font("Verdana", Font.BOLD, 30));

        //creating a die object
        Die die = new Die();

        //button for the player to roll dice
        JButton rollDice = new JButton("Roll Dice");
        fifthScene.add(rollResultText, gbc);
        gbc.gridy = 2;
        fifthScene.add(rollStatus, gbc);
        gbc.gridy = 3;
        fifthScene.add(rollDice, gbc);

        //dice button action listener
        rollDice.addActionListener((ActionEvent event) ->
        {
            //storing the result of 2 dice rolls
            int rollValue = die.rollDie() + die.rollDie();
            //displaying that result
            rollResultText.setText("Roll Value: " + rollValue);

            //checking if its the first roll since the rules adjust after the first roll
            if (round.getFirstRoll())
            {
                //shooter will win out of the gate
                if (rollValue == 7 || rollValue == 11)
                {
                    //ending the pass
                    endGame(true, currentGame.getPlayerList(), round, rollStatus);
                    //disabling the player from continuing to click the button
                    rollDice.setEnabled(false);
                }
                //shooter will lose out of the gate
                else if (rollValue == 2 || rollValue == 3 || rollValue == 12)
                {
                    //ending the pass
                    endGame(false, currentGame.getPlayerList(), round, rollStatus);
                    //disabling the player from continuing to click the button
                    rollDice.setEnabled(false);
                }

                //first roll is now over
                round.setFirstRoll(false);
                //assigning the shooterpoint since the shooter hasnt won or lost at this point
                round.setShooterPoint(rollValue);
                JLabel shooterPointLabel = new JLabel("Shooter Point: " + round.getShooterPoint(), SwingConstants.CENTER);
                rollStatus.setFont(new Font("Verdana", Font.BOLD, 20));
                fifthScene.add(shooterPointLabel);
            } else
            {
                //checking if they got the shooter point on second roll
                if (rollValue == round.getShooterPoint())
                {
                    //ending the pass
                    endGame(true, currentGame.getPlayerList(), round, rollStatus);
                    rollDice.setEnabled(false);
                }
                //checking if they lose
                if (rollValue == 7)
                {
                    //ending pass
                    endGame(false, currentGame.getPlayerList(), round, rollStatus);
                    rollDice.setEnabled(false);
                }
            }
        });
        try
        {
            //gets the old scene and removes it
            JPanel previousFifthScene = scenes.get(4);
            scenes.remove(previousFifthScene);
        } catch (IndexOutOfBoundsException ignored)
        {
        }
        //adding the fifth scene
        scenes.add(4, fifthScene);
    }

    /**
    Method Name:    endGame() <br>
    Purpose:        a private instance method that ends the pass, asks the shooter if they want to reroll, and creates a newly updated scene four <br>
    Accepts:        boolean if the shooter won the round, list of players, the previous round, and Jlabel with dicetext <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private void endGame(boolean shooterWin, ArrayList<Player> players, Pass round, JLabel diceText)
    {
        //text if they wont or lost pass
        String finishingText = shooterWin ? " has Won The roll" : " has lost the roll";
        diceText.setText(players.get(currentGame.getShooterIndex()).getName() + finishingText);
        
        // delays asking the user if they want to shoot again.
        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                //settles the bets between players
                round.settleBets(shooterWin, currentGame.getPlayerList());
                //updates the removed players from the list and returns if someone has been removed
                boolean shooterRemoved = currentGame.updatePlayerList();
                //checks if the game is over
                if(currentGame.getIsOver()){
                    buildFinalScene();
                    nextScene();
                    return;
                }
                //gets return value from shotorpass which asks the player if they want to roll again
                boolean shootingAgain = round.shootOrPass(shooterRemoved);
                //updates the index of which person is shooting
                currentGame.updateShooterIndex(shooterRemoved, shootingAgain);
                
                //deletes old scene and builds a new scene four
                deleteGameScenes();
                buildFourthScene();
                nextScene();
            }
        }, 1200l);
    }

    /**
    Method Name:    createNavBar() <br>
    Purpose:        a private instance method that creates the menu bar <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private void createNavBar()
    {
        //creates two menus
        aboutMenu = new JMenu("About");
        instructionsMenu = new JMenu("Instructions");

        navBar = new JMenuBar();

        //listens for MenuListener
        aboutMenu.addItemListener(new MenuListener());
        instructionsMenu.addItemListener(new MenuListener());

        //adds to menubar
        navBar.add(instructionsMenu);
        navBar.add(aboutMenu);
    }

    /**
    Method Name:    buildFinalScene() <br>
    Purpose:        a private instance method that builds the final scene <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private void buildFinalScene(){
        //creating the visuals of the scene
        JPanel gameOver = new JPanel(new GridLayout(2,1));
        JLabel gameOverLabel = new JLabel("GAME OVER!", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        JLabel playerWinner = new JLabel("Congrats " + currentGame.getPlayerList().get(0).getName() + ", YOU WIN!", SwingConstants.CENTER);
        playerWinner.setFont(new Font("Verdana", Font.BOLD, 40));

        //adding components
        gameOver.add(gameOverLabel);
        gameOver.add(playerWinner);

        //adding panel to jframe
        scenes.add(gameOver);
    }

    /**
    Method Name:    nextScene() <br>
    Purpose:        a private instance method that goes to the next scene index <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private void nextScene()
    {
        //hides the current scene
        scenes.get(sceneIndex).setVisible(false);
        ++sceneIndex;
        //checks if the scene is at the last scene
        if (sceneIndex >= scenes.size())
        {
            sceneIndex = GAME_BEGINNING_SCENE;
        }
        //adds scene
        this.add(scenes.get(sceneIndex));
        //sets scene to visible
        scenes.get(sceneIndex).setVisible(true);
    }

    /**
    Class Name:     SliderHandler <br>
    Purpose:        a private class listener that gets the value of the slider and changes the variable accordingly <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private class SliderHandler implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            //getting the slider
            JSlider source = (JSlider) e.getSource();

            //checking if its moving or not
            if (!source.getValueIsAdjusting())
            {
                //changing the variable to the value got
                numOfPlayers = (int) source.getValue();
            }
        }
    }

    /**
    Class Name:     MenuListener <br>
    Purpose:        a private class listener that listens for clicks on a menu bar <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private class MenuListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            //gets if the menu button is pressed
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                JMenu source = (JMenu) e.getSource();
                //displays a message based on the button pressed
                if (source.equals(aboutMenu))
                {
                    JOptionPane.showMessageDialog(null, "Coded By:\nBayethe Lungah\nKeenan Higgins", "About", JOptionPane.PLAIN_MESSAGE);
                } else
                {
                    JOptionPane.showMessageDialog(null, "(1) The shooter (randomly designated and apprears in red text) makes a bet. The other players can bet against their bet.\n(2) The shooter rolls two dice. If the sum of the roll is:  " + 
                    "\n      A) 7 or 11, they win\n      B) 2, 3, or 12, they lose\n      C) any other number (termed a \"point number\"), they keep re-rolling until:\n            i) they roll the point number again, without having rolled a 7. In this case, they win." +
                    "\n            ii) they roll a 7, without having rolled the point number. In this case, they lose.", "Instructions", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

    /**
    Class Name:     BetButtonListener <br>
    Purpose:        a private class listener that gets the bets from all players <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    private class BetButtonListener implements ActionListener
    {
        //useful static variables
        private static JButton[] buttons;
        private static JTextField[] fields;
        private static int betPool = 0;
        private static int currentPlayerIndex;
        private static JLabel instructions;
        private static ArrayList<Player> players;

        //method which returns a new player index
        private static int nextPlayerIndex()
        {
            return (currentPlayerIndex + 1) % players.size();
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                //parsing int from bet text fields
                int bet = Integer.parseInt(fields[currentPlayerIndex].getText());
                //getting the better and checking if they are the shooter
                boolean isShooter = players.get(currentPlayerIndex).getIsShooter();

                //checking if bet is multiple of 10
                if (bet % 10 != 0)
                {
                    JOptionPane.showMessageDialog(null, "Please Enter a natural number that is a multiple of 10");
                    return;
                }
                //checking if minimum bet is entered
                if (bet < 10)
                {
                    JOptionPane.showMessageDialog(null, "You must bet at minimum of $10");
                    return;
                }
                //checking if bet is larger then the betPool
                if (!isShooter && bet > betPool)
                {
                    JOptionPane.showMessageDialog(null, "You cannot place a bet that is larger then the pool");
                    return;
                }
                //checking if bet is larger then what they have in the bank
                if (bet > players.get(currentPlayerIndex).getBankBalance())
                {
                    JOptionPane.showMessageDialog(null,
                            "You cannot place a bet that is greater then your bank account");
                    return;
                }
                //since the bet passed all the checks, assigning the better with their bet
                players.get(currentPlayerIndex).setAmountBet(bet);
                //will check to see if the next player is the shooter and if they are then the bets are done
                boolean lastBet = players.get(nextPlayerIndex()).getIsShooter();

                if (isShooter)
                {
                    //assigning the bet pool to the shooters bet
                    betPool = bet;
                } else
                {
                    //subtracting from the bet pool of the shooter everytime an opposing player bets
                    betPool -= bet; 
                }
                if (!lastBet)
                {
                    //disabled anyone from betting out of turn
                    buttons[currentPlayerIndex].setEnabled(false);
                    fields[currentPlayerIndex].setEnabled(false);
                    //checking if the bet pool has been reduced to zero to not allow for more bets after
                    if (betPool > 0)
                    {
                        //instructing to set a bet the size of the shooters bet to the other players
                        instructions.setText(players.get(nextPlayerIndex()).getName() + " please make a bet (Maximum $"
                                + betPool + ")");

                        buttons[nextPlayerIndex()].setEnabled(true);
                        fields[nextPlayerIndex()].setEnabled(true);
                    } else
                    {
                        //not allowing more bets
                        instructions.setText("No More Bets");
                        //timer for 1.2s
                        new Timer().schedule(new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                //build new fifth scene
                                bulidFifthScene(new Pass(currentGame.getShooterIndex(),
                                        currentGame.getPlayerList().get(currentGame.getShooterIndex()).getAmountBet(),
                                        currentGame.getPlayerList().get(currentGame.getShooterIndex()).getAmountBet()));
                                //go to next scene
                                nextScene();
                            }
                        }, 1200l);

                    }

                } else
                {
                    //build a new fifth scene with the shooter bet minus the remaining pool that hasnt been fully covered
                    bulidFifthScene(new Pass(currentGame.getShooterIndex(),
                            currentGame.getPlayerList().get(currentGame.getShooterIndex()).getAmountBet(),
                            currentGame.getPlayerList().get(currentGame.getShooterIndex()).getAmountBet() - betPool));
                    nextScene();
                }
                //going to next index
                currentPlayerIndex = nextPlayerIndex();
            //catching a incorrect data type
            } catch (NumberFormatException exc)
            {
                JOptionPane.showMessageDialog(null,
                        players.get(currentPlayerIndex).getName() + "'s bet is not a natural number");
                return;
            }
        }
    }

    /**
    Class Name:     BetButtonListener <br>
    Purpose:        a private class listener that checks the bet textFields <br>
    Accepts:        no param <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
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
    public static void main(String[] args)
    {
        new Craps();
    }

}