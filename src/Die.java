/*
 * Class name: Die
 * Purpose: represents a die that can be rolled
 * Coder: Bayethe Lungah and Keenan
 * Date: April 3, 2023 
 */

public class Die
{
    //useful variables
    private int rollValue;
    //constructor
    public Die()
    {
        rollValue = 0;
    }

    public int getRollValue()
    {
        return rollValue;
    }

    /**
    Method Name:    rollDie() <br>
    Purpose:        a public instance method that gets a single roll of a 6 sided dice <br>
    Accepts:        no param <br>
    Returns:        integer which represents the roll value <br>
    Date:           April 14, 2023 <br>
    */      
    public int rollDie()
    {
        //using math.random to get a roll value
        rollValue = ((int) (Math.random() * 6)) + 1;
        return rollValue;
    }
}