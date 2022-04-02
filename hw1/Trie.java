/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw1;

/**
 *
 * @author dawg
 */
public class Trie {
    
    //variables
    TrieNode root;
    
    //Constructor
    public void Trie(TrieNode root) {
        
        //Build the root
        this.root = root;
    }
    
    //insertWord function
    //If succes -> return(1), if not -> return(-1)
    public int insertWord(String word) {
        
        //variables
        int i, pos, j;
        TrieNode posChild [] = new TrieNode[26];
        TrieNode curr;
        //CharWord = character of word, charNodeWord = character of currentNode we are checking him right now 
        //Each time we compare them
        char charWord ,charWordTemp, charNodeWordTemp;
        String addTempStr = new String();
        String nodeTempStr = new String();
        String parentTemp = new String();
        boolean WasFatherValidWord;
        
        //Always we begin from root
        curr = this.root; 
        
        
        for_loop:
        for(i = 0; i < word.length(); i++) { //Runing the word we want to add
         
           charWord = word.charAt(i); //Getting the nextChar of my word
           
           
           //CASE 1: this position is NULL(word does not exists) => add the Word
           
           if (curr.children[charWord - 'a'] == null) { 
               //Taking the remaining string, copying to addTempStr
               for (j = i; j < word.length(); j++) {
                   addTempStr += word.charAt(j);
                   
               }
               
               //Creating a newNode 
               curr.children[charWord - 'a'] = new TrieNode(addTempStr); 
               curr.children[charWord - 'a'].isNodeEndOfWord = true; //This str is a valid word
               curr.children[charWord - 'a'].nodeParent = curr; //set the parent
               
               break; //JOB FINISHED SUCCESFULLY
               
             } else {
               //This position is not null, the first letters are matched
               //We need to check the other letters
               test:
               while (true) {
                charWordTemp = charWord;
                //Running the nodeWord
                for (pos = 0; pos < curr.children[charWordTemp - 'a'].nodeWord.length(); pos++) {
                    
                    //if cars are matched
                    if (curr.children[charWordTemp - 'a'].nodeWord.charAt(pos) == word.charAt(i)) { 

                        //if both counters pos(for nodeWord) and i(for addWord) are lentgh - 1 && if word is a valid word
                        if ((pos == curr.children[charWordTemp - 'a'].nodeWord.length() - 1) && (i == word.length() - 1) && (curr.children[charWordTemp - 'a'].isNodeEndOfWord == true)) {
                             //Terminate with failure. Words already exists.
                             return(-1);
                        } 
                        else { //The word was false, making it true and terminate
                            if ((i == word.length() - 1) && (pos == curr.children[charWordTemp - 'a'].nodeWord.length() - 1)){
                                        curr.children[charWordTemp - 'a'].isNodeEndOfWord = true; 
                                        break for_loop; //Success
                                    }
                            else if (i == word.length() - 1) {
                                    //The currentWord(= nodeWord) has addWord inside him
                                    //AddWord must be the parent and nodeWord his kid
                                    pos++; //Taking the next char of nodeWord, cause pos is already checked

                                    //Taking the char of remaining str of currentWord
                                    charNodeWordTemp = curr.children[charWordTemp - 'a'].nodeWord.charAt(pos);

                                    for(j = 0; j < pos; j++) {
                                        addTempStr += curr.children[charWordTemp - 'a'].nodeWord.charAt(j); //New string from father
                                    }

                                    //Taking the rest String from currentWord and store it to nodeTempStr
                                    for (j = pos; j < curr.children[charWordTemp - 'a'].nodeWord.length(); j++) {
                                        nodeTempStr += curr.children[charWordTemp - 'a'].nodeWord.charAt(j);   
                                    }

                                    WasFatherValidWord = curr.children[charWordTemp - 'a'].isNodeEndOfWord;
                                    //Checking if parent had kids before, so the new child we gonna create
                                    //in CharNodeWordTemp will have those kids
                                    //We got posChild array for this reason
                                    //If positive, store them in a posChild[] array, empty my sits by making them null
                                    curr = curr.children[charWordTemp - 'a']; //CURR IS THE NODE WHO NEEDS SPLIT
                                    //CHECHICK IH HE HAS CHILDREN
                                    for (j = 0; j < 26; j++) {
                                        if ((curr.children[j] != null)) {
                                            posChild[j] = curr.children[j];
                                            curr.children[j] = null;
                                        }
                                    }
                                
                                    //Going up
                                    curr = curr.nodeParent;
                                    curr.children[charWordTemp - 'a'] = null;
                                    
                                    //father takes his string
                                    curr.children[charWordTemp - 'a'] = new TrieNode(addTempStr); //the father is word
                                    curr.children[charWordTemp - 'a'].nodeParent =  curr;
                                    curr.children[charWordTemp - 'a'].isNodeEndOfWord = true;
                                    
                                    //Moving down in children[] , to create the other kid which gonna have the kids from this father
                                    curr = curr.children[charWordTemp - 'a'];
                                    
                                    //Creating the node
                                    curr.children[charNodeWordTemp - 'a'] = new TrieNode(nodeTempStr);
                                    curr.children[charNodeWordTemp - 'a'].isNodeEndOfWord = WasFatherValidWord;
                                    curr.children[charNodeWordTemp - 'a'].nodeParent = curr;

                                    //Moving down
                                    curr =  curr.children[charNodeWordTemp - 'a'];
                                    
                                    //The transfer of the kids
                                    for (j = 0; j < 26; j++) {
                                        if (posChild[j] != null) {
                                            
                                            curr.children[j] = new TrieNode(posChild[j].nodeWord);
                                            curr.children[j] = posChild[j];
                                            curr.children[j].nodeParent = curr; //changing the parent
                                            curr.children[j].isNodeEndOfWord = posChild[j].isNodeEndOfWord;
                                        }
                                    }
                                //job finished
                                break for_loop;
                                
                            } else if (pos == curr.children[charWordTemp - 'a'].nodeWord.length() - 1){
                                //addWordword has currentWord inside him
                                //changing the current, moving "down" the trie and goes back to the loop_for
                                curr = curr.children[charWordTemp - 'a'];
                                break test;
                                }
                            else {
                                i++; //Continue Comparing, currentWord and addWord still got chars to compare
                                }
                            } 

                        }
                    else {
                        //DIFFERENT CHARS, both words have no more chars to be checked 
                        //so we need to split them and the father be the common string
                        
                        //For the common string(father)
                        for (j = 0; j < pos; j++) {
                            parentTemp += curr.children[charWord - 'a'].nodeWord.charAt(j);
                        }
                        
                        //For the rest of addWord
                        for (j = i; j < word.length(); j++) {
                            addTempStr += word.charAt(j);
                        }
                        
                        //For the rest of the currentWord
                        for (j = pos; j < curr.children[charWord - 'a'].nodeWord.length(); j++) {
                            nodeTempStr += curr.children[charWord - 'a'].nodeWord.charAt(j);
                        }
                        
                        //Taking the next chars to see where we will put them in curr.children[]
                        charNodeWordTemp = curr.children[charWord - 'a'].nodeWord.charAt(pos);
                        charWordTemp = word.charAt(i);
                        
                        //Now curr = node who needed to be split(moving down in my Trie)
                        curr = curr.children[charWord - 'a']; 
                        
                        //Checking if he had kids before
                        //We got posChild array for this reason
                        //If positive, store them in a posChild[] array, empty my sits by making them null
                        for (j = 0; j < 26; j++) {
                            if (curr.children[j] != null)  {
                                posChild[j] = curr.children[j];
                                curr.children[j] = null;
                            }
                        }
                        
                        WasFatherValidWord = curr.isNodeEndOfWord;
                        
                        //Going up in my Trie again
                        curr = curr.nodeParent;
                        
                        //Father gets his string
                        curr.children[charWord - 'a'] = null;
                        curr.children[charWord - 'a'] = new TrieNode(parentTemp);
                        curr.children[charWord - 'a'].nodeParent = curr;
                       
                        //Moving down my Trie, to create the 2 new kids
                        curr = curr.children[charWord - 'a']; //curr = node who needed to be split
                        
                        //addWord creation(child 1)
                        curr.children[charWordTemp - 'a'] = new TrieNode(addTempStr);
                        curr.children[charWordTemp - 'a'].nodeParent = curr;
                        curr.children[charWordTemp - 'a'].isNodeEndOfWord = true;
                        
                        //currentWord creation(child 2)
                        curr.children[charNodeWordTemp - 'a'] = new TrieNode(nodeTempStr);
                        curr.children[charNodeWordTemp - 'a'].nodeParent = curr;
                        if (WasFatherValidWord == true) { //if i was a valid word, now my son will be 
                            curr.isNodeEndOfWord = false; //no longer valid word
                            curr.children[charNodeWordTemp - 'a'].isNodeEndOfWord = true;
                            
                        } else {
                            curr.children[charNodeWordTemp - 'a'].isNodeEndOfWord = false; //the parent wasn't a valid word, so neither am i
                        }
                        
                        
                        //we need to transfer the kids from 
                        //father to the currentWord child
                        
                        //Moving down
                        curr = curr.children[charNodeWordTemp - 'a']; 
                        
                        //The transfer
                        for (j = 0; j < 26; j++) {
                            if (posChild[j] != null) {
                                curr.children[j] = new TrieNode(posChild[j].nodeWord);
                                curr.children[j] = posChild[j];
                                curr.children[j].nodeParent = curr; //changing the parent
                                curr.children[j].isNodeEndOfWord = posChild[j].isNodeEndOfWord;
                                
                            }
                        }
                        
                        //job finished
                        break for_loop;
                        }

                    }

                }
            }
        }
        //If we reach here => success
        return (1); 
    }

    //Recursive Method for finding words in Trie who have distance * diff chars with SimilarWord
    public void DistanceWordToFind(TrieNode node, String SimilarWord, int distance) {

        //Variables
        int i, countDist = 0;

        if (node != this.root) {
            if((node.isNodeEndOfWord == true) && (node.StrNodeBuilder.length() == SimilarWord.length())){ 
                for (i = 0; i < SimilarWord.length(); i++) {
                    if (node.StrNodeBuilder.charAt(i) != SimilarWord.charAt(i)) {
                        countDist += 1;
                    }
                }
                //If DiffChars == GivenDistance
                if (countDist == distance) {
                    System.out.println(""+node.StrNodeBuilder);
                }       
            }
        } 
        //Recursive now
        for (i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                DistanceWordToFind(node.children[i], SimilarWord, distance);
            }
        }
    }

    //Remove method
    //Input: a TrieNode
    public int WordToRemove(TrieNode NodeToRemove) {

        //variables
        TrieNode currParent, child;
        int i, myFathersChildrenPos, pos = 0;
        int countKids = 0;
        String childData;
        boolean ChildEndOfTheWord;

        //Checking if the NodeToRemove has kids
        for (i = 0; i < 26; i++) {
            if (NodeToRemove.children[i] == null) {

            } else {
                countKids += 1;
                pos = i;
            }
        }

        //Session begins
        if (countKids == 0) { //He has no kids
            //The NodeToRemove has no kids
            myFathersChildrenPos = (NodeToRemove.nodeWord.charAt(0) - 'a'); //Taking my Position from my fathersTable
            NodeToRemove.nodeParent.children[myFathersChildrenPos] = null; //Turn this sit of my parentsTable children into null
            currParent = NodeToRemove.nodeParent; //Set the parent to check if he has another kids
        } else {
            if ((countKids == 1) && (NodeToRemove.isNodeEndOfWord == false)) {
                //Need to combine in this node, the NodeToRemove.nodeWord + his child. Then, we transfer the -possible- kids of his child to him
                child = NodeToRemove.children[pos];
                ChildEndOfTheWord = NodeToRemove.children[pos].isNodeEndOfWord;
                childData = NodeToRemove.children[pos].nodeWord;
                NodeToRemove.children[pos] = null;

                NodeToRemove.nodeWord += childData;
                //Transfering the "possible" kids
                for (i = 0; i < 26; i++) {
                    if (child.children[i] != null) {
                        NodeToRemove.children[i] = new TrieNode(child.children[i].nodeWord);
                        NodeToRemove.children[i] = child.children[i];
                        NodeToRemove.children[i].nodeParent = NodeToRemove;
                        NodeToRemove.children[i].isNodeEndOfWord = child.children[i].isNodeEndOfWord;
                        child.children[i] = null;
                    }
                }
                NodeToRemove.isNodeEndOfWord = ChildEndOfTheWord; //Now the combined node is a valid(or non-valid) word. Depends from his kid
                currParent = NodeToRemove;

            } else {
                if (countKids > 1) {
                    //he has more than 1 kids.No combination needed. We make just the NodeToRemove a non valid word
                    NodeToRemove.isNodeEndOfWord = false;
                    currParent = NodeToRemove.nodeParent;
                } else {
                    //NodeToRemove is True and he has only 1 child
                    child = NodeToRemove.children[pos];
                    ChildEndOfTheWord = NodeToRemove.children[pos].isNodeEndOfWord;
                    childData = NodeToRemove.children[pos].nodeWord;
                    NodeToRemove.children[pos] = null; //Making my sit of my parentsTable null
                    NodeToRemove.nodeWord += childData; //my parent combine the 2 words

                    //Transfer the possible kids
                    for (i = 0; i < 26; i++) {
                        if (child.children[i] != null) {
                            NodeToRemove.children[i] = new TrieNode(child.children[i].nodeWord);
                            NodeToRemove.children[i] = child.children[i];
                            NodeToRemove.children[i].isNodeEndOfWord = child.children[i].isNodeEndOfWord;
                            NodeToRemove.children[i].nodeParent = NodeToRemove;
                            child.children[i] = null;
                        }
                    }
                    NodeToRemove.isNodeEndOfWord = ChildEndOfTheWord; //Now the combined node is a valid word
                    currParent = NodeToRemove.nodeParent;
                }
            }
        }    


        //Parents SESSION
        countKids = 0;
        pos = 0;
        //Now we must check the parent
        for (i = 0; i < 26; i++) {
            if (currParent.children[i] != null) {
                pos = i;
                countKids += 1;
            }
        }

        //If parent has only 1 child after delete
        if ((countKids == 1) && (currParent.isNodeEndOfWord == false) && (currParent != this.root)){
            //We want to "combine" the son to the father
            childData = currParent.children[pos].nodeWord; //taking the word from child
            currParent.nodeWord += childData;
            currParent.isNodeEndOfWord = currParent.children[pos].isNodeEndOfWord;
            child = currParent.children[pos];
            currParent.children[pos] = null;

            //Running the child Table
            for (i = 0; i < 26; i++) {
                    if (child.children[i] != null) {
                        currParent.children[i] = new TrieNode(child.children[i].nodeWord);
                        currParent.children[i] = child.children[i];
                        currParent.children[i].nodeParent = currParent;
                        currParent.children[i].isNodeEndOfWord = child.children[i].isNodeEndOfWord;
                        child.children[i] = null;
                    }
                }
        } else {}

        return(1); //Success
    }


    //Search key-Word in a Trie
    public TrieNode searchKey(String WordToSearch) {

        //Variables
        TrieNode curr;
        int i, pos;
        char nextChar;


        curr = this.root; //Begin from root

        //Initialize my counters
        i = 0; 

        while (i < WordToSearch.length()) {
            nextChar = WordToSearch.charAt(i);

            //Running the curr.children
            if (curr.children[nextChar - 'a'] == null) {
                //Failure. Word not exists
                return (null);
            }
            else {

                //not null, need to compare
                if (curr.children[nextChar - 'a'].StrNodeBuilder.toString().equals(WordToSearch)) {
                    //Word found
                    return (curr.children[nextChar - 'a']);
                } else {
                    pos = curr.children[nextChar - 'a'].nodeWord.length();
                    i += pos;
                    curr = curr.children[nextChar - 'a'];
                }
            }
        }
        //not success
        return null;
     }



    //Recursive preOrderPrint
    public void preOrderTrie(TrieNode node) {

        //Variables
        int i;


        if (node == this.root) {
        //DO not prtint
        } else {
            if(node.isNodeEndOfWord == true) { 
                //Terminal node
                //System.out.print(node.nodeWord +"#" +"("+node.nodeParent.nodeWord+")" + " ");
                System.out.print(node.nodeWord +"#" + " ");
            } else {
                //non - terminal node
                //System.out.print(node.nodeWord +"("+node.nodeParent.nodeWord+")" + " ");
                System.out.print(node.nodeWord + " ");
            }
        }

        //Recursive now
        for (i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                preOrderTrie(node.children[i]);
            }
        }
    }

    //Recursive method for finding and printing words with SUFFIX : suffix
    public void SuffixFind(TrieNode node, String suffix) {

        //Variables
        int i, posSuffix, count = 0;


        //if im not root
        if (node != this.root) {

            //if im a valid word and my entire word length >= suffix length
            if ((node.isNodeEndOfWord == true) && (node.StrNodeBuilder.length() >= suffix.length())) { 
                posSuffix = 0;

                //starting from the right position of my entire length
                for (i = (node.StrNodeBuilder.length() - suffix.length()); i < node.StrNodeBuilder.length(); i++) {
                    //Comparing
                    if (node.StrNodeBuilder.charAt(i) == suffix.charAt(posSuffix)) {
                        count += 1;
                        posSuffix += 1;
                    }
                }
                //if i have this suffix
                if (count == suffix.length()) {
                    System.out.println(""+node.StrNodeBuilder); 
                }    
            }
        } 
        //Recursive now
        for (i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                SuffixFind(node.children[i], suffix);
            }
        }
    }


    //Recursive method for printing the Dictionary
    public void DictionaryPrint(TrieNode node) {


        //Variables
        int i;

        if (node != this.root) {
            if(node.isNodeEndOfWord == true) { 
                System.out.println(""+node.StrNodeBuilder);       
            }
        } 
        //Recursive now
        for (i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                DictionaryPrint(node.children[i]);
            }
        }
    }


    //Recursive method for "building" the words in my Trie
    public void WordsInTrie(TrieNode node) {

        //Variables
        int i;
        StringBuilder currStr;
        String temp;

        if (node != this.root) {
            if (node.nodeParent == this.root) {
                temp = node.nodeWord; //Saving the Str from me
                node.StrNodeBuilder = node.StrNodeBuilder.delete(0, node.StrNodeBuilder.length()); //Delete my StringBuilder
                node.StrNodeBuilder = node.StrNodeBuilder.insert(0, temp); //Insert my parent's StringBuilder

            } else {
                currStr = node.nodeParent.StrNodeBuilder; //Saving the StringBuilder from my parent
                temp = node.nodeWord; //Saving the Str from me
                node.StrNodeBuilder = node.StrNodeBuilder.delete(0, node.StrNodeBuilder.length()); //Delete my StringBuilder
                node.StrNodeBuilder = node.StrNodeBuilder.insert(0, currStr); //Insert my parent's StringBuilder
                node.StrNodeBuilder = node.StrNodeBuilder.append(temp); //Append mine StringBuilder 
            }
        }

        //Recursive now
        for (i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                WordsInTrie(node.children[i]);
            }
        }
    }


    //Method to Check if input is correct
    public boolean ValidInput(String word) {

        //variables
        int i;

        for (i = 0; i < word.length(); i++) {
            if ((word.codePointAt(i) < 97) || (word.codePointAt(i) > 122)) { //English chars are in positions 97 - 122 in ascii table
                return false;
            }
        }
        return true;

    }
}


