import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class ParseJSONCommon {
    class TrollFromJSON {
        public int age;
        public String name;
        public int HP;
        public boolean hungry;
        public boolean ableToSpeak;
        public boolean isSit;
        public boolean isSad;
        public Type things = new TypeToken<ArrayDeque<Troll>>() {
        }.getType();
    }

    class ThingFromJSON {
        private Condition condition;
        private String name;
        private int weight;
    }
}