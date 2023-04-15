/*
 * Class name: Game
 * Purpose: represents an instance of a game of craps
 * Coder: Bayethe and Keenan
 * Date: April 3, 2023 
 */

 //importing libraries
import java.util.ArrayList;
import java.util.Iterator;

public class Game
{
    //useful variables
    private int totalPotAmount;
    private ArrayList<Player> playerList;
    private int shooterIndex;
    private boolean isOver;

    //constructor
    public Game()
    {
        totalPotAmount = 0;
        playerList = new ArrayList<Player>();
        isOver = false;
    }

    public boolean getIsOver()
    {
        return isOver;
    }

    public ArrayList<Player> getPlayerList()
    {
        return playerList;
    }

    public void setTotalPotAmount(int numPlayers)
    {
        this.totalPotAmount = numPlayers * 100;
    }

    public int getTotalPotAmount()
    {
        return totalPotAmount;
    }

    public int getShooterIndex()
    {
        return shooterIndex;
    }

    /**
    Method Name:    populatePlayerList() <br>
    Purpose:        a public instance method that creates the initial list of players <br>
    Accepts:        int of the amount of players <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */ 
    public void populatePlayerList(int numPlayers)
    {
        //creating a list of players based on numPlayers
        for (int i = 0; i < numPlayers; i++)
        {
            playerList.add(new Player(""));
        }
        //getting random shooter index
        shooterIndex = (int) (Math.random() * playerList.size());
        //assigning the player with the random index as the shooter
        playerList.get(shooterIndex).setIsShooter(true);
    }

    /**
    Method Name:    updatePlayerList() <br>
    Purpose:        a public instance method that updates the player list when players get removed and checks if a player was removed <br>
    Accepts:        no param <br>
    Returns:        boolean representing if a player has been removed or not <br>
    Date:           April 14, 2023 <br>
    */
    public boolean updatePlayerList()
    {
        //initializing bool
        boolean shooterDeleted = false;
        //uses an iterator to loop through the player list
        for (Iterator<Player> itr = playerList.iterator(); itr.hasNext(); ) {
            Player p = itr.next();
            //checking if player has no money
            if (p.getBankBalance() <= 0) {
                if(p.getIsShooter()){
                    shooterDeleted = true;
                }
                itr.remove();
            }
            //checking if they are the shooter and changing it to false
            else if(p.getIsShooter()){
                p.setIsShooter(false);
            }
        }
        //checking if one player remains for game over 
        if (playerList.size() == 1)
            isOver = true;
        return shooterDeleted;
    }

    /**
    Method Name:    updateShooterIndex() <br>
    Purpose:        a public instance method that will change the index of the shooter <br>
    Accepts:        bool if there has been a shooter removed, and if the person is shooting again <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */
    public void updateShooterIndex(boolean shooterRemoved, boolean shootingAgain)
    {
        //checking if a player has been removed or not
        if(!shooterRemoved){
            //checks if they are shooting again
            if(shootingAgain){
                shooterIndex = shooterIndex % playerList.size();
            }
            //if not need to increment the index
            else{
                shooterIndex = ++shooterIndex % playerList.size();
            }
        }
        //since someone has been removed, the index will stay the same.
        else{
            shooterIndex = shooterIndex % playerList.size();
        }
        //changes the new shooter bool to true
        playerList.get(shooterIndex).setIsShooter(true);
    }

    /**
    Method Name:    checkForGameWinner() <br>
    Purpose:        a public instance method that check if the game has been won or not <br>
    Accepts:        a ArrayList of all the players <br>
    Returns:        boolean based on if the game is over or not <br>
    Date:           April 14, 2023 <br>
    */
    public boolean checkForGameWinner(ArrayList<Player> list)
    {
        //loops through the players
        for (int i = 0; i < list.size(); i++)
        {
            //checking if that have all the money in the game
            if (list.get(i).getBankBalance() == totalPotAmount)
            {
                return true;
            }
        }
        return false;
    }
}
