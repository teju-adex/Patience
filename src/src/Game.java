import java.util.*;

public class Game {
    private  Map<String, Lane> Lanes =  new HashMap<>(); // Map to hold lanes where cards are played
    private final Map<String, Stack<Card>> foundation = new HashMap<>();  // Map for foundation piles (suit piles)
    private ArrayList<Card> deck;
    public ScoreKeeper scoreKeeper = new ScoreKeeper();
    private final Stack<Card> waste = new Stack<>(); // Waste pile for drawn cards
    private final Stack<Card> drawn = new Stack<>(); // Stack for the currently drawn card
    private Constants constants = new Constants();


    public Game()
    {
        initialiseGame();
    }

    private void initialiseGame() {
        initialiseDeck();
        initialiseFoundationPiles();
        setUpLanes();
        printBoard();
    }

    public void initialiseDeck()
    {
        Deck cardDeck = new Deck();
        deck = cardDeck.fillDeck();
        Collections.shuffle(deck);
    }

    public void initialiseFoundationPiles()
    {
        foundation.put("H", new Stack<>());
        foundation.put("S", new Stack<>());
        foundation.put("C", new Stack<>());
        foundation.put("D", new Stack<>());
    }

    public void setUpLanes()
    {
        for(int i = 1; i <= 7; i++)
        {
            Lane lane = new Lane();
            Lanes.put(String.valueOf(i), lane);
            for (int j = 1; j <= i; j++)
            {
               if(j == i){
                    lane.addUncoveredCard(deck.removeFirst());
                } else {
                    lane.addCoveredCard(deck.removeFirst());
                }
            }
        }
        waste.addAll(deck);// Add remaining cards to waste pile
        deck.clear(); // Clear the deck
    }

    public void drawCard() {
        if (!waste.isEmpty()) {
            drawn.add(waste.pop());
        }
        else {
            waste.addAll(drawn.reversed());
            drawn.clear();
        }
        printBoard();
    }


    public void printBoard() {
        System.out.println();
        System.out.printf("Score: %d\t\t\t\t\t\t\t\t\t\t\t\t\t Moves: %d\n\n", scoreKeeper.getScore(), scoreKeeper.getMoves());
        System.out.printf("%-50s", "[P]");
        System.out.printf("%-10s", foundation.get("H").isEmpty() ? "[H]": foundation.get("H").peek() + " ");
        System.out.printf("%-10s", foundation.get("S").isEmpty() ? "[S]" : foundation.get("S").peek() + " ");
        System.out.printf("%-10s", foundation.get("C").isEmpty() ? "[C]" : foundation.get("C").peek() + " ");
        System.out.printf("%-10s\n", foundation.get("D").isEmpty() ? "[D]" : foundation.get("D").peek() + " ");

        if(!drawn.isEmpty())
        {
            System.out.println("Drawn: " + drawn.peek().toString());
            System.out.print("\n");
        }

        System.out.println();

        int maxStackSize = Lanes.values().stream().mapToInt(lane -> lane.size()).max().orElse(0);
        // Define the longest card length (max of covered card or a typical uncovered card)
        int longestcardlength = constants.coveredcarddisplay.length();  // The longest possible card length, for alignment

        // Ensure longestcardlength accommodates the longest uncovered card if needed
        for (Lane lane : Lanes.values()) {
            for (Card uncoveredCard : lane.getUncovered()) {
                if (uncoveredCard.toString().length() > longestcardlength) {
                    longestcardlength = uncoveredCard.toString().length();
                }
            }
        }
        int columnspacing = 4;

        for(int i = 1; i <=7; i++)
        {
            System.out.printf("%-" + (longestcardlength + columnspacing) + "s",i);
        }

        System.out.println();
        // Print all the lanes row by row
        for (int row = 0; row < maxStackSize; row++) {
            for (int i = 1; i <= 7; i++) {
                Lane lane = Lanes.get(String.valueOf(i));
                if (row < lane.getCovered().size()) {
                    // Print covered cards as "[******]"
                    System.out.printf( "%-" + (longestcardlength + columnspacing) + "s",constants.coveredcarddisplay);
                } else if (row - lane.getCovered().size() < lane.getUncovered().size()) {
                    // Print the uncovered card
                    int uncoveredIndex = row - lane.getCovered().size();
                    String uncoveredCard = lane.getUncovered().get(uncoveredIndex).toString(); // Ensure it's a string
                    System.out.printf("%-" + (longestcardlength + columnspacing)  + "s", uncoveredCard);

                } else {
                    // Empty space for shorter lanes
                    System.out.printf("%-" + (longestcardlength + columnspacing)  + "s", " ");
                }
            }
            System.out.println();  // Move to next row
        }
    }


    public boolean isGameOver()
    {
        int numberoffullsuits = 0;

        for(var foundation : foundation.values())
        {
            if(foundation.size() == 13)
                numberoffullsuits++;
        }

        return numberoffullsuits == 4;
    }


    public void getCardToBeMoved(String move){
        try{
            String fromlabel = move.substring(0,1);
            String tolabel = move.substring(1).toUpperCase();

            if(Lanes.containsKey(fromlabel) && Lanes.get(fromlabel).getTopUncovered() != null) {
                Card card = Lanes.get(fromlabel).getTopUncovered();
                if(canMoveCard(card, tolabel)) {
                    Lanes.get(fromlabel).removeTopUncovered();
                    moveCard(card, tolabel);
                    if(!Lanes.get(fromlabel).hasUncoveredCards()) {
                        Lanes.get(fromlabel).coveredToUncoveredTopCard();
                    }
                    scoreKeeper.updateScore(fromlabel, tolabel, Lanes, foundation);
                } else {
                    throw new InvalidMoveException("Invalid Move");
                }

            } else if (fromlabel.equalsIgnoreCase("P")) {
                Card card = drawn.peek();
                if(canMoveCard(card, tolabel)) {
                    drawn.pop();
                    moveCard(card, tolabel);
                    scoreKeeper.updateScore(fromlabel, tolabel, Lanes, foundation);
                }
                else {
                    throw new InvalidMoveException("Invalid Move");
                }

            } else {
                throw new InvalidMoveException("Invalid Move");
            }
        }
        catch(InvalidMoveException e){
            System.out.println(e.getMessage());
        }

        printBoard();

    }

    public void getCardsToBeMoved(String move){
        try {
            String fromlabel = move.substring(0,1);
            String tolabel = move.substring(1,2).toUpperCase();
            int numofcardstomove;

            try {
                numofcardstomove = Integer.parseInt(move.substring(2, 3));
            } catch (NumberFormatException e) {
                throw new InvalidMoveException("Invalid number of cards to move. The move must specify a valid number.");
            }

            if(Lanes.containsKey(fromlabel) && Lanes.get(fromlabel).getTopUncovered() != null)
            {
                handleLaneToLaneMove(fromlabel, numofcardstomove, tolabel);

            } else if (fromlabel.equalsIgnoreCase("P")) {

                handleDrawnPileToLaneMove(numofcardstomove, tolabel, fromlabel);
            }
            else {
                throw new InvalidMoveException("Invalid Label: " + fromlabel);
            }
        }
        catch (InvalidMoveException e)
        {
            System.out.println(e.getMessage());
        }

        printBoard();
    }

    private void handleDrawnPileToLaneMove(int numofcardstomove, String tolabel, String fromlabel) {
        try{
            int size = drawn.size();

            // Check if there's enough cards to move
            if (size < numofcardstomove) {
                throw new InvalidMoveException("Invalid, Not enough cards in the drawn pile to move");
            }

            List<Card> cardsToMove = new ArrayList<>();
            for (int i = 0; i < numofcardstomove; i++) {
                Card card = drawn.get(size - 1 - i);  // Access the card from the top
                cardsToMove.add(card);  // Add the card to the list to be moved
            }

            if (canMoveCards(cardsToMove, tolabel)) {
                for (Card card : cardsToMove) {
                    drawn.pop();
                    moveCard(card, tolabel);
                    scoreKeeper.updateScore(fromlabel, tolabel, Lanes, foundation);
                }
            }
            else {
                throw new InvalidMoveException("Invalid, Not all cards can be moved to the target lane");
            }
        }
        catch(InvalidMoveException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            // Catch any unforseen exceptions.
            System.out.println(e.getMessage());
        }
    }

    private void handleLaneToLaneMove(String fromlabel, int numofcardstomove, String tolabel) {
        try {
            // Get the list of uncovered cards from the source lane
            List<Card> uncoveredCards = Lanes.get(fromlabel).getUncovered();
            int size = uncoveredCards.size();


            if (numofcardstomove <= 0 || numofcardstomove > size) {
                throw new InvalidMoveException("Invalid number of cards to move. You requested " + numofcardstomove + " but only " + size + " are available.");
            }

            if(canMoveCards(uncoveredCards.subList(size - numofcardstomove,size), tolabel)) {
                while (numofcardstomove > 0) {
                    Card card = uncoveredCards.getFirst();
                    Lanes.get(fromlabel).getUncovered().remove(0);
                    if (!Lanes.get(fromlabel).hasUncoveredCards())
                        Lanes.get(fromlabel).coveredToUncoveredTopCard();
                    moveCard(card, tolabel);
                    scoreKeeper.updateScore(fromlabel, tolabel, Lanes, foundation);
                    numofcardstomove--;
                    uncoveredCards = Lanes.get(fromlabel).getUncovered();
                }
            }
            else
            {
                throw new InvalidMoveException("Invalid, Not all cards can be moved");
            }
        }
        catch (InvalidMoveException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            // Catch any other unforeseen exceptions
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

    }

    // Method to check if multiple cards can be moved
    public boolean canMoveCards(List<Card> cardsToMove, String destination) {
        // Ensure there is at least one card to move
        if (cardsToMove.isEmpty()) {
            return false;
        }

        // Check if the target destination is a lane
        if (Lanes.containsKey(destination)) {
            return canMoveAllCardsToLane(cardsToMove, destination);
        }

        // If the target destination is a foundation pile
        if (foundation.containsKey(destination)) {
            return cardsToMove.size() == 1 && canMoveCard(cardsToMove.get(0), destination);
        }

        // Invalid destination
        return false;
    }

    private boolean canMoveAllCardsToLane(List<Card> cardsToMove, String destination) {
        Card topCardOnDestination = Lanes.get(destination).getTopUncovered();
        Card bottomCardOfSequence = cardsToMove.get(0);

        // If the destination lane is not empty
        if (topCardOnDestination != null) {
            // Check if the first card in the sequence can be placed on the top uncovered card
            // The first card in the sequence
            if (!bottomCardOfSequence.getColor().equals(topCardOnDestination.getColor()) &&
                    topCardOnDestination.getValue() - bottomCardOfSequence.getValue() == 1) {
                // Check that the sequence itself is valid
                return isValidSequence(cardsToMove);
            } else {
                return false;
            }
        } else {
            // If the destination lane is empty, only allow if the first card is a King (value 13)
            return bottomCardOfSequence.getValue() == 13 && isValidSequence(cardsToMove);
        }
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

    public boolean canMoveCard(Card card, String location)
    {
        if(Lanes.containsKey(location)) {
            return canMoveCardToLane(card, location);

        }
        if(foundation.containsKey(location) && card.getSuit().getKey().equals(location))
        {
            return canMove1CardToFoundationPile(card, location);
        }
        return false;
    }

    private boolean canMove1CardToFoundationPile(Card card, String location) {
        String firstletterofcardsuit = card.getSuit().getKey();
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

        return false;
    }

    private boolean canMoveCardToLane(Card card, String location) {
        if(Lanes.get(location).getTopUncovered() != null) {
            Card cardontopofpile = Lanes.get(location).getTopUncovered();
            int cardontopofpilerank = cardontopofpile.getValue();
            int cardrank = card.getValue();
            return !card.getColor().equals(cardontopofpile.getColor()) && cardontopofpilerank - cardrank == 1;
        } else return card.getValue() == 13;
    }

    public void moveCard(Card card, String location)
    {
        if(Lanes.containsKey(location)) {
            scoreKeeper.increaseMoves();
            Lanes.get(location).addUncoveredCard(card);
        }
        else if (foundation.containsKey(location)) {
            scoreKeeper.increaseMoves();
            foundation.get(location).push(card);
        }
    }
}
