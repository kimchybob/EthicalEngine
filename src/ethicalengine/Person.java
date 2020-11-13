/**
 * @name : wenjie xu
 * @student number : 1039070
 * @username : wxxu1
 */
package ethicalengine;

/*
each person is either considered to be a passenger or a pedestrian.
 */
public class Person extends Character {
    private Profession profession;
    private boolean isPregnant;
    private boolean isYou = false;

    public enum Profession{
        UNKNOWN, DOCTOR, CEO, CRIMINAL, HOMELESS, UNEMPLOYED, NONE;
    }
    public enum AgeCategory{
        BABY, CHILD, ADULT, SENIOR;
    }

    public static boolean notNone(Profession profession){
        if(profession == Profession.NONE){
            return false;
        }
        else{
            return true;
        }
    }

    public Person(){}

    public Person(int age, Profession profession, Gender gender, BodyType bodytype){
        setAge(age);
        this.profession = profession;
        setGender(gender);
        setBodyType(bodytype);
    }

    public Person(int age, Gender gender, BodyType bodytype){
        setAge(age);
        setGender(gender);
        setBodyType(bodytype);
    }

    public Person(int age, Profession profession, Gender gender, BodyType bodytype, boolean isPregnant){
        setAge(age);
        this.profession = profession;
        setGender(gender);
        setBodyType(bodytype);
        setPregnant(isPregnant);
    }

    public Person(Person otherPerson){
        setAge(otherPerson.getAge());
        this.profession = otherPerson.profession;
        setGender(otherPerson.getGender());
        setBodyType(otherPerson.getBodyType());
        this.isPregnant = otherPerson.isPregnant;
        this.isYou = otherPerson.isYou;
    }

    public AgeCategory getAgeCategory(){
        if(0 <= getAge() && getAge() <= 4){
            return AgeCategory.BABY;
        }
        else if(5 <= getAge() && getAge() <= 16){
            return AgeCategory.CHILD;
        }
        else if(17 <= getAge() && getAge() <= 68){
            return AgeCategory.ADULT;
        }
        else{
            return AgeCategory.SENIOR;
        }
    }

    public Profession getProfession(){
        if(getAgeCategory() == AgeCategory.ADULT){
            return profession;
        }
        else{
            return Profession.NONE;
        }
    }


    public boolean isPregnant(){
        if(getGender() != Gender.FEMALE){
            return false;
        }
        else{
            return isPregnant;
        }
    }

    public void setPregnant(boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    @Override
    public boolean isYou(){
        return isYou;
    }

    public void setAsYou(boolean isYou){
        this.isYou = isYou;
    }

    @Override
    public String toString(){
        String output = new String();
        if(isYou){
            output += " you";
        }
        output = output + " " + String.valueOf(getBodyType()).toLowerCase()
                + " " + String.valueOf(getAgeCategory()).toLowerCase();
        if(getAgeCategory() == AgeCategory.ADULT){
            output += " " + String.valueOf(getProfession()).toLowerCase();
        }
        output += " " + String.valueOf(getGender()).toLowerCase();
        if(getGender() == Gender.FEMALE && isPregnant()){
            output += " pregnant";
        }
        return output;
    }

    public static Person producePerson(String[] input){
        int age = Integer.parseInt(input[2]);
        Profession profession1;
        if(input[4].isEmpty()){
            profession1 = Profession.NONE;
        }
        else{
            profession1 = Profession.valueOf(input[4].toUpperCase());
        }
        Gender gender1 = Gender.valueOf(input[1].toUpperCase());
        BodyType bodyType1 = BodyType.valueOf(input[3].toUpperCase());
        Person newPerson;
        if(input[5].equals("true")){
            newPerson = new Person(age,profession1,gender1,bodyType1,true);
        }
        else {
            newPerson = new Person(age,profession1,gender1,bodyType1,false);
        }
        if(input[6].equals("true")){
            newPerson.setAsYou(true);
        }
        return newPerson;
    }
}
