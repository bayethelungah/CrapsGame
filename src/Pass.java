/*
 * Class Name: Pass
 * Purpose: Represents a round of play in the game
 * Coder: Bayethe, Keenan
 * Date: April 3, 2023
 */

import java.util.ArrayList;

public class Pass
{
    private int shooterID;
    private int actionAmount;
    private int actionAmountCovered;
    private boolean shooterWin;

    public Pass(int shooterID, int actionAmount, int actionAmountCovered)
    {
        this.shooterID = shooterID;
        this.actionAmount = actionAmount;
        this.actionAmountCovered = actionAmountCovered;
        this.shooterWin = false;
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

    public void settleBets(boolean shooterWin, ArrayList<Player> playerList)
    {
        for (Player player : playerList)
        {
            if (player.getIsShooter()) // this player is the shooter
            {
                if (shooterWin)
                {
                    player.setBankBalance(player.getBankBalance() + actionAmountCovered);
                } else
                {
                    player.setBankBalance(player.getBankBalance() - actionAmountCovered);
                }
            } else // this player is an opponent
            {
                if (shooterWin)
                {
                    player.setBankBalance(player.getBankBalance() - player.getAmountBet());
                } else
                {
                    player.setBankBalance(player.getBankBalance() + player.getAmountBet());
                }
            }
        }
    }

    public boolean shootOrPass()
    {
        return true;
    }

}