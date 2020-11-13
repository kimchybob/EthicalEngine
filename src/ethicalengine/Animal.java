/**
 * @name : wenjie xu
 * @student number : 1039070
 * @username : wxxu1
 */
package ethicalengine;

/*
People walk their pets so make sure your program accounts for these,
at least for: cats and dogs.
 */
public class Animal extends Character {
    private String species;
    private boolean isPet = false;

    public Animal(){}

    public Animal (String species){
        this.species = species;
    }

    public Animal(Animal otherAnimal){
        this.species = otherAnimal.species;
        this.isPet = otherAnimal.isPet;
    }

    public static Animal produceAnimal(String[] input){
        Animal newAnimal;
        newAnimal = new Animal(input[7]);
        newAnimal.setGender(Gender.valueOf(input[1].toUpperCase()));
        newAnimal.setAge(Integer.parseInt(input[2]));
        //can animals get pregnant?
        if(input[8].equals("true")){
            newAnimal.setAsPet();
        }
        return newAnimal;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species){
        this.species = species;
    }

    public void setAsPet(){
        isPet = true;
    }

    public void setPet(boolean isPet){
        this.isPet = isPet;
    }

    public boolean isPet(){
        return isPet;
    }

    @Override
    public String toString(){
        String output = new String();
        output = output + " " + getSpecies();
        if(isPet()){
            output += " is pet";
        }
        return output;
    }

    @Override
    public boolean isYou() {
        return false;
    }

    @Override
    public void setAsYou(boolean isYou){}
}
