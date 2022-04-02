/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw1;

/**
 *
 * @author dawg
 */
public class TrieNode {
    
    //Node's entries
    String nodeWord; //the suffix of word that node has inside him
    TrieNode nodeParent; //Parent of my Node
    TrieNode [] children; //children of node
    boolean isNodeEndOfWord; //if this node is the end of the word
    StringBuilder StrNodeBuilder = new StringBuilder(); //The entire word has inside him(beginning from rootChildren)
    
    //Constructor 
    public TrieNode(String word) {
        
        //Initialize
        this.nodeWord = word;
        this.children = new TrieNode[26];
        this.isNodeEndOfWord = false;  
        this.StrNodeBuilder = this.StrNodeBuilder.append(word);
    }
}
