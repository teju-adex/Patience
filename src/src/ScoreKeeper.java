import java.util.Map;
import java.util.Stack;

public class ScoreKeeper {
    private int Score;
    private int Moves;

    public ScoreKeeper() {
        Score = 0;
        Moves = 0;
    }

    public void updateScore(String fromlabel, String tolabel, Map<String, Lane> Lanes, Map<String, Stack<Card>> foundation)
    {
        if(fromlabel.equalsIgnoreCase("P") && foundation.containsKey(tolabel))
        {
            Score += 10;

        } else if (Lanes.containsKey(fromlabel) && foundation.containsKey(tolabel)) {
            Score += 20;
        } else if (Lanes.containsKey(fromlabel) && Lanes.containsKey(tolabel)) {
            Score += 5;
        }
        else {
            return;
        }

    }

    public void increaseMoves(){
        Moves++;
    }

    public int getMoves()
    {
        return Moves;
    }

    public int getScore()
    {
        return Score;
    }


}
