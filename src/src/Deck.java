import java.util.ArrayList;
import java.util.List;

public class Deck {
    private ArrayList<Card> Cards = new ArrayList<>();

    public ArrayList<Card> fillDeck()
    {
        int i = 0;
        for (CardSuit suit : CardSuit.values())
        {
            for(CardRank value : CardRank.values())
            {
                Cards.add(new Card(value, suit));
            }
        }

        return Cards;
    }

}
