import java.util.ArrayList;
import java.util.List;

/**
 * Key for a typesafe heterogeneous map, for example {@link HeteroMap}.
 *
 * A key is immutable to circumvent changing key crazyness. A key should
 * be equal to another key if and only if {@link HeteroMapKey#key} is equal. This
 * implies that it is not possible to put two instances of different classes
 * into the same container using an equal {@link HeteroMapKey#key}.
 *
 * @param K type of the key to be used
 * @param V type of the value this key is pointing to
 * @author Christian Brumm
 */
public final class HeteroMapKey<K,V> {
    private final K key;
    private final Class<V> valueType;

    private HeteroMapKey(K key, Class<V> valueType) {
	this.key = key;
	this.valueType = valueType;
    }

    public static <K, V> HeteroMapKey<K,V> from(K key, Class<V> valueType) {
	// We could cache instances here.
	return new HeteroMapKey<K,V>(key, valueType);
    }

    public V cast(Object value) {
	return valueType.cast(value);
    }

    @Override public boolean equals(Object o) {
	if (o == this) {
	    return true;
	}
	if (!(o instanceof HeteroMapKey)) {
	    return false;
	}
	HeteroMapKey otherKey = (HeteroMapKey) o;
	return key.equals(otherKey.key);
    }

    @Override public int hashCode() {
	int result = 17;
	result = 31 * result + key.hashCode();
	return result;
    }

    @Override public String toString() {
	return "(" + key + ", " + valueType + ")";
    }

    public static void main(String[] args) {
	List<HeteroMapKey<?,?>> keys = new ArrayList<HeteroMapKey<?,?>>();
	keys.add(HeteroMapKey.from("secret", Integer.class));
	keys.add(HeteroMapKey.from("dumdidum", String.class));
	keys.add(HeteroMapKey.from("secret", Double.class));
	for (HeteroMapKey<?,?> cur : keys) {
	    System.out.println(cur + " : " + cur.hashCode());
	}
    }
}