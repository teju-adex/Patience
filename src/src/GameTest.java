import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GameTest {

     public static void main(String[] args) {

         //Regex Patterns to match user input to move cards:
         Pattern pattern1 = Pattern.compile("([a-zA-Z]|\\d){2}"); // Move one card
         Pattern pattern2 = Pattern.compile("([a-zA-Z]|\\d){3}"); // Move more than one card

         printStartUpMessage();

         Scanner in = new Scanner(System.in);
        Game game = new Game(); // Initialise game

        boolean stillPlaying = true;
        while(stillPlaying)
        {
            printAvailableCommands();
            System.out.print("\nUser Input: ");
            String userInput = in.nextLine();

            // Match user input to commands
            Matcher onecardmatch = pattern1.matcher(userInput);
            Matcher multicardmatch = pattern2.matcher(userInput);

            switch (userInput.toUpperCase())
            {
                case "Q":
                    stillPlaying = false;
                    break;
                case "D":
                    game.drawCard();
                    break;
                default:
                    if(onecardmatch.matches())
                        game.getCardToBeMoved(onecardmatch.group());
                    else if (multicardmatch.matches()) game.getCardsToBeMoved(multicardmatch.group());
                    else {
                        System.out.println("Invalid command entered");
                        game.printBoard();
                    }
            }

            if(game.isGameOver())
            {
                System.out.println("Game Over, Congratulations you've won!");
                stillPlaying = false;
            }
        }

        System.out.println("Thanks for playing!");
        System.out.println("Moves: " + game.scoreKeeper.getMoves());
        System.out.println("Score: " + game.scoreKeeper.getScore());

    }

    private static void printAvailableCommands() {
        System.out.println("The uncovered pile is labelled 'P'");
        System.out.println("Card lanes are numbered 1-7");
        System.out.println("Suite piles: D (diamonds), H (hearts), C (clubs) and S (spades)");
        System.out.println("The following commands are available:");
        System.out.println("\tQ = Quit");
        System.out.println("\tD = Uncover new card from the draw pile");
        System.out.println("\t<label1><label2> = move onecard from <label1> to <label2>. E.g. “P2” or “2D”");
        System.out.println("\t<label1><label2><number> = move <number> cards from <label1> to <label2>. E.g. “413”.");
        System.out.println();
    }

    private static void printStartUpMessage() {
        System.out.println("*******************************************************************************************");
        System.out.print("Welcome to Solitaire!\n\n");
       printAvailableCommands();
        System.out.println("*******************************************************************************************");
        System.out.println("\n\n");
    }
}