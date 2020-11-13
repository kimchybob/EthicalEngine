/**
 * @name : wenjie xu
 * @student number : 1039070
 * @username : wxxu1
 */
import ethicalengine.Animal;
import ethicalengine.Person;
import ethicalengine.Scenario;
import ethicalengine.ScenarioGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Audit {
    private int scenarioNum;
    private float averageAge;//need to control first digit only
    private String name;
    private boolean savePassengers;
    private ArrayList<unit> statistic;
    private int humanAge;
    private int surviveHuman;
    private Scenario[] scenarios;
    private boolean interactive;
    private int countRun = 0;

    //constructor need scenario generator to produce random scenarios
    public Audit(){
        scenarioNum = 0;
        averageAge = 0;
        name = "Unspecified";
        statistic = new ArrayList<unit>();
        humanAge = 0;
        surviveHuman = 0;
    }

    public void setInteractive(boolean interactive){
        this.interactive = interactive;
    }

    public void setScenarios(Scenario[] scenarios){
        scenarioNum = scenarios.length;
        this.scenarios = scenarios;
    }

    public void setName(String name){
        this.name = name;
    }

    //data structure to store data keyword and statistic info
    private static class unit{
        private String factorName;
        private int number = 0;
        private int survival = 0;
        private float ratio = 0;

        public unit(String factorName,boolean survive){
            this.factorName = factorName.toLowerCase();
            number ++;
            if(survive){
                addSurvival();
            }
            update();
        }

        public String getName(){
            return factorName;
        }

        public void update(){
            ratio = (float)survival / (float)number;
        }

        public void addNumber(){
            number ++;
            update();
        }

        public void addSurvival(){
            survival ++;
            update();
        }

        public float getRatio(){
            return ratio;
        }

        @Override
        public String toString(){
            String output = factorName + " " + number;
            return output;
        }
    }

    public void checkUnique(String name,boolean survive){
        if(statistic.size() == 0){
            statistic.add(new unit(name,survive));
        }
        else{
            boolean found = false;
            for(int round = 0;round < statistic.size();round ++) {
                if (name.toLowerCase().equals(statistic.get(round).getName())) {
                    found = true;
                    if (survive) {
                        statistic.get(round).addNumber();
                        statistic.get(round).addSurvival();
                    } else {
                        statistic.get(round).addNumber();
                    }
                    break;
                }
            }
            if(!found){
                statistic.add(new unit(name,survive));
            }
        }
    }

    //record person data
    public int record(Person[] person, boolean isLegalCrossing, boolean savePassengers){
        int length = person.length;
        int totalAge = 0;
        for(int round = 0;round < length; round ++){
            checkUnique(String.valueOf(person[round].getAgeCategory()),savePassengers);
            checkUnique(String.valueOf(person[round].getGender()),savePassengers);
            checkUnique(String.valueOf(person[round].getBodyType()),savePassengers);
            if(Person.notNone(person[round].getProfession())){
                checkUnique(String.valueOf(person[round].getProfession()),savePassengers);
            }
            if(person[round].isYou()){
                checkUnique("you",savePassengers);
            }
            if(person[round].isPregnant()){
                checkUnique("pregnant",savePassengers);
            }
            checkUnique("person",savePassengers);
            if(isLegalCrossing){
                checkUnique("green",savePassengers);
            }
            else{
                checkUnique("red",savePassengers);
            }
            totalAge += person[round].getAge();
        }
        return totalAge;
    }

    //record animal data
    public void record(Animal[] animals, boolean isLegalCrossing, boolean savePassengers){
        int length = animals.length;
        int totalAge = 0;
        for(int round = 0;round < length; round ++){
            if(animals[round].isPet()){
                checkUnique("pet",savePassengers);
            }
            checkUnique(animals[round].getSpecies(),savePassengers);
            checkUnique("animal",savePassengers);
            if(isLegalCrossing){
                checkUnique("green",savePassengers);
            }
            else{
                checkUnique("red",savePassengers);
            }
        }
    }

    //run with given specific scenario[]
    public void run(){
        EthicalEngine engine = new EthicalEngine();
        for(int round = 0; round < scenarioNum; round ++){
            savePassengers = engine.isPassengerSurvive(scenarios[round],EthicalEngine.decide(scenarios[round]),interactive);
            if(savePassengers){
                //survive person, return age
                humanAge += record(scenarios[round].getHumanPassengers(),scenarios[round].isLegalCrossing(),true);
                record(scenarios[round].getHumanPedestrians(),scenarios[round].isLegalCrossing(),false);
                surviveHuman += scenarios[round].getHumanPassengers().length;
                record(scenarios[round].getPassengerAnimals(),scenarios[round].isLegalCrossing(),true);
                record(scenarios[round].getPedestrianAnimals(),scenarios[round].isLegalCrossing(),false);
            }
            else{
                record(scenarios[round].getHumanPassengers(),scenarios[round].isLegalCrossing(),false);
                humanAge += record(scenarios[round].getHumanPedestrians(),scenarios[round].isLegalCrossing(),true);
                surviveHuman += scenarios[round].getHumanPedestrians().length;
                record(scenarios[round].getPassengerAnimals(),scenarios[round].isLegalCrossing(),false);
                record(scenarios[round].getPedestrianAnimals(),scenarios[round].isLegalCrossing(),true);
            }
        }
        averageAge = (float)humanAge / (float)surviveHuman;
        sortAlphabetically(statistic);
        bubbleSort(statistic);
        countRun += scenarioNum;
    }

    //run with random scenario, input number of random scenario that want to generate
    public void run(int runs){
        scenarioNum = runs;
        EthicalEngine engine = new EthicalEngine();
        for(int round = 0; round < scenarioNum; round ++){
            ScenarioGenerator newScenario = new ScenarioGenerator();
            Scenario instance = newScenario.generate();
            savePassengers = engine.isPassengerSurvive(instance,EthicalEngine.decide(instance),interactive);
            if(savePassengers){
                //survive person, return age
                humanAge += record(instance.getHumanPassengers(),instance.isLegalCrossing(),true);
                record(instance.getHumanPedestrians(),instance.isLegalCrossing(),false);
                surviveHuman += instance.getHumanPassengers().length;
                record(instance.getPassengerAnimals(),instance.isLegalCrossing(),true);
                record(instance.getPedestrianAnimals(),instance.isLegalCrossing(),false);
            }
            else{
                record(instance.getHumanPassengers(),instance.isLegalCrossing(),false);
                humanAge += record(instance.getHumanPedestrians(),instance.isLegalCrossing(),true);
                surviveHuman += instance.getHumanPedestrians().length;
                record(instance.getPassengerAnimals(),instance.isLegalCrossing(),false);
                record(instance.getPedestrianAnimals(),instance.isLegalCrossing(),true);
            }
        }
        averageAge = (float)humanAge / (float)surviveHuman;
        sortAlphabetically(statistic);
        bubbleSort(statistic);
    }

    public void bubbleSort(ArrayList<unit> statistic){
        for(int i = 0;i < statistic.size();i ++){
            for(int j = 0;j < statistic.size() - 1 - i;j ++){
                if(statistic.get(j + 1).getRatio() > statistic.get(j).getRatio()){
                    Collections.swap(statistic, j,j+1);
                }
            }
        }
    }

    public void sortAlphabetically(ArrayList<unit> statistic){
        for(int i = 0;i < statistic.size();i ++){
            for(int j = i + 1;j < statistic.size();j ++){
                if(statistic.get(i).getName().compareTo(statistic.get(j).getName()) > 0){
                    Collections.swap(statistic, i,j);
                }
            }
        }
    }

    public void setAuditType(String name){
        this.name = name;
    }

    public String getAuditType(){
        return name;
    }

    public int getScenarioNum(){
        return scenarioNum;
    }

    @Override
    public String toString(){
        String output = new String();
        if(scenarioNum == 0){
            return "no audit available";
        }
        else{
            output += "======================================" + "\n"
                    + "# " + getAuditType() + " Audit" + "\n"
                    + "======================================" + "\n"
                    + "- % SAVED AFTER " + countRun + " RUNS" + "\n";
            for(int round = 0;round < statistic.size();round ++){
                output += statistic.get(round).factorName + ": ";
                output += (float)(Math.floor(statistic.get(round).getRatio() * 10))/10 + "\n";
            }
            output += "--" + "\n";
            output += "average age: " + (float)(Math.floor(averageAge * 10)) / 10;
        }
        return output;
    }

    public void printStatistic(){
        System.out.println(toString());
    }

    public void printToFile(String filepath){
        try {
            String filePath = filepath;
            File file = new File(filePath);
            if (file.exists()) {
                FileWriter myWriter = new FileWriter(filePath,true);
                myWriter.write(toString());
                myWriter.close();
            } else {
                file.createNewFile();
                FileWriter myWriter = new FileWriter(filePath);
                myWriter.write(toString());
                myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR: could not print results. Target directory does not exist.");
            System.exit(0);
        }
    }
}

