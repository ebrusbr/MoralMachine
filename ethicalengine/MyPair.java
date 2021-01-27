//Final Project
//Ebru Soezbir 1135390 nsoezbir

package ethicalengine;

/**
* MyPair class helps to store a pair of (String, double) in one object
*
* @author Ebru Soezbir
*/
public class MyPair implements Comparable<MyPair>{

    private String name;
    private double value;
    
    public MyPair(String name, double value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getValue() {
        return this.value;
    }
    
    @Override
    //Sort based on values primary, name secondary (not required)
    public int compareTo(MyPair otherPair) {
        if (this.value < otherPair.getValue())
            return -1;
        else if (this.value > otherPair.getValue())
            return 1;
        else
            return otherPair.name.compareTo(name);
    }
   
}
