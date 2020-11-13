/**
 * @name : wenjie xu
 * @student number : 1039070
 * @username : wxxu1
 */
package ethicalengine;

//all character types inherit
public abstract class Character {
    private int age;
    private Gender gender;
    private BodyType bodyType;

    public enum Gender{
        UNKNOWN, MALE, FEMALE;

        public static boolean contains(String type) {
            for (Gender gender : Gender.values()) {
                if (gender.name().equals(type)) {
                    return true;
                }
            }
            return false;
        }
    }
    public enum BodyType{
        UNSPECIFIED, AVERAGE, ATHLETIC, OVERWEIGHT;

        public static boolean contains(String type) {
            for (BodyType bodyType : BodyType.values()) {
                if (bodyType.name().equals(type)) {
                    return true;
                }
            }
            return false;
        }
    }


    public Character(){
        gender = Gender.UNKNOWN;
        bodyType = BodyType.UNSPECIFIED;
    }

    public Character(int age,Gender gender,BodyType bodyType){
        this.age = age;
        this.gender = gender;
        this.bodyType = bodyType;
    }

    public Character(Character c){
        this.age = c.age;
        this.gender = c.gender;
        this.bodyType = c.bodyType;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender(){
        return gender;
    }

    public BodyType getBodyType(){
        return bodyType;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setGender(Gender gender){
        this.gender = gender;
    }

    public void setBodyType(BodyType bodyType){
        this.bodyType = bodyType;
    }

    public abstract boolean isYou();

    public abstract void setAsYou(boolean isYou);
}
