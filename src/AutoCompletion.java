
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class AutoCompletion {
    DictionaryFinder df;
    AutoCompletionTrie t;
    
    public AutoCompletion(){
        this.df = new DictionaryFinder();
        this.t = new AutoCompletionTrie();
    }    
    
    public void fillTrie(){
        for (String word : this.df.getMap().keySet()) {
                this.t.add(word);
            }    
    }
    
    public static ArrayList<String> readQueries(String file) throws FileNotFoundException {
        Scanner sc=new Scanner(new File(file));
        ArrayList<String> words=new ArrayList<>();
        String str;
        while(sc.hasNextLine()){
            str=sc.nextLine();
            str=str.trim();
            str=str.toLowerCase();
            words.add(str);
        }
        return words;
    }
    
    public Integer getFrequency(List<String> words, String s){
        Integer total = 0;
        for(String w : words){
            String result = s + w.trim();
            total += this.df.getMap().get(result);
        }
        return total;
    }
    
    public HashMap<Double, String> getProbabilities(List<String> words, String s, Integer totalFreq){
        HashMap<Double, String> map = new HashMap<>();
        for(int i = 0; i < words.size(); i++){
            String result = s + words.get(i).trim();
            int freq = this.df.getMap().get(result);
            double prob = ((double)(freq)) / ((double)(totalFreq));
            map.put(prob, result);
            //pw.print(result + "," + decimal.format(prob) + ",");
            //System.out.println(result + " (probability " + decimal.format(prob) +")");
        } 
        return map;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        AutoCompletion ac = new AutoCompletion();
        ac.df.setInputList(DictionaryFinder.readWordsFromCSV("lotr.csv"));
        ac.df.formDictionary();
        ac.df.saveToFile();
        
        ac.fillTrie();
        
        ArrayList<String> list = readQueries("lotrQueries.csv");
        String file = "lotrMatches.csv";
	FileWriter fw = new FileWriter(file);
	PrintWriter pw = new PrintWriter(fw);
        DecimalFormat decimal = new DecimalFormat("#.######");
        
        for(String s : list){
            AutoCompletionTrie trie = ac.t.getSubTrie(s);
            List<String> words = trie.getAllWords(trie.getRoot(), "");
            
            Integer totalFreq = ac.getFrequency(words, s);
            HashMap<Double, String> map = ac.getProbabilities(words, s, totalFreq);
                 
            TreeMap<Double, String> treeMap = new TreeMap<>(map);
            pw.print(s + ",");
            int count = 0;
            for (Double prob : treeMap.descendingKeySet()) {
                if(count == 3){
                    break;
                }
                String result = treeMap.get(prob);
                pw.print(result + "," + decimal.format(prob) + ",");
                System.out.println(result + " (probability " + decimal.format(prob) +")");
                count++;
            }
            pw.println();
        }
        pw.close();
    }
}
