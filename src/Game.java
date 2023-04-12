import java.util.ArrayList;

/*
 * Class name: Die
 * Purpose: represents a die that can be rolled
 * Coder: Bayethe Lungah and Keenan
 * Date: April 3, 2023 
 */

public class Game
{
    private int totalPotAmount;
    private ArrayList<Player>playerList;

    public Game() {
        totalPotAmount = 0;
        playerList = new ArrayList<Player>();
    }

    public ArrayList<Player> getPlayerList()
    {
        return playerList;
    }

    public void setTotalPotAmount(int numPlayers) {
    	this.totalPotAmount = numPlayers * 100;
    }
    
    public int getTotalPotAmount()
    {
        return totalPotAmount;
    }

    public void populatePlayerList(int numPlayers)
    {
        for (int i = 0; i < numPlayers; i++){
            playerList.add(new Player(""));
        }
    }

    public boolean checkForGameWinner(ArrayList<Player>list){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getBankBalance() == totalPotAmount){
                return true;
            }
        }
        return false;
    }
}
