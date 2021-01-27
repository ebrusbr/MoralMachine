//Final Project
//Ebru Soezbir 1135390 nsoezbir

import ethicalengine.*;
import ethicalengine.MyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
* Audit simulates a variety of scenarios and calculating the statistics of their outcome
*
* @author Ebru Soezbir
*/
public class Audit {
    
    private final String DEFAULT_AUDITTYPE = "Unspecified";
    private final double PRECISION = 10.0;
    private String auditType;
    private int runs = 0;
    private int humanSurvivorCounter = 0;
    private int totalNumberOfParticipants = 0;
    private int totalAge = 0;
    private int illegalCrossingSurvivors = 0;
    private int totalNumberOflegalCrossing = 0;
    private int legalCrossingSurvivors = 0;
    private Object[][] scenarioReport = null;
    private ArrayList<Scenario> scenarioList = null;
    private HashMap<String, Integer> survivorsReport = new HashMap<String, Integer>();
    private HashMap<String, Integer> deathsReport = new HashMap<String, Integer>();
    
    
    /**
    * Creates a default/empty Audit
    */
    public Audit() {
        this.auditType = DEFAULT_AUDITTYPE;
    }
    
    
    /**
    * Creates an Audit given a list of scenarios
    * @param scenarioList An ArrayList of Scenario containing scenarios
    */
    public Audit(ArrayList<Scenario> scenarioList) {
        this.scenarioList = scenarioList;
        this.auditType = DEFAULT_AUDITTYPE;
        this.runs = scenarioList.size();
    }
    
    
    /**
    * Sets the audit type of the Audit
    * @param auditTypeName A String which is the name of the audit type
    */
    public void setAuditType(String auditTypeName) {
        this.auditType = auditTypeName;
    }
    
    
    /**
    * Gets the audit type of the Audit
    * @return String which is the name of the audit type
    */
    public String getAuditType() {
        return this.auditType;
    }
    
    
    /**
    * Runs the Audit with the specified number of random runs/scenarios
    * generates all the results of those random scenarios using ethicalengine
    * and updates the statistics in survivors and deathsReport
    * @param newRuns An int represents the number of random runs/scenarios
    */
    public void run(int newRuns) {
    
        this.runs += newRuns;
        
        if (runs <= 0) {
            this.scenarioReport = null;
            return;
        }
        
        this.scenarioList = new ArrayList<Scenario>();
        ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
        ArrayList<EthicalEngine.Decision> madeDecisions = new ArrayList<EthicalEngine.Decision>();
        
        for (int i = 0; i < newRuns; i++) {
            //Generate a new scenario each run
            Scenario eachScenario = scenarioGenerator.generate();
            scenarioList.add(eachScenario);
            madeDecisions.add(EthicalEngine.decide(eachScenario));
        }
        
        this.scenarioReport = getScenarioReport(this.scenarioList, madeDecisions);
        updateReports();
    }
    
    
    /**
    * Runs the Audit using the existing scenarioList
    * generates all the results of those random scenarios using ethicalengine
    * and updates the statistics in survivors and deathsReport
    */
    public void run() {
        if (scenarioList.isEmpty()) {
            this.scenarioReport = null;
            return;
        }
        
        ArrayList<EthicalEngine.Decision> madeDecisions = new ArrayList<EthicalEngine.Decision>();
        
        for (int i = 0; i < scenarioList.size(); i++) {
            Scenario eachScenario = scenarioList.get(i);
            madeDecisions.add(EthicalEngine.decide(eachScenario));
        }
        
        this.scenarioReport = getScenarioReport(this.scenarioList, madeDecisions);
        updateReports();
    }
    
    
    //This method generates a scenarioReport based on the scenarioList and users decision
    public void run(ArrayList<Scenario> scenarioList, ArrayList<EthicalEngine.Decision> usersDecision) {
        this.scenarioList = scenarioList;
        int newRuns = scenarioList.size();
        this.runs += newRuns;
    
        if (scenarioList.isEmpty()) {
            return;
        }
        
        this.scenarioReport = getScenarioReport(scenarioList, usersDecision);
        updateReports();
    }
    
    
    //This is a helper method to store scenarios, survivors and casualties in a matrix systematically
    public Object[][] getScenarioReport(ArrayList<Scenario> scenarioList, ArrayList<EthicalEngine.Decision> decisionList) {
        int newRuns = scenarioList.size();
        Object[][] scenarioReportTemp = new Object[newRuns][4];

        for (int i = 0; i < newRuns; i++) {
            Scenario eachScenario = scenarioList.get(i);
            EthicalEngine.Decision madeDecision = decisionList.get(i);

            //Store the results in scenarioReport[newRuns][4]
            //In the columns: scenario | madeDecision | survivors | deaths
            scenarioReportTemp[i][0] = eachScenario;
            scenarioReportTemp[i][1] = madeDecision;
            if (madeDecision == EthicalEngine.Decision.PASSENGERS) {
                scenarioReportTemp[i][2] = eachScenario.getPassengersMyFunc();
                scenarioReportTemp[i][3] = eachScenario.getPedestriansMyFunc();
            } else {
                scenarioReportTemp[i][2] = eachScenario.getPedestriansMyFunc();
                scenarioReportTemp[i][3] = eachScenario.getPassengersMyFunc();
            }
        }

        return scenarioReportTemp;
    }
    
    
    //This method extracts relevant characterics of an Animal and updates the report
    public void incrementAnimalCharacteristics(HashMap<String, Integer> report, Animal eachAnimal) {
        //Extract relevant characteristics of each Animal
        String eachSpecies = eachAnimal.getSpecies();
        boolean eachIsPet = eachAnimal.isPet();
        
        //Check Animal
        if (report.containsKey("animal") == true)
            report.put("animal", report.get("animal") + 1);
        else
            report.put("animal", 1);

        //Check isPet
        if (eachIsPet == true) {
            if (report.containsKey("pet") == true)
                report.put("pet", report.get("pet") + 1);
            else
                report.put("pet", 1);
        }

        //Check Species
        if (report.containsKey(eachSpecies) == true)
            report.put(eachSpecies, report.get(eachSpecies) + 1);
        else
            report.put(eachSpecies, 1);

    }
    
    
    //This method extracts relevant characterics of a Person and updates the report
    public void incrementPersonCharacteristics(HashMap<String, Integer> report, Person eachPerson) {
        
        //Extract relevant characteristics of each Person
        String eachGender = eachPerson.getGender().toString().toLowerCase();
        String eachBodyType = eachPerson.getBodyType().toString().toLowerCase();
        String eachProfession = eachPerson.getProfession().toString().toLowerCase();
        String eachAgeCategory = eachPerson.getAgeCategory().toString().toLowerCase();
        boolean eachIsYou = eachPerson.isYou();
        boolean eachIsPregnant = eachPerson.isPregnant();
        
        //Check Person
        if (report.containsKey("person") == true)
            report.put("person", report.get("person") + 1);
        else
            report.put("person", 1);

        //Check You
        if (eachIsYou == true) {
            if (report.containsKey("you") == true)
                report.put("you", report.get("you") + 1);
            else
                report.put("you", 1);
        }

        //Check Pregnant
        if (eachIsPregnant == true) {
            if (report.containsKey("pregnant") == true)
                report.put("pregnant", report.get("pregnant") + 1);
            else
                report.put("pregnant", 1);
        }

        //Check Gender
        //Dont include "unknown"
        if (report.containsKey(eachGender) == true && !eachGender.equals("unknown"))
            report.put(eachGender, report.get(eachGender) + 1);
        else
            report.put(eachGender, 1);

        //Check Bodytype
        if (report.containsKey(eachBodyType) == true)
            report.put(eachBodyType, report.get(eachBodyType) + 1);
        else
            report.put(eachBodyType, 1);

        //Check Profession
        if (report.containsKey(eachProfession) == true)
            report.put(eachProfession, report.get(eachProfession) + 1);
        else
            report.put(eachProfession, 1);

        //Check Agecategory
        if (report.containsKey(eachAgeCategory) == true)
            report.put(eachAgeCategory, report.get(eachAgeCategory) + 1);
        else
            report.put(eachAgeCategory, 1);
        
    }
 
    //This helper method transfers survivors- and deathsReport to a List of MyPairs
    public List<MyPair> hashMapToListOfPairs() {
        List<MyPair> pairList = new ArrayList<MyPair>();
        double value = 0.0;
        double deathValue = 0.0;
        
        //Loop over the survivorsReport and calculate the survivorratio to each characteristics
        for (Map.Entry mapElement : survivorsReport.entrySet()) {
            String key = (String)mapElement.getKey();

            //If deathsReport doesnt contain current key, set to zero
            if (deathsReport.containsKey(key) == true)
                deathValue = deathsReport.get(key);
            else
                deathValue = 0.0;
            
            //Exclude none from the statistics
            if (!key.equals("none")) {
                //Calculate the survivorratio = survivors of one characteristics/ (survivors and casualties of the same characteristics)
                value = (double)survivorsReport.get(key)/(deathValue + survivorsReport.get(key));
                pairList.add(new MyPair(key, value));
            }
        }
        
        //Remaining characteristics with zero survivorratio
        for (Map.Entry mapElement : deathsReport.entrySet()) {
            String key = (String)mapElement.getKey();
            if (survivorsReport.containsKey(key) == false && !key.equals("none"))
                pairList.add(new MyPair(key, 0.0));
        }
        
        return pairList;
    }
    
    //This method updates survivors and deathsReport
    //If one already exists, both reports are not overwritten to avoid multiple counting
    public void updateReports() {
        int newRuns = scenarioReport.length;
        //Loop over all scenarios
        for(int i = 0; i < newRuns; i++) {
            if (scenarioReport[i][0].getClass() == Scenario.class && scenarioReport[i][2].getClass() == ArrayList.class) {
                Scenario eachScenario = (Scenario)scenarioReport[i][0];
                ArrayList survivors = (ArrayList)scenarioReport[i][2];
                ArrayList casualties = (ArrayList)scenarioReport[i][3];
        
                //Count all participants (passengers + pedestrians)
                this.totalNumberOfParticipants += (eachScenario.getPassengerCount() + eachScenario.getPedestrianCount());
                
                //Check isLegalCrossing
                if (eachScenario.isLegalCrossing() == true) {
                    this.legalCrossingSurvivors += survivors.size();
                    this.totalNumberOflegalCrossing += survivors.size() + casualties.size();
                } else {
                    this.illegalCrossingSurvivors += survivors.size();
                }
                
                //Loop over all survivors
                for(int p = 0; p < survivors.size(); p++) {
                
                    //Check Person Characteristics
                    if(survivors.get(p).getClass() == Person.class) {
                        this.humanSurvivorCounter += 1;
                        Person eachPerson = (Person)survivors.get(p);
                        this.totalAge += eachPerson.getAge();
                        incrementPersonCharacteristics(this.survivorsReport, eachPerson);
                    }
                    
                    //Check Animal Characteristics
                    if(survivors.get(p).getClass() == Animal.class) {
                        Animal eachAnimal = (Animal)survivors.get(p);
                        incrementAnimalCharacteristics(this.survivorsReport, eachAnimal);
                        
                    }
                }
                
                //Loop over all casualties
                for(int p = 0; p < casualties.size(); p++) {
                
                    //Check Person Characteristics
                    if(casualties.get(p).getClass() == Person.class) {
                        Person eachPerson = (Person)casualties.get(p);
                        incrementPersonCharacteristics(this.deathsReport, eachPerson);
                    }
                    
                    //Check Animal Characteristics
                    if(casualties.get(p).getClass() == Animal.class) {
                        Animal eachAnimal = (Animal)casualties.get(p);
                        incrementAnimalCharacteristics(this.deathsReport, eachAnimal);
                        
                    }
                }
            }
        }

        //Update reports
        this.survivorsReport = survivorsReport;
        this.deathsReport = deathsReport;
    }
    
    
    /**
    * Returns a String in which all the relevant information is listed from the survivors and deathsReport
    * @return String representing the statistics
    */
    @Override
    public String toString() {
        //Check if scenarioReport exists
        if (scenarioReport == null) {
            String outputStr = "no audit available";
            return outputStr;
        }
     
        String outputStr = "";
        
        //Display header
        outputStr += "======================================\n";
        outputStr += "# " + auditType + " Audit\n";
        outputStr += "======================================\n";
        outputStr += "- % SAVED AFTER " + Integer.toString(runs) + " RUNS\n";

        //Transfer survivor- and deathsReports into a List of Pairs
        List<MyPair> pairList = hashMapToListOfPairs();
        
        //Add green and red ratios
        double greenRatio = 0.0;
        double redRatio = 0.0;
        if (totalNumberOflegalCrossing != 0) {
            greenRatio = (double)legalCrossingSurvivors/totalNumberOflegalCrossing;
            pairList.add(new MyPair("green", greenRatio));
        }
        
        if ((totalNumberOfParticipants - totalNumberOflegalCrossing) != 0) {
            redRatio = (double)illegalCrossingSurvivors/(totalNumberOfParticipants - totalNumberOflegalCrossing);
            pairList.add(new MyPair("red", redRatio));
        }
        
        //Sort List in descending order based on the values
        Collections.sort(pairList, Collections.reverseOrder());
        
        //Convert the List into the given output format
        //Clip ratio value one digit after comma
        for (MyPair eachPair : pairList) {
            outputStr += eachPair.getName() + ": " + Math.floor(eachPair.getValue() * PRECISION) / PRECISION + "\n";
            String name = eachPair.getName();
        }
        
        outputStr += "--\n";
        outputStr += "average age: " + Math.floor(((double)totalAge/humanSurvivorCounter) * PRECISION) / PRECISION;
        
        return outputStr;
        
    }
    
    
    /**
    * Prints the statistics in the given output format
    */
    public void printStatistic() {
        //Check if scenarioReport exists
        if (scenarioReport == null) {
            System.out.println("no audit available");
        } else {
            System.out.println(this);
        }
    }
    
    
    /**
    * Stores the statistics into the given filepath
    * @param filepath A String representing the filepath to targetfile
    */
    public void printToFile(String filepath) {
        
        File file = new File(filepath);
        String fileName = filepath;
        PrintWriter printWriter = null;
    
        //If the given directory does not exist and terminate the program
        try {
            //If file exits, append new results
            //If not, create a new file
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            printWriter.println(this);

            //Close current file
            printWriter.close();
            
        } catch(IOException e) {
            System.out.println("ERROR: could not print results. Target directory does not exist.");
            System.exit(0);
        }

    }
    
}
