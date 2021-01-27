//Final Project
//Ebru Soezbir 1135390 nsoezbir

package ethicalengine;
import java.util.Random;

/**
* Character is an abstract class from which all character types inherit
*
* @author Ebru Soezbir
*/
public abstract class Character {
    
    private int age;
    private Gender gender;
    private BodyType bodyType;
    public static final int DEFAULT_AGE = 18;
    public enum Gender {
        FEMALE,
        MALE,
        UNKNOWN;
        
        //This method generates random Gender value
        public static Gender getRandomGender(Random random) {
            Gender[] arrayOfValues = Gender.values();
            return arrayOfValues[random.nextInt(arrayOfValues.length)];
        }
    }
    public enum BodyType {
        AVERAGE,
        ATHLETIC,
        OVERWEIGHT,
        UNSPECIFIED;
        
        //This method generates random BodyType value
        public static BodyType getRandomBodyType(Random random) {
            BodyType[] arrayOfValues = BodyType.values();
            return arrayOfValues[random.nextInt(arrayOfValues.length)];
        }
    }
    
    /**
    * Creates a default Character
    */
    public Character() {
        //Default age is 18
        this.age = DEFAULT_AGE;
        this.gender = Gender.UNKNOWN;
        this.bodyType = BodyType.UNSPECIFIED;
    }
    
    /**
    * Creates a Character with the specified age, gender and bodytype
    * @param age An int representing Character's age
    * @param gender A Gender representing Character's gender
    * @param bodyType A BodyType representing Character's bodytype
    */
    public Character(int age, Gender gender, BodyType bodyType) {
        setAge(age);
        this.gender = gender;
        this.bodyType = bodyType;
    }
    
    /**
    * Creates a copy of the given Character
    * @param otherCharacter A Character which is copied
    */
    public Character(Character otherCharacter) {
        age = otherCharacter.age;
        gender = otherCharacter.gender;
        bodyType = otherCharacter.bodyType;
    }
    
    /**
    * Gets Character's age
    * @return int representing the Character's age
    */
    public int getAge() {
        return this.age;
    }
    
    /**
    * Gets Character's gender
    * @return Gender representing the Character's gender
    */
    public Gender getGender() {
        return this.gender;
    }
    
    /**
    * Gets Character's bodyType
    * @return BodyType representing the Character's bodytype
    */
    public BodyType getBodyType() {
        return this.bodyType;
    }
    
    /**
    * Sets Character's age
    * @param age An int containing the Character's age
    */
    public void setAge(int age) {
        //If age is negative, set it to DEFAULT_AGE
        if (age < 0) 
            this.age = DEFAULT_AGE;
        else
            this.age = age;
    }
    
    /**
    * Sets Character's gender
    * @param gender A Gender containing the Character's gender
    */
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    /**
    * Sets Character's bodyType
    * @param bodyType A BodyType containing the Character's bodytype
    */
    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

}
