import java.util.ArrayList;
import java.util.Iterator;

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
        //shooterIndex = (int) (Math.random() * playerList.size()); //note player list isnt initialized yet so moving this
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
        shooterIndex = (int) (Math.random() * playerList.size());
        playerList.get(shooterIndex).setIsShooter(true);
    }

    public boolean updatePlayerList()
    {
        boolean shooterDeleted = false;
        for (Iterator<Player> itr = playerList.iterator(); itr.hasNext(); ) {
            Player p = itr.next();
            if (p.getBankBalance() <= 0) {
                if(p.getIsShooter()){
                    shooterDeleted = true;
                }
                itr.remove();
            }
            else if(p.getIsShooter()){
                p.setIsShooter(false);
            }
        }
        if (playerList.size() == 1)
            isOver = true;
        return shooterDeleted;
    }

    public void updateShooterIndex(boolean shooterRemoved, boolean shootingAgain)
    {
        if(!shooterRemoved){
            if(shootingAgain){
                shooterIndex = shooterIndex % playerList.size();
                System.out.println("indx = " + shooterIndex + " list size =" + playerList.size());
            }
            else{
                shooterIndex = ++shooterIndex % playerList.size();
                System.out.println("indx = " + shooterIndex + " list size =" + playerList.size());
            }
            
        }
        else{
            shooterIndex = shooterIndex % playerList.size();
            System.out.println("indx = " + shooterIndex + " list size =" + playerList.size());
        }
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
