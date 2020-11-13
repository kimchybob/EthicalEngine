/**
 * @name : wenjie xu
 * @student number : 1039070
 * @username : wxxu1
 */
/*
Scenario.java contains a list of passengers,
a list of pedestrians, as well as additional scenario conditions,
such as whether pedestrians are legally crossing at the traffic light.
 */

package ethicalengine;


import java.util.ArrayList;

public class Scenario {
    private Character[] passengers ;
    private Character[] pedestrians ;
    private boolean isLegalCrossing = true;

    //must be invoked by Person
    public Scenario(Character[] passengers, Character[] pedestrians, boolean isLegalCrossing){
        this.passengers = passengers;
        this.pedestrians = pedestrians;
        this.isLegalCrossing = isLegalCrossing;
    }

    public boolean hasYouInCar(){
        boolean flag = false;
        for(int round = 0;passengers[round] != null;round ++){
            if(passengers[round].isYou()){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public boolean hasYouInLane(){
        boolean flag = false;
        for(int round = 0;pedestrians[round] != null;round ++){
            if(pedestrians[round].isYou()){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public Character[] getPassengers(){
        return passengers;
    }

    public Person[] getHumanPassengers(){
        ArrayList<Person> temp = new ArrayList<Person>();
        int length = 0;
        while(length < passengers.length){
            if(passengers[length].getClass() == Person.class && passengers[length] != null){
                temp.add((Person) passengers[length]);
            }
            length ++;
        }
        Person[] output = new Person[temp.size()];
        for(int round = 0; round < temp.size(); round ++){
            output[round] = temp.get(round);
        }
        return output;
    }

    public Animal[] getPassengerAnimals(){
        ArrayList<Animal> temp = new ArrayList<Animal>();
        int length = 0;
        while(length < passengers.length){
            if(passengers[length].getClass() == Animal.class && passengers[length] != null){
                temp.add((Animal) passengers[length]);
            }
            length ++;
        }
        Animal[] output = new Animal[temp.size()];
        for(int round = 0; round < temp.size(); round ++){
            output[round] = temp.get(round);
        }
        return output;
    }

    public Character[] getPedestrians(){
        return pedestrians;
    }

    public Person[] getHumanPedestrians(){
        ArrayList<Person> temp = new ArrayList<Person>();
        int length = 0;
        while(length < pedestrians.length){
            if(pedestrians[length].getClass() == Person.class && pedestrians[length] != null){
                temp.add((Person) pedestrians[length]);
            }
            length ++;
        }
        Person[] output = new Person[temp.size()];
        for(int round = 0; round < temp.size(); round ++){
            output[round] = temp.get(round);
        }
        return output;
    }

    public Animal[] getPedestrianAnimals(){
        ArrayList<Animal> temp = new ArrayList<Animal>();
        int length = 0;
        while(length < pedestrians.length){
            if(pedestrians[length].getClass() == Animal.class && pedestrians[length] != null){
                temp.add((Animal) pedestrians[length]);
            }
            length ++;
        }
        Animal[] output = new Animal[temp.size()];
        for(int round = 0; round < temp.size(); round ++){
            output[round] = temp.get(round);
        }
        return output;
    }

    public boolean isLegalCrossing(){
        return isLegalCrossing;
    }

    public void setLegalCrossing(boolean isLegalCrossing){
        this.isLegalCrossing = isLegalCrossing;
    }

    //person and animal
    public int getPassengerCount(){
        return passengers.length;
    }

    //person and animal
    public int getPedestrianCount(){
        return pedestrians.length;
    }

    @Override
    public String toString(){
        String output = new String();
        output += "======================================" + "\n"
                + "# Scenario" + "\n"
                + "======================================" + "\n"
                + "Legal Crossing: ";
        if(isLegalCrossing()){
            output = output + "yes\n";
        }
        else{
            output = output + "no\n";
        }
        int passengerNum = getPassengerCount();
        output = output + "Passengers (" + String.valueOf(passengerNum) + ")\n";
        for(int round = 0;round < passengerNum; round ++){
            output += "-" + passengers[round] + "\n";
        }
        int pedestrianNum = getPedestrianCount();
        output = output + "Pedestrians (" + String.valueOf(pedestrianNum) + ")";
        for(int round = 0;round < pedestrianNum; round ++){
            output += "\n-" + pedestrians[round] ;
        }
        return output;
    }

    public void printScenario(){
        System.out.println(toString());
    }

//    @Override
//    public boolean isYou() {
//        return false;
//    }
//
//    @Override
//    public void setAsYou(boolean isYou) {
//
//    }
}
