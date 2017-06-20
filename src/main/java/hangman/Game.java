package hangman;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/* Hangman is a Java implementation of the classic paper and pencil guessing game.  */

public class Game {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner game_state = new Scanner(new FileReader("src/main/resources/game_state.txt"));
        game_state.useDelimiter("\\s*;\\s*");
        Scanner in = new Scanner(new FileReader("src/main/resources/words.txt"));
        List<String> words = new ArrayList<String>();
        while (in.hasNextLine()) {
            words.add(in.nextLine());
        }
        in.close();

        // String[] wordsArray = words.toArray(new String[words.size()]);
        // System.out.println(Arrays.toString(wordsArray));

        System.out.println("-=+ W E L C O M E   T O   H A N G M A N +=-");
        printGameStateString(game_state);
        /*
         * Game code here
         * */
        
        game_state.close();
    }
    
    
    //Each time it's called it will print next set of characters surrounded by ; and ; in file game_state.txt
    private static void printGameStateString(Scanner state) throws FileNotFoundException{
    	if( state.hasNext() ){
	    	System.out.println(state.next());
    	}else
            System.out.println("Game has already ended!");
     }
}
