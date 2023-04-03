/*
 * Class name: Die
 * Purpose: represents a die that can be rolled
 * Coder: Bayethe Lungah and Keenan Higgins
 * Date: April 3, 2023 
 */

public class Die
{
    private int rollValue;

    public Die()
    {
        rollValue = 0;
    }

    public int getRollValue()
    {
        return rollValue;
    }

    /*
     * Method name: rollDie Purpose: simulates a die roll Accepts: Nothing;
     * Returns: an integer value that is value of the roll
     */
    public int rollDie()
    {
        rollValue = (int) Math.random() * 7 + 1;
        return rollValue;
    }
}