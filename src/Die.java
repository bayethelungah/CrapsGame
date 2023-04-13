/*
 * Class name: Die
 * Purpose: represents a die that can be rolled
 * Coder: Bayethe Lungah and Keenan
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

    public int rollDie()
    {
        rollValue = ((int) (Math.random() * 6)) + 1;
        return rollValue;
    }
}