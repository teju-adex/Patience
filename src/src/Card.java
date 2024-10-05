public class Card {

    private CardSuit suit;
    private CardRank rank;

    public Card(CardRank rank, CardSuit Suit)
    {
        this.suit = Suit;
        this.rank = rank;
    }

    public CardSuit getSuit()
    {
        return suit;
    }

    public CardRank getValue()
    {
        return rank;
    }

    public String toString()
    {
        return suit.toString() + rank.toString();
    }
    public void printCard()
    {
        System.out.println(rank.toString() + " of " + suit.toString());
    }


}
