/*
 * Class Name: Pass
 * Purpose: Represents a round of play in the game
 * Coder: Bayethe, Keenan
 * Date: April 3, 2023
 */
//useful imports

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Pass
{
    //useful variables
    private int shooterID;
    private int actionAmount;
    private int actionAmountCovered;
    private boolean shooterWin;
    private boolean firstRoll;
    private int shooterPoint;

    //constructor
    public Pass(int shooterID, int actionAmount, int actionAmountCovered)
    {
        this.shooterID = shooterID;
        this.actionAmount = actionAmount;
        this.actionAmountCovered = actionAmountCovered;
        this.shooterWin = false;
        this.firstRoll = true;
        this.shooterPoint = 0;

    }

    public int getShooterPoint()
    {
        return shooterPoint;
    }

    public void setShooterPoint(int shooterPoint)
    {
        this.shooterPoint = shooterPoint;
    }

    public void setFirstRoll(boolean firstRoll)
    {
        this.firstRoll = firstRoll;
    }

    public boolean getFirstRoll()
    {
        return firstRoll;
    }

    public int getShooterID()
    {
        return shooterID;
    }

    public void setShooterID(int shooterID)
    {
        this.shooterID = shooterID;
    }

    public int getActionAmount()
    {
        return actionAmount;
    }

    public void setActionAmount(int actionAmount)
    {
        this.actionAmount = actionAmount;
    }

    public int getActionAmountCovered()
    {
        return actionAmountCovered;
    }

    public void setActionAmountCovered(int actionAmountCovered)
    {
        this.actionAmountCovered = actionAmountCovered;
    }

    public boolean getShooterWinOrLose()
    {
        return shooterWin;
    }

    public void setShooterWinOrLose(boolean winOrLose)
    {
        shooterWin = winOrLose;
    }

    /**
    Method Name:    settleBets() <br>
    Purpose:        a public instance method that distributes the won and lost bets according to whoever won <br>
    Accepts:        bool if the shooter won the pass, and a list of all the players <br>
    Returns:        nothing, void <br>
    Date:           April 14, 2023 <br>
    */   
    public void settleBets(boolean shooterWin, ArrayList<Player> playerList)
    {
        //uses passed variable to change whether shooter won
        setShooterWinOrLose(shooterWin);
        //looping through players to distribute money
        for (Player player : playerList)
        {
            if (player.getIsShooter()) // this player is the shooter
            {
                if (shooterWin)
                {
                    //player won so they win the amount covered
                    player.setBankBalance(player.getBankBalance() + actionAmountCovered);
                } else
                {
                    //player lost so they win the amount covered
                    player.setBankBalance(player.getBankBalance() - actionAmountCovered);
                }
            } else // this player is an opponent
            {
                if (shooterWin)
                {
                    //opposition lost so they lose the amount they bet
                    player.setBankBalance(player.getBankBalance() - player.getAmountBet());
                } else
                {
                    //opposition won so they win the amount they bet
                    player.setBankBalance(player.getBankBalance() + player.getAmountBet());
                }
            }
            //changing their bets back to zero
            player.setAmountBet(0);
        }
    }

    /**
    Method Name:    shootOrPass() <br>
    Purpose:        a public instance method that asks if the user wants to shoot again <br>
    Accepts:        bool if the shooter has been removed or not <br>
    Returns:        boolean deciding if the shooter wants to shoot again <br>
    Date:           April 14, 2023 <br>
    */ 
    public boolean shootOrPass(boolean shooterRemoved)
    {
        try {
            //checking if the shooter has run out of money, then we wont need to ask them if they want to shoot again
            if(shooterRemoved){
                return false;
            }
            //checking the return from the showConfirmDialog() to see whether they want to shoot again
            int selected = JOptionPane.showConfirmDialog(null, "would you like to shoot again?", null, JOptionPane.YES_NO_OPTION);
            if(selected == JOptionPane.CLOSED_OPTION){
                return false;
            }
            return selected == JOptionPane.YES_OPTION;
    
        } catch (Exception ignored) {
        }
        return false;
    }

}