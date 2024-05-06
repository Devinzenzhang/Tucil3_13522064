package SearchAlgorithm;
import StringCheck.*;
import java.util.List;
import java.util.ArrayList;

public class SearchAlgorithm {
    // atribut
    private static List<StringNode> stringList = new ArrayList<StringNode>(); // list nodes
    private static List<Integer> nodesToExpand = new ArrayList<Integer>(); // list index node mana yang bisa di expand, untuk a*
    private static int VisitedNodes = 0;

    // getter setter
    public static void AddNode(StringNode n){stringList.add(n);}
    public static StringNode getNode(int index){return (stringList.get(index));}
    public static void AddTask(int n){nodesToExpand.add(n);}
    public static void RemoveTask(int idx){nodesToExpand.remove(idx);}
    public static void incrementVisitedNodes(){VisitedNodes++;}
    public static int getVisitedNodes(){return VisitedNodes;}

    // cek apakah string ada di list
    public static int getIndex(String s){
        for (int i = 0; i < stringList.size(); i++){
            if (s.equals(getNode(i).getWord())){return i;}
        }
        return -1;
    }

    // print start path to end path
    public static int printPath(int index){
        if(index == -1){return 0;} else {
            int x = 1;
            StringNode currentNode = getNode(index);
            x += printPath(currentNode.getParentIdx());
            System.out.println(x + ". " + currentNode.getWord());
            return x;
        }
    }

    // algoritma
    public static int UCS(String dest, String path){
        for (int i = 0; i < stringList.size(); i++){
            String currentWord = getNode(i).getWord();

            // expand
            incrementVisitedNodes();
            for (int pos = 0; pos < currentWord.length(); pos++){ // posisi huruf yang ditukar
                StringBuilder next = new StringBuilder(currentWord);
                for (char c = 'a'; c <= 'z'; c++){ // huruf yang ditukar
                    if (c != currentWord.charAt(pos)){
                        next.setCharAt(pos, c);
                        String maybeNext = next.toString();
                        if (StringCheck.checkDictionary(path, maybeNext, 2)){ // cek di kamus
                            if (getIndex(maybeNext) == -1){ // add kalau belum ada
                                StringNode newNode = new StringNode(maybeNext, (getNode(i).getCost() + 1), 0, i);
                                AddNode(newNode);
                                if (maybeNext.equals(dest)){return (stringList.size() - 1);}
                            }
                        }
                    }
                }
            }
        }
        return -1; // loop habis = no solution
    }

    public static int Greedy(String dest, String path){
        for (int i = 0; i < stringList.size(); i++){
            String currentWord = getNode(i).getWord();
            incrementVisitedNodes();
            StringNode nextWord = null;
            for (int pos = 0; pos < currentWord.length(); pos++){ // posisi huruf yang ditukar
                StringBuilder next = new StringBuilder(currentWord);
                for (char c = 'a'; c <= 'z'; c++){ // huruf yang ditukar
                    if (c != currentWord.charAt(pos)){
                        next.setCharAt(pos, c);
                        String maybeNext = next.toString();
                        if (StringCheck.checkDictionary(path, maybeNext, 2)){ // cek di kamus
                            // kalau sama langsung return
                            if (maybeNext.equals(dest)){
                                StringNode newNode = new StringNode(maybeNext, 0, StringCheck.countEstimate(maybeNext, dest), i);
                                AddNode(newNode);
                                return (stringList.size() - 1);
                            }

                            // memilih apakah dimasukkan ke array
                            if (getIndex(maybeNext) == -1){
                                if (nextWord == null){nextWord = new StringNode(maybeNext, 0, StringCheck.countEstimate(maybeNext, dest), i);}
                                else {
                                    StringNode maybeReplaced = new StringNode(maybeNext, 0, StringCheck.countEstimate(maybeNext, dest), i);
                                    if ((maybeReplaced.getEstimate() < nextWord.getEstimate()) || ((maybeReplaced.getEstimate() == nextWord.getEstimate()) && (maybeReplaced.getWord().compareTo(nextWord.getWord()) < 0))){
                                        nextWord = maybeReplaced;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (nextWord != null){
                AddNode(nextWord);
            }
        }
        return -1; // loop habis = no solution
    }

    public static int AStar(String dest, String path){
        while (nodesToExpand.size() > 0){
            // cari index
            int idx = 0;
            int minhn = getNode(nodesToExpand.get(0)).hn();
            for (int i = 1; i < nodesToExpand.size(); i++){
                int HN = getNode(nodesToExpand.get(i)).hn();
                if (HN < minhn){
                    idx = i;
                    minhn = HN;
                }
            }
            int listidx = nodesToExpand.get(idx);
            String currentWord = getNode(listidx).getWord();
            // expand
            incrementVisitedNodes();
            RemoveTask(idx);
            for (int pos = 0; pos < currentWord.length(); pos++){ // posisi huruf yang ditukar
                StringBuilder next = new StringBuilder(currentWord);
                for (char c = 'a'; c <= 'z'; c++){ // huruf yang ditukar
                    if (c != currentWord.charAt(pos)){
                        next.setCharAt(pos, c);
                        String maybeNext = next.toString();
                        if (StringCheck.checkDictionary(path, maybeNext, 2)){ // cek di kamus
                            if (getIndex(maybeNext) == -1){ // add kalau belum ada
                                StringNode newNode = new StringNode(maybeNext, (getNode(listidx).getCost() + 1), StringCheck.countEstimate(maybeNext, dest), listidx);
                                AddNode(newNode);
                                AddTask(stringList.size() - 1);
                                if (maybeNext.equals(dest)){return (stringList.size() - 1);}
                            }
                        }
                    }
                }
            }
        }
        return -1; // loop habis = no solution
    }
}