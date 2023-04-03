/*
 * Class name: Die
 * Purpose: represents a die that can be rolled
 * Coder: Bayethe Lungah and Keenan
 * Date: April 3, 2023 
 */

public class Player
{
    private String name;
    private int bankBalance;
    private boolean isShooter;
    private int betAmount;
    private boolean passCompleted;

    public Player(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public int getBankBalance()
    {
        return bankBalance;
    }

    public void setBankBalance(int newBalance)
    {
        bankBalance = newBalance;
    }

    public boolean getIsShooter()
    {
        return isShooter;
    }

    public void setIsShooter(boolean value)
    {
        isShooter = value;
    }

    public int getAmountBet()
    {
        return betAmount;
    }

    public void setAmountBet(int betAmount)
    {
        this.betAmount = betAmount;
    }

    public boolean getPassCompleted()
    {
        return passCompleted;
    }

    public void setPassCompleted(boolean value)
    {
        passCompleted = value;
    }

}