package hangman;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* Hangman is a Java implementation of the classic paper and pencil guessing game. */

public class Game {

    private static Scanner user = new Scanner(System.in);   // reading all user input

    private static int stage = 0;       // stage of current round
    private static List<String> words;  // pool of words
    private static String word;         // plaintext word for current round
    private static String hidden;       // masked word

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new FileReader("src/main/resources/words.txt"));
        words = new ArrayList<String>();
        while (input.hasNextLine()) {
            words.add(input.nextLine());
        }
        input.close();

        System.out.println("-=+   W E L C O M E   T O   H A N G M A N   +=-");

        playRound();

        // code block asking for another round or exit
        boolean finished = false;
	System.out.println(" \n \n" + "Do you fancy another round of Hangman?");
        System.out.print(" \n \n" + "YES [Y] or NO [N]:");
        do {
            if (playAgainPrompt()) {
                System.out.println(" \n \n" + "Alright, here we go again!");
                stage = 0;
                playRound();
            } else {
                System.out.println(" \n \n" + "Okay, let's call it a day! Thanks for playing Hangman!");
		            user.close();
                finished = true;
            }
        } while (!finished);
        user.close(); // minor issue: is this statement reachable? If not, let's delete this line.
    }

    private static void playRound() throws FileNotFoundException {
        Scanner game_state = new Scanner(new FileReader("src/main/resources/game_state.txt"));
        game_state.useDelimiter("\\s*;\\s*");
        printGameStateString(game_state); // show title screen on console (first in game_state.txt)
        Random pick = new Random();
        int  n = pick.nextInt(100) + 1;
        word = words.get(n); // to-do: replace with .remove() and handle empty words list
        hidden = new String(new char[word.length()]).replace("\0", "_");
        int stage_tracker;

        while (hidden.contains("_") && stage < 7) {
            // System.out.println(" \n \n" + "ONLY FOR DEVELOPMENT // " + "STAGE:" + stage + " | " + "WORD:" + word);
            System.out.println(" \n \n" + "HIDDEN WORD: " + hidden.replace("", " ").trim());
            System.out.println(" \n \n" + "Take a guess (enter a letter A - Z)");

            char c = user.next().toLowerCase().charAt(0);

            stage_tracker = stage;
            evaluateGuess(checkInput(c));
            if (stage_tracker != stage) { printGameStateString(game_state); }

            if (stage == 7) {
                System.out.println(" \n \n" + "Oh no! You couldn't guess the word \"" + word + "\""
                                            + " in time to escape the executioner!");
            }

            if (hidden.equals(word)) {
                System.out.println(" \n \n" + "SOLUTION: " + word.replace("", " ").trim());
                System.out.println(" \n \n" + "Congrats! You correctly guessed the word \"" + word + "\"");
                break;
            }
        }
        game_state.close();
    }

    private static String checkInput(char letter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if      (hidden.charAt(i) != '_')   { sb.append(word.charAt(i)); }
            else if (letter == word.charAt(i))  { sb.append(letter); }
            else                                { sb.append("_"); }
        }
        return sb.toString();
    }

    private static void evaluateGuess(String checked) {
        if      (hidden.equals(checked)) { stage++; }
        else                             { hidden = checked; }
    }
    
    // each time it's called it will print next set of characters surrounded by ; and ; in file game_state.txt
    private static void printGameStateString(Scanner state) throws FileNotFoundException{
    	if( state.hasNext() ){
	    	System.out.println(state.next());
    	}else
            System.out.println("Game has already ended!");
     }

    // current issue:
    // fat-finger error will be printed after completion of each round
    // (without input)
    private static boolean playAgainPrompt() {
	 String input = user.next();
	 if(input.toLowerCase().equals("y")) { 
	 	return true;
	 }
	 else if(input.toLowerCase().equals("n")) { 
	 	return false;
	 }
	 else{
	 	System.out.println(" \n \n" + "Fat-finger error? Let's try this again:");
	 	return playAgainPrompt();
	 }
   }
}