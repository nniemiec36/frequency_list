/**
 * A class that will search through an ArrayList of Passages and find
 * the occurrence of each given word to store in an ArrayList of integers.
 * It will also hold a Hashtable which matches each Passage title to their index
 * within the FrequencyList. This class will also hold values greater than 0.
 *
 * @author
 * Nicole Niemiec
 * CSE 214 R08 FELIX
 * NOVEMBER 19TH, HW #6
 *
 * @version 1
 */

import java.util.ArrayList;
import java.util.Hashtable;

public class FrequencyList{

    private String word;
    private ArrayList<Integer> frequencies;
    private Hashtable<String, Integer> passageIndices;
    private int indicies;

    /**
     * A constructor method for creating a new FrequencyList object
     * that has two parameters.
     * @param word
     *      The word to search for its frequency.
     * @param passages
     *      An ArrayList of Passage objects to look through.
     */
    public FrequencyList(String word, ArrayList<Passage> passages){
        this.word = word;
        for(int i = 0; i < passages.size(); i++){
            passageIndices.put(passages.get(i).getTitle(), i);
            for(int j = 0; j < passages.get(i).getTable().size(); i++){
                frequencies.add(passages.get(i).getTable().get(j));
            }
            indicies = i;
        }

    }

    /**
     * A method to add a given Passage object to the current FrequencyList
     * object.
     * @param p
     *      The Passage object that we are adding.
     */
    public void addPassage(Passage p){

            passageIndices.put(p.getTitle(), indicies + 1);

    }

    /**
     * A method to return an integer value of the frequency of the word
     * in a given Passage.
     * @param p
     *      The Passage object being searched.
     * @return
     *      The integer value of the word frequency.
     */
    public int getFrequency(Passage p){

        if(passageIndices.contains(p))
            return (int) p.getWordFrequency(word);
        else
            return 0;
    }

}
