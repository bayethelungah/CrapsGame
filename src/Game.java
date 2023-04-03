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
<<<<<<< HEAD
    private ArrayList<Player>playerList;

    public Game() {
        totalPotAmount = 0;
        playerList = new ArrayList<Player>();
=======
    private ArrayList<Player> playerList;
    private ArrayList<Pass> pass;

    public Game()
    {

>>>>>>> 55e753e09e4fae22581671a5d92f1a980477c2a1
    }

    public ArrayList<Player> getPlayerList()
    {
        return playerList;
    }

    public int getTotalPotAmount()
    {
        return totalPotAmount;
    }

    public void populatePlayerList()
    {

    }

    public boolean checkForGameWinner(){
        return true;
    }
