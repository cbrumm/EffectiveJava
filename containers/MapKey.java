import java.util.ArrayList;
import java.util.List;

/**
 * Key for a typesafe heterogeneous map, for example {@link HeteroMap}.
 *
 * A key is immutable to circumvent changing key crazyness. A key should be
 * equal to another key if and only if {@link MapKey#key} is equal. This
 * implies that it is not possible to put two instances of different classes
 * into the same container using an equal {@link MapKey#key}.
 *
 * @param <K> type of the key to be used
 * @param <V> type of the value this key is pointing to
 * @author cbrumm
 */
public final class MapKey<K, V> {
  private final K key;
  private final Class<V> valueType;

  private MapKey(K key, Class<V> valueType) {
    this.key = key;
    this.valueType = valueType;
  }

  public static <K, V> MapKey<K, V> from(K key, Class<V> valueType) {
    // We could cache instances here.
    return new MapKey<K, V>(key, valueType);
  }

  public V cast(Object value) {
    return valueType.cast(value);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof MapKey)) {
      return false;
    }
    MapKey<?,?> otherKey = (MapKey<?,?>) o;
    return key.equals(otherKey.key);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + key.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "(" + key + ", " + valueType + ")";
  }

  public static void main(String[] args) {
    List<MapKey<?, ?>> keys = new ArrayList<MapKey<?, ?>>();
    keys.add(MapKey.from("secret", Integer.class));
    keys.add(MapKey.from("dumdidum", String.class));
    keys.add(MapKey.from("secret", Double.class));
    for (MapKey<?, ?> cur : keys) {
      System.out.println(cur + " : " + cur.hashCode());
    }
  }
}