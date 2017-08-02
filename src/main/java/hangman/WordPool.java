package hangman;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.jeremybrooks.knicker.AccountApi;
import net.jeremybrooks.knicker.Knicker;
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordsApi;
import net.jeremybrooks.knicker.dto.TokenStatus;
import net.jeremybrooks.knicker.dto.Word;

/**
 * Created by XOPHER on 02/08/2017.
 */
public class WordPool {


    // Wordnik API key setup and test
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


    // dictionary query parameters for easy difficulty
    public static ArrayList<String> easy() throws Exception {

        boolean hasDictionaryDef = true;
        EnumSet<Knicker.PartOfSpeech> includePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.noun);
        EnumSet<Knicker.PartOfSpeech> excludePartOfSpeech =
                EnumSet.of(Knicker.PartOfSpeech.proper_noun,
                            Knicker.PartOfSpeech.adjective,
                            Knicker.PartOfSpeech.verb,
                            Knicker.PartOfSpeech.adverb,
                            Knicker.PartOfSpeech.interjection,
                            Knicker.PartOfSpeech.pronoun,
                            Knicker.PartOfSpeech.preposition,
                            Knicker.PartOfSpeech.abbreviation,
                            Knicker.PartOfSpeech.affix,
                            Knicker.PartOfSpeech.article,
                            Knicker.PartOfSpeech.auxiliary_verb,
                            Knicker.PartOfSpeech.conjunction,
                            Knicker.PartOfSpeech.definite_article,
                            Knicker.PartOfSpeech.idiom,
                            Knicker.PartOfSpeech.preposition,
                            Knicker.PartOfSpeech.prefix,
                            Knicker.PartOfSpeech.suffix,
                            Knicker.PartOfSpeech.past_participle,
                            Knicker.PartOfSpeech.imperative,
                            Knicker.PartOfSpeech.noun_plural,
                            Knicker.PartOfSpeech.proper_noun_plural,
                            Knicker.PartOfSpeech.verb_intransitive,
                            Knicker.PartOfSpeech.pronoun,
                            Knicker.PartOfSpeech.verb_transitive,
                            Knicker.PartOfSpeech.proper_noun_posessive,
                            Knicker.PartOfSpeech.noun_posessive,
                            Knicker.PartOfSpeech.family_name,
                            Knicker.PartOfSpeech.given_name,
                            Knicker.PartOfSpeech.phrasal_prefix);
        int minCorpusCount = 10000;
        int maxCorpusCount = -1;
        int minDictionaryCount = 10;
        int maxDictionaryCount = -1;
        int minLength = 6;
        int maxLength = 12;
        Knicker.SortBy sortBy = null;
        Knicker.SortDirection sortDirection = null;
        int limit = 1000;

        List<Word> result = WordsApi.randomWords(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, sortBy, sortDirection, limit);

/*------// printout deactivated (remove block comment to reactivate)
        System.out.println("LIST PRINTOUT: " + "list for easy difficulty");
        System.out.println(" \n" + "NUMBER OF WORDS: " + result.size() + " \n");

        int count = 1;
        for (Word Word: result) {
            System.out.printf("%03d | ", count);
            System.out.println(Word.getWord());
            count++;
        }
        System.out.println(" \n" + "-=+~~~~~~~~~~+=-" + " \n");
------*/

        ArrayList<String> easyWords = new ArrayList<String>();
        for (Word w: result) {
            easyWords.add(w.getWord());
        }
        return easyWords;
    }


    // dictionary query parameters for hard difficulty
    public static ArrayList<String> hard() throws Exception {

        boolean hasDictionaryDef = false;
        EnumSet<Knicker.PartOfSpeech> includePartOfSpeech = EnumSet.of(Knicker.PartOfSpeech.noun);
        EnumSet<Knicker.PartOfSpeech> excludePartOfSpeech =
                EnumSet.of(Knicker.PartOfSpeech.proper_noun,
                        Knicker.PartOfSpeech.adjective,
                        Knicker.PartOfSpeech.verb,
                        Knicker.PartOfSpeech.adverb,
                        Knicker.PartOfSpeech.interjection,
                        Knicker.PartOfSpeech.pronoun,
                        Knicker.PartOfSpeech.preposition,
                        Knicker.PartOfSpeech.abbreviation,
                        Knicker.PartOfSpeech.affix,
                        Knicker.PartOfSpeech.article,
                        Knicker.PartOfSpeech.auxiliary_verb,
                        Knicker.PartOfSpeech.conjunction,
                        Knicker.PartOfSpeech.definite_article,
                        Knicker.PartOfSpeech.idiom,
                        Knicker.PartOfSpeech.preposition,
                        Knicker.PartOfSpeech.prefix,
                        Knicker.PartOfSpeech.suffix,
                        Knicker.PartOfSpeech.past_participle,
                        Knicker.PartOfSpeech.imperative,
                        Knicker.PartOfSpeech.noun_plural,
                        Knicker.PartOfSpeech.proper_noun_plural,
                        Knicker.PartOfSpeech.verb_intransitive,
                        Knicker.PartOfSpeech.pronoun,
                        Knicker.PartOfSpeech.verb_transitive,
                        Knicker.PartOfSpeech.proper_noun_posessive,
                        Knicker.PartOfSpeech.noun_posessive,
                        Knicker.PartOfSpeech.family_name,
                        Knicker.PartOfSpeech.given_name,
                        Knicker.PartOfSpeech.phrasal_prefix);
        int minCorpusCount = 2500;
        int maxCorpusCount = -1;
        int minDictionaryCount = 5;
        int maxDictionaryCount = -1;
        int minLength = 10;
        int maxLength = 16;
        Knicker.SortBy sortBy = null;
        Knicker.SortDirection sortDirection = null;
        int limit = 1000;

        List<Word> result = WordsApi.randomWords(hasDictionaryDef, includePartOfSpeech, excludePartOfSpeech, minCorpusCount, maxCorpusCount, minDictionaryCount, maxDictionaryCount, minLength, maxLength, sortBy, sortDirection, limit);

/*------// printout deactivated (remove block comment to reactivate)
        System.out.println("LIST PRINTOUT: " + "list for hard difficulty");
        System.out.println(" \n" + "NUMBER OF WORDS: " + result.size() + " \n");

        int count = 1;
        for (Word Word: result) {
            System.out.printf("%03d | ", count);
            System.out.println(Word.getWord());
            count++;
        }
        System.out.println(" \n" + "-=+~~~~~~~~~~+=-" + " \n");
------*/

        ArrayList<String> hardWords = new ArrayList<String>();
        for (Word w: result) {
            hardWords.add(w.getWord());
        }
        return hardWords;
    }


}
