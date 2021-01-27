//Final Project
//Ebru Soezbir 1135390 nsoezbir

package ethicalengine;
import java.util.ArrayList;
import java.util.Arrays;

/**
* Scenario represents a scenario containing passangers, pedestrians and
* information about if crossing the lane was legal or not
*
* @author Ebru Soezbir
*/
public class Scenario {

    private ArrayList<Character> passengers = new ArrayList<Character>();
    private ArrayList<Character> pedestrians = new ArrayList<Character>();
    private boolean isLegalCrossing;
    
    /**
    * Creates a Scenario with the specified passenger, pedestrians and if the crossing is legal or not
    * @param passengers An Array of Characters representing passengers
    * @param pedestrians An Array of Characters representing pedestrians
    * @param isLegalCrossing A boolean representing if crossing the lane was legal or not
    */
    public Scenario(Character[] passengers, Character[] pedestrians, boolean isLegalCrossing) {
        //I used ArrayLists here but the tests are using Arrays
        ArrayList<Character> passengersList = new ArrayList<Character>(Arrays.asList(passengers));
        ArrayList<Character> pedestriansList = new ArrayList<Character>(Arrays.asList(pedestrians));
        this.passengers = passengersList;
        this.pedestrians = pedestriansList;
        this.isLegalCrossing = isLegalCrossing;
//        try {
//            if (checkIfSingleIsYou(passengersList, pedestriansList) == false) {
//                throw new ArithmeticException("ERROR: scenario contains more than one isYou.");
//            } else {
//                this.passengers = passengersList;
//                this.pedestrians = pedestriansList;
//                this.isLegalCrossing = isLegalCrossing;
//            }
//        } catch(ArithmeticException e) {
//            System.out.println(e.getMessage());
//            System.exit(0);
//        }
       
    }
    
//    //This method checks if there is more than one isYou among the passengers and pedestrians
//    public boolean checkIfSingleIsYou(ArrayList<Character> passengers, ArrayList<Character> pedestrians) {
//        int isYouCounter = 0;
//        boolean singleIsYou = true;
//        for (int i = 0; i < passengers.size(); i++) {
//            if (passengers.get(i).getClass() == Person.class) {
//                Person eachPerson = (Person)passengers.get(i);
//                if (eachPerson.isYou() == true) {
//                    isYouCounter++;
//                    if (isYouCounter > 1) {
//                        singleIsYou = false;
//                        break;
//                    }
//                    
//                }
//            }
//        }
//        
//        for (int i = 0; i < pedestrians.size(); i++) {
//            if (pedestrians.get(i).getClass() == Person.class) {
//                Person eachPerson = (Person)pedestrians.get(i);
//                if (eachPerson.isYou() == true) {
//                    isYouCounter++;
//                    if (isYouCounter > 1) {
//                        singleIsYou = false;
//                        break;
//                    }
//                }
//            }
//        }
//
//        return singleIsYou;
//        
//    }
    
    /**
    * Checks if there is You in the car or not
    * @return boolean representing if You is in the car
    */
    public boolean hasYouInCar() {
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getClass() == Person.class) {
                Person eachPerson = (Person)passengers.get(i);
                if (eachPerson.isYou() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
    * Checks if there is You in the lane or not
    * @return boolean representing if You is in the lane
    */
    public boolean hasYouInLane() {
        for (int i = 0; i < pedestrians.size(); i++) {
            if (pedestrians.get(i).getClass() == Person.class) {
                Person eachPerson = (Person)pedestrians.get(i);
                if (eachPerson.isYou() == true) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //This method counts the number of babies and children in the car
    public int getKidsInCarCount() {
        int counter = 0;
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getClass() == Person.class) {
                Person eachPerson = (Person)passengers.get(i);
                if (eachPerson.getAgeCategory() == Person.AgeCategory.BABY || eachPerson.getAgeCategory() == Person.AgeCategory.CHILD) {
                    counter++;
                }
            }
        }
        return counter;
    }
    
    //This method counts the number of babies and children in the lane
    public int getKidsInLaneCount() {
        int counter = 0;
        for (int i = 0; i < pedestrians.size(); i++) {
            if (pedestrians.get(i).getClass() == Person.class) {
                Person eachPerson = (Person)pedestrians.get(i);
                if (eachPerson.getAgeCategory() == Person.AgeCategory.BABY || eachPerson.getAgeCategory() == Person.AgeCategory.CHILD) {
                    counter++;
                }
            }
        }
        return counter;
    }
    
    /**
    * Gets all passenger of the Scenario
    * @return An Array of Characters representing passengers
    */
    public Character[] getPassengers() {
        //I used ArrayLists here but the tests are using Arrays
        Character[] passengersArray = passengers.toArray(new Character[passengers.size()]);
        return passengersArray;
    }
    
    /**
    * Gets all pedestrians of the Scenario
    * @return An Array of Characters representing pedestrians
    */
    public Character[] getPedestrians() {
        //I used ArrayLists here but the tests are using Arrays
        Character[] pedestriansArray = pedestrians.toArray(new Character[pedestrians.size()]);
        return pedestriansArray;
    }
    
    public ArrayList<Character> getPassengersMyFunc() {
        return passengers;
    }
    
    public ArrayList<Character> getPedestriansMyFunc() {
        return pedestrians;
    }

    /**
    * Checks if crossing the lane is legal or not
    * @return boolean representing if the crossing is legal
    */
    public boolean isLegalCrossing() {
        return isLegalCrossing;
    }
    
    /**
    * Sets if crossing the lane is legal or not
    * @param isLegalCrossing A boolean representing the crossing is legal
    */
    public void setLegalCrossing(boolean isLegalCrossing) {
        this.isLegalCrossing = isLegalCrossing;
    }
    
    /**
    * Gets the number of passengers in the Scenario
    * @return int representing the number of passengers in the Scenario
    */
    public int getPassengerCount() {
        return this.passengers.size();
    }

    /**
    * Gets the number of pedestrians in the Scenario
    * @return int representing the number of pedestrians in the Scenario
    */
    public int getPedestrianCount() {
        return this.pedestrians.size();
    }
    
    //This method counts the number of animals in the car
    public int getPassengerAnimalsCount() {
        int counter = 0;
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getClass() == Animal.class) {
                counter++;
            }
        }
        return counter;
    }
    
    //This method counts the number of animals in the lane
    public int getPedestrianAnimalsCount() {
        int counter = 0;
        for (int i = 0; i < pedestrians.size(); i++) {
            if (pedestrians.get(i).getClass() == Animal.class) {
                counter++;
            }
        }
        return counter;
    }

    /**
    * Returns a String in which all the relevant information of a scenario is listed
    * @return String representing the relevant information about the Scenario
    */
    @Override
    public String toString() {
        String outputStr = "";
        outputStr += "======================================\n";
        outputStr += "# Scenario\n";
        outputStr += "======================================\n";
        
        outputStr += "Legal Crossing: ";
        if (this.isLegalCrossing == true) {
            outputStr += "yes\n";
        } else {
            outputStr += "no\n";
        }
        
        outputStr += "Passengers (" + this.getPassengerCount() + ")\n";
        for (int i = 0; i < passengers.size(); i++) {
            outputStr += "- " + passengers.get(i).toString() + "\n";
        }
        
        outputStr += "Pedestrians (" + this.getPedestrianCount() + ")\n";
        for (int i = 0; i < pedestrians.size(); i++) {
            outputStr += "- " + pedestrians.get(i).toString() + "\n";
        }
        
        outputStr = outputStr.trim();
        
        return outputStr;
    }

}





