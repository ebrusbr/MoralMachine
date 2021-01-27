//Final Project
//Ebru Soezbir 1135390 nsoezbir

package ethicalengine;

/**
* Animal represents an animal and inherits from the abstract class Character
*
* @author Ebru Soezbir
*/
public class Animal extends Character {

    private final String DEFAULT_SPECIES = "ant";
    private String species;
    private boolean isPet;

    /**
    * Creates a default Animal
    */
    public Animal() {
        super();
        this.species = DEFAULT_SPECIES;
    }
    
    /**
    * Creates an Animal with the specified species
    * @param species A String representing Animal's species
    */
    public Animal(String species) {
        super();
        this.species = species;
    }

    //Constructor
    public Animal(String species, boolean isPet) {
        super();
        this.species = species;
        this.isPet = isPet;
    }
    
    //Constructor
    public Animal(int age, Gender gender, BodyType bodyType, String species, boolean isPet) {
        super(age, gender, bodyType);
        this.species = species;
        this.isPet = isPet;
    }
    
    /**
    * Creates a copy of the given Animal
    * @param otherAnimal An Animal which is copied
    */
    public Animal(Animal otherAnimal) {
        super(otherAnimal);
        species = otherAnimal.getSpecies();
    }
    
    /**
    * Gets Animal's species
    * @return String representing the Animal's species
    */
    public String getSpecies() {
        return this.species;
    }
    
    /**
    * Sets Animal's species
    * @param species A String containing the Animal's species
    */
    public void setSpecies(String species) {
        this.species = species;
    }
    
    /**
    * Gets if the Animal is pet or not
    * @return boolean representing if Animal is pet or not
    */
    public boolean isPet() {
        return this.isPet;
    }
    
    /**
    * Sets if the Animal is pet or not
    * @param isPet A boolean containing if Animal is pet or not
    */
    public void setPet(boolean isPet) {
        this.isPet = isPet;
    }
    
    /**
    * Returns a String with relevant information about the Animal
    * @return String representing the relevant information about the Animal
    */
    @Override
    public String toString() {
        String outputStr = "";
        outputStr += species;
        
        if (this.isPet() == true) {
            outputStr += " is pet";
        }
        
        return outputStr;
    }


}


