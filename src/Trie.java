import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Trie {
    private TrieNode root;
    
    public Trie(){
        root = new TrieNode();
    }
    
    public void setRoot(TrieNode n){
        this.root = n;
    }
    
    public TrieNode getRoot(){
        return this.root;
    }
    
    public boolean add(String key){
        if(this.contains(key)){
            return false;
        }
        TrieNode tn = this.root;
        for(int s = 0; s < key.length(); s++){
            char c = key.charAt(s);
            int index = c - 97;
            
            if(tn.getOffspring(index) == null){
                TrieNode temp = new TrieNode();
                tn.offspring[index] = temp;
                tn.offspring[index].setC(c);
                tn = temp;
            } else {
                tn = tn.getOffspring(index);
            }
        }
        tn.setCompleteWord(true);
        return true;
    }
    
    public boolean contains(String key){
        TrieNode temp = root;
        for(int s = 0; s < key.length(); s++){
            char c= key.charAt(s);
            int index = c - 97;
            if(temp.getOffspring(index) != null){
                temp = temp.getOffspring(index);
            } else {
                return false;
            }
        }
 
        if(temp==root){
            return false;
        }
         
        if(temp.isCompleteWord()){
                return true;
        }
        return false;
    }
    
    public String outputBreadthFirstSearch(){
        String output = "";
        if(this.root == null){
            output += "";
        }
        Queue<TrieNode> queue = new LinkedList<>();
        
        queue.add(this.root);
        
        while(!queue.isEmpty()){
            TrieNode temp = queue.remove();
            output += temp.c;
            for(int i = 0; i < temp.numOffspring(); i++){
                if(temp.offspring[i] != null){
                    queue.add(temp.offspring[i]);
                }
            }
        }
        return output;
    }
    
    public String outputDepthFirstSearch(){
        String output = "";
        if(this.root == null){
            output += "";
        }
        Stack<TrieNode> stack = new Stack<>();
        
        stack.push(this.root);
        
        while(!stack.isEmpty()){
            TrieNode temp = stack.pop();
            output = temp.c + output; 
            
            for(int i = 0; i < temp.numOffspring(); i++){
                if(temp.offspring[i] != null){
                    stack.push(temp.offspring[i]);
                }
            }
            
        }
        return output;
    }
    
    public Trie getSubTrie(String prefix){
        TrieNode tn = this.root;
        for(int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            int index = c - 97;
            
            if(tn.getOffspring(index) != null){
                tn = tn.getOffspring(index);
            } else {
                return null;
            }
        }
        Trie t = new Trie();
        t.setRoot(tn);
        t.getRoot().setC('\0');
        return t;
    }
    
    
    public List<String> getAllWords(TrieNode tn, String s){
        List<String> list = new ArrayList<>();
        String output = s;
        output += tn.getC();
        
        Stack<TrieNode> stack = new Stack<>();
        for(TrieNode t : tn.getAllOffspring()){
            if(t != null){
                stack.push(t);
            }
        }
        while(!stack.empty()){
            TrieNode t = stack.pop();
            list.addAll(getAllWords(t,output));
        }
        if(tn.isCompleteWord()){
            list.add(output);
        }
        
        return list;
    }    
}
