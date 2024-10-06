import java.util.*;

public class Game {

    private final Map<String, Stack<Card>> lanes = new HashMap<>();
    private final Map<String, Stack<Card>> foundation = new HashMap<>();
    private ArrayList<Card> deck;
    private final int Score = 0;
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
            lanes.put(String.valueOf(i), new Stack<>());
            for (int j = 1; j <= i; j++)
            {
                lanes.get(String.valueOf(i)).add(deck.removeFirst());

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
        String[][] board = new String[7][7];
        boolean[][] uncovered = new boolean[7][7];
        System.out.printf("Score: %d\t\t\t\t\t\t\t\t\t\t\t\t\t Moves: %d\n\n", Score, Moves);
        System.out.printf("%-50s", "[D]");
        System.out.printf("%-10s", foundation.get("H").isEmpty() ? "[ ]" : foundation.get("H").peek());
        System.out.printf("%-10s", foundation.get("S").isEmpty() ? "[ ]" : foundation.get("S").peek());
        System.out.printf("%-10s", foundation.get("C").isEmpty() ? "[ ]" : foundation.get("C").peek());
        System.out.printf("%-10s\n", foundation.get("D").isEmpty() ? "[ ]" : foundation.get("D").peek());
        if(!drawn.isEmpty())
        {
            System.out.println("Drawn: " + drawn.peek().toString());
            System.out.print("\n");
        }
        for (var lane : lanes.values()) {
            int size = lane.size();
            System.out.printf("%-21s",(size));
        }
        System.out.println();
        for (var lane: lanes.values()) {
            if(!lane.isEmpty())
            {

                System.out.printf("%-30s",lane.peek().toString());
            }
            else
            {

                System.out.printf("%-30s"," ");
            }
        }



        
    }

    public void DrawCard() {
        if (!waste.isEmpty())
        {
            drawn.add(waste.pop());
            PrintBoard();
        }
        else
        {
            waste.addAll(drawn);
            drawn.clear();
            PrintBoard();
        }

    }

    public void GetCardToBeMoved(String move){
        String label1 = move.substring(0,1);
        String label2 = move.substring(1).toUpperCase();

        if(lanes.containsKey(label1) && !lanes.get(label1).isEmpty())
        {
            Card card = lanes.get(label1).peek();
            if(CanMoveCard(card,label2))
            {
                lanes.get(label1).pop();
                MoveCard(card,label2);
            }
            else
            {
                System.out.println("Invalid Move");
                PrintBoard();
            }

        } else if (label1.equalsIgnoreCase("P")) {
            Card card = drawn.peek();
            if(CanMoveCard(card,label2))
            {
                drawn.pop();
                MoveCard(card,label2);
            }
            else
            {
                System.out.println("Invalid Move");
                PrintBoard();
            }


        }
        else
        {
            System.out.println("Invalid Move");
            PrintBoard();
        }
    }

    public boolean CanMoveCard(Card card, String location)
    {
        if(lanes.containsKey(location))
        {
            if(!lanes.get(location).isEmpty())
            {
                Card cardontopofpile = lanes.get(location).peek();
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

                return  cardontopofpilerank - cardrank == 1;
            }
        }
        return false;
    }

    public void MoveCard(Card card, String location)
    {
        if(lanes.containsKey(location))
        {
            Moves++;
            lanes.get(location).push(card);
            PrintBoard();
        } else if (foundation.containsKey(location)) {
            Moves++;

            foundation.get(location).push(card);
            PrintBoard();

        }
    }
}
