/*
 * Class Name: Pass
 * Purpose: Represents a round of play in the game
 * Coder: Bayethe, Keenan
 * Date: April 3, 2023
 */

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Pass
{
    private int shooterID;
    private int actionAmount;
    private int actionAmountCovered;
    private boolean shooterWin;
    private boolean firstRoll;
    private int shooterPoint;

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

    public void settleBets(boolean shooterWin, ArrayList<Player> playerList)
    {
        setShooterWinOrLose(shooterWin);
        System.out.println("Shooter Won? " + shooterWin);
        for (Player player : playerList)
        {
            if (player.getIsShooter()) // this player is the shooter
            {
                if (shooterWin)
                {
                    player.setBankBalance(player.getBankBalance() + actionAmount);
                    System.out.println("Shooter Won And is receiving $" + actionAmount);
                } else
                {
                    player.setBankBalance(player.getBankBalance() - actionAmount);
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

        int selected = JOptionPane.showConfirmDialog(null, "would you like to shoot again?", null, JOptionPane.YES_NO_OPTION);
        if(selected == JOptionPane.CANCEL_OPTION){
            return false;
        }
        return selected == JOptionPane.YES_OPTION;

    }

}