/**
 * A class that acts as a Wrapper for story excerpt. The class is a hash table that
 * maps a String and an integer value. These values represent the words in that text file
 * and the number of occurrences of that word.
 *
 * @author
 * Nicole Niemiec
 * #112039349
 * nicole.niemiec@stonybrook.edu
 * CSE 214 R08 FELIX
 * NOVEMBER 19TH, HW #6
 *
 * @version 1
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Passage{
    private String title;
    private Hashtable<String, Integer> table;
    private int wordCount;
    private Hashtable<String, Double> similarTitles;
    private ArrayList<String> stopWords;

    /**
     * A constructor method that creates a Passage object with two parameters.
     * @param title
     *      The title of the passage.
     * @param file
     *      The file containing the passage.
     * @throws FileNotFoundException
     *      Throws a FileNotFoundException if StopWords.txt cannot be found.
     */
    public Passage(String title, File file) throws FileNotFoundException{
        File stops = new File("StopWords.txt");
        //String stops = stopWords.toString();
        stopWords = new ArrayList<>();
        this.title = title;

        try{
            Scanner read = new Scanner(stops);
            String line = "";
            while(read.hasNextLine()){
                line = read.nextLine();
                stopWords.add(line);
            }
        }catch(FileNotFoundException ex){
            throw new FileNotFoundException();
            //System.out.println("File not found.");
        }
        wordCount = 0;
        table = new Hashtable<>();
        similarTitles = new Hashtable<>();
        parseFile(file);
    }

    /**
     * Method that calculates the similarity between two Passage objects
     * using the cosine (angle between two vectors) formula and modifies
     * their similarTitles accordingly.
     * @param p1
     *      First passage being compared.
     * @param p2
     *      Second passage being compared.
     * @return
     *      Returns a percentage of similarity as a double.
     */
    public static double cosineSimilarity(Passage p1, Passage p2){

        Object[] passageWords1 = p1.getWords().toArray();
        Object[] passageWords2 = p2.getWords().toArray();

        double similarity = 0.0;
        double u = 0.0;
        double v = 0.0;
        double bottomU = 0.0;
        double top = 0.0;
        double bottomV = 0.0;
        //double topV;

        for(int i = 0; i < passageWords1.length; i++){
            String word = (String) passageWords1[i];
            if(p2.table.containsKey(word)){
                u = (double) p1.table.get(word) / (double) p1.getWordCount();
                v = (double) p2.table.get(word) / (double) p2.getWordCount();
                top += (u * v);
                bottomU += (u * u);
                bottomV += (v * v);
            }
            else{
                u = (double) p1.table.get(word) / (double) p1.getWordCount();
                bottomU += (u * u);
            }
        }
        for(int i = 0; i < passageWords2.length; i++){
            String word = (String) passageWords2[i];
            if(!p1.table.containsKey(word)){
                v = (double) p2.table.get(word) / (double) p2.getWordCount();
                bottomV += (v * v);
            }
        }

        double denominator = Math.sqrt(bottomU) * Math.sqrt(bottomV);
        double numerator = top;

        similarity = (numerator/denominator)*100.0;

        p1.similarTitles.put(p2.title, similarity);
        p2.similarTitles.put(p1.title, similarity);

        return similarity;
    }

    public void checkSimilarity(Passage p){
            if(similarTitles.get(p.title) >= 60.0)
                System.out.println("'" + this.title + "' and '" + p.title + "' may have the same author. ("
                        + (int) similarTitles.get(p.title).intValue() + "%).");
    }

    /**
     * A method that reads the given text file and counts each word's occurrence, exclduing
     * stop words. It then adds them into the hashtable.
     * @param file
     *      The file to be parsed through.
     */
    public void parseFile(File file){
        try{
            Scanner in = new Scanner(file);
            String line;
            while(in.hasNextLine()) {
                line = in.nextLine();
                line = line.replaceAll("'", "");

                String[] words = line.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");

                for (String i : words) {
                    if (!stopWords.contains(i) && !i.equals("")) {
                        wordCount++;
                        if (table.containsKey(i)) {
                            int occurrences = table.get(i);
                            table.replace(i, occurrences, occurrences + 1);
                        } else {
                            table.put(i, 1);
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex){
            System.out.println("File does not exist.");
        }
    }

    /**
     * Accessor method for returning the HashTable containing the word frequencies.
     * @return
     *      A Hashtable containing word frequencies.
     */
    public Hashtable<String, Integer> getTable() {
        return table;
    }

    /**
     * Mutator method for setting the Hashtable containing the word frequencies.
     * @param table
     *      The table to be set to.
     */
    public void setTable(Hashtable<String, Integer> table) {
        this.table = table;
    }

    /**
     * Accessor method for returning the ArrayList holding all of the stop words.
     * @return
     *      Returns an ArrayList of Strings holding all of the stop words.
     */
    public ArrayList<String> getStopWords() {
        return stopWords;
    }

    /**
     * Mutator method for setting the ArrayList holding all of the stop words.
     * @param stopWords
     *      The list being set to the current list.
     */
    public void setStopWords(ArrayList<String> stopWords) {
        this.stopWords = stopWords;
    }

    /**
     * Accessor method for returning a double of a frequency of a specific word.
     * @param word
     *      Word to find the frequency of.
     * @return
     *      Returns the frequency of the word as a double.
     */
    public double getWordFrequency(String word){
        return (double) table.get(word) / (double) wordCount;
    }

    /**
     * Accessor method for returning a Set of Strings holding all of the words in a passage.
     * @return
     *      Returns a Set of words.
     */
    public Set<String> getWords(){
        return table.keySet();
    }

    /**
     * Accessor method for returning the title of the Passage object.
     * @return
     *      Returns the title of the Passage object as a String.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Mutator method for setting the title of the Passage object.
     * @param title
     *      Title to be set to the current title.
     */
    public void setTitle(String title){
        this.title = title;

    }

    /**
     * Accessor method for returning the word count of the passage. This excludes the stop words.
     * @return
     *      Returns the word count of the passage as an int.
     */
    public int getWordCount(){
        return wordCount;
    }

    /**
     * Mutator method for setting the word count of the passage.
     * @param wordCount
     *      Word count to be set to.
     */
    public void setWordCount(int wordCount){

        this.wordCount = wordCount;

    }

    /**
     * Accessor method for returning the Hashtable of similarity between the passages.
     * @return
     *      Returns the Hashtable holding the similarities between passages.
     */
    public Hashtable<String, Double> getSimilarTitles(){
        return similarTitles;
    }

    /**
     * Mutator method for setting the Hashtable of similarity.
     * @param similarTitles
     *      Hashtable to be set to the current Hashtable.
     */
    public void setSimilarTitles(Hashtable<String, Double> similarTitles){
        this.similarTitles = similarTitles;
    }

    /**
     * Method that returns a String representation of the current Passage object.
     * @return
     *      Returns a String representation of the current Passage object.
     */
    @Override
    public String toString() {

        String tableString = "";
        tableString += String.format("%-30s", title);
        Object[] titleSimilar = similarTitles.keySet().toArray();

        for(int i = 0; i < titleSimilar.length; i++){
            if(i == 0)
                tableString += "| ";

            if(i % 2 == 0 && i != 0) {
                tableString += "\n";
                tableString += "                              | ";
            }

            String str = (String) titleSimilar[i];
            tableString += (String) titleSimilar[i] + "(" + similarTitles.get(str).intValue() + "%)";

            if(i != titleSimilar.length - 1)
                tableString += ", ";
        }

        return tableString;

    }
}
