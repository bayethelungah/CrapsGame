import java.util.ArrayList;

/*
 * Class name: Die
 * Purpose: represents a die that can be rolled
 * Coder: Bayethe and Keenan
 * Date: April 3, 2023 
 */

public class Game
{
    private int totalPotAmount;
    private ArrayList<Player> playerList;
    private int shooterIndex;
    private boolean isOver;

    public Game()
    {
        totalPotAmount = 0;
        playerList = new ArrayList<Player>();
        shooterIndex = (int) (Math.random() * playerList.size());
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

    public void populatePlayerList(int numPlayers)
    {
        for (int i = 0; i < numPlayers; i++)
        {
            playerList.add(new Player(""));
        }

        playerList.get(shooterIndex).setIsShooter(true);
    }

    public void updatePlayerList()
    {
        for (Player player : playerList)
        {
            if (player.getBankBalance() <= 0)
            {
                playerList.remove(player);
            }
        }

        if (playerList.size() == 1)
            isOver = true;
    }

    public void updateShooterIndex()
    {
        playerList.get(shooterIndex).setIsShooter(false);
        shooterIndex = ++shooterIndex % playerList.size();
        playerList.get(shooterIndex).setIsShooter(true);
    }

    public int getShooterIndex()
    {
        return shooterIndex;
    }

    public boolean checkForGameWinner(ArrayList<Player> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getBankBalance() == totalPotAmount)
            {
                return true;
            }
        }
        return false;
    }
}
