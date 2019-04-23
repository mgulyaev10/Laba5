import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Troll extends Essence implements SkillsMove, SkillsState, SkillsMood, Speakable, Comparable<Troll> {
    private boolean isSit;
    private boolean isSad;
    private List<Thing> thingsInHands = new ArrayList<>();

    //constructors
    public Troll() {
        super();
        isSit = true;
        isSad = true;
    }

    public Troll(int age, String name) {
        super(age, name);
        isSit = true;
        isSad = true;
    }

    public Troll(int age, String name, int HP) {
        super(age, name, HP);
        isSit = true;
        isSad = true;
    }

    public Troll(JSONObject json) {
        super(json.getInt("age"), json.getString("name"), json.getInt("HP"));
        isSit = json.getBoolean("isSit");
        isSad = json.getBoolean("isSad");
        JSONArray jsonArray = json.getJSONArray("things");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Thing t = new Thing(object.getString("name"), Condition.valueOf(object.getString("condition")),
                    object.getInt("weight"));
            thingsInHands.add(t);
        }
    }
    //end constructors


    //implements Comparable<Troll>
    @Override
    public int compareTo(Troll o) {
        if (this.hashCode() == o.hashCode()) {
            return 0;
        } else if (this.thingsInHands.size() > o.thingsInHands.size()) {
            return 1;
        } else if (this.thingsInHands.size() < o.thingsInHands.size()) {
            return -1;
        } else if (this.sumWeightOfThings() > o.sumWeightOfThings()) {
            return 1;
        } else if (this.sumWeightOfThings() < o.sumWeightOfThings()) {
            return -1;
        } else return 0;
    }
    //end Comparable<Troll>

    //implements SkillsState Interface
    public void sit() {
        isSit = true;
        System.out.println(getName() + " сел");
    }

    public void standUp() {
        isSit = false;
        System.out.println(getName() + " встал");
    }
    //end SkillsState

    //implements SkillsMove Interface
    public void hug(Troll t) {
        System.out.println(getName() + " прижался к " + t.getName());
    }

    public void eat(Sandwich s) {
        System.out.println(getName() + " съел сендвич. Здоровье пополнено на " + Integer.toString(s.getCalorie()));
        if (getHP() + s.getCalorie() > 100) {
            setHP(100);
        } else {
            setHP(getHP() + s.getCalorie());
        }
        if (getHP() > 75) {
            setHungry(false);
        } else {
            setHungry(true);
        }
    }
    //end SkillsMove

    //implements SkillsMood Interface
    public void sad() {
        isSad = true;
        System.out.println(getName() + " плачет");
    }

    public void happy() {
        isSad = false;
        System.out.println(getName() + " радуется");
    }

    //end SkillsMood
    //implements Speakable
    public void giveSpeakPower() {
        this.setAbleToSpeak(true);
    }

    public void takeAwaySpeakPower() {
        this.setAbleToSpeak(false);
    }

    public void saySome(String speech) throws EssenceSpeakException {
        if (this.getAbleToSpeak()) {
            System.out.println(this.getName() + " говорит: \"" + speech + "\"");
        } else {
            throw new EssenceSpeakException(this);
        }
    }
    //end Speakable

    //Own Methods
    public void addThing(Thing t) {
        thingsInHands.add(t);
        System.out.println("Предмет " + t.getName() + " был дан троллю " + this.getName());
    }

    public List<Thing> getThings() {
        return thingsInHands;
    }

    public void getMood() {
        if (isSad) {
            System.out.println(getName() + " плачет");
        } else {
            System.out.println(getName() + " радуется");
        }
    }

    public void getState() {
        if (isSit) {
            System.out.println(getName() + " сидит");
        } else {
            System.out.println(getName() + " стоит");
        }
    }

    public int sumWeightOfThings() {
        int w = 0;
        for (int k = 0; k < thingsInHands.size(); k++) {
            w += thingsInHands.get(k).getWeight();
        }
        return w;
    }
    //end own Methods

    //override

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Troll temp = (Troll) o;
        if (temp.isSit != this.isSit)
            return false;
        if (temp.isSad != this.isSad)
            return false;
        if (temp.getAge() != this.getAge())
            return false;
        if (!temp.getName().equals(this.getName()))
            return false;
        if (temp.getHP() != this.getHP())
            return false;
        if (temp.getHungry() != this.getHungry())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int rnd = 31;
        int result = 1;
        result = (rnd * result + getAge()) * rnd + getHP();
        if (isSit) {
            result = result * rnd + 9;
        } else {
            result = result * rnd + 3;
        }
        if (isSad) {
            result = result * rnd + 2;
        } else {
            result = result * rnd + 22;
        }
        if (getHungry()) {
            result = result * rnd + 3;
        } else {
            result = result * rnd + 33;
        }
        result = result * rnd + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Troll " + getName() + ". Возраст: " + Integer.toString(getAge()) + ". HP: " + Integer.toString(getHP())
                + ". IsHungry: " + Boolean.toString(getHungry()) +
                ". IsSit: " + Boolean.toString(isSit) + ". IsSad: " + Boolean.toString(isSad) + ". Things: "
                + thingsInHands;
    }

    public JSONObject getJSON() {
        JSONObject object = new JSONObject();
        object.put("age", this.getAge());
        object.put("name", this.getName());
        object.put("HP", this.getHP());
        object.put("isSit", isSit);
        object.put("isSad", isSad);
        object.put("things", thingsInHands);

        return object;
    }

}
