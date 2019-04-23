public class Thing {
    private Condition condition;
    private String name;
    private int weight;

    public Thing(){
        name="Неизвестная вещь";
        condition=Condition.Solid;
    }
    public Thing(String name, Condition condition, int weight){
        this.name=name;
        this.condition=condition;
        this.weight=weight;
    }

    public Condition getCondition(){
        return condition;
    }
    public String getName(){
        return name;
    }
    public int getWeight(){return weight;}

    public String toString(){
        return "Thing "+name+". Condition is "+condition.toString()+". Weight = "+weight;
    }
}
