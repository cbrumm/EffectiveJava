import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of a heterogeneous Map as an example of a heterogeneous container.
 *
 * @author cbrumm
 */
public class HeteroMap {
  private Map<MapKey<?, ?>, Object> elements = new HashMap<MapKey<?, ?>, Object>();

  /**
   * Put an instance of type <V> keyed by an instance of type <K> into the container.
   */
  public <K, V> void put(MapKey<K, V> key, V value) {
    if (key == null) {
      throw new NullPointerException("Not allowed to pass a null key.");
    }
    elements.put(key, value);
  }

  /**
   * Retrieve a value from the map, indexed by the passed key.
   */
  public <V> V get(MapKey<?, ? extends V> key) {
    return key.cast(elements.get(key));
  }

  public static void main(String[] args) {
    HeteroMap map = new HeteroMap();
    map.put(MapKey.from("answer", String.class), "FortyTwo");
    map.put(MapKey.from("answer", Integer.class), new Integer(42));
    map.put(MapKey.from("question", String.class), "What is the answer?");
    System.out.println(map.get(MapKey.from("question", String.class)));
    System.out.println(map.get(MapKey.from("answer", Integer.class)));
    // This will give a class cast exception.
    System.out.println(map.get(MapKey.from("answer", String.class)));
    // The following does not compile.
    // map.put(HeteroMapKey.from("answer", String.class), new Integer(42));
  }
}
