//Final Project
//Ebru Soezbir 1135390 nsoezbir

import ethicalengine.*;
import ethicalengine.Character;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.EnumSet;
import java.util.Scanner;
import java.lang.Math;

//To suppress unchecked warnings
//Converting a List into an Arraylist causes a warning and the only option
//to get rid of the warning is to use @SuppressWarnings("unchecked").
@SuppressWarnings("unchecked")

/**
* EthicalEngine manages the program execution, takes care of program parameters and user input.
* It also contains the decision-making algorithm deciding either PEDESTRIANS or PASSANGERS
* depending on whom to save
*
* @author Ebru Soezbir
*/
public class EthicalEngine {

    private static final double BABIES_CHILDREN_WEIGHT = 0.55;
    private static final double PASSENGERS_PEDESTRIANS_WEIGHT = 0.10;
    private static final double ISLEGELCROSSING_WEIGHT = 0.05;
    private static final double HUMAN_WEIGHT = 0.30;
    private static final int DEFAULT_NUMBER_OF_RANDOM_SCENARIOS = 3;
    private static final String DEFAULT_RESULT_PATH = "results.log";
    private static final String DEFAULT_USER_RESULT_PATH = "user.log";
    public static enum Decision {
        PEDESTRIANS,
        PASSENGERS
    }
    
    /**
    * Decides if passengers or pedestrians should be saved for any scenario and returns an enum value
    * Assumption: At least one participant as passenger or pedestrian
    * Following characteristics are considered:
    * * isYou
    * * isLegalCrossing
    * * number of humans
    * * number of passengers
    * * number of pedestrians
    * * number of babies and children
    * @param scenario A Scenario
    */
    public static Decision decide(Scenario scenario) {
        // isYou has the highest priority to save
        if(scenario.hasYouInCar() == true) {
            return Decision.PASSENGERS;
        } else if (scenario.hasYouInLane() == true) {
            return Decision.PEDESTRIANS;
            
            //Decision based on Multicriteria Decision Analysis if there is no isYou
        } else {
            //Extract relevant values
            double passengerScore = 0.0;
            double pedestrianScore = 0.0;
            int numberOfPassengers = scenario.getPassengerCount();
            int numberOfPedestrians = scenario.getPedestrianCount();
            int numberOfHumanPassengers = numberOfPassengers - scenario.getPassengerAnimalsCount();
            int numberOfHumanPedestrians = numberOfPedestrians - scenario.getPedestrianAnimalsCount();
            int numberOfTotalPerson = numberOfPassengers + numberOfPedestrians;
            int numberOfKidsInCar = scenario.getKidsInCarCount();
            int numberOfKidsInLane = scenario.getKidsInLaneCount();
            
            //Calculate Score of passengers and pedestrians
            passengerScore = PASSENGERS_PEDESTRIANS_WEIGHT * (numberOfPassengers/numberOfTotalPerson) +
                             BABIES_CHILDREN_WEIGHT * (numberOfKidsInCar/numberOfTotalPerson) +
                             HUMAN_WEIGHT * (numberOfHumanPassengers/numberOfTotalPerson);
            pedestrianScore = PASSENGERS_PEDESTRIANS_WEIGHT * (numberOfPedestrians/numberOfTotalPerson) +
                              BABIES_CHILDREN_WEIGHT * (numberOfKidsInLane/numberOfTotalPerson) +
                              HUMAN_WEIGHT * (numberOfHumanPedestrians/numberOfTotalPerson);
            
            //Take isLegalCrossing into account
            if (scenario.isLegalCrossing() == true) {
                pedestrianScore += ISLEGELCROSSING_WEIGHT * 0.10;
            } else {
                passengerScore += ISLEGELCROSSING_WEIGHT * 0;
            }
            
            //Compare both scores
            if (passengerScore >= pedestrianScore) {
                return Decision.PASSENGERS;
            } else {
                return Decision.PEDESTRIANS;
            }
        }
    }
    
    
    //This method returns a Person by extracting relevant characteristics from current dataRow
    public static Person extractPerson(String[] dataRow, int lineCounter) {
        //Set default characteristics for Person
        int currentAge = Character.DEFAULT_AGE;
        Character.Gender currentGender = Character.Gender.UNKNOWN;
        Character.BodyType currentBodyType = Character.BodyType.UNSPECIFIED;
        Person.Profession currentProfession = Person.Profession.UNKNOWN;
        //If all enum values are valid throwException = true, else false
        boolean throwException = (contains(Character.Gender.class, dataRow[1].toUpperCase())  &&
                                  contains(Character.BodyType.class, dataRow[3].toUpperCase()));
        
        //Check if age can be casted, if not throw NumberFormatException
        try {
            currentAge = Integer.parseInt(dataRow[2]);
        } catch(NumberFormatException e) {
            System.out.println("WARNING: invalid number format in config file in line " + lineCounter);
        }
        
        //Consider wrong profession only for adults
        if (currentAge >= 17 && currentAge < 69 && !contains(Person.Profession.class, dataRow[4].toUpperCase())) {
            throwException = false;
        } else {
            currentProfession = Person.Profession.NONE;
        }
        
        //Check if enum values exist, if not throw InvalidCharacteristicException
        try {
            if (contains(Character.Gender.class, dataRow[1].toUpperCase()) == true) {
                currentGender = Character.Gender.valueOf(dataRow[1].toUpperCase());
            }
            if (contains(Character.BodyType.class, dataRow[3].toUpperCase()) == true) {
                currentBodyType = Character.BodyType.valueOf(dataRow[3].toUpperCase());
            }
            if (contains(Person.Profession.class, dataRow[4].toUpperCase()) == true) {
                currentProfession = Person.Profession.valueOf(dataRow[4].toUpperCase());
            }
            if(throwException == false) {
                throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line " + lineCounter);
            }

        } catch(InvalidCharacteristicException e) {
            System.out.println(e.getMessage());
        }

        boolean currentIsPregnant = Boolean.valueOf(dataRow[5]);
        boolean currentIsYou = Boolean.valueOf(dataRow[6]);

        //Generate a Person
        Person currentPerson = new Person(currentAge, currentProfession, currentGender, currentBodyType, currentIsPregnant);
        currentPerson.setAsYou(currentIsYou);
        return currentPerson;
    }
    
    
    //This method returns an Animal by extracting relevant characteristics from current dataRow
    public static Animal extractAnimal(String[] dataRow, int lineCounter) {
        String currentSpecies = dataRow[7];
        boolean currentIsPet = Boolean.valueOf(dataRow[8]);
        //Set default characteristic for Animal
        int currentAge = Character.DEFAULT_AGE;
        Character.Gender currentGender = Character.Gender.UNKNOWN;

        //Check if age can be casted, if not throw NumberFormatException
        try {
            currentAge = Integer.parseInt(dataRow[2]);
        } catch(NumberFormatException e) {
            System.out.println("WARNING: invalid number format in config file in line " + lineCounter);
        }
        
        //Check if enum value exists, if not throw InvalidCharacteristicException
        try {
            if(contains(Character.Gender.class, dataRow[1].toUpperCase()) == false) {
                throw new InvalidCharacteristicException("WARNING: invalid characteristic in config file in line " + lineCounter);
            } else {
                currentGender = Character.Gender.valueOf(dataRow[1].toUpperCase());
            }
        } catch(InvalidCharacteristicException e) {
            System.out.println(e.getMessage());
        }

        //Generate an Animal
        Animal currentAnimal = new Animal(currentSpecies, currentIsPet);

        //Set age and gender of Animal
        currentAnimal.setAge(currentAge);
        currentAnimal.setGender(currentGender);
        return currentAnimal;
    }


    //This helper method checks if the given enum class contains the given String value
    public static <E extends Enum<E>> boolean contains(Class<E> enumClass, String value) {
        try {
            return EnumSet.allOf(enumClass).contains(Enum.valueOf(enumClass, value));
        } catch (Exception e) {
            return false;
        }
    }
    
    
    //This method reads the config file from the given filepath and generates a scenarioList of all scenarios in the given file
    public static ArrayList<Scenario> readConfigFile(String filepath) {
        BufferedReader csvReader = null;
        ArrayList<Scenario> scenarioList = null;
        
        try {
            //Open input stream filepath for reading purpose
            csvReader = new BufferedReader(new FileReader(filepath));
            
            //Initializations
            String currentLine = null;
            String[] currentData = null;
            boolean currentIsLegalCrossing = false;
            scenarioList = new ArrayList<Scenario>();
            ArrayList<Character> passengers = new ArrayList<Character>();
            ArrayList<Character> pedestrians = new ArrayList<Character>();
            int lineCounter = 1;

            //Skip header
            csvReader.readLine();
            //Read first IsLegalCrossing
            currentLine = csvReader.readLine();
            lineCounter++;
            //Split each line by ","
            currentData = currentLine.split(",");
            if (currentData[0].equals("scenario:green"))
                currentIsLegalCrossing = true;
            else
                currentIsLegalCrossing = false;
            
            //Read line by line
            while ((currentLine = csvReader.readLine()) != null) {
                //Update lineCounter
                lineCounter++;
                //Split each line by ","
                currentData = currentLine.split(",");
                
                //If invalid number of data fields, throw InvalidDataFormatException and skip current data field
                try {
                    if ((currentData[0].equals("person") || currentData[0].equals("animal")) && currentData.length != 10) {
                        throw new InvalidDataFormatException("WARNING: invalid data format in config file in line " + lineCounter);
                    }
                } catch(InvalidDataFormatException e) {
                    System.out.println(e.getMessage());
                    //Skip current row
                    continue;
                }
                
                //If first element is "scenario..", generate the scenario with all the data collected so far
                if (currentData[0].startsWith("scenario")) {
                    //Create scenario and add to the scenarioList
                    //I used ArrayLists here but the tests are using Arrays
                    Character[] passengersArray = passengers.toArray(new Character[passengers.size()]);
                    Character[] pedestriansArray = pedestrians.toArray(new Character[pedestrians.size()]);
                    Scenario eachScenario = new Scenario(passengersArray, pedestriansArray, currentIsLegalCrossing);
                    scenarioList.add(eachScenario);
                    
                    //Reset input arguments for next scenario generation
                    passengers = new ArrayList<Character>();
                    pedestrians = new ArrayList<Character>();
                    if (currentData[0].equals("scenario:green"))
                        currentIsLegalCrossing = true;
                    else
                        currentIsLegalCrossing = false;
                //If first element is not "scenario.."
                } else {
                    
                    //Read characteristics of Person
                    if(currentData[0].equals("person")) {
                        //Generate a Person
                        Person currentPerson = extractPerson(currentData, lineCounter);
                        
                        //Add Person to the corresponding role list
                        if(currentData[9].equals("passenger")) {
                            passengers.add(currentPerson);
                        } else {
                            pedestrians.add(currentPerson);
                        }
                    }
                    
                    //Read characteristics of Animal
                    if(currentData[0].equals("animal")) {
                        Animal currentAnimal = extractAnimal(currentData, lineCounter);
                        
                        //Add Animal to the corresponding role list
                        if(currentData[9].equals("passenger")) {
                            passengers.add(currentAnimal);
                        } else {
                            pedestrians.add(currentAnimal);
                        }
                    }
                }
            }
            
            //Add last Scenario into the scenarioList
            Character[] passengersArray = passengers.toArray(new Character[passengers.size()]);
            Character[] pedestriansArray = pedestrians.toArray(new Character[pedestrians.size()]);
            Scenario eachScenario = new Scenario(passengersArray, pedestriansArray, currentIsLegalCrossing);
            scenarioList.add(eachScenario);
            
            //Close current file
            csvReader.close();
              
        } catch(FileNotFoundException e) {
                System.out.println("ERROR: could not find config file.");
                System.exit(0);
        } catch(IOException e) {
                e.printStackTrace();
                System.exit(0);
        }
        
        return scenarioList;
    }
    
    //This method prints the help Message
    public static void printHelpMessage(String arguments) {
        System.out.println("EthicalEngine - COMP90041 - Final Project\n");
        System.out.println("Usage: java EthicalEngine " + arguments + "\n");
        System.out.println("Arguments:");
        System.out.printf("   %-19s %-40s\n", "-c or --config", "Optional: path to config file");
        System.out.printf("   %-19s %-40s\n", "-h or --help", "Print Help (this message) and exit");
        System.out.printf("   %-19s %-40s\n", "-r or --results", "Optional: path to results log file");
        System.out.printf("   %-19s %-40s\n", "-i or --interactive", "Optional: launches interactive mode");
    }
    
    //This method ask the user to decide for one group and handles with invalid user input
    public static Decision getUsersDecision(Scanner keyboard) {
        EthicalEngine.Decision eachDecision = null;
        boolean done = false;
        String line = null;
        
        //Ask the user to make a decision for each scenario
        //until the user enters the expected input
        System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
        while(done != true) {
            try {
                line = keyboard.nextLine();
                if(line.equals("passenger") || line.equals("passengers") || line.equals("1")) {
                    eachDecision = EthicalEngine.Decision.PASSENGERS;
                    done = true;
                } else if (line.equals("pedestrian") || line.equals("pedestrians") || line.equals("2")) {
                    eachDecision = EthicalEngine.Decision.PEDESTRIANS;
                    done = true;
                } else {
                    throw new InvalidInputException("ERROR: wrong input. Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
                }
            } catch(InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return eachDecision;
    }
    
    //This helper method asks the user if they want to continue
    //The program terminates if they answer no
    public static void getWantToContinueAnswer(Scanner keyboard, Audit audit, boolean userConsentInput, String resultsPath) {
        //Ask the user if they want to continue
        System.out.println("Would you like to continue? (yes/no)");
        boolean done = false;
        boolean userWantsContinue = false;
        String line;
        
        while(done != true) {
            try {
                line = keyboard.nextLine();
                if(line.equals("yes")) {
                    userWantsContinue = true;
                    done = true;
                } else if (line.equals("no")) {
                    userWantsContinue = false;
                    done = true;
                } else {
                    throw new InvalidInputException("Invalid response. Would you like to continue? (yes/no)");
                }
            } catch(InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }

        //If the user doesn't want to continue, terminate the program and
        //save results if user consented previously
        //else present the next 3 scenarios
        if (userWantsContinue == false) {
            //Save results if the user consented previously
            if (userConsentInput == true && resultsPath == null) {
                audit.printToFile(DEFAULT_USER_RESULT_PATH);
            } else if (userConsentInput == true && resultsPath != null) {
                audit.printToFile(resultsPath);
            }
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        
        //If no arguments are given run 3 random scenarios with ethical engine
        if (args.length == 0) {
            Audit audit = new Audit();
            audit.run(DEFAULT_NUMBER_OF_RANDOM_SCENARIOS);
            System.out.println(audit);
            audit.printStatistic();
            //Save results in the default result path
            audit.printToFile(DEFAULT_RESULT_PATH);
            System.exit(0);
        }
        
        //Initialization
        int i = 0;
        String configPath = null;
        String resultsPath = null;
        boolean interactiveModeIsSet = false;
        
        //Check which flags are set,
        //If its set wrong, print help message and terminate
        while (i < args.length) {

            //Check if the help flag is set
            if ((args[i].equals("-h") || args[i].equals("--help"))){
                printHelpMessage(String.join(" ", args));
                System.exit(0);
             
            //Check if the config flag is set
            } else if ((args[i].equals("-c") || args[i].equals("--config"))) {
                if ((i+1) < args.length && !args[i+1].startsWith("-")) {
                    //Config flag is set
                    configPath = args[i+1];
                    
                } else {
                    printHelpMessage(String.join(" ", args));
                    System.exit(0);
                }
            
            //Check if the results flag is set
            } else if((args[i].equals("-r") || args[i].equals("--results"))) {
                if (i+1 < args.length && !args[i+1].startsWith("-")) {
                    //Results flag is set
                    resultsPath = args[i+1];
                } else {
                    printHelpMessage(String.join(" ", args));
                    System.exit(0);
                }
                
            //Check if the interactive flag is set
            } else if((args[i].equals("-i") || args[i].equals("--interactive"))) {
                //Interactive flag is set
                interactiveModeIsSet = true;
            }
            
            i++;
        }
        
        //If the config flag is set only
        if (interactiveModeIsSet == false && configPath != null) {
            ArrayList<Scenario> scenarioList = readConfigFile(configPath);
            Audit audit = new Audit(scenarioList);
            audit.run();
            audit.printStatistic();
            
            //Save results in the default result path or in the given result path
            if (resultsPath == null)
                audit.printToFile(DEFAULT_RESULT_PATH);
            else
                audit.printToFile(resultsPath);
            
            System.exit(0);
            
            
        //If the interactive flag is set
        } else if(interactiveModeIsSet == true) {
            ArrayList<Scenario> scenarioList = null;
            
            //Read Config file here to show the warnings before welcome message
            if (configPath != null) {
                scenarioList = readConfigFile(configPath);
            }
        
            String line = null;

            //Display welcome message
            try {
                //Read "welcome.ascii"
                BufferedReader bufferedReader = new BufferedReader(new FileReader("welcome.ascii"));
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }

            //Initialization
            Scanner keyboard = new Scanner(System.in);
            boolean userConsentInput = false;
            boolean done = false;
            int startIndex = 0;

            //Collect users consent before saving any results
            //Ask until the user inputs yes or no
            System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
            while(done != true) {
                try {
                    line = keyboard.nextLine();
                    if(!line.equals("yes") && !line.equals("no")) {
                        throw new InvalidInputException("Invalid response. Do you consent to have your decisions saved to a file? (yes/no)");
                    }
                    done = true;
                    if (line.equals("yes"))
                        userConsentInput = true;

                } catch(InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
            
            //If interactive flag and config flag is set
            if (configPath != null) {
                //Initialization
                Audit audit = new Audit();
                audit.setAuditType("User");
                ArrayList<Decision> usersDecision = new ArrayList();
                boolean userWantsContinue = false;
                
                for (int j = 0; j < scenarioList.size(); j++) {
                    System.out.println(scenarioList.get(j));
                    usersDecision.add(getUsersDecision(keyboard));

                    //Present results after every 3 scenarios
                    int endIndex = Math.min(j+1, scenarioList.size());
                    if (j != 0 && (j+1) % 3 == 0 || endIndex == scenarioList.size()) {
                        ArrayList<Scenario> eachScenarioSublist = new ArrayList<Scenario>(scenarioList.subList(startIndex, endIndex));
                        startIndex = startIndex + 3;
                        audit.run(eachScenarioSublist, usersDecision);
                        audit.printStatistic();
                        
                        //Ask user if they want to continue
                        if (j < scenarioList.size()-1) {
                            getWantToContinueAnswer(keyboard, audit, userConsentInput, resultsPath);
                        }
                        
                        //Clear userDecision
                        usersDecision = new ArrayList();
                    }
                }
                
                //End of scenarioList, save the results if the user consented previously
                //terminate the program after the user presses enter
                if (userConsentInput == true && resultsPath == null) {
                    audit.printToFile(DEFAULT_USER_RESULT_PATH);
                } else if (userConsentInput == true && resultsPath != null) {
                    audit.printToFile(resultsPath);
                }
                    
                System.out.println("That's all. Press Enter to quit.");
                line = keyboard.nextLine();
                if (line != null) {
                    System.exit(0);
                }
            }
            
            //If interactive flag is set but not config flag
            if (configPath == null) {
                //Initialization
                Audit audit = new Audit();
                audit.setAuditType("User");
                ArrayList<Decision> usersDecision = new ArrayList();
                ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
                boolean userWantsContinue = false;
                
                while (true) {
                    //Generate 3 random scenarios with default numbers of passengers and pedestrians
                    scenarioList = new ArrayList();
                    usersDecision = new ArrayList();
                    for (int j = 0; j < DEFAULT_NUMBER_OF_RANDOM_SCENARIOS; j++) {
                        Scenario eachScenario = scenarioGenerator.generate();
                        scenarioList.add(eachScenario);
                        System.out.println(eachScenario);
                        usersDecision.add(getUsersDecision(keyboard));
                    }

                    //Present results after every 3 scenarios
                    ArrayList<Scenario> eachScenarioSublist = new ArrayList<Scenario>(scenarioList.subList(0, DEFAULT_NUMBER_OF_RANDOM_SCENARIOS));
                    audit.run(eachScenarioSublist, usersDecision);
                    audit.printStatistic();
                    //Ask user if they want to continue
                    getWantToContinueAnswer(keyboard, audit, userConsentInput, resultsPath);
                }
            }
        }
    
    
    }
   
   
}
