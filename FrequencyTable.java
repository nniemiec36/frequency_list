/**
 * A class that extends an ArrayList in order to hold FrequencyLists
 * and keep track of a passage's words and frequencies of the given word.
 * We can also build a FrequencyList from a list of Passage Objects as as
 * access the frequency of each word in a given Passage.
 *
 * @author
 * Nicole Niemiec
 * CSE 214 R08 FELIX
 * NOVEMBER 19TH, HW #6
 *
 * @version 1
 */

import java.util.ArrayList;

public class FrequencyTable {

    private static ArrayList<FrequencyList> frequencyTable = new ArrayList<>();
    private static ArrayList<Passage> passageList = new ArrayList<>();//= new ArrayList<>();

    /**
     * A method that iterates through a number of Passage objects and constructs
     * FrequencyLists with the given Passage's word frequencies.
     * @param passages
     *      The list of Passages being iterated through.
     * @return
     *      Returns a FrequencyTable constructed with all of the FrequencyLists of
     *      each Passage object.
     */
    public static FrequencyTable buildTable(ArrayList<Passage> passages){
        FrequencyTable table = new FrequencyTable();
        //frequencyTable = new ArrayList<>();
        for(int i = 0; i < passages.size(); i++){
            passageList.add(passages.get(i));
            Object[] words = passages.get(i).getWords().toArray();
            for(int j = 0; j < words.length; j++){
                FrequencyList list = new FrequencyList((String)words[i], passages);
                frequencyTable.add(list);
            }
        }
        return table;
    }

    /**
     * A method that adds a Passage object into the current FrequencyTable and updates
     * each FrequencyList accordingly.
     * @param p
     *      The Passage object being added. Must not be null or empty.
     * @throws IllegalArgumentException
     *      Throws an IllegalArgumentException if the given Passage is null or empty.
     */
    public void addPassage(Passage p) throws IllegalArgumentException{

        if(p == null)
            throw new IllegalArgumentException("Passage is null.");
        else {
            Object[] words = p.getWords().toArray();
            passageList.add(p);
            for(int i = 0; i < words.length; i++){
                FrequencyList list = new FrequencyList((String)words[i], passageList);
            }
        }


    }

    /**
     * A method that gets the frequency of a given word in a given Passage.
     * @param word
     *      The given word.
     * @param p
     *      The Passage being searched through for the frequency.
     * @return
     *      Returns the frequency of the word in that Passage object.
     */
    public int getFrequency(String word, Passage p){
        if(frequencyTable.contains(p))
            return (int) p.getWordFrequency(word);
        else
            return 0;
    }
}
