package SearchAlgorithm;

public class StringNode {
    // atribut
    private String word;
    private int cost; // g(n)
    private int estimate; // f(n)
    private int parent; // pointer list

    // konstruktor
    public StringNode(String s, int gn, int fn, int p){
        word = s;
        cost = gn;
        estimate = fn;
        parent = p;
    }

    // getter setter
    public String getWord(){return word;}
    public int getCost(){return cost;}
    public int getEstimate(){return estimate;}
    public int hn(){return (cost + estimate);} // h(n) untuk algoritma A*
    public int getParentIdx(){return parent;}
}