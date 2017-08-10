package hangman;

import java.io.FileReader;
import java.util.Scanner;
import net.jeremybrooks.knicker.KnickerException;
import java.util.ArrayList;
import java.util.Random;

/* Hangman is a Java implementation of the classic paper and pencil guessing game. */

public class Game {

    private static Scanner user = new Scanner(System.in);   // reading all user input
    private static int stage = 0;       // stage of current round
    private static ArrayList<String> words = new ArrayList<String>();  // pool of words
    private static String word;         // plaintext word for current round
    private static String hidden;       // masked word
    private static boolean finished = false;
    
    public static void main(String[] args) {
    	System.out.println("-=+   W E L C O M E   T O   H A N G M A N   +=-");
    	System.out.println("Loading...");

        // Wordnik API: fetch random words list from online dictionary
    	// If API key is incorrect then switch to offline word list 
        if( WordPool.setAPIkey("YOUR_API_KEY_HERE") ){
        	playOnline();
        }else{
        	playOffline();
        }

/*------// test block deactivated (to be removed after testing phase)
        //---Wordnik test---///////////////////////////////////////////////////////////
		try {
            //WordnikTest.testRandomWord_0args(); System.out.println("-=+~~~~~~~~~~+=-" + " \n");
            //WordnikTest.testRandomWord_9args(); System.out.println("-=+~~~~~~~~~~+=-" + " \n");
            //WordnikTest.testRandomWords_12args(); System.out.println("-=+~~~~~~~~~~+=-" + " \n");

            //WordnikTest.testRandomWords_500easy(); System.out.println("-=+~~~~~~~~~~+=-" + " \n");
            //WordnikTest.testRandomWords_1000hard(); System.out.println("-=+~~~~~~~~~~+=-" + " \n");

            //WordPool.easy();
            //WordPool.hard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ///////////////////////////////////////////////////////////////End Wordnik test
------*/
		
        
        // code block asking for another round or exit
        while (!finished) {
            System.out.println(" \n \n" + "Do you fancy another round of Hangman?");
            System.out.print(" \n \n" + "YES [Y] or NO [N]:");
            if (playAgainPrompt()) {
                System.out.println(" \n \n" + "Alright, here we go again!");
                stage = 0;
                playRound();
            } else {
                System.out.println(" \n \n" + "Okay, let's call it a day! Thanks for playing Hangman!");
                user.close();
                finished = true;
            }
        }
    }

    private static void playOffline() {
    	Scanner input;
    	System.out.println("Switching to offline...");
    	System.out.println(" \n \n" + "You are playing Hangman offline today.");
    	
    	//Load words from offline list
    	try{
 	       	input = new Scanner(new FileReader("src/main/resources/words.txt"));
     	}catch(Exception e){
     		System.out.println("Offline word list not found! Closing program! \nError: " + e);
     		finished = true;
     		return;
     	}
     	
     	while (input.hasNextLine())
             words.add(input.nextLine());

         input.close();
         playRound();
    }
    
    // If word list generation fails then switch to offline word list
    private static void playOnline() {
    	System.out.println(" \n \n" + "Please choose your difficulty for this session.");
        System.out.print(" \n \n" + "[E]ASY or [H]ARD:");
        
        if (difficultyPrompt()) {
            System.out.println(" \n \n" + "Let's keep things nice and easy.");
            try {
            	System.out.println("Loading words...");
                words = WordPool.easy();
            } catch (KnickerException e) {
            	System.out.println("There was an error with online services:"+e);
            	playOffline();
            	return;
            }
        } else {
            System.out.println(" \n \n" + "So you are up for a bit of a challenge? Good luck!");
            try {
            	System.out.println("Loading words...");
                words = WordPool.hard();
            } catch (KnickerException e) {
            	System.out.println("There was an error with online services: "+e);
            	playOffline();
            	return;
            }
        }
        playRound();
    }
    
    private static void playRound() {
    	String letters  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // remaining letters to pick from
    	String alphabet  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // required for validity check
    	Scanner game_state;
    	int stage_tracker;
    	
    	try{
    		game_state = new Scanner(new FileReader("src/main/resources/game_state.txt"));
    	}catch(Exception e){
    		e.printStackTrace();
    		return;
    	}
  
    	game_state.useDelimiter("\\s*;\\s*");
        printGameStateString(game_state); // show title screen on console (first in game_state.txt)
        Random pick = new Random();
        int  n = pick.nextInt(words.size()) + 1; // dynamic approach: variable that measures number of words in text file
        word = words.get(n-1); // to-do: replace with .remove() and handle empty words list
        hidden = new String(new char[word.length()]).replace("\0", "_");

        while (hidden.contains("_") && stage < 7) {
            // System.out.println(" \n \n" + "ONLY FOR DEVELOPMENT // " + "STAGE:" + stage + " | " + "WORD:" + word);
            System.out.println(" \n \n" + "HIDDEN WORD: " + hidden.replace("", " ").trim());
            System.out.println(" \n \n" + letters.replace("", " ").trim());
            System.out.println(" \n \n" + "Take a guess (enter a letter A - Z)");
            System.out.print(">");

            char c = user.next().toLowerCase().charAt(0);

            while (!alphabet.contains(String.valueOf(c).toUpperCase())) {
                System.out.println("That's not a letter! Please try again.");
                System.out.print(">");
                c = user.next().toLowerCase().charAt(0);
            }

            while (!letters.contains(String.valueOf(c).toUpperCase())) {
            	 System.out.println("You already tried this letter! Please pick another one.");
                 System.out.print(">");
                 c = user.next().toLowerCase().charAt(0);
            }
            letters = letters.replace(String.valueOf(c).toUpperCase(), "\u2588");

            System.out.println(" \n" + "-=+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+=-" + " \n");

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
    
    // Each time it's called it will print next set of characters surrounded by ; and ; in file game_state.txt
    private static void printGameStateString(Scanner state) {
    	if( state.hasNext() ){
	    	System.out.println(state.next());
    	}else
            System.out.println("Game has already ended!");
     }

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

    private static boolean difficultyPrompt() {
        String input = user.next();
        if(input.toLowerCase().equals("e")) {
            return true;
        }
        else if(input.toLowerCase().equals("h")) {
            return false;
        }
        else{
            System.out.println(" \n \n" + "Fat-finger error? Let's try this again:");
            return difficultyPrompt();
        }
    }

}
