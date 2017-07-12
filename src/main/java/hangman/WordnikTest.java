package hangman;

import net.jeremybrooks.knicker.AccountApi;
import net.jeremybrooks.knicker.Knicker;
// import net.jeremybrooks.knicker.Knicker.SortBy;
// import net.jeremybrooks.knicker.Knicker.SortDirection;
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordsApi;
import net.jeremybrooks.knicker.dto.TokenStatus;
import net.jeremybrooks.knicker.dto.Word;

// import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by XOPHER on 12/07/2017.
 */
public class WordnikTest {

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
        System.out.println("Test description" + "Testing multiple random word with specific conditions");
        boolean hasDictionaryDef = false;
        EnumSet<Knicker.PartOfSpeech> includePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.noun);; //to fix so the method randomWords works
        EnumSet<net.jeremybrooks.knicker.Knicker.PartOfSpeech> excludePartOfSpeech = null; //to fix so the method randomWords work
        int minCorpusCount = 0;
        int maxCorpusCount = 0;
        int minDictionaryCount = 0;
        int maxDictionaryCount = 0;
        int minLength = 0;
        int maxLength = 0;
        Knicker.SortBy sortBy = null;
        Knicker.SortDirection sortDirection = null;
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
        System.out.println("Test description" + "Testing one random word with no conditions");
        Word result = WordsApi.randomWord();
        System.out.println( result.getWord() );
    }


    //Test for calling random word with specific conditions
    public static void testRandomWord_9args() throws Exception {
        System.out.println("Test description" + "Testing one random word with specific conditions");
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


    //Testing dictionary query parameters for easy difficulty
    public static void testRandomWords_500easy() throws Exception {
        System.out.println("TEST DESCRIPTION: " + "Candidate list for easy difficulty");
        boolean hasDictionaryDef = true;
        EnumSet<Knicker.PartOfSpeech> includePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.noun); //to fix so the method randomWords works
        EnumSet<Knicker.PartOfSpeech> excludePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.proper_noun); //to fix so the method randomWords work
        int minCorpusCount = 0;
        int maxCorpusCount = -1;
        int minDictionaryCount = 20;
        int maxDictionaryCount = -1;
        int minLength = 8;
        int maxLength = 12;
        Knicker.SortBy sortBy = null;
        Knicker.SortDirection sortDirection = null;
        int limit = 500;

        List<Word> result = WordsApi.randomWords(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, sortBy, sortDirection, limit);

        System.out.println(" \n" + "NUMBER OF WORDS: " + result.size() + " \n");

        int count = 1;
        for (Word Word: result) {
            System.out.printf("%03d | ", count);
            System.out.println(Word.getWord());
            count++;
        }
    }


    //Testing dictionary query parameters for hard difficulty
    public static void testRandomWords_1000hard() throws Exception {
        System.out.println("TEST DESCRIPTION: " + "Candidate list for hard difficulty");
        boolean hasDictionaryDef = false;
        EnumSet<Knicker.PartOfSpeech> includePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.noun); //to fix so the method randomWords works
        EnumSet<Knicker.PartOfSpeech> excludePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.proper_noun); //to fix so the method randomWords work
        int minCorpusCount = 0;
        int maxCorpusCount = -1;
        int minDictionaryCount = 10;
        int maxDictionaryCount = -1;
        int minLength = 12;
        int maxLength = 16;
        Knicker.SortBy sortBy = null;
        Knicker.SortDirection sortDirection = null;
        int limit = 1000;

        List<Word> result = WordsApi.randomWords(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, sortBy, sortDirection, limit);

        System.out.println(" \n" + "NUMBER OF WORDS: " + result.size() + " \n");

        int count = 1;
        for (Word Word: result) {
            System.out.printf("%03d | ", count);
            System.out.println(Word.getWord());
            count++;
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////End wordnik test

}
