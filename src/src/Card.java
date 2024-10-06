import java.util.Objects;

public class Card {
    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";
    Constants constants = new Constants();
    // Declaring the color
    // Custom declaration
    public static final String RED = "\u001B[31m";
    public static final String BLACK = "\u001B[30m";

    private final CardSuit suit;
    private final CardRank rank;
    private String color;

    public Card(CardRank rank, CardSuit Suit)
    {
        this.suit = Suit;
        this.rank = rank;
        AddColor();
    }

    private void AddColor()
    {
        if(Objects.equals(suit.toString(), constants.Hearts) || Objects.equals(suit.toString(), constants.Diamonds))
            color = constants.Red;
        else color = constants.Black;
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

    public String toString() {
        String redsuit = RED + rank.toString() + " of " + suit.toString() + ANSI_RESET;
        String blacksuit = BLACK + rank.toString() + " of " + suit.toString() + ANSI_RESET;

        return Objects.equals(color,constants.Red) ? redsuit : blacksuit;
    }


}
