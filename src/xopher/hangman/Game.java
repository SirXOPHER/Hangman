package xopher.hangman;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/* Hangman is a Java implementation of the classic paper and pencil guessing game.  */

public class Game {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(new FileReader("resources/words.txt"));
        List<String> words = new ArrayList<String>();
        while (in.hasNextLine()) {
            words.add(in.nextLine());
        }
        in.close();

        // String[] wordsArray = words.toArray(new String[words.size()]);
        // System.out.println(Arrays.toString(wordsArray));

        System.out.println("-=+ W E L C O M E   T O   H A N G M A N +=-");

    }
}