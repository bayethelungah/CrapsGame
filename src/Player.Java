/*
 * Class Name: Player
 * Purpose: represents a entity that can play and place bets in a crap game
 * Coder: Bayethe Lungah and Keenan
 * Date: April 3, 2023 
 */

 public class Player
 {
     //useful variables
     private String name;
     private int bankBalance;
     private boolean isShooter;
     private int betAmount;
     private boolean passCompleted;
 
     //constructor
     public Player(String name)
     {
         this.name = name;
         bankBalance = 100;
         isShooter = false;
         betAmount = 0;
         passCompleted = false;
     }
 
     public String getName()
     {
         return name;
     }
 
     public void setName(String name)
     {
         this.name = name;
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
 