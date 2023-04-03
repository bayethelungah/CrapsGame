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

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    public int getTotalPotAmount() {
        return totalPotAmount;
    }
    public void populatePlayerList(){

    }
    public boolean checForGameWinner(){
        return true;
    }

}