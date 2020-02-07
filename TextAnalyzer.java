/**
 * A driver class that also contains an FrequencyTable object. This class will
 * iterate through the contents of a directory, adding the Passages to the FrequencyTable
 * and determining the relationship and similarity between two Passage objects.
 * This class will also determine which works may have been written
 * by the same author.
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
import java.util.ArrayList;
import java.util.Scanner;

public class TextAnalyzer {

    private FrequencyTable frequencyTable;

    /**
     * Where the program runs.
     * @param args
     *      The input of the program.
     */
    public static void main(String[] args){

        //frequencyTable = new FrequencyTable();

        ArrayList<Passage> passages = new ArrayList<>();

        Scanner input = new Scanner(System.in);

        //String directoryPath = "";

        System.out.println("Enter the directory of a folder of text files: ");
        ///Users/nicoleniemiec/IdeaProjects/CSE214/passageFolder
        String directoryPath = input.nextLine();

        try {

            File[] directoryOfFiles = new File(directoryPath).listFiles();
            for (File i : directoryOfFiles) {

                if(!i.getName().equalsIgnoreCase("StopWords.txt")
                        && !i.getName().equalsIgnoreCase("StopWords")) {
                    Passage p = new Passage(i.getName(), i);
                    passages.add(p);
                }
            }
        }catch(FileNotFoundException ex){
            System.out.println("File not found exception.");
        }

        for(int i = 0; i < passages.size(); i++){

            for(int j = i + 1; j < passages.size(); j++){
                Passage.cosineSimilarity(passages.get(i), passages.get(j));
            }
        }

        System.out.printf("%-30s%-40s", "Text (title)", "| Similarities (%)");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------");

        for(int i = 0; i < passages.size(); i++){
            System.out.println(passages.get(i).toString());
            System.out.println("-------------------------------------------------------------------------------");
        }

        System.out.println();
        System.out.println("Suspected Texts With Same Authors");
        System.out.println("--------------------------------------");
        for(int i = 0; i < passages.size(); i++){

            for(int j = i + 1; j < passages.size(); j++){
                passages.get(i).checkSimilarity(passages.get(j));
            }
        }





    }
}
