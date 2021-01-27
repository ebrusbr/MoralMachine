//Final Project
//Ebru Soezbir 1135390 nsoezbir

package ethicalengine;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
* ScenarioGenerator creates a variety of scenarios with randomized elements
*
* @author Ebru Soezbir
*/
public class ScenarioGenerator {

    private final int DEFAULT_MIN = 1;
    private final int DEFAULT_MAX = 5;
    private final int MAX_AGE_LIMIT = 100;
    private Random random = new Random();
    private int passengerCountMinimum;
    private int passengerCountMaximum;
    private int pedestrianCountMinimum;
    private int pedestrianCountMaximum;

    /**
    * Creates a ScenarioGenerator with the default min and max
    * number of passengers and pedestrians
    */
    public ScenarioGenerator() {
        this.passengerCountMinimum = DEFAULT_MIN;
        this.passengerCountMaximum = DEFAULT_MAX;
        this.pedestrianCountMinimum = DEFAULT_MIN;
        this.pedestrianCountMaximum = DEFAULT_MAX;
    }
    
    /**
    * Creates a ScenarioGenerator with the default min and max
    * number of passengers and pedestrians and the given seed
    * @param seed A long representing seed to apply pseudorandomness to the generator
    */
    public ScenarioGenerator(long seed) {
        random.setSeed(seed);
        this.passengerCountMinimum = DEFAULT_MIN;
        this.passengerCountMaximum = DEFAULT_MAX;
        this.pedestrianCountMinimum = DEFAULT_MIN;
        this.pedestrianCountMaximum = DEFAULT_MAX;
    }
    
    /**
    * Creates a ScenarioGenerator with the given min and max
    * number of passengers and pedestrians and the given seed
    * @param seed A long representing seed to apply pseudorandomness to the generator
    * @param passengerCountMinimum An int representing the min number of passengers in the Scenario
    * @param passengerCountMaximum An int representing the max number of passengers in the Scenario
    * @param pedestrianCountMinimum An int representing the min number of pedestrians in the Scenario
    * @param pedestrianCountMaximum An int representing the max number of pedestrians in the Scenario
    */
    public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum, int pedestrianCountMinimum, int pedestrianCountMaximum) {
        random.setSeed(seed);
        //If min > max, then min = max
        this.passengerCountMaximum = passengerCountMaximum;
        this.pedestrianCountMaximum = pedestrianCountMaximum;
        setPassengerCountMin(passengerCountMinimum);
        setPedestrianCountMin(pedestrianCountMinimum);
        
    }
    
    /**
    * Sets the min number of passengers
    * @param min An int representing the min number of passengers in the Scenario
    */
    public void setPassengerCountMin(int min) {
        if (min > this.passengerCountMaximum)
            this.passengerCountMinimum = this.passengerCountMaximum;
        else
            this.passengerCountMinimum = min;
    }
    
    /**
    * Sets the max number of passengers
    * @param max An int representing the max number of passengers in the Scenario
    */
    public void setPassengerCountMax(int max) {
        if (max < this.passengerCountMinimum)
            this.passengerCountMaximum = this.passengerCountMinimum;
        else
            this.passengerCountMaximum = max;
    }
    
    /**
    * Sets the min number of pedestrians
    * @param min An int representing the min number of pedestrians in the Scenario
    */
    public void setPedestrianCountMin(int min) {
        if (min > this.pedestrianCountMaximum)
            this.pedestrianCountMinimum = this.pedestrianCountMaximum;
        else
            this.pedestrianCountMinimum = min;
    }
    
    /**
    * Sets the max number of pedestrians
    * @param max An int representing the max number of pedestrians in the Scenario
    */
    public void setPedestrianCountMax(int max) {
        if (max < this.pedestrianCountMinimum)
            this.pedestrianCountMaximum = this.pedestrianCountMinimum;
        else
            this.pedestrianCountMaximum = max;
    }
    
    /**
    * Generates a Person with random characteristics
    * @return Person with random characteristics
    */
    public Person getRandomPerson() {
        //Generate random characteristics for Person
        //Random integer from 0 (inclusive) to 100 (exclusive)
        int randomAge = random.nextInt(MAX_AGE_LIMIT);
        Person.Profession randomProfession = Person.Profession.getRandomProfession(random);
        Character.Gender randomGender = Character.Gender.getRandomGender(random);
        Character.BodyType randomBodyType = Character.BodyType.getRandomBodyType(random);
        boolean randomPregnancyBoolean = random.nextBoolean();
        
        Person newRandomPerson = new Person(randomAge, randomProfession, randomGender, randomBodyType, randomPregnancyBoolean);
        return newRandomPerson;
    }
    
    /**
    * Generates an Animal with random characteristics
    * @return Animal with random characteristics
    */
    public Animal getRandomAnimal() {
        //Generate random characteristics for Animal
        //Random integer from 0 (inclusive) to 100 (exclusive)
        int randomAge = random.nextInt(MAX_AGE_LIMIT);
        Character.Gender randomGender = Character.Gender.getRandomGender(random);
        Character.BodyType randomBodyType = Character.BodyType.getRandomBodyType(random);
        String[] speciesCollection = {"cat", "dog", "fish", "hamster", "chicken", "sheep"};
        String randomSpecies = speciesCollection[random.nextInt(speciesCollection.length)];
        boolean randomPetBoolean = random.nextBoolean();
        
        Animal newRandomAnimal = new Animal(randomAge, randomGender, randomBodyType, randomSpecies, randomPetBoolean);
        return newRandomAnimal;
    }
    
    /**
    * Generates a Scenario with random passengers, pedestrians and isLegalCrossing
    * @return Scenario with random elements
    */
    public Scenario generate() {
        //min (inclusive), max (inclusive)
        int numberOfPassengers = random.nextInt(passengerCountMaximum - passengerCountMinimum + 1) + passengerCountMinimum;
        int numberOfPedestrians = random.nextInt(pedestrianCountMaximum - pedestrianCountMinimum + 1) + pedestrianCountMinimum;
        //Random integer from 0 (inclusive) to 100 (exclusive)
        int numberOfPersonPassengers = random.nextInt(numberOfPassengers+1);
        int numberOfPersonPedestrians = random.nextInt(numberOfPedestrians+1);
        
        ArrayList<Character> passengerCollection = new ArrayList<Character>();
        ArrayList<Character> pedestrianCollection = new ArrayList<Character>();
        
        //Generate Person passengers
        boolean isYouExistsAlready = false;
        for (int i = 0; i < numberOfPersonPassengers; i++) {
            Person randomPerson = this.getRandomPerson();
            //If isYou already exists, set further isYou = false
            if (randomPerson.isYou() == true && isYouExistsAlready == false)
                isYouExistsAlready = true;
            else if (randomPerson.isYou() == true && isYouExistsAlready == true)
                randomPerson.setAsYou(false);
            passengerCollection.add(this.getRandomPerson());
        }
        
        //Generate Animal passengers
        for (int i = numberOfPersonPassengers; i < numberOfPassengers; i++) {
            passengerCollection.add(this.getRandomAnimal());
        }
        
        //Generate Person Pedestrians
        isYouExistsAlready = false;
        for (int i = 0; i < numberOfPersonPedestrians; i++) {
            Person randomPerson = this.getRandomPerson();
            //If isYou already exists, set further isYou = false
            if (randomPerson.isYou() == true && isYouExistsAlready == false)
                isYouExistsAlready = true;
            else if (randomPerson.isYou() == true && isYouExistsAlready == true)
                randomPerson.setAsYou(false);
            pedestrianCollection.add(this.getRandomPerson());
        }
        
        //Generate Animal pedestrians
        for (int i = numberOfPersonPedestrians; i < numberOfPedestrians; i++) {
            pedestrianCollection.add(this.getRandomAnimal());
        }
    
        boolean randomIsLegalCrossingBoolean = random.nextBoolean();
    
        //I used ArrayLists here but the tests are using Arrays
        Character[] passengersArray = passengerCollection.toArray(new Character[passengerCollection.size()]);
        Character[] pedestriansArray = pedestrianCollection.toArray(new Character[pedestrianCollection.size()]);
        Scenario newRandomScenario = new Scenario(passengersArray, pedestriansArray, randomIsLegalCrossingBoolean);
        return newRandomScenario;
    }

}


