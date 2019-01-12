

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class DictionaryFinder {
    HashMap<String,Integer> map;
    ArrayList<String> inputList;
    
    public DictionaryFinder(){
        this.map = new HashMap<>();
        this.inputList = new ArrayList<>();
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.map = map;
    }

    public ArrayList<String> getInputList() {
        return inputList;
    }

    public void setInputList(ArrayList<String> inputList) {
        this.inputList = inputList;
    }
    
    
       
/**
 * Reads all the words in a comma separated text document into an Array
     * @param file
     * @return 
     * @throws java.io.FileNotFoundException 
 */   
    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
        Scanner sc=new Scanner(new File(file));
        sc.useDelimiter(" |,");
        ArrayList<String> words=new ArrayList<>();
        String str;
        while(sc.hasNext()){
            str=sc.next();
            str=str.trim();
            str=str.toLowerCase();
            words.add(str);
        }
        return words;
    }
    
    public static void saveCollectionToFile(Collection<?> c,String file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(Object w: c){
            printWriter.println(w.toString());
        }
        printWriter.close();
    }
     
    public void formDictionary(){
        for(String word : inputList){
            Integer frequency = map.get(word);
            if(frequency == null){
                map.put(word, 1);
            } else {
                map.put(word, frequency+1);
            }
        }  
    } 
         
    public void saveToFile() throws IOException{
        Map<String,Integer> treeMap = new TreeMap<>(map);
        
        String file = "test.csv";
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        for (String word : treeMap.keySet()) {
            int freq = map.get(word);
            pw.println(word + ", " + freq);
        }
        pw.close();
    }
       
  
    public void testMap(){
        for(String word : map.keySet()){
            String name = word;
            int value = map.get(word);
            System.out.println(name + ", " + value);
            
        }        
    }

   
    
    public static void main(String[] args) throws Exception {
        DictionaryFinder df=new DictionaryFinder();
        df.inputList = readWordsFromCSV("lotr.csv");
        
        df.formDictionary();
        df.saveToFile();
        df.testMap();
    }
    
}
