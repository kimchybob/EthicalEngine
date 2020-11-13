/**
 * @name : wenjie xu
 * @student number : 1039070
 * @username : wxxu1
 */
import ethicalengine.*;
import ethicalengine.Character;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
EthicalEngine.java contains the main function and coordinates the program flow
 */
public class EthicalEngine {
    private boolean consent = true;
    private String[] input;
    private String resultPath;
    private static Scanner scanner = new Scanner(System.in);
    public enum Decision{
        PEDESTRIANS, PASSENGERS;
    }

    class InvalidDataFormatException extends Exception{
        public InvalidDataFormatException(){super();}
    }
    class InvalidCharacteristicException extends Exception{
        public InvalidCharacteristicException(){super();}
    }
    class InvalidInputException extends Exception{
        public InvalidInputException(){ super(); }
    }

    public boolean isPassengerSurvive(Scenario scenario,Decision decision,boolean interactive){
        //user decide
        if(interactive){
            System.out.println(scenario);
            System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
            String answer = scanner.nextLine();
            if(answer.equals("passenger") || answer.equals("passengers") || answer.equals("1")){
                return true;
            }
            else{
                return false;
            }
        }
        //algorithm decide
        else{
            if(decision == Decision.PASSENGERS){
                return true;
            }
            else {
                return false;
            }
        }

    }

    //decision-making algorithm, who to save,not finished yet
    public static Decision decide(Scenario scenario){
        if(scenario.getHumanPassengers().length > scenario.getHumanPedestrians().length){
            return Decision.PASSENGERS;
        }
        else{
            return Decision.PEDESTRIANS;
        }
    }

    //not finished
    public boolean checkValid(int line){
        boolean valid = true;
        try {
            if(input.length != 10) {
                throw new InvalidDataFormatException();
            }
            if(!input[2].isEmpty()){
                //age only
                Integer.parseInt(input[2]);
            }
            //BodyType only,better transfer the value to the Charactor.class but not set value public
            if((!Character.BodyType.contains(input[3].toUpperCase()) && !input[3].isEmpty()) ||
                    (!Character.Gender.contains(input[1].toUpperCase()) && !input[1].isEmpty())){
                throw new InvalidCharacteristicException();
            }
        } catch (InvalidDataFormatException e) {
            valid = false;
            System.out.printf("WARNING: invalid data format in config file in line %d\n", line);
        }catch (NumberFormatException e){
            valid = false;
            System.out.printf("WARNING: invalid number format in config file in line %d\n", line);
            input[2] = "1";
        }catch (InvalidCharacteristicException e){
            valid = false;
            System.out.printf("WARNING: invalid characteristic in config file in line %d\n", line);
        }
        return valid;
    }

    public Character[] ArrayListToCharactor(ArrayList<Character> example){
        Character[] output = new Character[example.size()];
        for(int round = 0;round < example.size();round ++){
            output[round] = example.get(round);
        }
        return output;
    }

    public Scenario[] ArrayListToScenario(ArrayList<Scenario> example){
        Scenario[] output = new Scenario[example.size()];
        for(int round = 0;round < example.size();round ++){
            output[round] = example.get(round);
        }
        return output;
    }

    public Scenario[] readData(BufferedReader reader) throws IOException {
        String command;
        ArrayList<Character> passengers = new ArrayList<Character>();
        ArrayList<Character> pedestrians = new ArrayList<Character>();
        ArrayList<Scenario> producedScenario = new ArrayList<Scenario>();
        boolean isLegalCrossing = true;
        int line = 0;
        while( (command = reader.readLine()) != null){
            line ++;
            //skip first header line
            if(line > 1){
                input = null;
                input = (command + " ").split(",");
                //if invalid then skip this line
                if(!checkValid(line)){
                    continue;
                }
                else{
                    //represent end of last recording process
                    if(input[0].equals("scenario:green")){
                        //filter first time detected
                        if(passengers.size()>0 || pedestrians.size()>0){
                            Character[] passenger = ArrayListToCharactor(passengers);
                            Character[] pedestrian = ArrayListToCharactor(pedestrians);
                            producedScenario.add(new Scenario(passenger,pedestrian,isLegalCrossing));
                            //clear arraylist to ensure whole arraylist correspond to single scenario
                            passengers.clear();
                            pedestrians.clear();
                        }
                        isLegalCrossing = true;
                    }
                    else if(input[0].equals("scenario:red")){
                        //filter first time detected
                        if(passengers.size()>0 || pedestrians.size()>0){
                            Character[] passenger = ArrayListToCharactor(passengers);
                            Character[] pedestrian = ArrayListToCharactor(pedestrians);
                            producedScenario.add(new Scenario(passenger,pedestrian,isLegalCrossing));
                            //clear arraylist to ensure whole arraylist correspond to single scenario
                            passengers.clear();
                            pedestrians.clear();
                        }
                        isLegalCrossing = false;
                    }
                    else if(input[0].equals("person")){
                        if(input[9].equals("passenger ")){
                            passengers.add(Person.producePerson(input));
                        }
                        else {
                            pedestrians.add(Person.producePerson(input));
                        }
                    }
                    else if(input[0].equals("animal")){
                        if(input[9].equals("passenger ")){
                            passengers.add(Animal.produceAnimal(input));
                        }
                        else {
                            pedestrians.add(Animal.produceAnimal(input));
                        }
                    }
                }
            }
        }
        //handle the last scenario
        if(passengers.size()>0 || pedestrians.size()>0){
            Character[] passenger = ArrayListToCharactor(passengers);
            Character[] pedestrian = ArrayListToCharactor(pedestrians);
            producedScenario.add(new Scenario(passenger,pedestrian,isLegalCrossing));
        }
        Scenario[] scenarios = ArrayListToScenario(producedScenario);
//        for(int i = 0 ;i < scenarios.length; i ++){
//            System.out.println(scenarios[i]);
//        }
        return scenarios;
    }

    public void config(String filepath){
        try{
            File file = new File(filepath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Audit config = new Audit();
            config.setInteractive(false);
            config.setScenarios(readData(reader));
            config.setName("config");
            config.run();
            System.out.println(config);
            reader.close();
            config.printToFile(resultPath);
            System.exit(0);
        }catch (FileNotFoundException e){
            System.out.println("ERROR: could not find config file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void help(){
        String output = "EthicalEngine - COMP90041 - Final Project\n" + "\n" +
                " Usage: java EthicalEngine [arguments]\n" + "\n" +
                "Arguments:\n" +
                "   -c or --config Optional: path to config file\n" +
                "   -h or --help Print Help (this message) and exit\n" +
                "   -r or --results Optional: path to results log file\n" +
                "   -i or --interactive Optional: launches interactive mode";
        System.out.println(output);
    }

    public void flagsOption(String[] flags){
        //config with a path
        if((flags[0].equals("--config") || flags[0].equals("-c") )&& flags.length > 1){
            config(flags[1]);
        }
        //config without a path
        else if((flags[0].equals("--config") || flags[0].equals("-c")) && flags.length == 1){
            help();
        }
        else if(flags[0].equals("--results") || flags[0].equals("-r")){
            resultPath = flags[1];
        }
        else if(Arrays.asList(flags).contains("--help") || Arrays.asList(flags).contains("-h")){
            help();
        }
        else if(flags[0].equals("--interactive") || flags[0].equals("-i")){
            //random
            if(flags.length == 1){
                interactiveMode("random");
            }
            //config file
            else if(flags.length >= 3){
                interactiveMode(flags[2]);
            }
        }
    }

    public void welcome() {
        File welcomeText = new File("welcome.ascii");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(welcomeText));
            String output = null;
            while((output = reader.readLine()) != null){
                System.out.println(output);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void interactiveMode(String filePath){
        Audit userAudit ;
        //randomly generate
        if(filePath.equals("random")){
            welcome();
            collectConsent();
            userAudit = new Audit();
            userAudit.setAuditType("User");
            userAudit.setInteractive(true);
            while(true){
                userAudit.run(3);
                userAudit.printStatistic();
                if(consent){
                    userAudit.printToFile("user.log");
                }
                if(!isContinue()){
                    System.exit(0);
                }
            }
        }
        //import data from config file
        else{
            try{
                File file = new File(filePath);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                Scenario[] scenarios = readData(reader);
                welcome();
                collectConsent();
                reader.close();
                userAudit = new Audit();
                userAudit.setAuditType("User");
                userAudit.setInteractive(true);
                for(int round = 0; round < scenarios.length; round = round + 3){
                    int loIndex = round;
                    int hiIndex = round + 2;
                    ArrayList<Scenario> input = new ArrayList<Scenario>();
                    for(;loIndex < scenarios.length && loIndex <= hiIndex ; loIndex ++){
                        input.add(scenarios[loIndex]);
                    }
                    userAudit.setScenarios(ArrayListToScenario(input));
                    userAudit.run();
                    userAudit.printStatistic();
                    if(consent){
                        userAudit.printToFile("user.log");
                    }
                    if(round + 3 >= scenarios.length){
                        System.out.println("That's all. Press Enter to quit.");
                        while(scanner.hasNextLine()){
                            System.exit(0);
                        }
                    }
                    if(!isContinue()){
                        System.exit(0);
                    }
                }
            }catch (FileNotFoundException e){
                System.out.println("ERROR: could not find config file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //distinguish whether user choose to continue
    public boolean isContinue(){
        System.out.println("Would you like to continue? (yes/no)");
        String answer = scanner.nextLine();
        if(answer.equals("yes")){
            return true;
        }
        else{
            return false;
        }
    }

    //collect message of whether user consent to record the result data
    public void collectConsent(){
        System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
        while(true){
            String answer = scanner.nextLine();
            try{
                //print and record
                if(answer.equals("yes")){
                    consent = true;
                    break;
                }
                //print but not record
                else if(answer.equals("no")){
                    consent = false;
                    break;
                }
                else{
                    throw new InvalidInputException();
                }
            }catch (InvalidInputException e) {
                System.out.println("Invalid response. Do you consent to have your decisions saved to a file? (yes/no)");
            }
        }
    }

    public static void main(String[] args){
        EthicalEngine engine = new EthicalEngine();
        while(true){
            if(args.length>0){
                engine.flagsOption(args);
            }
            else{
                engine.flagsOption(scanner.nextLine().split(" "));
            }
        }
    }
}
