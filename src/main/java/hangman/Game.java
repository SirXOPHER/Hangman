package hangman;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import net.jeremybrooks.knicker.AccountApi;
import net.jeremybrooks.knicker.Knicker;
import net.jeremybrooks.knicker.Knicker.SortBy;
import net.jeremybrooks.knicker.Knicker.SortDirection;
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordsApi;
import net.jeremybrooks.knicker.dto.TokenStatus;
import net.jeremybrooks.knicker.dto.Word;

import java.util.ArrayList;
import java.util.EnumSet;
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

        //Wornik test
        setAPIkey("YOUR_API_KEY_HERE");
		try {
			testRandomWord_0args();
			testRandomWord_9args();
			testRandomWords_12args();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println("-=+   W E L C O M E   T O   H A N G M A N   +=-");

        playRound();

        // code block asking for another round or exit
        boolean finished = false;
        do {
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
        } while (!finished);
    }

    private static void playRound() throws FileNotFoundException {
        Scanner game_state = new Scanner(new FileReader("src/main/resources/game_state.txt"));
        game_state.useDelimiter("\\s*;\\s*");
        printGameStateString(game_state); // show title screen on console (first in game_state.txt)
        Random pick = new Random();
        int wordPoolSize = words.size(); // dynamic approach: variable that measures number of words in text file
        int  n = pick.nextInt(wordPoolSize) + 1;
        word = words.get(n-1); // to-do: replace with .remove() and handle empty words list
        hidden = new String(new char[word.length()]).replace("\0", "_");
        String letters  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // remaining letters to pick from
        String alphabet  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // required for validity check
        int stage_tracker;

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

            if (letters.contains(String.valueOf(c).toUpperCase())) {
                letters = letters.replace(String.valueOf(c).toUpperCase(), "\u2588");
            } else {
                System.out.println("You already tried this letter! Please pick another one.");
                System.out.print(">");
                c = user.next().toLowerCase().charAt(0);
            }

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
    
    // each time it's called it will print next set of characters surrounded by ; and ; in file game_state.txt
    private static void printGameStateString(Scanner state) throws FileNotFoundException{
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
    
/////////////////////////////////////////////////////////////////////////////////////////
/////Wordnik test
/////////////////////////////////////////////////////////////////////////////////////////

//Test Wornik API
public static Boolean setAPIkey( String key)  {
	System.setProperty("WORDNIK_API_KEY", key );
	TokenStatus status;
	// Check the status of the API key
	try {
		status = AccountApi.apiTokenStatus();
		if (status.isValid()) {
			System.out.println("API key is valid.");
			return true;
		} else {
			System.out.println("API key is invalid!");
			System.exit(1);
			return false;
		}
	} catch (KnickerException e) {
		System.out.println("Set your API key!");
		e.printStackTrace();
		System.out.println("Set your API key!");
		return false;
	}
}


//Test for calling multiple words with specific conditions
public static void testRandomWords_12args() throws Exception {
	System.out.println("Testing multiple random word with specific conditions");
	boolean hasDictionaryDef = false;
	EnumSet<net.jeremybrooks.knicker.Knicker.PartOfSpeech> includePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.noun);; //to fix so the method randomWords works
	EnumSet<net.jeremybrooks.knicker.Knicker.PartOfSpeech> excludePartOfSpeech = null; //to fix so the method randomWords work
	int minCorpusCount = 0;
	int maxCorpusCount = 0;
	int minDictionaryCount = 0;
	int maxDictionaryCount = 0;
	int minLength = 0;
	int maxLength = 0;
	SortBy sortBy = null;
	SortDirection sortDirection = null;
	int limit = 3;
	
	List<Word> result = WordsApi.randomWords(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, sortBy, sortDirection, limit);
	
	minLength = 10;
	maxLength = 10;
	result = WordsApi.randomWords(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, sortBy, sortDirection, limit);
	//Print list array
	//System.out.println(Arrays.toString(result.toArray())); //prints everything
	for (Word Word: result) {
		System.out.println(Word.getWord());
	}
}


//Test for calling random word with NO conditions
public static void testRandomWord_0args() throws Exception {
	System.out.println("Testing one random word with no conditions");
	Word result = WordsApi.randomWord();
	System.out.println( result.getWord() );
}


//Test for calling random word with specific conditions
public static void testRandomWord_9args() throws Exception {
	System.out.println("Testing one random word with specific conditions");
	boolean hasDictionaryDef = false;
	EnumSet<net.jeremybrooks.knicker.Knicker.PartOfSpeech> includePartOfSpeech = null;
	EnumSet<net.jeremybrooks.knicker.Knicker.PartOfSpeech> excludePartOfSpeech = null;
	int minCorpusCount = 0;
	int maxCorpusCount = 0;
	int minDictionaryCount = 0;
	int maxDictionaryCount = 0;
	int minLength = 0;
	int maxLength = 0;
	
	Word result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);
	
	minLength = 10;
	result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);
	
	minLength = 5;
	includePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.noun);
	result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);
	
	hasDictionaryDef = false;
	result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);
	
	includePartOfSpeech = null;
	maxLength = 5;
	result = WordsApi.randomWord(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength);
	
	//Print list array
	System.out.println( result.getWord() );
}

/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////End wordnik test
}
