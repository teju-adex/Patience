import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

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
            System.out.print("\nEnter a command: ");
            String userInput = in.nextLine();
            Matcher onecardmatch = pattern1.matcher(userInput);
            Matcher multicardmatch = pattern2.matcher(userInput);
            switch (userInput.toUpperCase())
            {
                case "Q":
                    stillPlaying = false;
                    break;
                case "D":
                    game.DrawCard();
                    break;
                default:
                    if(onecardmatch.matches())
                        game.GetCardToBeMoved(onecardmatch.group());
                    else if (multicardmatch.matches()) game.GetCardsToBeMoved(multicardmatch.group());
                    else {
                        System.out.println("Invalid command entered");
                    }
            }

            if(game.GameOver())
            {
                System.out.println("Game Over, Congratulations you've won!");
                stillPlaying = false;
            }
        }

        System.out.println("Thanks for playing!");
        System.out.println("Moves: " + game.GetMoves());
        System.out.println("Score: " + game.GetScore());

    }

    private static void printStartUpMessage() {
        System.out.println("*******************************************************************************************");
        System.out.print("Welcome to Solitaire!\n\n");
        System.out.println("The uncovered pile is labelled 'P'");
        System.out.println("Card lanes are numbered 1-7");
        System.out.println("Suite piles: D (diamonds), H (hearts), C (clubs) and S (spades)");
        System.out.println("The following commands are available:");
        System.out.println("\tQ = Quit");
        System.out.println("\tD = Uncover new card from the draw pile");
        System.out.println("\t<label1><label2> = move onecard from <label1> to <label2>. E.g. “P2” or “2D”");
        System.out.println("\t<label1><label2><number> = move <number> cards from <label1> to <label2>. E.g. “413”.");
        System.out.println();
        System.out.println("*******************************************************************************************");
        System.out.println("\n\n");
    }
}