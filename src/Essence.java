public abstract class Essence implements Speakable {
    private int age;
    private String name;
    private int HP;
    private boolean hungry;
    private boolean ableToSpeak;

    public Essence() {
        age = 0;
        name = "NoName";
        HP = 100;
        hungry = false;
        ableToSpeak = true;
        System.out.println("Создан персонаж(этот персонаж ещё не открыт)");
    }

    public Essence(int age, String name) {
        this.age = age;
        this.name = name;
        HP = 100;
        hungry = false;
        ableToSpeak = true;
    }

    public Essence(int age, String name, int HP) {
        this.age = age;
        this.name = name;
        this.HP = HP;
        if (HP != 100) {
            hungry = true;
        } else {
            hungry = false;
        }
        ableToSpeak = true;
    }

    public Essence(StringBuilder s) {
        name = new String();
        parsCSV(s);
        if (HP != 100) {
            hungry = true;
        } else {
            hungry = false;
        }
        ableToSpeak = true;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void happybirthday() {
        age++;
        System.out.println(name + ", с Днём рождения! Персонажу исполнилось " + age);
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int hp) {
        HP = hp;
    }

    public boolean getHungry() {
        return hungry;
    }

    public void setHungry(boolean b) {
        hungry = b;
    }

    public void printHungry(boolean hung) {
        if (hung) {
            System.out.println(getName() + " хочет есть");
        } else {
            System.out.println(getName() + " не хочет есть");
        }
    }

    public boolean getAbleToSpeak() {
        return ableToSpeak;
    }

    public void setAbleToSpeak(Boolean b) {
        ableToSpeak = b;
    }

    public void parsCSV(StringBuilder s) {
        int k = 0;
        String pAge = "";
        String pHP = "";
        while (s.charAt(k) != ',') {
            name += s.charAt(k);
            s.deleteCharAt(0);
        }
        s.deleteCharAt(0);
        while (s.charAt(k) != ',') {
            pAge += s.charAt(k);
            s.deleteCharAt(0);
        }
        s.deleteCharAt(0);
        age = Integer.parseInt(pAge);
        while (s.charAt(k) != ',') {
            pHP += s.charAt(k);
            s.deleteCharAt(0);
        }
        HP = Integer.parseInt(pHP);
        s.deleteCharAt(0);
        if (s.charAt(0) == '0') hungry = false;
        else hungry = true;
        s.delete(0, 2);
        if (s.charAt(0) == '0') ableToSpeak = false;
        else ableToSpeak = true;
        s.delete(0, 2);
    }
}
