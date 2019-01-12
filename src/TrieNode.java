
public class TrieNode {
    TrieNode[] offspring = null;
    boolean completeWord;
    char c;
    
    public TrieNode(){
        this.offspring = new TrieNode[26];
        this.completeWord = false;
        this.c = 0;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }
    
    public TrieNode[] getAllOffspring() {
        return offspring;
    }
    
    public TrieNode getOffspring(int n){
        return offspring[n];
    }
    
    public int numOffspring(){
        return this.getAllOffspring().length;
    }

    public void setOffspring(TrieNode[] offspring) {
        this.offspring = offspring;
    }

    public boolean isCompleteWord() {
        return completeWord;
    }

    public void setCompleteWord(boolean completeWord) {
        this.completeWord = completeWord;
    }

    public char print(){
        return this.c;
    }    
}
