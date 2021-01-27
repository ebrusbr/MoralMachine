//Final Project
//Ebru Soezbir 1135390 nsoezbir

package ethicalengine;
import java.util.Random;

/**
* Person represents a person and inherits from the abstract class Character
*
* @author Ebru Soezbir
*/
public class Person extends Character {

    private boolean isPregnant;
    private boolean isYou;
    private Profession profession;
    private AgeCategory ageCategory;
    public enum Profession {
        DOCTOR,
        CEO,
        CRIMINAL,
        HOMELESS,
        UNEMPLOYED,
        TEACHER,
        STUDENT,
        UNKNOWN,
        NONE,
        PROFESSOR;
        
        public static Profession getRandomProfession(Random random) {
            Profession[] arrayOfValues = Profession.values();
            return arrayOfValues[random.nextInt(arrayOfValues.length)];
        }
        
    }
    public enum AgeCategory {
        BABY,
        CHILD,
        ADULT,
        SENIOR;
        
        public static AgeCategory getRandomAgeCategory(Random random) {
            AgeCategory[] arrayOfValues = AgeCategory.values();
            return arrayOfValues[random.nextInt(arrayOfValues.length)];
        }
    }
    
    /**
    * Creates a default Person
    */
    public Person() {
        super();
        this.profession = Profession.UNKNOWN;
        this.ageCategory = this.getAgeCategory(this.getAge());
        this.isPregnant = false;
        this.isYou = false;
    }
    
    /**
    * Creates a Person with the specified age, gender and bodytype
    * @param age An int representing Person's age
    * @param gender A Gender representing Person's gender
    * @param bodyType A BodyType representing Person's bodytype
    */
    public Person(int age, Gender gender, BodyType bodyType) {
        super(age, gender, bodyType);
        this.isYou = false;
        this.ageCategory = this.getAgeCategory(age);
        this.isPregnant = false;
        if (this.ageCategory == AgeCategory.ADULT) {
            this.profession = profession.UNKNOWN;
        } else {
            this.profession = Profession.NONE;
        }
    }
    
    /**
    * Creates a Person with the specified age, profession, gender, bodytype and isPregnant
    * @param age An int representing Person's age
    * @param profession A Profession representing Person's profession
    * @param gender A Gender representing Person's gender
    * @param bodyType A BodyType representing Person's bodytype
    * @param isPregnant A boolean representing if the Person is pregnant
    */
    public Person(int age, Profession profession, Gender gender, BodyType bodyType, boolean isPregnant) {
        super(age, gender, bodyType);
        this.isYou = false;
        this.ageCategory = this.getAgeCategory(age);
        this.setPregnant(isPregnant);
        if (this.ageCategory == AgeCategory.ADULT) {
            this.profession = profession;
        } else {
            this.profession = Profession.NONE;
        }
    }
    
    /**
    * Creates a copy of the given Person
    * @param otherPerson A Person which is copied
    */
    public Person(Person otherPerson) {
        super(otherPerson);
        profession = otherPerson.profession;
        ageCategory = otherPerson.ageCategory;
        isPregnant = otherPerson.isPregnant();
        isYou = otherPerson.isYou();
    }
    
    /**
    * Gets if a Person is pregnant or not
    * @return boolean if a Person is pregnant or not
    */
    public boolean isPregnant() {
        return this.isPregnant;
    }
    
    /**
    * Sets Person's pregnancy
    * @param pregnant A boolean representing if a Person is pregnant or not
    */
    public void setPregnant(boolean pregnant) {
        //Only female adults can be pregnant
        if (this.getGender() == Gender.FEMALE && pregnant == true && this.getAgeCategory() == AgeCategory.ADULT) {
            this.isPregnant = true;
        } else {
            this.isPregnant = false;
        }
    }
    
    /**
    * Gets if a Person is You or not
    * @return boolean if a Person is You or not
    */
    public boolean isYou() {
        return this.isYou;
    }
    
    /**
    * Sets if a Person is You or not
    * @param isYou A boolean representing if a Person is You or not
    */
    public void setAsYou(boolean isYou) {
        this.isYou = isYou;
    }
    
    /**
    * Gets Person's age category based on the given age
    * @param age An int containing Person's age
    * @return AgeCategory representing the Person's age category
    */
    public AgeCategory getAgeCategory(int age) {
        if (age >= 0 && age < 5) {
            return AgeCategory.BABY;
        } else if (age >= 5 && age < 17) {
            return AgeCategory.CHILD;
        } else if (age >= 17 && age < 69) {
            return AgeCategory.ADULT;
        } else {
            return AgeCategory.SENIOR;
        }
    }
    
    /**
    * Gets Person's age category
    * @return AgeCategory representing the Person's age category
    */
    public AgeCategory getAgeCategory() {
        return this.ageCategory;
    }
    
    /**
    * Gets Person's profession
    * @return Profession representing the Person's profession
    */
    public Profession getProfession() {
        return this.profession;
    }
    
    /**
    * Returns a String with relevant information about the Person
    * @return String representing the relevant information about the Person
    */
    @Override
    public String toString() {
        String outputStr = "";
        if (this.isYou() == true) {
            outputStr += "you ";
        }
        
        outputStr += this.getBodyType().toString().toLowerCase() + " ";
        outputStr += this.ageCategory.toString().toLowerCase() + " ";
        
        if (this.ageCategory == AgeCategory.ADULT) {
            outputStr += this.profession.toString().toLowerCase() + " ";
        }
        
        outputStr += this.getGender().toString().toLowerCase() + " ";
        if (this.isPregnant() == true) {
            outputStr += "pregnant";
        } else {
            outputStr = outputStr.trim();
        }
        
        return outputStr;
    }

}
