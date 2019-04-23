import java.util.ArrayDeque;
import java.util.Deque;

public class CollectionCase {
    private Deque<Troll> collection;

    public CollectionCase() {
        collection = new ArrayDeque<Troll>();
    }

    public Deque<Troll> getCollection() {
        return collection;
    }
}
