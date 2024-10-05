import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

     public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.




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
                System.out.print("Enter: ");
                String userInput = in.nextLine();
                switch (userInput.toUpperCase())
                {
                    case "Q":
                        stillPlaying = false;
                        break;
                    case "D":
                        game.drawCard();
                }





            }

        }

        System.out.println("Thanks for playing!");
        System.out.println(Moves);
        System.out.println(Score);



    }
}