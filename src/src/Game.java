import java.util.*;

public class Game {


    private  Map<String, Lane> Lanes =  new HashMap<>();
    private final Map<String, Stack<Card>> foundation = new HashMap<>();
    private ArrayList<Card> deck;
    private int Score = 0;
    private int Moves = 0;
    private final Stack<Card> waste = new Stack<>();
    private final Stack<Card> drawn = new Stack<>();



    public Game()
    {
        InitialiseDeck();
        InitialiseFoundationPiles();
        SetUpLanes();
        PrintBoard();
    }

    public void InitialiseFoundationPiles()
    {
        foundation.put("H", new Stack<>());
        foundation.put("S", new Stack<>());
        foundation.put("C", new Stack<>());
        foundation.put("D", new Stack<>());
    }

    public void SetUpLanes()
    {
        for(int i = 1; i <= 7; i++)
        {
            Lane lane = new Lane();
            Lanes.put(String.valueOf(i), lane);
            for (int j = 1; j <= i; j++)
            {
               if(j == i){
                    lane.AddUncoveredCard(deck.removeFirst());
                } else {
                    lane.AddCoveredCard(deck.removeFirst());
                }
            }
        }
        waste.addAll(deck);
        deck.clear();
    }

    public void InitialiseDeck()
    {
        Deck cardDeck = new Deck();
        deck = cardDeck.fillDeck();
        Collections.shuffle(deck);
    }



    public void PrintBoard() {

        System.out.printf("Score: %d\t\t\t\t\t\t\t\t\t\t\t\t\t Moves: %d\n\n", Score, Moves);
        System.out.printf("%-50s", "[D]");
        System.out.printf("%-10s", foundation.get("H").isEmpty() ? "[H]" : foundation.get("H").peek());
        System.out.printf("%-10s", foundation.get("S").isEmpty() ? "[S]" : foundation.get("S").peek());
        System.out.printf("%-10s", foundation.get("C").isEmpty() ? "[C]" : foundation.get("C").peek());
        System.out.printf("%-10s\n", foundation.get("D").isEmpty() ? "[D]" : foundation.get("D").peek());

        if(!drawn.isEmpty())
        {
            System.out.println("Drawn: " + drawn.peek().toString());
            System.out.print("\n");
        }

        System.out.println();

        int maxStackSize = Lanes.values().stream().mapToInt(lane -> lane.size()).max().orElse(0);
        // Define the longest card length (max of covered card or a typical uncovered card)
        int longestcardlength = "[******]".length();  // The longest possible card length, for alignment

        // Ensure longestcardlength accommodates the longest uncovered card if needed
        for (Lane lane : Lanes.values()) {
            for (Card uncoveredCard : lane.GetUncovered()) {
                if (uncoveredCard.toString().length() > longestcardlength) {
                    longestcardlength = uncoveredCard.toString().length();
                }
            }
        }
        int columnspacing = 4;
        // Print all the lanes row by row
        for (int row = 0; row < maxStackSize; row++) {
            for (int i = 1; i <= 7; i++) {
                Lane lane = Lanes.get(String.valueOf(i));
                if (row < lane.GetCovered().size()) {
                    // Print covered cards as "[******]"
                    System.out.printf( "%-" + (longestcardlength + columnspacing) + "s","[******]");
                } else if (row - lane.GetCovered().size() < lane.GetUncovered().size()) {
                    // Print the uncovered card
                    int uncoveredIndex = row - lane.GetCovered().size();
                    String uncoveredCard = lane.GetUncovered().get(uncoveredIndex).toString(); // Ensure it's a string
                    System.out.printf("%-" + (longestcardlength + columnspacing)  + "s", uncoveredCard);

                } else {
                    // Empty space for shorter lanes
                    System.out.printf("%-" + (longestcardlength + columnspacing)  + "s", " ");
                }
            }
            System.out.println();  // Move to next row
        }
    }


    public boolean GameOver()
    {
        int numberoffullsuits = 0;

        for(var foundation : foundation.values())
        {
            if(foundation.size() == 13)
                numberoffullsuits++;
        }

        return numberoffullsuits == 4;
    }

    public void DrawCard() {
        if (!waste.isEmpty()) {
            drawn.add(waste.pop());
        }
        else {
            waste.addAll(drawn.reversed());
            drawn.clear();
        }
        PrintBoard();
    }

    public void GetCardToBeMoved(String move){
        String fromlabel = move.substring(0,1);
        String tolabel = move.substring(1).toUpperCase();

        if(Lanes.containsKey(fromlabel) && Lanes.get(fromlabel).GetTopUncovered() != null) {
            Card card = Lanes.get(fromlabel).GetTopUncovered();
            if(CanMoveCard(card, tolabel)) {
                Lanes.get(fromlabel).RemoveTopUncovered();
                MoveCard(card, tolabel);
                if(!Lanes.get(fromlabel).hasUncoveredCards()) {
                    Lanes.get(fromlabel).CoveredToUncoveredTopCard();
                }
                UpdateScore(fromlabel, tolabel);
            } else {
                System.out.println("Invalid Move");
            }

        } else if (fromlabel.equalsIgnoreCase("P")) {
            Card card = drawn.peek();
            if(CanMoveCard(card, tolabel)) {
                drawn.pop();
                MoveCard(card, tolabel);
                UpdateScore(fromlabel, tolabel);
            }
            else {
                System.out.println("Invalid Move");
            }

        } else {
            System.out.println("Invalid Move");
        }
        PrintBoard();
    }

    public void GetCardsToBeMoved(String move){
        String fromlabel = move.substring(0,1);
        String tolabel = move.substring(1,2).toUpperCase();
        int numofcardstomove = Integer.parseInt(move.substring(2,3));

        if(Lanes.containsKey(fromlabel) && Lanes.get(fromlabel).GetTopUncovered() != null)
        {
            List<Card> uncoveredCards = Lanes.get(fromlabel).GetUncovered();
            int size = uncoveredCards.size();
            int startIndex = size - numofcardstomove; // Starting index for the cards to move
            int endIndex = size; // End index for the cards to move (exclusive)
            if (numofcardstomove <= 0 || numofcardstomove > size) {
                System.out.println("Invalid number of cards to move");
                return; // Early exit if invalid
            }

            if(CanMoveCards(uncoveredCards.subList(startIndex,endIndex),tolabel)) {
                while (numofcardstomove > 0) {
                    Card card = uncoveredCards.getFirst();
                    Lanes.get(fromlabel).GetUncovered().remove(0);
                    if (!Lanes.get(fromlabel).hasUncoveredCards())
                        Lanes.get(fromlabel).CoveredToUncoveredTopCard();
                    MoveCard(card, tolabel);
                    UpdateScore(fromlabel, tolabel);
                    numofcardstomove--;
                    uncoveredCards = Lanes.get(fromlabel).GetUncovered();
                }
            }
            else
            {
                System.out.println("Invalid, Not all cards can be moved");
            }

        } else if (fromlabel.equalsIgnoreCase("P")) {

            int size = drawn.size();

            if (size < numofcardstomove) {
                System.out.println("Invalid, Not enough cards to move");
                return;  // Exit if not enough cards
            }

            List<Card> cardsToMove = new ArrayList<>();
            for (int i = 0; i < numofcardstomove; i++) {
                Card card = drawn.get(size - 1 - i);  // Access the card from the top
                cardsToMove.add(card);  // Add the card to the list to be moved
            }

            if (CanMoveCards(cardsToMove, tolabel)) {
                for (Card card : cardsToMove) {
                    drawn.pop();
                    MoveCard(card, tolabel);
                    UpdateScore(fromlabel, tolabel);
                }
            }
            else {
                System.out.println("Invalid, Not all cards can be moved");
            }
        }
        else {
            System.out.println("Invalid Label");
        }
        PrintBoard();
    }

    // Method to check if multiple cards can be moved
    public boolean CanMoveCards(List<Card> cardsToMove, String destination) {
        // Ensure there is at least one card to move
        if (cardsToMove.isEmpty()) {
            return false;
        }

        // 1. If the target location is a lane
        if (Lanes.containsKey(destination)) {
            Card topCardOnDestination = Lanes.get(destination).GetTopUncovered();
            // If the destination lane is not empty
            if (topCardOnDestination != null) {
                // Check if the first card in the sequence can be placed on the top uncovered card
                Card bottomCardOfSequence = cardsToMove.get(0); // The first card in the sequence
                if (!bottomCardOfSequence.getColor().equals(topCardOnDestination.getColor()) &&
                        topCardOnDestination.getValue() - bottomCardOfSequence.getValue() == 1) {
                    // Check that the sequence itself is valid
                    return isValidSequence(cardsToMove); // Check if the cards in the sequence follow the rules
                } else {
                    return false; // The first card can't be placed on the destination
                }
            } else {
                // If the destination lane is empty, only allow if the first card is a King (value 13)
                return cardsToMove.get(0).getValue() == 13 && isValidSequence(cardsToMove);
            }
        }

        // 2. If the target location is a foundation
        if (foundation.containsKey(destination)) {
            // Foundations usually allow only one card to be moved
            return cardsToMove.size() == 1 && CanMoveCard(cardsToMove.get(0), destination);
        }

        // Invalid destination
        return false;
    }

    // Helper method to check if the sequence of cards follows the rules
    private boolean isValidSequence(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            Card currentCard = cards.get(i);
            Card nextCard = cards.get(i + 1);

            // Check if the colors alternate and if the ranks are descending
            if (currentCard.getColor().equals(nextCard.getColor()) || currentCard.getValue() - nextCard.getValue() != 1) {
                return false;  // Invalid sequence
            }
        }
        return true;  // Sequence is valid
    }

    public boolean CanMoveCard(Card card, String location)
    {
        if(Lanes.containsKey(location)) {
            if(Lanes.get(location).GetTopUncovered() != null) {
                Card cardontopofpile = Lanes.get(location).GetTopUncovered();
                int cardontopofpilerank = cardontopofpile.getValue();
                int cardrank = card.getValue();
                return !card.getColor().equals(cardontopofpile.getColor()) &&  cardontopofpilerank - cardrank == 1;
            } else return card.getValue() == 13;

        }

        if(foundation.containsKey(location))
        {
            String firstletterofcardsuit = card.getSuit().toString().substring(0,1);
            int cardrank = card.getValue();
            if (foundation.get(location).isEmpty() && foundation.containsKey(firstletterofcardsuit) && cardrank == 1)
            {
                return true;
            }
            if (!foundation.get(location).isEmpty() && foundation.containsKey(firstletterofcardsuit)) {

                Card cardontopofpile = foundation.get(location).peek();
                int cardontopofpilerank = cardontopofpile.getValue();
                return cardrank - cardontopofpilerank == 1;
            }
        }
        return false;
    }

    public void MoveCard(Card card, String location)
    {
        if(Lanes.containsKey(location)) {
            Moves++;
            Lanes.get(location).AddUncoveredCard(card);
        }
        else if (foundation.containsKey(location)) {
            Moves++;
            foundation.get(location).push(card);
        }
    }

    public void UpdateScore(String fromlabel, String tolabel)
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

    public int GetMoves()
    {
        return Moves;
    }

    public int GetScore()
    {
        return Score;
    }
}
