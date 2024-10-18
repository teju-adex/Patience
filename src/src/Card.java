public class Card {
    Constants constants = new Constants();


    private final CardSuit suit;
    private final CardRank rank;
    private String color;

    public Card(CardRank rank, CardSuit Suit)
    {
        this.suit = Suit;
        this.rank = rank;
        addColor(); // Set the card color based on its suit
    }
    // Assign color based on the suit
    private void addColor()
    {
        if (suit == CardSuit.H || suit == CardSuit.D) {
            color = constants.Red; // Red for hearts and diamonds
        } else {
            color = constants.Black; // Black for clubs and spades
        }
    }

    public String getColor() {
        return color;
    }

    public CardSuit getSuit()
    {
        return suit;
    }

    public int getValue()
    {
        return rank.getValue();
    }

    // Override toString to display the card as "Rank of Suit"
    public String toString() {

        return  rank.toString() + " of " + suit.toString() ;
    }


}
