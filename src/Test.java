
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Test {
    

    public static void main(String[] args) throws FileNotFoundException, IOException {
        AutoCompletion ac = new AutoCompletion();
        ac.df.setInputList(DictionaryFinder.readWordsFromCSV("testDocument.csv"));
        ac.df.formDictionary();
        ac.df.saveToFile();
        
        for (String word : ac.df.getMap().keySet()) {
            ac.t.add(word);
        }
        
        ArrayList<String> list = DictionaryFinder.readWordsFromCSV("testQueries.txt");
        String file = "matches.csv";
	FileWriter fw = new FileWriter(file);
	PrintWriter pw = new PrintWriter(fw);
        DecimalFormat decimal = new DecimalFormat("#.######");
        for(String s : list){
            AutoCompletionTrie trie = ac.t.getSubTrie(s);
            List<String> words = trie.getAllWords(trie.getRoot(), "");
            Integer totalFreq = 0;
            for(String w : words){
                String result = s + w.trim();
                totalFreq += ac.df.getMap().get(result);
            }

            pw.print(s + ",");
            for(String w : words){
                String result = s + w.trim();
                int freq = ac.df.getMap().get(result);
                double prob = ((double)(freq)) / ((double)(totalFreq));
                pw.print(result + "," + decimal.format(prob) + ",");
                System.out.println(result + " (probability " + decimal.format(prob) +")");
            }
            pw.println();
        }
        pw.close();
    }
}
