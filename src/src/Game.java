import java.util.*;

public class Game {

    private Map<String, ArrayList<Card>> lanes = new HashMap<>();
    private Map<String, Stack<Card>> foundation = new HashMap<>();
    private ArrayList<Card> deck;
    private int Score = 0;
    private int Moves = 0;
    private Stack<Card> waste = new Stack<>();
    private Stack<Card> drawn = new Stack<>();


    public Game()
    {
        InitialiseDeck();
        InitialiseFoundationPiles();
        SetUpLanes();
        PrintBoard();
    }
    public void InitialiseFoundationPiles()
    {
        foundation.put("A", new Stack<>());
        foundation.put("S", new Stack<>());
        foundation.put("C", new Stack<>());
        foundation.put("D", new Stack<>());
    }
    public void SetUpLanes()
    {
        for(int i = 1; i <= 7; i++)
        {
            lanes.put(String.valueOf(i), new ArrayList<>());
            for (int j = 1; j <= i; j++)
            {
                lanes.get(String.valueOf(i)).add(deck.getFirst());
                deck.removeFirst();
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
        System.out.printf("Score: %d\n", Score);
        System.out.printf("Moves: %d\n", Moves);
        for (var lane : lanes.values()) {
           System.out.println(lane);
        }

        if(!drawn.isEmpty())
        {
            System.out.println(drawn.peek());
        }

        
    }

    public void drawCard() {
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
}
