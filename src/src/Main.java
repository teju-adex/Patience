import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

     public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.


         Pattern pattern1 = Pattern.compile("([a-zA-Z]|\\d)+([a-zA-Z]|\\d)");
         Pattern pattern2 = Pattern.compile("([a-zA-Z]|\\d)+([a-zA-Z]|\\d)([a-zA-Z]|\\d)");
         //Matcher matcher;

        System.out.print("Are you ready to play? [Y/N]: ");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        int Score = 0;
        int Moves = 0;
        if(answer.equalsIgnoreCase("Y")) {
            Game game = new Game();
            boolean stillPlaying = true;
            while(stillPlaying)
            {
                System.out.print("\nEnter: ");
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
                        if(onecardmatch.find())
                            game.GetCardToBeMoved(onecardmatch.group());
                        else if (multicardmatch.find()) game.GetCardToBeMoved(multicardmatch.group());
                        else {
                            System.out.println("Invalid command entered");
                        }
                }





            }

        }

        System.out.println("Thanks for playing!");
        System.out.println(Moves);
        System.out.println(Score);



    }
}