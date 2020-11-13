/**
 * @name : wenjie xu
 * @student number : 1039070
 * @username : wxxu1
 */
package ethicalengine;

import java.util.Random;

public class ScenarioGenerator {
    private Random random = new Random();
    private int minPassenger = 1;
    private int maxPassenger = 20;
    private int minPedestrian = 1;
    private int maxPedestrian = 20;

    public ScenarioGenerator(){
        long seed = random.nextLong();
        random.setSeed(seed);
    }

    public ScenarioGenerator(long seed){
        random.setSeed(seed);
    }

    //this constructor sets the seed as well as the minimum and maximum number for both passengers and pedestrians with predefined values
    public ScenarioGenerator(long seed, int passengerCountMinimum,
                                  int passengerCountMaximum,
                                  int pedestrianCountMinimum,
                                  int pedestrianCountMaximum){
        random.setSeed(seed);
        minPassenger = passengerCountMinimum;
        maxPassenger = passengerCountMaximum;
        minPedestrian = pedestrianCountMinimum;
        maxPedestrian = pedestrianCountMaximum;
    }

    public void setPassengerCountMin(int min){
        minPassenger = min;
    }

    public void setPassengerCountMax(int max){
        maxPassenger = max;
    }

    public void setPedestrianCountMin(int min){
        minPedestrian = min;
    }

    public void setPedestrianCountMax(int max){
        maxPedestrian = max;
    }

    public Person getRandomPerson(){
        int age = random.nextInt(100);
        Person.Profession profession = Person.Profession.values()[random.nextInt(6)];
        Person.Gender gender = Person.Gender.values()[random.nextInt(3)];
        Person.BodyType bodytype = Person.BodyType.values()[random.nextInt(4)];
        boolean isPregnant;
        if(gender == Person.Gender.FEMALE){
            isPregnant = random.nextBoolean();
        }
        else{
            isPregnant = false;
        }
        Person instancePerson = new Person(age, profession, gender, bodytype, isPregnant);
        return instancePerson;
    }

    public Animal getRandomAnimal(){
        int age = random.nextInt(100);
        Animal.Gender gender = Animal.Gender.values()[random.nextInt(3)];
        Animal.BodyType bodytype = Animal.BodyType.values()[random.nextInt(4)];
        boolean ispet = random.nextBoolean();
        //random species name, 10 char max
        String str="abcdefghijklmnopqrstuvwxyz";
        //name should at least have one char
        int speciesLength = random.nextInt(10) + 1;
        String species = "";
        for(int i=0;i<speciesLength;i++){
            int number=random.nextInt(26);
            species = species + str.charAt(number);
        }
        //set random instance
        Animal instanceAnimal = new Animal(species);
        instanceAnimal.setAge(age);
        instanceAnimal.setGender(gender);
        instanceAnimal.setBodyType(bodytype);
        if(ispet == true){
            instanceAnimal.setAsPet();
        }
        return instanceAnimal;
    }

    public Scenario generate(){
        int passengerNum;
        int pedestrianNum;
        passengerNum = random.nextInt(maxPassenger - minPassenger + 1) + minPassenger;
        pedestrianNum = random.nextInt(maxPedestrian - minPedestrian + 1) + minPedestrian;
        Character[] passengers = new Character[passengerNum];
        Character[] pedestrians = new Character[pedestrianNum];
        passengers[0] = getRandomPerson();//at least one person in passengers
        pedestrians[0] = getRandomPerson();//at least one person in pedestrians
        //get rest random passengers
        for(int round = 1; round < passengerNum; round ++){
            boolean isPerson = random.nextBoolean();
            if(isPerson){
                passengers[round] = getRandomPerson();
            }
            else{
                passengers[round] = getRandomAnimal();
            }
        }
        //get rest random pedestrians
        for(int round = 1; round < pedestrianNum; round ++){
            boolean isPerson = random.nextBoolean();
            if(isPerson){
                pedestrians[round] = getRandomPerson();
            }
            else{
                pedestrians[round] = getRandomAnimal();
            }
        }
        int youIndex = random.nextInt(passengerNum + pedestrianNum);
        //randomly select if you are passenger or pedestrian
        if(youIndex < passengerNum){
            //if character of this index is person then set as you
            passengers[youIndex].setAsYou(true);
        }
        else{
            pedestrians[random.nextInt(pedestrianNum)].setAsYou(true);
        }
        Scenario scenario = new Scenario(passengers, pedestrians, random.nextBoolean());
        return scenario;
    }
}
