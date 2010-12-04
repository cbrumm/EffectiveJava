import java.util.HashMap;
import java.util.Map;

public class HeteroMap {
  private Map<HeteroMapKey<?,?>, Object> elements = new HashMap<HeteroMapKey<?,?>, Object>();

  public <K,V> void put(HeteroMapKey<K,V> key, V value) {
    if (key == null) {
      throw new NullPointerException("Not allowed to pass a null key.");
    }
    elements.put(key, value);
  }

  public <V> V get(HeteroMapKey<?,? extends V> key) {
    return key.cast(elements.get(key));
  }

  public static void main(String[] args) {
    HeteroMap map = new HeteroMap();
    map.put(HeteroMapKey.from("answer", String.class), "FortyTwo");
    map.put(HeteroMapKey.from("answer", Integer.class), new Integer(42));
    map.put(HeteroMapKey.from("question", String.class), "What is the answer?");
    System.out.println(map.get(HeteroMapKey.from("question", String.class)));
    System.out.println(map.get(HeteroMapKey.from("answer", Integer.class)));
    // This will give a class cast exception.
    System.out.println(map.get(HeteroMapKey.from("answer", String.class)));
    // The following does not compile.
    // map.put(HeteroMapKey.from("answer", String.class), new Integer(42));
  }
}
