/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw1;
import java.util.Scanner;

/**
 *
 * @author dawg
 */
public class HW1 {

    public static void main(String[] args) {
        
        //Variables
        Scanner sc = new Scanner(System.in);
        String choice, suffix, word, WordLowerCase;
        int result, distance;
        Trie myTrie = new Trie();
        TrieNode TrieRoot = new TrieNode(""); //Initialize the root
        TrieNode SearchNode;
        
        //Initialize my Trie
        myTrie.Trie(TrieRoot);
        
        
        //Session of inputs begins
        while (true) {
            System.out.println("?: ");
            while (sc.hasNextLine()) { //Each time we reading all the line
                
                choice = sc.next(); //The preference of the user(insert, remove, search etc..)
                
                
                switch(choice) {

                    //Quit session
                    case "-q": System.out.println("Bye bye!");
                               return; 
                    
                    //Insert Session
                    case "-i": word = sc.next();     //The word
                               WordLowerCase = word.toLowerCase();
                               if (!myTrie.ValidInput(WordLowerCase)) { //Wrong input
                                   break;
                               }
                               result = myTrie.insertWord(WordLowerCase);
                               if (result == 1) {
                                   System.out.println("ADD "+word+ " OK");
                               } else {
                                   System.out.println("ADD "+word+ " NOK");
                               }
                               break;

                    //Remove Session
                    case "-r": 
                               word = sc.next();     //The word
                               WordLowerCase = word.toLowerCase();
                               if (!myTrie.ValidInput(WordLowerCase)) {
                                   break;
                               }
                               myTrie.WordsInTrie(TrieRoot);
                               SearchNode = myTrie.searchKey(WordLowerCase);
                               if (SearchNode == null) {
                                   //Word not exists, 
                                   System.out.println("RMV "+word+" NOK");
                               } else {
                                   myTrie.WordToRemove(SearchNode);
                                   System.out.println("RMV "+word+" OK");
                               }
                               break;
                    
                    //Search/find Session
                    case "-f": word = sc.next();     //The word
                               WordLowerCase = word.toLowerCase();
                               if (!myTrie.ValidInput(WordLowerCase)) {
                                   break;
                               }
                               myTrie.WordsInTrie(TrieRoot);
                               SearchNode = myTrie.searchKey(WordLowerCase);
                               if (SearchNode == null) {
                                   System.out.println("FND "+word+ " NOK");
                               } else {
                                   System.out.println("FND "+word+ " OK");
                               }
                               break;
                               
                    //Print (preOrder) Session
                    case "-p": System.out.print("PreOrder: ");
                               myTrie.preOrderTrie(TrieRoot);
                               System.out.println();
                               break;

                    //Print the Dictionary Session           
                    case "-d": myTrie.WordsInTrie(TrieRoot);
                               System.out.println();
                               System.out.println("***** Dictionary *****");
                               myTrie.DictionaryPrint(TrieRoot);
                               System.out.println();
                               break;

                    //Prints the words with distance X
                    case "-w": word = sc.next();     //The word
                               WordLowerCase = word.toLowerCase();
                               if (!myTrie.ValidInput(WordLowerCase)) {
                                   break;
                               }
                               System.out.println();
                               distance = sc.nextInt();
                               System.out.println("Distant words of "+word+" ("+distance+"):");
                               myTrie.WordsInTrie(TrieRoot);
                               myTrie.DistanceWordToFind(TrieRoot, WordLowerCase, distance);
                               System.out.println();
                               break;

                    //Prints the words with Suffix
                    case "-s": suffix = sc.next();
                               WordLowerCase = suffix.toLowerCase();
                               if (!myTrie.ValidInput(WordLowerCase)) {
                                   break;
                               }
                               System.out.println();
                               System.out.println("Words with suffix "+suffix+":");
                               myTrie.WordsInTrie(TrieRoot);
                               myTrie.SuffixFind(TrieRoot, WordLowerCase);
                               System.out.println();
                               break;

                    //When wrong input given
                    default: break;
                }
                break;
            }
        }
    }
}
